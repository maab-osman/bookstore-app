package fi.haagahelia.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.haagahelia.bookstore.model.AppUser;
import fi.haagahelia.bookstore.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userRepository.findByUsername(username);

    if (user == null) {
        throw new UsernameNotFoundException("User not found: " + username);
    }
    

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        authorities
    );
}
}