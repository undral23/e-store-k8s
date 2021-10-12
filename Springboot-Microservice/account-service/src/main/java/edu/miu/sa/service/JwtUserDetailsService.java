package edu.miu.sa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.sa.entity.User;
import edu.miu.sa.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}

	@Transactional
	public void updateUser(User user) {
		User detail = userRepository.findById(user.getId()).get();
		if (detail != null) {
			detail.setPreferredMethod(user.getPreferredMethod());
			detail.setPaymentMethods(user.getPaymentMethods());
			userRepository.save(detail);
		}
	}
}