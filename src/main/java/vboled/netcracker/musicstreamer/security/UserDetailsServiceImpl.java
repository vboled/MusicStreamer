package vboled.netcracker.musicstreamer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vboled.netcracker.musicstreamer.model.User;
import vboled.netcracker.musicstreamer.repository.UserRepository;

/*
    Пока писал комменты - подумал над тем как сделать аутентификацию по телефону и Email
    Пока пришла в голову такая идея:
    в методе loadUserByUsername проверять является ли полученная строка
    именем пользователя, Email или номером телефона. И в зависимости от этого вызывать
    подходящий метод у userRepository
 */

// Моя реализация UserDetailsService с кастомным поиском пользователей
@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exist"));
        return SecurityUser.fromUser(user);
    }
}
