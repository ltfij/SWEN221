package simpleweb;

import java.io.*;
import java.net.*;

/**
 * The following class implements a very simple single threaded web server. A
 * web server responds to HTTP requests over a socket by loading and
 * transmitting back the requested file.
 * 
 * @author djp
 * 
 */
public class Server {
	private int port; // port to respond on
	private String root;  // root of all html pages
	
	public Server(int port, String root) {
		this.port = port;
		this.root = root;
	}
	
	public void run() {
		boolean exit=false;
		try {						
			ServerSocket ss = new ServerSocket(port);
			while (!exit) {
				// Wait for a socket
				Socket s = ss.accept();
				// Ok, if we get here, then we got a connection				
				log("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
				processRequest(s);
				// finally, close the socket!
				s.close();
			}

		} catch (IOException e) {
			// something bad happened
			log(e.getMessage());
		}
	}

	/**
	 * The following method is responsible for processing a single HTTP request
	 * command.
	 * 
	 * @param s
	 *            --- the socket over which the request will be communicated.
	 */
	public void processRequest(Socket s) {
		try {
			String request = readRequest(s);
			log("RECEIVED REQUEST: " + request.length() + " bytes");
			String httpCommand = stripHttpGetCommand(request);
			String page = httpCommand.split(" ")[1];			
			if(page.equals("/")) {
				// auto convert empty page request into index page.
				page = "/index.html";
			}
			
			// Determine the file name, by appending the root.  Note, we need to ensure that the right "separator" is used for path names.  For example, on windows the separate char is "\", whilst on UNIX it is "/".  However, all HTTP get commands use "/".
			String filename = (root + page).replace('/',File.separatorChar);
			// Now, check if file exists
			if(new File(filename).exists()) {
				// Yes, it exists!!
				sendFile(filename,s);
			} else {
				// No, the file doesn't exist.
				send404(page,s);
			}
			
		} catch(IOException e) {
			log("I/O Error - " + e);
		}
	}
	
	public static void send404(String page, Socket s) throws IOException {
		PrintStream output = new PrintStream(s.getOutputStream());
		output.println("HTTP/1.1 200 OK");
		output.println("Content-Type: text/html; charset=UTF-8\n"); 
		output.println("<h1>Not Found</h1>\n\n"+
		"The requested URL " + page + " was not found on this server.\n");
	}

	
	private void log(String message) {
		System.out.println(message);
	}
	
	/**
	 * This method looks for the HTTP GET command, and returns that; or, null if
	 * none was found.
	 * 
	 * @param request
	 * @return
	 */
	public static String stripHttpGetCommand(String request) throws IOException {
		BufferedReader r = new BufferedReader(new StringReader(request));
		String line = "";
		while((line = r.readLine()) != null) {
			if(line.startsWith("GET")) {
				// found the get command
				return line;
			}
		} 
		return null;
	}
	
	/**
	 * This method reads all possible data from the socket and returns it.
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static String readRequest(Socket s) throws IOException {
		Reader input = new InputStreamReader(new BufferedInputStream(s.getInputStream()));
		String request = "";
		char[] buf = new char[1024];
		int nread;
		
		// Read from socket until nothing left.
		do {
			nread=input.read(buf);			
			String in = new String(buf,0,nread);
			request += in;
		} while(nread == 1024);
		
		return request;
	}

	/**
	 * Transmit file to socket in 1024 byte chunks.
	 * 
	 * @param filename
	 * @param s
	 * @throws IOException
	 */
	public static void sendFile(String filename, Socket s) throws IOException {
		OutputStream output = s.getOutputStream();
		PrintStream pout = new PrintStream(output);		
		FileInputStream input = new FileInputStream(filename);
		
		pout.println("HTTP/1.1 200 OK");
		if(filename.endsWith("jpg")) {
			pout.println("Content-Type: image/jpeg; charset=UTF-8\n");
		} else {
			pout.println("Content-Type: text/html; charset=UTF-8\n");
		}
		pout.flush();
		
		try {
			int n;
			while((n = input.read(file_buffer)) > 0) {
				output.write(file_buffer, 0, n);
				// I have introduced the following artificial delay specifically
				// to throttle the rate of transmission. This is necessary since
				// the server will be running on the same machine as the
				// web-browser and, hence, will be extremely fast compared with
				// normal. Thus, the pause helps us to see the real problem.
				// Removing this line is not how to solve this lab :)
				pause(25); 
			}
		} finally {
			// We must close the sockets, no matter what happens.
			output.close();
			input.close();
		}
	}	
	
	private static byte[] file_buffer = new byte[1024];
	
	private static void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch(InterruptedException e) {
			
		}
	}
}
