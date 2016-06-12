package ch.hevs.marathonservice;

import java.util.List;

import javax.ejb.Local;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Manager;
import ch.hevs.exception.MarathonException;
import ch.hevs.managedbeans.ManagerManagedBean;

@Local
public interface ManagerInterface {
	
	List<Manager> showAllManager();
	
	public void addManager(Manager man);
	
	public void deleteManager(long man_id);

	public Manager showSpecificManager(long man_id) throws MarathonException ;
	
	public void modifyManager(Long man_id, String lastname, String firstname, String phoneNumber, String mail, String function);
}
