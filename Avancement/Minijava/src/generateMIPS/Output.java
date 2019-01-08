package generateMIPS;
import java.io.*;

public class Output {
    private BufferedWriter bw;
    public void open(String outfile) {
	try { bw = new java.io.BufferedWriter(new FileWriter(outfile));}
	catch (IOException e) { e.printStackTrace(); }
    }
    public void close() {
	try { bw.close(); } 
	catch (IOException e) { e.printStackTrace(); }
    }
    public void write(String s) {
	try { bw.write(s); } 
	catch (IOException e) { e.printStackTrace(); }
    }
    public void concat(String fileName) {
	String temp;
	try {	
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    while((temp = br.readLine()) != null) 
		bw.write(temp + "\n");
	    br.close();
	} catch (IOException e) { e.printStackTrace(); }
    }
}
