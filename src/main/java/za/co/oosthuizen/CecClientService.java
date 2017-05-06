package za.co.oosthuizen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import za.co.oosthuizen.remote.model.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;

/**
 * @author Andre Oosthuizen
 *
 */
@Service
public class CecClientService implements DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(CecClientService.class);

	@Autowired
	private State state;

	private Process command;
	private InputStream inputStream;

	@Async
	public void run() throws IOException {
        LOGGER.info("Starting CEC client");
        this.command = Runtime.getRuntime().exec("cec-client -d 8");
        this.inputStream = command.getInputStream();
	    String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = reader.readLine()) != null) {
            LOGGER.info(line);
            if (line.endsWith(">> 02:90:00")) {
                state.getTv().setPower(true);
            } else if (line.endsWith(">> 0f:36")) {
                state.getTv().setPower(false);
            } else if (line.endsWith(">> 1f:82:10:00")) {
                state.getTv().setPower(true);
                state.getTv().setSource(Tv.Source.KODI);
            } else if (line.endsWith(">> 4f:82:30:00")) {
                state.getTv().setPower(true);
                state.getTv().setSource(Tv.Source.CHROMECAST);
            }
        }
        LOGGER.info("CEC client disconnected");
    }

    @Override
    public void destroy() throws Exception {
        LOGGER.info("Shutting down CEC client");
        try { inputStream.close(); } catch (Exception e) {}
        command.destroy();
    }

}
