package ch.hevs.businessobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Event")
public class Event implements Serializable {
	
	// Variables	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String eventName;
	private int year;
	
	//Relations
	@Embedded
	private Contact contact;
	@Embedded
	private Address address;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Athlete> athletes;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Manager> managers;
	@OneToMany(mappedBy="event", cascade = CascadeType.ALL)
	private Set<Time> times;
	
	// Constructors
	public Event(){
	}
	
	public Event(String eventName, int year){
		athletes = new HashSet<Athlete>();
		managers = new HashSet<Manager>();
		times = new HashSet<Time>();
		this.eventName = eventName;
		this.year = year;
	}	
	
	// Getters & setters
	
	// id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	// event name
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	// year
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	// contact
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	// address
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	// athletes
	public Set<Athlete> getAthletes() {
		return athletes;
	}
	public void setAthletes(Set<Athlete> athletes) {
		this.athletes = athletes;
	}
	
	// managers
	public Set<Manager> getManagers() {
		return managers;
	}
	public void setManagers(Set<Manager> managers) {
		this.managers = managers;
	}
	
	// times
	public Set<Time> getTimes() {
		return times;
	}
	public void setTimes(Set<Time> times) {
		this.times = times;
	}
	
	// Helper methods
	public void addAthlete(Athlete athlete) {
		athletes.add(athlete);
	}
	public void addManager(Manager manager) {
		managers.add(manager);
	}
	public void addTime(Time time) {
		times.add(time);
	}		
}
