package ru.otus.highload.homework.first.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.highload.homework.first.dto.AuthorizeDto;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetail implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AuthorizeDto authorizeDto = userService.getUserByLogin(login);
        if (authorizeDto == null) {
            throw new UsernameNotFoundException("User not exists by login");
        }
        Set<GrantedAuthority> set = new HashSet<>();

        return new org.springframework.security.core.userdetails.User(login, authorizeDto.password(), set);
    }
}
