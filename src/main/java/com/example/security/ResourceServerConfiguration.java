package com.example.security;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Task;
import com.example.entity.User;
import com.example.error.UserNotFoundException;
import com.example.repository.TasksRepository;
import com.example.repository.UserRepository;

@EnableResourceServer
@RestController
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TasksRepository taskRepo;

	@RequestMapping("/tasks/{id}")
	public Set<Task> listAllTasks(@PathVariable Long id)
	{
		User user = userRepository.findById(id)
			      .orElseThrow(() -> new UserNotFoundException(id));
		return user.getTasks();
	}
	
	@RequestMapping(value="/tasks/add", method=RequestMethod.POST)
	public ResponseEntity<Task> addTask(@RequestBody Task task,Principal principal )
	{
		User user = userRepository.findByUserName(principal.getName());
		if(user != null)
		{
			task.setUser( user );
			return new ResponseEntity<Task>(taskRepo.save( task ), HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}
		
	}

	@Override
	public void configure( HttpSecurity http ) throws Exception
	{
		http.authorizeRequests().antMatchers( "/oauth/token", "/oauth/authorize**" ).permitAll();
		http.requestMatchers().antMatchers( "/tasks/**" ).and().authorizeRequests().antMatchers( "/tasks/**" ).access( "hasRole('USER')" );
	}
}
