package com.arturPimentelApp.applicationspring.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arturPimentelApp.applicationspring.Roles;
import com.arturPimentelApp.applicationspring.repository.UserRepository;



@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.arturPimentelApp.applicationspring.User appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("La connexion d'utilisateur n'est pas valide."));

		Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();
		for (Roles roles: appUser.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roles.getDescription());
			grantList.add(grantedAuthority);
		}
		UserDetails user = (UserDetails) new User(username, appUser.getPassword(), grantList );
		return user;
	}
}