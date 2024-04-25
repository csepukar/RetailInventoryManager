package com.capstoneproject.userservice.service;

import com.capstoneproject.userservice.model.User;
import com.capstoneproject.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            System.out.println("ROLE===="+user.getRoles().iterator().next().getName().toString());
            //builder.roles(user.getRoles().iterator().next().getName().toString(),"ADMIN");
            builder.roles(user.getRoles().iterator().next().getName().toString());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
}
