import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class Bluetooth implements SerialPortEventListener {

	private SerialPort serialPort;
	private static final int TIME_OUT = 1000; // Port open timeout
	private static final int BAUD_RATE = 115200; // device serial port
	private String appName;
	@SuppressWarnings("unused")
	private BufferedReader input;
	private OutputStream output;

	public Bluetooth(String portID) throws PortInUseException, TooManyListenersException,
			UnsupportedCommOperationException, IOException, InterruptedException {
		initialize(portID);
	}

	private boolean initialize(String portID) throws PortInUseException, TooManyListenersException,
			UnsupportedCommOperationException, IOException, InterruptedException {
		CommPortIdentifier portId = null;
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		// Enumerate system ports and try connecting to device over each

		System.out.println(System.currentTimeMillis() + ": Entering connection loop...");
		while (portId == null && portEnum.hasMoreElements()) {
			// Iterate through your host computer's serial port IDs
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

			System.out.println(System.currentTimeMillis() + ": " + currPortId);
			
			if (currPortId.getName().equals(portID) || currPortId.getName().startsWith(portID)) {
				// Try to connect to the device on this port
				// Open serial port
				System.out.println(System.currentTimeMillis() + ": Connecting to robot");
				serialPort = (SerialPort) currPortId.open(appName, TIME_OUT);
				portId = currPortId;
				System.out.println(System.currentTimeMillis() + ": Connected on port" + currPortId.getName());
				break;
			}
		}

		if (portId == null || serialPort == null) {
			System.out.println("Could not connect to the device");
			return false;
		}

		// set port parameters
		serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

		// add event listeners
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);

		// Give the device some time
		Thread.sleep(2000);
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = new BufferedOutputStream(serialPort.getOutputStream());

		return true;
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			// whatever you want if data is received
		}
	}

	public void sendData(int data) throws IOException {
		output.write(data);
	}

	public void flush() throws IOException {
		output.flush();
	}
}
