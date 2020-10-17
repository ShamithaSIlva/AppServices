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
import com.example.entity.Task;
import com.example.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
class TasksRepositoryTest
{

	@Autowired
    private TasksRepository taskrepo;
	@Autowired
    private UserRepository userRepository;
    
    @Test
    public void whenFindingTaskById_thenCorrect() {
    	User user = userRepository.save(new User("userone", "passone"));
    	taskrepo.save( new Task("17/10/2020","Task 1","Description one",user) );
        assertThat(taskrepo.findById(1L)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void whenFindingAllUsers_thenCorrect() {
    	User user = userRepository.save(new User("userone", "passone"));
        taskrepo.save(new Task("17/10/2020","Task 1","Description one",user));
        taskrepo.save(new Task("17/10/2020","Task 2","Description one",user));
        assertThat(taskrepo.findAll()).isInstanceOf(List.class);
    }
    
    @Test
    public void whenSavingUser_thenCorrect() {
    	User user = userRepository.save(new User("userone", "passone"));
        Task saved = taskrepo.save(new Task("17/10/2020","Task 2","Description one",user));
        User userRet = userRepository.findById(1L).orElseGet(() 
          -> new User("userfour", "passfour"));
        assertThat(saved.getUser().getUsername()).isEqualTo(userRet.getUsername());
    }

}
