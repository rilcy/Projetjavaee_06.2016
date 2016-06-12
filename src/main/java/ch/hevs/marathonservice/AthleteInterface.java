package ch.hevs.marathonservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;

@Local
public interface AthleteInterface {
	
	List<Athlete> showAllAthlete();
	
	public Athlete showSpecificAthlete(long id_ath);
	
	public void addAthlete(Athlete ath);
	
	public void deleteAthlete(long id_ath);
	
	public void deleteAthleteOfEvent(long id_athlete, long id_event);
	
	public List<Athlete> showAthleteOfEvent(long id_event);

	public void modifyAthlete(Long id, String lastname, String firstname, String sex, 
				String club, String phoneNumber, String mail, String street, 
				int number, int postalCode, String city);

	

}
