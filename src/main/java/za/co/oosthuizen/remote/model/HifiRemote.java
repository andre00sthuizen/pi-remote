package za.co.oosthuizen.remote.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.oosthuizen.IrRemoteService;

/**
 * @author Andre Oosthuizen
 *
 */
@Service
public class HifiRemote {

	public static final String REMOTE_NAME = "Samsung_AH59-02144K";

	public static final String KEY_POWER = "KEY_POWER";
	public static final String KEY_EJECTCLOSECD = "KEY_EJECTCLOSECD";
	public static final String KEY_BRIGHTNESS_CYCLE = "KEY_BRIGHTNESS_CYCLE";
	public static final String KEY_DVD = "KEY_DVD";
	public static final String KEY_TUNER = "KEY_TUNER";
	public static final String KEY_AUX = "KEY_AUX";
	public static final String KEY_1 = "KEY_1";
	public static final String KEY_2 = "KEY_2";
	public static final String KEY_3 = "KEY_3";
	public static final String KEY_4 = "KEY_4";
	public static final String KEY_5 = "KEY_5";
	public static final String KEY_6 = "KEY_6";
	public static final String KEY_7 = "KEY_7";
	public static final String KEY_8 = "KEY_8";
	public static final String KEY_9 = "KEY_9";
	public static final String KEY_0 = "KEY_0";
	public static final String KEY_AUDIO = "KEY_AUDIO";
	public static final String KEY_SUBTITLE = "KEY_SUBTITLE";
	public static final String KEY_PREVIOUS = "KEY_PREVIOUS";
	public static final String KEY_NEXT = "KEY_NEXT";
	public static final String KEY_SLOW = "KEY_SLOW";
	public static final String KEY_PAUSE = "KEY_PAUSE";
	public static final String KEY_REWIND = "KEY_REWIND";
	public static final String KEY_FORWARD = "KEY_FORWARD";
	public static final String KEY_STOP = "KEY_STOP";
	public static final String KEY_PLAY = "KEY_PLAY";
	public static final String KEY_VOLUMEDOWN = "KEY_VOLUMEDOWN";
	public static final String KEY_VOLUMEUP = "KEY_VOLUMEUP";
	public static final String KEY_MUTE = "KEY_MUTE";
	public static final String KEY_FRAMEFORWARD = "KEY_FRAMEFORWARD";
	public static final String KEY_CHANNELUP = "KEY_CHANNELUP";
	public static final String KEY_CHANNELDOWN = "KEY_CHANNELDOWN";
	public static final String KEY_MENU = "KEY_MENU";
	public static final String KEY_BACK = "KEY_BACK";
	public static final String KEY_INFO = "KEY_INFO";
	public static final String KEY_EXIT = "KEY_EXIT";
	public static final String KEY_UP = "KEY_UP";
	public static final String KEY_DOWN = "KEY_DOWN";
	public static final String KEY_LEFT = "KEY_LEFT";
	public static final String KEY_RIGHT = "KEY_RIGHT";
	public static final String KEY_ENTER = "KEY_ENTER";
	public static final String KEY_RED = "KEY_RED";
	public static final String KEY_GREEN = "KEY_GREEN";
	public static final String KEY_YELLOW = "KEY_YELLOW";
	public static final String KEY_BLUE = "KEY_BLUE";
	public static final String KEY_MODE = "KEY_MODE";
	public static final String KEY_TITLE = "KEY_TITLE";
	public static final String KEY_HOME = "KEY_HOME";
	public static final String KEY_PLII_MODE = "KEY_PLII_MODE";
	public static final String KEY_PLII_EFFECT = "KEY_PLII_EFFECT";
	public static final String MARKER = "MARKER";
	public static final String KEY_PIP = "KEY_PIP";
	public static final String KEY_SFE_MODE = "KEY_SFE_MODE";
	public static final String KEY_DSP = "KEY_DSP";
	public static final String KEY_MEDIA_REPEAT = "KEY_MEDIA_REPEAT";
	public static final String KEY_REPEAT_A_B = "KEY_REPEAT_A_B";
	public static final String KEY_ZOOM = "KEY_ZOOM";
	public static final String KEY_SLEEP = "KEY_SLEEP";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HifiRemote.class);

	@Autowired
	private IrRemoteService irRemoteService;

	@Autowired
	private Hifi hifi;

	public HifiRemote() {

	}

	public void switchOn() throws Exception {
		if (!hifi.isPower()) {
			LOGGER.info("Switching Hifi on");
			irRemoteService.sendIrCommand(REMOTE_NAME, KEY_POWER);
			Thread.sleep(6750);
			hifi.setPower(true);
		} else {
			LOGGER.info("Hifi is already switched on");
		}
	}

	public void switchOff() throws Exception {
		if (hifi.isPower()) {
			LOGGER.info("Switching Hifi off");
			irRemoteService.sendIrCommand(REMOTE_NAME, KEY_POWER);
			hifi.setPower(false);
		} else {
			LOGGER.info("TV is already switched off");
		}
	}

	public void switchToAux() throws Exception {
		if (hifi.getSource() != Hifi.Source.AUX) {
			LOGGER.info("Switching Hifi to Aux");
			for (int i=0; i<3; i++) {
				irRemoteService.sendIrCommand(REMOTE_NAME, KEY_AUX);	
			}
			hifi.setSource(Hifi.Source.AUX);
		} else {
			LOGGER.info("Hifi is already on Aux");
		}
	}

	public void setVolume(int level) throws Exception {
		if (!hifi.isPower()) {
			return;
		}
		if (hifi.getVolume() > level) {
			LOGGER.info("Setting volume level to "+level);
			int adjustments = hifi.getVolume() - level;
			for (int i=0; i<adjustments; i++) {
				irRemoteService.sendIrCommand(REMOTE_NAME, KEY_VOLUMEDOWN);
			}
		} else if (hifi.getVolume() < level) {
			LOGGER.info("Setting volume level to "+level);
			int adjustments = level - hifi.getVolume();
			for (int i=0; i<adjustments; i++) {
				irRemoteService.sendIrCommand(REMOTE_NAME, KEY_VOLUMEUP);
			}
		} else {
			LOGGER.debug("Hifi volume already at level "+level);
		}
		hifi.setVolume(level);
	}

	public void switchToChannel(int channel) throws Exception {
		if (hifi.getChannel() != channel) {
			LOGGER.info("Changing to radio channel "+channel);
			irRemoteService.sendIrCommand(REMOTE_NAME, "KEY_"+channel);
			hifi.setChannel(channel);
		} else {
			LOGGER.info("Radio already on channel "+channel);
		}
	}
	
	public void switchToTuner() throws Exception {
		LOGGER.info("Switch to radio");
		if (hifi.getSource() == Hifi.Source.TUNER) {
			LOGGER.info("Radio already playing");
		} else {
			irRemoteService.sendIrCommand(REMOTE_NAME, KEY_TUNER);
			hifi.setSource(Hifi.Source.TUNER);
		}
	}

}
