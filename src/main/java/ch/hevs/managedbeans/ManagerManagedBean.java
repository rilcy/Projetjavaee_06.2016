package ch.hevs.managedbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Manager;
import ch.hevs.marathonservice.ManagerInterface;

public class ManagerManagedBean {


	//Variables
	// person
	private String lastname;
	private String firstname;
	// manager
	private String function;
	// contact
	private String phoneNumber;
	private String mail;

	private String managerName;

	private List<Manager> listManagers;
	private Manager myManager;
	private List<String> athName;
	private String sexChoice;


	private ManagerInterface manager;

	@PostConstruct
	public void initialize() throws NamingException
	{

		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		manager = (ManagerInterface) ctx.lookup("java:global/marathon-0.0.1-SNAPSHOT/ManagerBean!ch.hevs.marathonservice.ManagerInterface");

		listManagers = manager.showAllManager();
		myManager = new Manager();		
	}

	// Display ALL Managers
	public void displayAllManagers()
	{
		listManagers = manager.showAllManager();
	}


	// Show a specific manager
	public String showSpecificManager(long man_id)
	{
		try
		{
			myManager = manager.showSpecificManager(man_id);
			return "ok";
		}
		catch(Exception e)
		{
			return "stop";
		}
	}

	// 	Add Manager	
	public String addManager()
	{		 
		Manager man = new Manager(lastname, firstname, function);
		Contact contact = new Contact(phoneNumber, mail);
		man.setContact(contact);

		manager.addManager(man);

		listManagers = manager.showAllManager();


		lastname = "";
		firstname = "";
		function = "";
		phoneNumber = "";
		mail = "";	
		
		return "ok";
	}

	// Delete Manager
	public void deleteManager(long id_man)
	{
		manager.deleteManager(id_man);
		listManagers = manager.showAllManager();
	}

	// Modify Manager	
	public String modifyManager()
	{
		try{
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			lastname = ec.getRequestParameterMap().get("formModifyManager:lastname");
			firstname = ec.getRequestParameterMap().get("formModifyManager:firstname");
			phoneNumber  = ec.getRequestParameterMap().get("formModifyManager:phoneNumber");
			mail = ec.getRequestParameterMap().get("formModifyManager:mail");
			function = ec.getRequestParameterMap().get("formModifyManager:function");
			
			manager.modifyManager(myManager.getId(), lastname, firstname, phoneNumber, mail, function);
			
			listManagers = manager.showAllManager();

			lastname = "";
			firstname = "";
			function = "";
			phoneNumber = "";
			mail = "";

			return "ok";
		}
		catch(Exception e)
		{
			return "stop";
		}
	}


	//GETTERS & SETTERS

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public List<Manager> getListManagers() {
		return listManagers;
	}

	public void setListManagers(List<Manager> listManagers) {
		this.listManagers = listManagers;
	}

	public Manager getMyManager() {
		return myManager;
	}

	public void setMyManager(Manager myManager) {
		this.myManager = myManager;
	}

	public List<String> getAthName() {
		return athName;
	}

	public void setAthName(List<String> athName) {
		this.athName = athName;
	}

	public String getSexChoice() {
		return sexChoice;
	}

	public void setSexChoice(String sexChoice) {
		this.sexChoice = sexChoice;
	}

	public ManagerInterface getManager() {
		return manager;
	}

	public void setManager(ManagerInterface manager) {
		this.manager = manager;
	}


}
