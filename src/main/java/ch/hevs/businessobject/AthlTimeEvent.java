package ch.hevs.businessobject;

/*
 * Classe permettant d'afficher le temps d'un athlète lors d'une compétition sur la page specificEvent.xhtml
 */

public class AthlTimeEvent {
	
	private long idAth;
	private long idEvent;
	private long idTime;
	private String athLastname;
	private String athFirstname;
	private String athTime;
	
	// constructeurs
	public AthlTimeEvent(){
		
	}
	public AthlTimeEvent(long idAth, long idEvent, long idTime, String athLastname,	String athFirstname, String athTime){
		
		this.idAth = idAth;
		this.idEvent = idEvent;
		this.idTime = idTime;
		this.athFirstname = athFirstname;
		this.athLastname = athLastname;
		this.athTime = athTime;
		
	}
	
	// getters & setters
	
	public long getIdAth() {
		return idAth;
	}
	public void setIdAth(long idAth) {
		this.idAth = idAth;
	}
	public long getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(long idEvent) {
		this.idEvent = idEvent;
	}
	public long getIdTime() {
		return idTime;
	}
	public void setIdTime(long idTime) {
		this.idTime = idTime;
	}
	public String getAthLastname() {
		return athLastname;
	}
	public void setAthLastname(String athLastname) {
		this.athLastname = athLastname;
	}
	public String getAthFirstname() {
		return athFirstname;
	}
	public void setAthFirstname(String athFirstname) {
		this.athFirstname = athFirstname;
	}
	public String getAthTime() {
		return athTime;
	}
	public void setAthTime(String athTime) {
		this.athTime = athTime;
	}

}
