import java.io.IOException;
import java.util.TooManyListenersException;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

public class Controller {
	private Robot robot;

	public Controller(String name, String portID) throws PortInUseException, TooManyListenersException,
			UnsupportedCommOperationException, IOException, InterruptedException {
		this.robot = new Robot(name, portID);
	}

	public void sendCommand(String command) throws IOException {
		robot.sendCommand(command);
	}
}