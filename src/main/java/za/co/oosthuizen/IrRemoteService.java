package za.co.oosthuizen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import za.co.oosthuizen.remote.model.Hifi;
import za.co.oosthuizen.remote.model.HifiRemote;
import za.co.oosthuizen.remote.model.State;
import za.co.oosthuizen.remote.model.Tv;
import za.co.oosthuizen.remote.model.TvRemote;

/**
 * @author Andre Oosthuizen
 *
 */
@Service
@ConfigurationProperties(prefix="libreelec")
public class IrRemoteService implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(IrRemoteService.class);

	@Autowired
	private State state;

	@Autowired
	private CecClientService cecClientService;

	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("Initialising remote");
		cecClientService.run();
	}

	@Override
	public void destroy() throws Exception {
		LOGGER.info("Shutting down remote");
	}

	public void calibrate() throws Exception {
		//Power on TV
		sendIrCommand(TvRemote.REMOTE_NAME, TvRemote.KEY_TV);
		//Power on HiFi
		sendIrCommand(HifiRemote.REMOTE_NAME, HifiRemote.KEY_TUNER);
		Thread.sleep(6750);
		//Switch to Aux
		for (int i=0; i<3; i++) {
			sendIrCommand(HifiRemote.REMOTE_NAME, HifiRemote.KEY_AUX);	
		}
		//Switch to Chromecast
		sendIrCommand(TvRemote.REMOTE_NAME, TvRemote.HDMI);
		//Volume down
		for (int i=0; i<30; i++) {
			sendIrCommand(HifiRemote.REMOTE_NAME, TvRemote.KEY_VOLUMEDOWN);
		}
		//Initialize state
		state.getTv().setPower(true);
		state.getTv().setSource(Tv.Source.CHROMECAST);
		state.getHifi().setPower(true);
		state.getHifi().setSource(Hifi.Source.AUX);
		state.getHifi().setVolume(0);
	}

	public void sendIrCommand(String remote, String key) throws Exception {
		LOGGER.info("sendIrCommand  remote: "+remote+" and key: "+key);
		Process command = Runtime.getRuntime().exec("irsend -d /run/lirc/lircd SEND_ONCE "+remote+" "+key);
		command.waitFor();
		if (command.exitValue() != 0) {
			LOGGER.error("Command failed with exit value "+command.exitValue()+": "+toString(command.getErrorStream()));
		}
	}
	
	private String toString(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer output = new StringBuffer();
		String line = "";
		while ((line = reader.readLine())!= null) {
			output.append(line + "\n");
		}
		return output.toString();
	}

}
