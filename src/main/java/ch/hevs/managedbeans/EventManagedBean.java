package ch.hevs.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.AthlTimeEvent;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Event;
import ch.hevs.businessobject.Manager;
import ch.hevs.marathonservice.AthleteInterface;
import ch.hevs.marathonservice.EventInterface;
import ch.hevs.marathonservice.ManagerInterface;

public class EventManagedBean {
	
	//Variables
	// event
	private String eventname;
	private int year;
	// address
	private String street;
	private int number;
	private int postalCode;
	private String city;
	// contact
	private String phoneNumber;
	private String mail;
	
	private String eventName;
		
	private List<Event> listEvents;
	private List<AthlTimeEvent> listAthletes;
	private List<Manager> listManagers;
	
	private Event myEvent;
	private List<String> eveName;
	private long myIdEvent;
	// pour la DropDownList des managers
	private List<Manager> listManForDropDownList;
	private List<String> managerDescriptions;
	private String managerDescription;
	// pour la DropDownList des athlètes.
	private List<Athlete> listAthForDropDownList;
	private List<String> athleteDescriptions;
	private String athleteDescription;
	private String time;
	
	private EventInterface event;
	
	@PostConstruct
	public void initialize() throws NamingException {
    	
    	// use JNDI to inject reference to bank EJB
    	InitialContext ctx = new InitialContext();
		event = (EventInterface) ctx.lookup("java:global/marathon-0.0.1-SNAPSHOT/EventBean!ch.hevs.marathonservice.EventInterface");
		
		listEvents = event.showAllEvents();
		myEvent = new Event();
		listManForDropDownList = new ArrayList<Manager>();
		listAthForDropDownList = new ArrayList<Athlete>();
		
	}
	
	// Display ALL Events
	public void displayAllEvents()
	{
		listEvents = event.showAllEvents();
	}
	
	// 	Add Event	
	public String addEvent()
	{
		Event eve = new Event(eventName, year);
		Address address = new Address(street, number, postalCode, city);
		eve.setAddress(address);
		Contact contact = new Contact(phoneNumber, mail);
		eve.setContact(contact);
		
		event.addEvent(eve);
		
		listEvents = event.showAllEvents();
		
		eventName = "";
		year = 0;
		street = "";
		number = 0;
		postalCode = 0;
		city = "";
		phoneNumber = "";
		mail = "";
		
		return "ok";		
	}
	
// 	Modify Event	
	public String modifyEvent()
	{
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
		eventName = ec.getRequestParameterMap().get("formModifyEvent:name");
		year = Integer.parseInt(ec.getRequestParameterMap().get("formModifyEvent:year"));
		phoneNumber  = ec.getRequestParameterMap().get("formModifyEvent:phoneNumber");
		mail = ec.getRequestParameterMap().get("formModifyEvent:mail");
		street = ec.getRequestParameterMap().get("formModifyEvent:street");
		number = Integer.parseInt(ec.getRequestParameterMap().get("formModifyEvent:number"));
		postalCode = Integer.parseInt(ec.getRequestParameterMap().get("formModifyEvent:postalCode"));
		city = ec.getRequestParameterMap().get("formModifyEvent:city");
		
		event.modifyEvent(myIdEvent, eventName, year, phoneNumber, mail, street, number, postalCode, city);
		
		listEvents = event.showAllEvents();
		
		
		eventName = "";
		year = 0;
		phoneNumber = "";
		mail = "";
		street = "";
		number = 0;
		postalCode = 0;
		city = "";
		
		return "ok";
	}
	
	// Show a specific event
	public String showSpecificEvent(long eve_id)
	{
		try
		{
			myEvent = event.showSpecificEvent(eve_id);
			myIdEvent = myEvent.getId();
			showAlthetesForEvent();
			showManagersForEvent();
			showAllManagers();
			showAllAthletes();
			
			updateDropDownListManager();
			updateDropDownListAthletes();
			
			return "ok";
		}
		catch(Exception e)
		{
			return "stop";
		}
	}
	
	// DropDownList Manager
	private void updateDropDownListManager() {
		List<Manager> listTravail = listManForDropDownList;
		for(int i = 0; i< listManagers.size(); i++){
			int j = 0;
			while(j<listManForDropDownList.size() && listManagers.get(i).getId() != listTravail.get(j).getId()){
				j = j + 1;
			}
			if(listTravail.get(j).getId() == listManagers.get(i).getId())
				listTravail.remove(j);
		}
		listManForDropDownList = null;
		listManForDropDownList = listTravail;
		managerDescriptions = new ArrayList<String>();
		// dropdownlist manager ajoute tous les managers
		for(int i = 0; i< listManForDropDownList.size(); i++){
			managerDescriptions.add(listManForDropDownList.get(i).getId() + " | " + listManForDropDownList.get(i).getFirstname() + " " + listManForDropDownList.get(i).getLastname());	
		}		
	}
	// DropDownList Manager
	private void showAllManagers() {
		listManForDropDownList = null;
		listManForDropDownList = event.showAllManagers();
	}
	// DropDownList Manager
	public String addManagerToEvent(){
		try{
			long manId;
			String stManId;
			// S'il y a un seul manager disponible et que l'on utilise la méthode, on prend l'id de celui-ci.
			if(managerDescription.length() < 1){
				stManId = managerDescriptions.get(0);
				stManId = stManId.substring(0, stManId.indexOf(" "));
				manId = Long.parseLong(stManId);
			}
			// S'il y a plusieurs on prend l'ID de ceui qui a été sélectionné
			else{
				stManId = managerDescription.substring(0, managerDescription.indexOf(" "));
				manId = Long.parseLong(stManId);
			}
			// S'il n'y a personne à ajouter dans la liste.
			if(managerDescription == null)
				return "stop";
			
			event.addManagerToEvent(myIdEvent, manId);
			listManagers = event.showEventManager(myIdEvent);
			showAllManagers();
			updateDropDownListManager();		
		return "ok";
		}
		catch(Exception e)
		{
			return "stop";
		}
	}
	
	// DropDownList Athletes
	private void updateDropDownListAthletes() {
		List<Athlete> listTravail = listAthForDropDownList;
		for(int i = 0; i< listAthletes.size(); i++){
			int j = 0;
			while(j<listAthForDropDownList.size() && listAthletes.get(i).getIdAth() != listTravail.get(j).getId()){
				j = j + 1;
			}
			if(listTravail.get(j).getId() == listAthletes.get(i).getIdAth())
				listTravail.remove(j);
		}
		listAthForDropDownList = null;
		listAthForDropDownList = listTravail;
		athleteDescriptions = new ArrayList<String>();
		// dropdownlist athletes ajoute tous les athletes
		for(int i = 0; i< listAthForDropDownList.size(); i++){
			athleteDescriptions.add(listAthForDropDownList.get(i).getId() + " | " + listAthForDropDownList.get(i).getFirstname() + " " + listAthForDropDownList.get(i).getLastname());	
		}		
	}
	// DropDownList Athletes
	private void showAllAthletes() {
		listAthForDropDownList = null;
		listAthForDropDownList = event.showAllAthletes();
	}
	
	// DropDownList Athletes
	public String addAthleteTimeToEvent(){
		try{
			long athId;
			String stAthId;
			// S'il y a un seul athlete disponible et que l'on utilise la méthode, on prend l'id de celui-ci.
			if(athleteDescription.length() < 1){
				stAthId = athleteDescriptions.get(0);
				stAthId = stAthId.substring(0, stAthId.indexOf(" "));
				athId = Long.parseLong(stAthId);
			}
			// S'il y a plusieurs on prend l'ID de ceui qui a été sélectionné
			else{
				stAthId = athleteDescription.substring(0, athleteDescription.indexOf(" "));
				athId = Long.parseLong(stAthId);
			}
			// S'il n'y a personne à ajouter dans la liste.
			if(athleteDescription == null)
				return "stop";
			

			
			event.addAthleteToEvent(myIdEvent, athId, time);
			listAthletes = event.showEventAthletesWithTime(myIdEvent);
			showAllAthletes();
			updateDropDownListAthletes();		
		return "ok";
		}
		catch(Exception e)
		{
			return "stop";
		}
	}
	
	public void deleteTime(long time_id){
		event.deleteTime(time_id);
	}
	
	public void showManagersForEvent(){
		listManagers = event.showEventManager(myIdEvent);
	}
	
	public void showAlthetesForEvent(){
		listAthletes = event.showEventAthletesWithTime(myIdEvent);
	}
	
	// Delete event
	public String deleteEvent(long eve_id)
	{
		return "test";
	}
	
	// Getters & setters

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public List<Event> getListEvents() {
		return listEvents;
	}

	public void setListEvents(List<Event> listEvents) {
		this.listEvents = listEvents;
	}

	public Event getMyEvent() {
		return myEvent;
	}

	public void setMyEvent(Event myEvent) {
		this.myEvent = myEvent;
	}

	public List<String> getEveName() {
		return eveName;
	}

	public void setEveName(List<String> eveName) {
		this.eveName = eveName;
	}

	public EventInterface getEvent() {
		return event;
	}

	public void setEvent(EventInterface event) {
		this.event = event;
	}

	public List<AthlTimeEvent> getListAthletes() {
		return listAthletes;
	}

	public void setListAthletes(List<AthlTimeEvent> listAthletes) {
		this.listAthletes = listAthletes;
	}

	public long getMyIdEvent() {
		return myIdEvent;
	}

	public void setMyIdEvent(long myIdEvent) {
		this.myIdEvent = myIdEvent;
	}

	public List<Manager> getListManagers() {
		return listManagers;
	}

	public void setListManagers(List<Manager> listManagers) {
		this.listManagers = listManagers;
	}

	public List<String> getManagerDescriptions() {
		return managerDescriptions;
	}

	public void setManagerDescriptions(List<String> managerDescriptions) {
		this.managerDescriptions = managerDescriptions;
	}

	public String getManagerDescription() {
		return managerDescription;
	}

	public void setManagerDescription(String managerDescription) {
		this.managerDescription = managerDescription;
	}

	public List<Manager> getListManForDropDownList() {
		return listManForDropDownList;
	}

	public void setListManForDropDownList(List<Manager> listManForDropDownList) {
		this.listManForDropDownList = listManForDropDownList;
	}

	public List<Athlete> getListAthForDropDownList() {
		return listAthForDropDownList;
	}

	public void setListAthForDropDownList(List<Athlete> listAthForDropDownList) {
		this.listAthForDropDownList = listAthForDropDownList;
	}

	public List<String> getAthleteDescriptions() {
		return athleteDescriptions;
	}

	public void setAthleteDescriptions(List<String> athleteDescriptions) {
		this.athleteDescriptions = athleteDescriptions;
	}

	public String getAthleteDescription() {
		return athleteDescription;
	}

	public void setAthleteDescription(String athleteDescription) {
		this.athleteDescription = athleteDescription;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
