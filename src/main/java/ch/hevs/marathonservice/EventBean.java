package ch.hevs.marathonservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.AthlTimeEvent;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Event;
import ch.hevs.businessobject.Manager;
import ch.hevs.businessobject.Time;
import ch.hevs.exception.MarathonException;

@Stateless
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class EventBean implements EventInterface {


	@PersistenceContext(name="marathPU")
	private EntityManager em;
	
	
	@Override
	public List<Event> showAllEvents() {
		List<Event> listAllEvent = (List<Event>) em.createQuery("SELECT eve FROM Event eve").getResultList();
		return listAllEvent;
	}
	
	@Override
	public void addEvent(Event e) throws MarathonException  {
		em.persist(e);
	}

	@Override
	public void deleteEvent(long id_event) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public Event showSpecificEvent(long id_eve) {
		
		Query query = em.createQuery("SELECT eve FROM Event eve WHERE eve.id=:id");
		query.setParameter("id", id_eve);
		
		Event event = (Event) query.getSingleResult();
		
		return event;
	}

	public List<AthlTimeEvent> showEventAthletesWithTime(long id_eve) {

		//Query query = em.createQuery("SELECT ath FROM Event e, IN(e.athletes) ath where e.id=:id and IN(ath.times) t where t.events.id=e.id");
		Query query = em.createQuery("SELECT ath FROM Event e, IN(e.athletes) ath where e.id=:id");
		query.setParameter("id", id_eve);
		
		List<Athlete> listAllAthletesEvent = (List<Athlete>) query.getResultList();
		
		//liste AthTimeEvent de retour vers EvenManagedBean
		List<AthlTimeEvent> listAthWithTime = new ArrayList<AthlTimeEvent>();
				
		//méthode permettant de trier les résultats d'une athlètes pour un évènement.
		for (int i = 0; i < listAllAthletesEvent.size(); i++){
			
			//System.out.println("!!!!!!!!class: "+ listAllAthletesEvent.get(i).getTimes());
			
			// on boucle sur tous les athlètes
			// création d'une liste avec les temps d'un athlètes.
			List<Time> times = (List<Time>) listAllAthletesEvent.get(i).getTimes();
			
			for(int j = 0; j<times.size(); j++){
				
				// on boucle sur tous les athlètes pour trouver les temps de celui-ci dans cette compétition
				if(times.get(j).getEvent().getId() == id_eve)
					
					//ajout des infos relatives à cette athlètes qui a un temps pour cette compétition
					listAthWithTime.add(new AthlTimeEvent(listAllAthletesEvent.get(i).getId(),	id_eve, times.get(j).getId(),
							listAllAthletesEvent.get(i).getLastname(), listAllAthletesEvent.get(i).getFirstname(), times.get(j).getTime()));
			}
		}		
		return listAthWithTime;
	}
	

	@Override
	public List<Manager> showEventManager(long id_eve) {
		
		Query query = em.createQuery("SELECT man FROM Event e, IN(e.managers) man where e.id=:id");
		query.setParameter("id", id_eve);
		
		List<Manager> listAllManager = (List<Manager>) query.getResultList();
		
		return listAllManager;
	}

	@Override
	public List<Manager> showAllManagers() {
		Query query = em.createQuery("SELECT man FROM Manager man");
		List<Manager> listAllManagers = (List<Manager>) query.getResultList();
		return listAllManagers;
	}
	
	@Override
	public List<Athlete> showAllAthletes() {
		Query query = em.createQuery("SELECT ath FROM Athlete ath");
		List<Athlete> listAllAthletes = (List<Athlete>) query.getResultList();
		return listAllAthletes;
	}

	@Override
	public void addManagerToEvent(long id_eve, long id_man) {
		Query query = em.createQuery("SELECT e FROM Event e where e.id=:id");
		query.setParameter("id", id_eve);
		Event event = (Event) query.getSingleResult();
		
		query = em.createQuery("SELECT man FROM Manager man where man.id=:id");
		query.setParameter("id", id_man);
		Manager manager = (Manager) query.getSingleResult();
		
		event.addManager(manager);
		
		em.merge(event);		
	}

	@Override
	public void addAthleteToEvent(long id_eve, long id_ath, String time) {
		Query query = em.createQuery("SELECT e FROM Event e where e.id=:id");
		query.setParameter("id", id_eve);
		Event event = (Event) query.getSingleResult();
		
		query = em.createQuery("SELECT ath FROM Athlete ath where ath.id=:id");
		query.setParameter("id", id_ath);
		Athlete ath = (Athlete) query.getSingleResult();
		
		Time athTime = new Time(time);
		athTime.setEvent(event);
		ath.addTime(athTime);		
		
		event.addAthlete(ath);
		
		em.merge(event);	
	}

	@Override
	public void modifyEvent(long id_eve, String eventName, int year, String phoneNumber, String mail, String street,
			int number, int postalCode, String city) {
		
		Query query = em.createQuery("SELECT e FROM Event e where e.id=:id");
		query.setParameter("id", id_eve);
		Event event = (Event) query.getSingleResult();
		event.setEventName(eventName);
		event.setYear(year);
		Contact contact = new Contact(phoneNumber, mail);
		event.setContact(contact);
		Address address = new Address(street, number, postalCode, city);
		event.setAddress(address);
		em.merge(event);		
	}

	@Override
	public void deleteTime(long time_id) {
		Query query = em.createQuery("SELECT t FROM Time t where t.id=:id");
		query.setParameter("id", time_id);
		Time time = (Time) query.getSingleResult();

		em.remove(time);

	}
}
