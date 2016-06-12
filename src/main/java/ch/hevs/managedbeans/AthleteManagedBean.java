package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.marathonservice.AthleteInterface;

public class AthleteManagedBean {

	//Variables
	// person
	private String lastname;
	private String firstname;
	// athelete
	private String sex;
	private String club;
	// address
	private String street;
	private int number;
	private int postalCode;
	private String city;
	// contact
	private String phoneNumber;
	private String mail;

	private String athleteName;

	private List<Athlete> listAthletes;
	private Athlete myAthlete;
	private List<String> listSex;


	private AthleteInterface athlete;

	@PostConstruct
	public void initialize() throws NamingException {

		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		athlete = (AthleteInterface) ctx.lookup("java:global/marathon-0.0.1-SNAPSHOT/AthleteBean!ch.hevs.marathonservice.AthleteInterface");

		listAthletes = athlete.showAllAthlete();
		sex = "Masculin";
		myAthlete = new Athlete();	
		listSex = new ArrayList<String>();
		listSex.add("Masculin");
		listSex.add("Féminin");
	}

	// Display ALL Athletes
	public void displayAllAthletes()
	{
		listAthletes = athlete.showAllAthlete();
	}

	// Show a specific athlete
	public String showSpecificAthlete(long ath_id)
	{
		try
		{
			myAthlete = athlete.showSpecificAthlete(ath_id);
			return "ok";
		}
		catch(Exception e)
		{
			return "stop";
		}
	}

	// 	Add Athlete	
	public String addAthlete()
	{
		Athlete ath = new Athlete(lastname, firstname, sex, club);
		Address address = new Address(street, number, postalCode, city);
		ath.setAddress(address);
		Contact contact = new Contact(phoneNumber, mail);
		ath.setContact(contact);

		athlete.addAthlete(ath);
		listAthletes = athlete.showAllAthlete();

		lastname = "";
		firstname = "";
		sex = "";
		club = "";
		street = "";
		number = 0;
		postalCode = 0;
		city = "";
		phoneNumber = "";
		mail = "";

		return "ok";		
	}
	
	// Modify Athlete	
	public String modifyAthlete()
	{	
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
		lastname = ec.getRequestParameterMap().get("formModifyAth:lastname");
		firstname = ec.getRequestParameterMap().get("formModifyAth:firstname");
		//sex = ec.getRequestParameterMap().get("formModifyAth:sex");
		club = ec.getRequestParameterMap().get("formModifyAth:club");
		phoneNumber  = ec.getRequestParameterMap().get("formModifyAth:phoneNumber");
		mail = ec.getRequestParameterMap().get("formModifyAth:mail");
		street = ec.getRequestParameterMap().get("formModifyAth:street");
		number = Integer.parseInt(ec.getRequestParameterMap().get("formModifyAth:number"));
		postalCode = Integer.parseInt(ec.getRequestParameterMap().get("formModifyAth:postalCode"));
		city = ec.getRequestParameterMap().get("formModifyAth:city");
	
		athlete.modifyAthlete(myAthlete.getId(), lastname, firstname, sex, club, phoneNumber, mail,
								street, number, postalCode, city);
		
		listAthletes = athlete.showAllAthlete();
		
		
		lastname = "";
		firstname = "";
		sex = "";
		club = "";
		street = "";
		number = 0;
		postalCode = 0;
		city = "";
		phoneNumber = "";
		mail = "";
		
		return "ok";
	}

	// Delete Athlete
	public void deleteAthlete(long id_ath)
	{
		athlete.deleteAthlete(id_ath);
		listAthletes = athlete.showAllAthlete();
	}

	//Getters & setters

	public String getLastname() {
		return lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(final String sex) {
		this.sex = sex;
	}

	public String getClub() {
		return club;
	}

	public void setClub(final String club) {
		this.club = club;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(final String mail) {
		this.mail = mail;
	}

	public String getAthleteName() {
		return athleteName;
	}

	public void setAthleteName(String athleteName) {
		this.athleteName = athleteName;
	}

	public List<Athlete> getListAthletes() {
		return listAthletes;
	}

	public void setListAthletes(List<Athlete> listAthletes) {
		this.listAthletes = listAthletes;
	}

	public AthleteInterface getAthlete() {
		return athlete;
	}

	public void setAthlete(AthleteInterface athlete) {
		this.athlete = athlete;
	}


	public Athlete getMyAthlete() {
		return myAthlete;
	}


	public void setMyAthlete(Athlete myAthlete) {
		this.myAthlete = myAthlete;
	}

	public List<String> getListSex() {
		return listSex;
	}

	public void setListSex(List<String> listSex) {
		this.listSex = listSex;
	}

}
