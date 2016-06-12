package ch.hevs.marathonservice;


import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.exception.MarathonException;

@Stateless
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class AthleteBean implements AthleteInterface {


	@PersistenceContext(name="marathPU")
	private EntityManager em;
	
	@Override
	public List<Athlete> showAllAthlete() {
		List<Athlete> listAllAthlete = (List<Athlete>) em.createQuery("SELECT ath FROM Athlete ath").getResultList();
		return listAllAthlete;
	}
	
	@Override
	public Athlete showSpecificAthlete(long id_ath) {
		
		Query query = em.createQuery("Select ath FROM Athlete ath WHERE ath.id=:id");
		query.setParameter("id", id_ath);
		
		Athlete athlete = (Athlete) query.getSingleResult();
		
		return athlete;
	}

	@Override
	public void addAthlete(Athlete ath) throws MarathonException  {		
		em.persist(ath);
	}
	
	@Override
	public void deleteAthlete(long id_ath) {
		Query query = em.createQuery("SELECT ath FROM Athlete ath WHERE ath.id=:id");
		query.setParameter("id", id_ath);
		
		Athlete ath = (Athlete) query.getSingleResult();
		
		em.remove(ath);

	}

	@Override
	public void deleteAthleteOfEvent(long id_athlete, long id_event) {
		//TODO Terminer méthode
	}

	@Override
	public List<Athlete> showAthleteOfEvent(long id_event) {
		//TODO Terminer méthode
		return null;
	}

	@Override
	public void modifyAthlete(Long id_ath, String lastname, String firstname, String sex, String club, String phoneNumber,
			String mail, String street, int number, int postalCode, String city) {
		
		Query query = em.createQuery("SELECT ath FROM Athlete ath WHERE ath.id=:id");
		query.setParameter("id", id_ath);
		Athlete ath = (Athlete) query.getSingleResult();
		ath.setLastname(lastname);
		ath.setFirstname(firstname);
		ath.setClub(club);
		ath.setSex(sex);
		Contact contact = new Contact(phoneNumber, mail);
		ath.setContact(contact);
		Address address = new Address(street, number, postalCode, city);
		ath.setAddress(address);
		em.merge(ath);
	}

}
