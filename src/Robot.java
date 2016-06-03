import java.io.IOException;
import java.util.TooManyListenersException;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

public class Robot {
	private Bluetooth connection;
	private String portID;
	private String name;

	private static final String PREFIX = "fffffd00";
	private static final String DYNAMIXEL_ID = "c8";
	private static final String COMMAND_PACKET_LENGTH = "0700";
	private static final String INSTRUCTION_WRITE = "03";
	private static final String ADDRESS_EXECUTE = "4200";

	public Robot(String name, String portID) throws PortInUseException, TooManyListenersException,
			UnsupportedCommOperationException, IOException, InterruptedException {
		this.portID = portID;
		this.name = name;
		connection = new Bluetooth(portID);
	}

	public void sendCommand(String command) throws IOException {
		String hex = lowToHigh(prefix(command));
		String packet = PREFIX + DYNAMIXEL_ID + COMMAND_PACKET_LENGTH + INSTRUCTION_WRITE + ADDRESS_EXECUTE + hex;
		packet = packet + AddCRC.addCrc(packet);
		char[] bytes = packet.toCharArray();
		System.out.println(bytes);
		for (int i = 0; i < bytes.length; i += 2) {
			String bite = "";
			bite += bytes[i];
			bite += bytes[i + 1];
			int thisByte = Integer.parseInt(bite, 16);
			System.out.println(thisByte);
			connection.sendData(thisByte);
		}
		connection.flush();

	}

	private String prefix(String command) {
		int add = 4 - command.length();
		String prefix = "";
		for (int i = 0; i < add; i++) {
			prefix += "0";
		}
		return prefix + command;
	}

	public String lowToHigh(String number) {
		char[] hex = number.toCharArray();
		String low = String.valueOf(hex[2]) + String.valueOf(hex[3]);
		String high = String.valueOf(hex[0]) + String.valueOf(hex[1]);
		return low + high;
	}
	
	public void close() {
		connection.close();
	}

	public void readState() {

	}

	public String getName() {
		return name;
	}

	public String getPortID() {
		return portID;
	}
}
