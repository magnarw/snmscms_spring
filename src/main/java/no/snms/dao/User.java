package no.snms.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{

	
	private String name;

	private String password;

	private Set<String> roles = new HashSet<String>();
	
	
	public Set<String> getRoles() {

		return this.roles;
	}


	public void setRoles(Set<String> roles) {

		this.roles = roles;
	}


	public void addRole(String role) {

		this.roles.add(role);
	}


	@Override
	public String getPassword() {

		return this.password;
	}


	public void setPassword(String password) {

		this.password = password;
	}
	
	
	public void setUsername(String username) {

		this.name = username;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<String> roles = this.getRoles();

		if (roles == null) {
			return Collections.emptyList();
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}


	@Override
	public String getUsername() {

		return this.name;
	}


	@Override
	public boolean isAccountNonExpired() {

		return true;
	}


	@Override
	public boolean isAccountNonLocked() {

		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}


	@Override
	public boolean isEnabled() {

		return true;
	}
	
	
	public static User createDummyUser() {
		User user = new User();
		user.setPassword("admin123");
		user.setUsername("admin");
		user.addRole("admin");
		return user;
		
	}

}
