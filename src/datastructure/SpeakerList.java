package datastructure;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SpeakerList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3995642574345106415L;
	
	List<Speaker> speakers = new LinkedList<Speaker>();
	
	public SpeakerList() {
		
	}
	
	public void addSpeaker(Speaker s) {
		speakers.add(s);
	}
	
	public void removeSpeaker() {
		
	}
	
	public List<Speaker> returnList(){
		return speakers;
	}
}
