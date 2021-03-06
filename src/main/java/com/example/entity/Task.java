package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tasks")
public class Task
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
    private User user;

	private String lastUpdated;
	
	private String taskName;

	private String description;
	
	private boolean checked;

	public Task()
	{
	}

	public Task( String lastUpdated, String taskName,String description,User user,boolean checked )
	{
		super();
		this.lastUpdated = lastUpdated;
		this.taskName = taskName;
		this.description = description;
		this.checked = checked;
		this.user = user;
	}

	public String getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated( String lastUpdated )
	{
		this.lastUpdated = lastUpdated;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser( User user )
	{
		this.user = user;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName( String taskName )
	{
		this.taskName = taskName;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked( boolean checked )
	{
		this.checked = checked;
	}

	public long getId()
	{
		return id;
	}		
}
