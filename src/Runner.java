import java.io.IOException;
import java.util.Scanner;
import java.util.TooManyListenersException;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

public class Runner {
	public static void main(String[] main) {
		Robot r1, r2;
		try {
			r1 = new Robot("Andy2", "COM3");
			System.out.println(r1);
//			r2 = new Robot("Sophia", "COM9"); 
			Scanner input = new Scanner(System.in);
			do {
				String command = input.nextLine(); 
				System.out.println(command);
				r1.sendCommand(command);
//				r2.sendCommand(command);
			} while (true);
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
/*1
 * 1 = stand
 * 2 = stand
 * 3 = sit
 * 5 = bow
 * 6 = greet bow
 * 7 = right jab
 * 8 = right hook
 * 9 = right uppercut
*/
