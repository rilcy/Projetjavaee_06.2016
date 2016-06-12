package ch.hevs.businessobject;

import javax.persistence.Embeddable;


@Embeddable
public class Contact {
	
	// Variables
	private String phoneNumber;
	private String mail;
	
	// Constructor
	public Contact(){
	}
	
	public Contact(String phoneNumber, String mail){
		this.phoneNumber = phoneNumber;
		this.mail = mail;
	}

	// Getters & setters
	// phoneNumber
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	// mail
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}	
}
