package com.example.security;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Task;
import com.example.entity.User;
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

	@RequestMapping("/tasks")
	public ResponseEntity<Set<Task>> listAllTasks(Principal principal)
	{
		User user = userRepository.findByUserName(principal.getName());
		if(user!=null)
		{
			return new ResponseEntity<Set<Task>>(user.getTasks(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Set<Task>>(HttpStatus.NOT_FOUND);
		}
		
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
	
	@RequestMapping(value="/tasks/update", method=RequestMethod.PUT)
	public ResponseEntity<Task> updateTask(@RequestBody Task task,Principal principal )
	{
		Optional<Task> option = taskRepo.findById(task.getId());
		if(option.isPresent())
		{
			Task taskToUpdate = option.get();
			taskToUpdate.setLastUpdated( task.getLastUpdated() );
			taskToUpdate.setTaskName( task.getTaskName() );
			taskToUpdate.setDescription( task.getDescription() );
			taskToUpdate.setChecked( task.isChecked() );
			return new ResponseEntity<Task>(taskRepo.save( taskToUpdate ), HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value="/tasks/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id )
	{
		Optional<Task> option = taskRepo.findById(id);
		if(option.isPresent())
		{
			taskRepo.deleteById( id );
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		else 
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
	}

	@Override
	public void configure( HttpSecurity http ) throws Exception
	{
		http.csrf().disable().authorizeRequests().antMatchers( "/oauth/token", "/oauth/authorize**" ).permitAll();
		http.requestMatchers().antMatchers( "/tasks/**" ).and().authorizeRequests().antMatchers( "/tasks/**" ).access( "hasRole('USER')" );
	}
}
