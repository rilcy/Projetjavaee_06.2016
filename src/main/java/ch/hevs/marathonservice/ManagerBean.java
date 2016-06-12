package ch.hevs.marathonservice;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Manager;
import ch.hevs.exception.MarathonException;
import ch.hevs.managedbeans.ManagerManagedBean;

@Stateless
public class ManagerBean implements ManagerInterface {

	@PersistenceContext(name="marathPU")
	private EntityManager em;
	
	@Override
	public List<Manager> showAllManager() {
		List<Manager> listAllManager = (List<Manager>) em.createQuery("SELECT man FROM Manager man").getResultList();
		return listAllManager;
	}
	
	@Override
	public Manager showSpecificManager(long man_id) {
		
		Query query = em.createQuery("Select man FROM Manager man WHERE man.id=:id");
		query.setParameter("id", man_id);
		
		Manager manager = (Manager) query.getSingleResult();
		
		return manager;
	}

	@Override
	public void addManager(Manager man) throws MarathonException  {		
		em.persist(man);
	}

	@Override
	public void deleteManager(long id_manager) {
		Query query = em.createQuery("SELECT man FROM Manager man WHERE man.id=:id");
		query.setParameter("id", id_manager);
		
		Manager man = (Manager) query.getSingleResult();
		
		em.remove(man);
	}

	@Override
	public void modifyManager(Long man_id, String lastname, String firstname, String phoneNumber, String mail,
			String function) {
		
		Query query = em.createQuery("SELECT man FROM Manager man WHERE man.id=:id");
		query.setParameter("id", man_id);
		Manager man = (Manager) query.getSingleResult();
		
		man.setFirstname(firstname);
		man.setLastname(lastname);
		man.setFunction(function);
		Contact contact = new Contact(phoneNumber, mail);
		man.setContact(contact);
		
		em.merge(man);
	}
}
