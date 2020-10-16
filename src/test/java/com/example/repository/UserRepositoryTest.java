package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DemoApplication;
import com.example.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository UserRepository;
    
    @Test
    public void whenFindingUserById_thenCorrect() {
        UserRepository.save(new User("userone", "passone"));
        assertThat(UserRepository.findById(1L)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void whenFindingAllUsers_thenCorrect() {
        UserRepository.save(new User("userone", "passone"));
        UserRepository.save(new User("usertwo", "passtwo"));
        assertThat(UserRepository.findAll()).isInstanceOf(List.class);
    }
}
