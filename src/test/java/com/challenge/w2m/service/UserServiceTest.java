package com.challenge.w2m.service;

import com.challenge.w2m.repository.IUserRepository;
import com.challenge.w2m.entity.User;
import com.challenge.w2m.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private UserDetailsService userService;

    @Mock
    private IUserRepository repository = Mockito.mock(IUserRepository.class);



    @BeforeEach
    public void setup() {
        Mockito.clearInvocations(repository);
        userService = new UserService(repository);
    }

    @Test
    public void when_user_exits_return_user() {
        Mockito.when(repository.findByUsername("existsUser"))
                .thenReturn(Optional.of(new User("user01", "pass01")));

        var user = userService.loadUserByUsername("existsUser");

        assertEquals("user01", user.getUsername());
    }

    @Test
    public void when_user_not_exits_throw_error() {
        Mockito.when(repository.findByUsername("notExistsUser"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("notExitsUser"));
    }
}
