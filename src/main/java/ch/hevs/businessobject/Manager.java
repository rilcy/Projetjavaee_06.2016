package ch.hevs.businessobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Manager extends Person implements Serializable {
	
	// Variables
	private String function;
	
	//Relations
	@ManyToMany(mappedBy="managers", cascade = CascadeType.ALL)
	private Set<Event> events;
	
	//Constructuor
	public Manager(){
		super();
	}
	
	public Manager(String lastname, String firstname, String function){
		super(lastname, firstname);
		this.function = function;
		events = new HashSet<Event>();
	}
	
	//Getters & setters
	//function
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	// Helper methods
	public void addEvent(Event event) {
		events.add(event);
	}
	
}
