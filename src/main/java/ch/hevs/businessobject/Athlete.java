package ch.hevs.businessobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Athlete extends Person implements Serializable {
	
	//Variables
	private String sex;
	private String club;
	
	//Relationss
	@Embedded
	private Address address;
	@ManyToMany(mappedBy="athletes", cascade = CascadeType.ALL)
	private Set<Event> events;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "athlete_fk")
	private List<Time> times;
	
	//Constructuor
	public Athlete(){
		super();
	}
	
	public Athlete(String lastname, String firstname, String sex, String club){
		super(lastname, firstname);
		this.sex = sex;
		this.club = club;		
		events = new HashSet<Event>();
		times = new ArrayList<Time>();
	}
	
	//Getters & setters
	//sex
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	//club
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	
	//address	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	//events	
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	//times
		public List<Time> getTimes() {
		return times;
	}
	public void setTimes(List<Time> times) {
		this.times = times;
	}
	
	// Helper methods
	public void addEvent(Event event) {
		events.add(event);
	}
	public void addTime(Time time) {
		times.add(time);
	}
	
}
