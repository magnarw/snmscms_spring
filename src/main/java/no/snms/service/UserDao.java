package no.snms.service;


import no.snms.dao.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDao implements  UserDetailsService {

	User findByName(String name) {
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		
		return User.createDummyUser();
	}

}
