package simpleweb;

/**
 * This class simply parses any command-line parameters, then creates a server
 * object and let's it run.
 * 
 * @author djp
 * 
 */
public class Main {
	public static void main(String[] args) {
		int port = 8080; // default
		String root = "."; // default
		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if(arg.equals("-help")) {
					usage();
					System.exit(0);
				} else if(arg.equals("-port")) {					
					port = Integer.parseInt(args[++i]);
				} else if(arg.equals("-root")) {
					root = args[++i];
				} 
			}
		}
		
		new Server(port,root).run();
	}
	
	
	private static void usage() {		
		String[][] info = {		
				{"port <n>",
						"Respond to connections on port <n>; default is 8080."},
				{"root <dir>",
						"Set the root of all pages to this directory; default is current directory."}							
		};
		System.out.println("Usage: simpleweb.Main <options> ");
		System.out.println("Options:");

		// first, work out gap information
		int gap = 0;

		for (String[] p : info) {
			gap = Math.max(gap, p[0].length() + 5);
		}

		// now, print the information
		for (String[] p : info) {
			System.out.print("  -" + p[0]);
			int rest = gap - p[0].length();
			for (int i = 0; i != rest; ++i) {
				System.out.print(" ");
			}
			System.out.println(p[1]);
		}
	}
}
