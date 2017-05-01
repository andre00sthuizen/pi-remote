package za.co.oosthuizen.remote.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Andre Oosthuizen
 *
 */
@Component
public class State {

	@Autowired
	private Tv tv;
	
	@Autowired
	private Hifi hifi;
	
	public State() {
		
	}

	public Tv getTv() {
		return tv;
	}

	public void setTv(Tv tv) {
		this.tv = tv;
	}

	public Hifi getHifi() {
		return hifi;
	}

	public void setHifi(Hifi hifi) {
		this.hifi = hifi;
	}
	
}
