package za.co.oosthuizen.remote.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Andre Oosthuizen
 *
 */
@Component
public class Tv {

	@JsonSerialize(using = BooleanSerializer.class)
	private boolean power;
	private Source source;
	
	public Tv() {
		
	}
	
	public boolean isPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
	
	public enum Source {
		CHROMECAST, KODI
	}
}
