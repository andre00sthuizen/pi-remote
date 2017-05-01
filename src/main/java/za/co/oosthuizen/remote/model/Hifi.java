package za.co.oosthuizen.remote.model;

import org.springframework.stereotype.Component;

/**
 * @author Andre Oosthuizen
 *
 */
@Component
public class Hifi {
	
	private boolean power;
	private int volume;
	private Source source;
	private int channel;

	public Hifi() {
		
	}

	public boolean isPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
	
	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
	
	public enum Source {
		TUNER, AUX
	}
	
}
