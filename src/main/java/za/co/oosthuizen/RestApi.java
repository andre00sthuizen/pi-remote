package za.co.oosthuizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import za.co.oosthuizen.remote.model.HifiRemote;
import za.co.oosthuizen.remote.model.State;
import za.co.oosthuizen.remote.model.TvRemote;

/**
 * @author Andre Oosthuizen
 *
 */
@RestController
public class RestApi {

	@Autowired
	private TvRemote tvRemote;
	
	@Autowired
	private HifiRemote hifiRemote;
	
	@Autowired
	private IrRemoteService irRemoteService;
	
	@Autowired
	private State state;
	
	@RequestMapping(method=RequestMethod.GET, path="/")
    private State getState() throws Exception {
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/calibrate")
    private State calibrate() throws Exception {
		irRemoteService.calibrate();
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/switchoff")
	public State switchOff() throws Exception {
		tvRemote.switchOff();
		hifiRemote.switchOff();
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/switchon")
	public State switchOn() throws Exception {
		tvRemote.switchOn();
		hifiRemote.switchOn();
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/chromecast")
	public State switchToChromecast() throws Exception {
		tvRemote.switchOn();
		tvRemote.switchToChromecast();
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/kodi")
	public State switchToKodi() throws Exception {
		tvRemote.switchOn();
		tvRemote.switchToKodi();
		hifiRemote.switchOn();
		hifiRemote.switchToAux();
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/radio/{channel}")
	public State switchToRadio(@PathVariable int channel) throws Exception {
		hifiRemote.switchOn();
		hifiRemote.switchToTuner();
		hifiRemote.switchToChannel(channel);
		tvRemote.switchOff();
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/volume/{level}")
	public State setVolume(@PathVariable int level) throws Exception {
		hifiRemote.setVolume(level);
		return state;
    }
	
	@RequestMapping(method=RequestMethod.GET, path="/remote/{remote}/{key}")
    public State sendIrRemoteCommand(@PathVariable String remote, @PathVariable String key) throws Exception {
		irRemoteService.sendIrCommand(remote, key);
		return state;
    }
	
}
