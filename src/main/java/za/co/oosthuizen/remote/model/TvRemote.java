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
public class TvRemote {

	public static final String REMOTE_NAME = "Samsung_BN59-00686A";
	
	public static final String KEY_POWER = "KEY_POWER";
	public static final String KEY_TV = "KEY_TV";
	public static final String KEY_CYCLEWINDOWS = "KEY_CYCLEWINDOWS";
	public static final String KEY_1 = "KEY_1";
	public static final String KEY_2 = "KEY_2";
	public static final String KEY_3 = "KEY_3";
	public static final String KEY_4 = "KEY_4";
	public static final String KEY_5 = "KEY_5";
	public static final String KEY_6 = "KEY_6";
	public static final String KEY_7 = "KEY_7";
	public static final String KEY_8 = "KEY_8";
	public static final String KEY_9 = "KEY_9";
	public static final String KEY_CHANNEL = "KEY_CHANNEL";
	public static final String KEY_0 = "KEY_0";
	public static final String PRE_CH = "PRE_CH";
	public static final String KEY_VOLUMEUP = "KEY_VOLUMEUP";
	public static final String KEY_VOLUMEDOWN = "KEY_VOLUMEDOWN";
	public static final String KEY_MUTE = "KEY_MUTE";
	public static final String KEY_CHANNELUP = "KEY_CHANNELUP";
	public static final String KEY_CHANNELDOWN = "KEY_CHANNELDOWN";
	public static final String CH_LIST = "CH_LIST";
	public static final String KEY_MENU = "KEY_MENU";
	public static final String W_LINK = "W_LINK";
	public static final String TOOLS = "TOOLS";
	public static final String KEY_BACK = "KEY_BACK";
	public static final String KEY_UP = "KEY_UP";
	public static final String KEY_DOWN = "KEY_DOWN";
	public static final String KEY_LEFT = "KEY_LEFT";
	public static final String KEY_RIGHT = "KEY_RIGHT";
	public static final String KEY_ENTER = "KEY_ENTER";
	public static final String KEY_INFO = "KEY_INFO";
	public static final String KEY_EXIT = "KEY_EXIT";
	public static final String KEY_RED = "KEY_RED";
	public static final String KEY_GREEN = "KEY_GREEN";
	public static final String KEY_YELLOW = "KEY_YELLOW";
	public static final String KEY_BLUE = "KEY_BLUE";
	public static final String TTX_MIX = "TTX_MIX";
	public static final String P_SIZE = "P_SIZE";
	public static final String DMA = "DMA";
	public static final String E_MODE = "E_MODE";
	public static final String P_MODE = "P_MODE";
	public static final String HDMI = "HDMI";
	public static final String KEY_REWIND = "KEY_REWIND";
	public static final String KEY_PAUSE = "KEY_PAUSE";
	public static final String KEY_FASTFORWARD = "KEY_FASTFORWARD";
	public static final String KEY_RECORD = "KEY_RECORD";
	public static final String KEY_PLAY = "KEY_PLAY";
	public static final String KEY_STOP = "KEY_STOP";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TvRemote.class);
	
	@Autowired
	private IrRemoteService irRemoteService;
	
	@Autowired
	private Tv tv;
	
	public TvRemote() {
		
	}

	public void switchOn() throws Exception {
		if (!tv.isPower()) {
			LOGGER.info("Switching TV on");
			irRemoteService.sendIrCommand(REMOTE_NAME, TvRemote.KEY_POWER);
			tv.setPower(true);
			Thread.sleep(7750);
		} else {
			LOGGER.info("TV is already switched on");
		}
	}
	
	public void switchOff() throws Exception {
		if (tv.isPower()) {
			LOGGER.info("Switching TV off");
			irRemoteService.sendIrCommand(REMOTE_NAME, TvRemote.KEY_POWER);
			tv.setPower(false);
		} else {
			LOGGER.info("TV is already switched off");
		}
	}

	public void switchToChromecast() throws Exception {
		LOGGER.info("Switch TV to Chromecast");
		switchOn();
		if (tv.getSource() == Tv.Source.CHROMECAST) {
			LOGGER.info("TV already on Chromecast");
		} else {
			irRemoteService.sendIrCommand(REMOTE_NAME, TvRemote.HDMI);
			tv.setSource(Tv.Source.CHROMECAST);
		}
	}

	public void switchToKodi() throws Exception {
		LOGGER.info("Switch TV to Kodi");
		switchOn();
		if (tv.getSource() == Tv.Source.KODI) {
			LOGGER.info("TV already on Kodi");
		} else {
			irRemoteService.sendIrCommand(REMOTE_NAME, TvRemote.HDMI);
			tv.setSource(Tv.Source.KODI);
		}
	}
	
}
