package viewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadInFromFile {
	private String filename;
	private double[] x;
	private double[] y;
	
	public ReadInFromFile(String filename) throws IOException{
		this.filename = filename;
		FileReader file=new FileReader(this.filename);
		BufferedReader br=new BufferedReader(file);
		String str;
		int lineCount = 0;
		
		//Get File Size
		while ((str = br.readLine()) != null) {
			if (!str.contains("#")^ str.isEmpty()){
				lineCount++;
			}
		}
		br.close();
		
		//Copy file into data array
		FileReader file2=new FileReader(this.filename);
		BufferedReader br2=new BufferedReader(file2);
		
		
		x = new double[lineCount];
		y = new double[lineCount];
		int i = 0;
		while(i<lineCount){
			str=br2.readLine();
			if(!str.contains("#")^ str != null ^ !str.isEmpty()){
				String delims = "[ , \\t]+";
				 String[] parts =str.trim().split(delims);
				try{
					x[i] = Double.parseDouble(parts[0]);
					y[i] = Double.parseDouble(parts[1]);
				}catch (Exception e){
					x[i] = Double.NaN;
					y[i] = Double.NaN;
				}
			i++;
			}
		}
		br2.close();
	}

	public double[] getX(){
		return x;
	}
	
	public double[] getY(){
		return y;
	}
}
