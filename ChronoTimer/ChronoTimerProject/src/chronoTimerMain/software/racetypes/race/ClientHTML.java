package chronoTimerMain.software.racetypes.race;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

import chronoTimerMain.software.racetypes.Racer;

public class ClientHTML {
	ArrayList<Racer> racers = new ArrayList<Racer>();
	String url = "";
	public ClientHTML() {
		this.url = "http://localhost:8000";
	}
	public Racer getRacers(int i) {
		return racers.get(i);
	}
	public void addRacer(Racer e) {
		racers.add(e);
	}
	public void sendData() {
		try{
			//System.out.println("in the client");
		//Client will connect to this location
		URL site = new URL(url + "/sendresults");
		HttpURLConnection conn = (HttpURLConnection) site.openConnection();

		// now create a POST request
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		
		// build a string that contains JSON from console
		String content = "";
		content += "data=" + getJSON();
		//System.out.println("\n" + content);
		// write out string to output buffer for message
		out.writeBytes(content);
		out.flush();
		out.close();

		//System.out.println("Done sent to server");
		
		InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

		// string to hold the result of reading in the response
		StringBuilder sb = new StringBuilder();

		// read the characters from the request byte by byte and build up the sharedResponse
		int nextChar = inputStr.read();
		while (nextChar > -1) {
			sb=sb.append((char)nextChar);
			nextChar=inputStr.read();
		}
		//System.out.println("Return String: "+ sb);
		}
		catch (Exception e) {
			System.out.println("Connection Error to Server.");
		}
	}
	
	
	
	String getJSON() {
		//System.out.println("in the client");
		Gson g = new Gson();		
		String out = g.toJson(racers);
		return out;
	}

}
