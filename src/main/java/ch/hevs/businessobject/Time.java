package ch.hevs.businessobject;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Time")
public class Time implements Serializable {
	
	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String time;
	
	// constructor
	public Time(){
	}
	
	public Time(String time){
		this.time = time;
	}
	
	//Relations
	@ManyToOne(cascade = CascadeType.ALL)
	private Event event;

	
	//Geters & setters
	// id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	// time
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	// event
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}	
}
