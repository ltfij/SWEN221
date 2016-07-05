import java.awt.*;

/**
 * The Clock Thread is responsible for producing a consistent "pulse" which is
 * used to refresh the display. Setting the pulse rate too high may cause
 * problems, when the point is reached at which the work done to service a given
 * pulse exceeds the time between pulses.
 * 
 * @author djp
 * 
 */
public class ClockThread extends Thread {
	private final int delay; // delay between pulses in us	
	private final Frame display;
	
	public ClockThread(int delay, Frame display) {
		this.delay = delay;		
		this.display = display;
	}
	
	public void run() {
		while(1 == 1) {
			// Loop forever			
			try {
				Thread.sleep(delay);				
				if(display != null) {
					display.invalidate();
					display.repaint();
				}
			} catch(InterruptedException e) {
				// should never happen
			}			
		}
	}
}
