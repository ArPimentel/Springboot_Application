package com.arturPimentelApp.applicationspring.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.arturPimentelApp.applicationspring.User;
import com.arturPimentelApp.applicationspring.dto.ChangePassword;
import com.arturPimentelApp.applicationspring.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override 
	public Iterable <User> getAllUsers() {
		return repository.findAll();
	}

	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			throw new Exception("Pseudo pas disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty() ) {
			throw new Exception("La confirmation de mort de pass est obligatoire");	

		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Le mot de pass ne correspond pas");	
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if(checkUsernameAvailable(user) && checkPasswordValid(user)) {
			
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			
			user = repository.save(user);
		}
		return user;
	}

	@Override 
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("L'utilisateur n'est existe pas "));
	}

	@Override 
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return repository.save(toUser); 
	}

	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());	
	}
	
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws Exception{
		User user = getUserById(id);
		repository.delete(user);
	}

	@Override
	public User changePassword(ChangePassword form) throws Exception {
		User user =getUserById(form.getId());

		if (!isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception ("Le mot de passe actuel n'est pas valide..");
		}
		if (user.getPassword().equals(form.getNewPassword())) {
			throw new Exception ("Le nouveau mot de passe doit être différent de celui actuel.");
		}
		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Le nouveau mot de passe et confirmation ne correspondent pas.");
		}
		
		String encodePassword = bCryptPasswordEncoder.encode(form.getConfirmPassword());
		user.setPassword(encodePassword);
		return repository.save(user);
	}
	
	public boolean isLoggedUserADMIN() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			loggedUser.getAuthorities().stream()
					.filter(x -> "ADMIN".equals(x.getAuthority() ))      
					.findFirst().orElse(null); //loggedUser = null;
		}
		return loggedUser != null ?true :false;
	}
}
