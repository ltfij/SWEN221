package swen221.concurrent;

/**
 * A job represents a unit of work which can be executed on a ThreadPool. To
 * perform a parallel computation, the task needs to be divided up into a number
 * of small jobs which are then submitted to the thread pool.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class Job {
	/**
	 * Used to know that this thread is finished.
	 */
	private volatile boolean finished;

	/**
	 * Execute this job on a given thread.
	 */
	public abstract void run();
		
	public synchronized void start() {
		run();
		finished = true;
		notify();
	}
		
	/**
	 * Force the thread which calls this method to block until this job has been
	 * finished.
	 */
	public synchronized void waitUntilFinished() {
		if(finished) { return; }
		
		try {
			this.wait();
		} catch(InterruptedException e) {
			
		}
	}
}
