package com.pk.cards.cards.service;

import com.pk.cards.cards.model.Role;
import com.pk.cards.cards.model.User;
import com.pk.cards.cards.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByEmail(username));
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = userOptional.get();

        // Get the roles or permissions assigned to the user
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = Collections.singletonList(user.getRole());
        for (int i = 0; i < roles.size(); i++) {
            authorities.add(new SimpleGrantedAuthority(roles.get(i).name()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),authorities
        );
    }
}


