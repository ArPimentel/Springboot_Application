package com.arturPimentelApp.applicationspring.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arturPimentelApp.applicationspring.User;
import com.arturPimentelApp.applicationspring.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;

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
			user = repository.save(user);
		}
		return user;
	}

	@Override 
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("L'utilisateur à editer il n'est existe pas "));
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
		to.setPassword(from.getPassword());	
	}
}
