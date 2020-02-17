package com.charity.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.charity.entities.User;
import com.charity.entities.UserRole;

@Service("currentUserDetailsService")
public class CurrentUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {

			User user = userService.findByUserNameIgnoreCase(username);
			if (user == null) {
				throw new UsernameNotFoundException("User not found");
			}

			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
					user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(),
					user.isAccountNonLocked(), getAuthorities(user));
		} catch (Exception e) {
			throw e;
		}
	}

	public Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (UserRole userRole : user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.getRole().getName());
			authorities.add(grantedAuthority);
		}
		return authorities;
	}

}
