package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.readUser(username);
        user.setAuthorities(getAuthorities(username));

        System.out.println("loadUserByUsername");

        return user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<GrantedAuthority> authorities = userMapper.readAuthorities(username);
        return authorities;
    }

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public void createAuthorities(User user) {
        userMapper.createAuthority(user);
    }

    @Override
    public User readUser(String username) {
        return userMapper.readUser(username);
    }



}
