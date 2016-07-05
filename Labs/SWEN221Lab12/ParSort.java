import java.io.*;
import java.math.BigInteger;
import java.util.*;

import swen221.concurrent.Job;
import swen221.concurrent.ThreadPool;

public class ParSort {
	
	// =======================================================================
	// SWEN221: Look at this
	// =======================================================================

	/**
	 * The following implements a sequential quick sort.
	 * 
	 * @param list
	 *            --- data to be sorted.
	 * @param start
	 *            --- start position within data for sort.
	 * @param end
	 *            --- end position within data for sort.
	 */
	public static void sequentialSort(List<Integer> list, int start, int end) {		
		if(start >= end) { return; } // done 
		// recursive case		
		// now sort into two sections so stuff in lower section is less than
		// pivot, and remainder is in upper section.
		int pivot = list.get((start + end) / 2);
		int lower = start;
		int upper = end-1;
		
		while(lower < upper) {
			int lowerItem = list.get(lower); 
			int upperItem = list.get(upper);
			if(lowerItem < pivot) {
				// this item is not out of place
				lower++;
			} else if(upperItem > pivot) {
				// this item is not out of place
				upper--;
			} else {
				// both items are out of place --- so swap them.
				list.set(lower, upperItem);
				list.set(upper, lowerItem);
				if(upperItem != pivot) { lower++; }
				if(lowerItem != pivot) { upper--; }
			}
		}
		
		list.set(lower,pivot);
		
		pause(list,100); // to make animation in display mode better
		
		// A this point, lower == upper.
		sequentialSort(list,start,lower);
		sequentialSort(list,lower+1,end);		
	}

	/**
	 * The following implements a parallel quick sort.
	 * 
	 * @param list
	 *            --- data to be sorted
	 */
	public static void parallelSort(List<Integer> data, int numProcessors) {
	
		// TODO: see ParSum.parallelSum() for inspiration ...
		
	}
	
	// =======================================================================
	// SWEN221: No need to look at code below this!
	// =======================================================================
	
	public static void main(String[] args) {
		boolean displayMode = false;
		boolean parallelMode = false;
		String filename = null;

		// First, some rudimentary command-line argument processing.
		if (args.length == 0) {
			System.out.println("Usage: java Main [-gui] input.dat");
			System.exit(1);
		} 
		int index = 0;
		
		while(args[index].startsWith("--")) {
			String arg = args[index++];
			if (arg.equals("--gui")) {
				displayMode = true;
			} else if(arg.equals("--parallel")){
				parallelMode = true;
			} else {
				System.out.println("Unrecognised argument encountered: " + arg);
				System.exit(1);
			}
		}
		filename = args[index];
		
		// Second, read in the data and sort it.
		try {
			int numProcessors = Runtime.getRuntime().availableProcessors();
			System.out.println("Executing on machine with "
					+ numProcessors + " processor(s).");
			List<Integer> data = readInput(new FileReader(filename));
			System.out.println("Read " + data.size() + " data items.");

			if (displayMode) {
				System.out
						.println("Running in Display Mode (so ignore timings).");
				data = new DisplayList<Integer>(data);
			}

			long start = System.currentTimeMillis();

			if(parallelMode) {
				// do a parallel quick sort
				System.out.println("Performing a PARALLEL quicksort...");
				parallelSort(data, numProcessors);
			} else {
				// perform a sequential quick sort
				System.out.println("Performing a SEQUENTIAL quicksort...");
				sequentialSort(data, 0, data.size());
			}

			long time = System.currentTimeMillis() - start;

			System.out.println("Sorted data in " + time + "ms");
			if(checkValid(data)) {
				System.out.println("Data was sorted correctly!");
			} else {
				System.out.println("!!! ERROR: data not sorted correctly");
			}
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		}
	}

	public static boolean checkValid(List<Integer> data) {
		for(int i=1;i!=data.size();++i) {
			int previous = data.get(i-1);
			int current = data.get(i);
			if(previous > current) {
				// invalid sort
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<Integer> readInput(Reader input) throws IOException {		
		BufferedReader reader = new BufferedReader(input);
		
		String line;
		ArrayList<Integer> data = new ArrayList<Integer>();
		while((line = reader.readLine()) != null) {
			if(line.equals("")) { continue; } // skip blank lines
			data.add(Integer.parseInt(line));
		}
		return data;
	}
	
	// don't worry about what this method does.
	public static void pause(List<Integer> data, int delay) {
		if(data instanceof DisplayList) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}
}
