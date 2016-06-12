package ch.hevs.marathonservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.AthlTimeEvent;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Event;
import ch.hevs.businessobject.Manager;
import ch.hevs.businessobject.Time;

@Local
public interface EventInterface {
	
	
	List<Event> showAllEvents();
	
	public void addEvent(Event e);
	
	public void deleteEvent(long id_eve);

	public Event showSpecificEvent(long id_eve);

	public List<AthlTimeEvent> showEventAthletesWithTime(long id_eve);

	public List<Manager> showEventManager(long id_eve);

	public List<Manager> showAllManagers();

	public void addManagerToEvent(long id_eve, long manId);

	public List<Athlete> showAllAthletes();

	public void addAthleteToEvent(long id_eve, long athId, String time);

	public void modifyEvent(long id_eve, String eventName, int year, 
							String phoneNumber, String mail, String street,
							int number, int postalCode, String city);

	public void deleteTime(long time_id);
}
