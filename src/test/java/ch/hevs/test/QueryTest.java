package ch.hevs.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.eclipse.persistence.annotations.Properties;
import org.junit.Test;

import ch.hevs.businessobject.Address;
import ch.hevs.businessobject.Athlete;
import ch.hevs.businessobject.Contact;
import ch.hevs.businessobject.Event;
import ch.hevs.businessobject.Manager;
import ch.hevs.businessobject.Time;

public class QueryTest {

	@PersistenceContext(unitName="test", type = PersistenceContextType.EXTENDED)
	EntityManager em;
	@Test
	public void test() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String cmd;
			while (true) {
				System.out
						.println("Write a query [or 'populate' or 'quit']: ");
				cmd = reader.readLine();

				if ("populate".equals(cmd)) {
					populate();
				} else if ("quit".equals(cmd)) {
					System.out.println("The End");
					break;
				} else {
					executeRequest(cmd);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void executeRequest(String cmd) {
		List result = null;
		EntityTransaction tx = null;
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
			
			EntityManager em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			result = em.createQuery(cmd).getResultList();
			Iterator it = result.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
			tx.commit();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			try {
				tx.rollback();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} 
		}
	}

	public static void populate() {
		EntityTransaction tx = null;
		try {
			
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("test");
			EntityManager em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();			
			
			
			// Athlete 1
			Athlete ath1 = new Athlete("Zufferey", "Cyril", "Masculin", "CC Sierre");
			Address add1 = new Address("Rue de la Grange", 1, 3966, "Vercorin");
			ath1.setAddress(add1);
			Contact cont1 = new Contact("078 845 88 99", "czuff@netplus.ch");
			ath1.setContact(cont1);

			
			// Athlete 2
			Athlete ath2 = new Athlete("van Emelen", "Xavier", "Masculin", "CC Bruxelles");
			Address add2 = new Address("Rue de l'Industrie", 34, 3960, "Sierre");
			ath2.setAddress(add2);
			Contact cont2 = new Contact("079 564 33 82", "xav.vane@yoplay.be");
			ath2.setContact(cont2);

			
			// Athlete 3
			Athlete ath3 = new Athlete("Ariane", "Jeanbourquin", "Féminin", "Sion Course");
			Address add3 = new Address("Route de Chippis", 88, 1950, "Sion");
			ath3.setAddress(add3);
			Contact cont3 = new Contact("027 302 34 56", "a.jeanbourquin@bluemail.ch");
			ath3.setContact(cont3);
			
			// Manager 1
			Manager man1 = new Manager("Delapierre", "Jacky", "Organisateur");
			Contact cont4 = new Contact("076 452 23 67", "jacky.delapierre@athletissima.ch");
			man1.setContact(cont4);
			
			// Manager 2
			Manager man2 = new Manager("Pont", "Jean-Claude", "Directeur de course");
			Contact cont5 = new Contact("076 452 23 67", "jcp@sierre-zinal.ch");
			man2.setContact(cont5);
			
			// Event 1
			Event event1 = new Event("Athletissima", 2016);
			Contact cont6 = new Contact("024 293 21 35", "info@athletissima.ch");
			event1.setContact(cont6);
			Address add4 = new Address("Case Postale", 56, 1041, "Poliez-le-Grand");
			event1.setAddress(add4);
			event1.addManager(man1);
			event1.addAthlete(ath1);
			event1.addAthlete(ath2);

			
				// Time for Event1
			Time time1 = new Time("01:26:34");
			Time time2 = new Time("01:27:34");
			time1.setEvent(event1);
			ath1.addTime(time1);
			time2.setEvent(event1);
			ath2.addTime(time2);
			
			
			// Event 2
			Event event2 = new Event("Sierre-Zinal", 2016);
			Contact cont7 = new Contact("027 456 12 25", "info@sierre-zinal.ch");
			event2.setContact(cont7);
			Address add5 = new Address("Case Postale", 65, 3961, "Vissoie");
			event2.setAddress(add5);
			event2.addManager(man2);
			event2.addAthlete(ath1);
			event2.addAthlete(ath2);
			event2.addAthlete(ath3);
			
			em.persist(ath1);
			em.persist(ath2);
			em.persist(ath3);
			em.persist(man1);
			em.persist(man2);
			em.persist(event1);
			em.persist(time1);
			em.persist(time2);
			em.persist(event2);
			
			
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
