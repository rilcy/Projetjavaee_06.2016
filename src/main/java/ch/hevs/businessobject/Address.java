package ch.hevs.businessobject;

import javax.persistence.Embeddable;


@Embeddable
public class Address {

	//Variables
	private String street;
	private int number;
	private int postalCode;
	private String city;

	// constructors
	public Address() {
	}

	public Address(String street, int number, int postalCode, String city) {
		this.postalCode = postalCode;
		this.number = number;
		this.street = street;
		this.city = city;
	}

	// Setters & getters
	// street
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	//number
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	//postal code
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	//city
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
