package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    	User user = userRepository.save(new User("usertwo", "passone"));
    	taskrepo.save( new Task("17/10/2020","Task 1","Description one",user,false) );
        assertThat(taskrepo.findById(1L)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void whenFindingAllTasks_thenCorrect() {
    	User user = userRepository.save(new User("userthree", "passthree"));
        taskrepo.save(new Task("17/10/2020","Task 1","Description one",user,true));
        taskrepo.save(new Task("17/10/2020","Task 2","Description one",user,true));
        assertThat(userRepository.findById( user.getId() ).get().getTasks()).isInstanceOf(Set.class);
    }
    
    @Test
    public void whenSavingTask_thenCorrect() {
    	User user = userRepository.save(new User("userone", "passone"));
        Task saved = taskrepo.save(new Task("17/10/2020","Task 2","Description one",user,true));
        User userRet = userRepository.findById(1L).orElseGet(() 
          -> new User("userfive", "passfive"));
        assertThat(saved.getUser().getUsername()).isEqualTo(userRet.getUsername());
    }

}
