package src;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
    	
    	printWelcomeScreen();
    	
    	//ask the user for the path 
    	System.out.println("Please enter a path to the folder containing the print tags:");
    	Scanner userInput = new Scanner(System.in);
    	String folderPath = userInput.nextLine();  
    	
    	    	
    	//Recurseivly getting all the text files from a given path
    	List<String> fileList = null;
    	while (fileList == null) {
	    	try {
	    		fileList = fileExplorer(folderPath);
	    	} catch (Exception e) {
	    		System.out.println("ERROR! Invalid path!\nThe path should be in the form\tC:\\Users\\YOUR_USERNAME\\FOLDER\\FOLDER2\\FOLDER3\n");
	    		System.out.println("Please enter a path to the folder containing the print tags:\n");
	    		folderPath = userInput.nextLine(); 
	    	}
    	}
    	
    	System.out.println("\nFiles found:");
    	fileList.forEach(System.out::println);
    	
    	
    	//ask the user if he want to continue with the current files
    	System.out.println("\nContinue? y/n");
    	
    	String inputString ;
    	char choice = ' ';
    	
    	while (choice != 'y' && choice != 'n'){
    		inputString = userInput.next().toLowerCase();
    		
    		//check for single character input
    		while (inputString.length() > 1) {
    			System.out.print("please enter a valid option!\nContinue? y/n\n\n");
    			inputString = userInput.next().toLowerCase();
    		}
    		
    		choice = inputString.charAt(0);
    		
    		if (choice == 'n') {
        		System.out.println("OK. the program will close now.\n");
        		System.exit(0);
        	} else if (choice == 'y') {
        		System.out.println("\nOK. the programs start.\n");
        	} else {
        		System.out.print("\nPlease enter a valid option!\nContinue? y/n\n");
        	}
    	}
    	
    	userInput.close();

    	//for each file read it line by line and get the print tags
    	String serverURL = "http://yugiohprices.com/api/price_for_print_tag/";
    	
    	//	0 - high	1 - average		2 - low
    	float prices[] = new float[3];
    	
    	for (String currentFile : fileList) {
    		System.out.println("File:\t" + currentFile);
    		
    		List<String> lines = Files.readAllLines(Paths.get(currentFile));
    		
    		//for each print tag send GET requests to the YugiohPrices API
    		for(int i = 0; i < lines.size(); i++) {
    			
    			String pathURL = lines.get(i);

    			try {
    			    			
    			URL YGOPrices = new URL(serverURL + pathURL);
    	        URLConnection yc = YGOPrices.openConnection();
    	        
    	        BufferedReader in = new BufferedReader( new InputStreamReader(yc.getInputStream()));
    	        
    	        String inputLine = in.readLine();
    	        
    	        System.out.println("Print tag: " + pathURL + " data: " + inputLine);
    	        
    	        //pull the data from the JSON recived from the GET request and add it to the sums
    	        float[] currentPrices = extractDataFromJson(inputLine);
    	        prices[0] += currentPrices[0];
    	        prices[1] += currentPrices[1];
    	        prices[2] += currentPrices[2];
    	        
    	        in.close();  
    	        } catch (Exception e) {
    	        	System.out.println("BAD FILE: " + serverURL + pathURL);
    	        }
    		}
    	}
    	
    	outputResults(prices, folderPath);
    	
    	System.exit(0);
    }
        
    public static List <String> fileExplorer(String path){    	
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles();

    	List<String> absoluteFileList = new ArrayList<String>();
    	
    	for (int i = 0; i < listOfFiles.length; i++) {
    	  if (listOfFiles[i].isFile()) {
    		  //filter out all the non-text files
			if (getFileExtension(listOfFiles[i].toString()).equals("txt")) {
				absoluteFileList.add(path + "\\" +listOfFiles[i].getName());
			}
    	  } else if (listOfFiles[i].isDirectory()) {
    	    absoluteFileList.addAll(fileExplorer(path + "\\" +listOfFiles[i].getName()));
    	  }
    	}
    	return absoluteFileList;
    }
    
    public static String getFileExtension(String fullName) {
        if (fullName == null)
        	return null;
        
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    
    public static void printWelcomeScreen() {    	
    	System.out.println(	" __   __           _       _        ____      _ _           _   _               ____       _             ____      _            _       _             \n" 
    			+ 			" \\ \\ / /   _  __ _(_) ___ | |__    / ___|___ | | | ___  ___| |_(_) ___  _ __   |  _ \\ _ __(_) ___ ___   / ___|__ _| | ___ _   _| | __ _| |_ ___  _ __ \n"
    			+			"  \\ V / | | |/ _` | |/ _ \\| '_ \\  | |   / _ \\| | |/ _ \\/ __| __| |/ _ \\| '_ \\  | |_) | '__| |/ __/ _ \\ | |   / _` | |/ __| | | | |/ _` | __/ _ \\| '__|\n"
    			+ 			"   | || |_| | (_| | | (_) | | | | | |__| (_) | | |  __/ (__| |_| | (_) | | | | |  __/| |  | | (_|  __/ | |__| (_| | | (__| |_| | | (_| | || (_) | |   \n"
    			+			"   |_| \\__,_|\\__, |_|\\___/|_| |_|  \\____\\___/|_|_|\\___|\\___|\\__|_|\\___/|_| |_| |_|   |_|  |_|\\___\\___|  \\____\\__,_|_|\\___|\\__,_|_|\\__,_|\\__\\___/|_|   \n"
    			+ 			"             |___/                                                                                                                                    \n"
    			+			"__________________________________________________________________________________________________________________________________________________________________________"
    			+ 			"");
    	
    	
    	System.out.println(	"\nInformation: \n"
    			+			"The program read all the files and folders recurcievly from the path given by the user.\r\n"
    			+ 			"For each .txt file in the given path the program will read all the print tags, a unique ID for each version of each card from the Yugioh TCG.\r\n"
    			+ 			"Each print tag will be sent to the YugiohPrices API as a GET request and it will return all the relevant information.\r\n"
    			+ 			"After the program will finish gather all the information it will create a text file called \"Output\" at the same path which contains the collection's price in 3 different methods:\r\n"
    			+ 			"- the lowest price for the collection based on the lowest priced eBay listing.\r\n"
    			+ 			"- the average price for the collectoon based on employing a recursive algorithm that uses standard deviations to filter out outlier card prices in order to produce a more statistically sound average price.\r\n"
    			+ 			"- the highest price for the collection based on sales from recet time."
    			+			"\n\n"
    			+			"*IMPORATANT:* make sure the text files are in the following pattern: each print tag in a new line, without spaces before or after the tag."
    			+			"\n\n"
    			+			"For the complete information and guide please visit the Github page at:\n" + "https://github.com/cijhho123/Yugioh-Collection-Price-Calculator" + "\n"
    			);
    }
    
    public static float[] extractDataFromJson(String str) {
    	try {
    	//high
    	int length = 0;
    	int index = str.indexOf("\"high\"") + "\"high\":".length();
    	while (str.charAt(index+ length) != ',') 
    		length++;
    	float high = Float.parseFloat(str.substring(index, index + length));
    	
    	//average
    	length = 0;
    	index = str.indexOf("\"average\":") + "\"average\":".length();
    	while (str.charAt(index+ length) != ',') 
    		length++;
    	float average = Float.parseFloat(str.substring(index, index + length));
    	
    	//low
    	length = 0;
    	index = str.indexOf("\"low\":") + "\"low\":".length();
    	while (str.charAt(index+ length) != ',') 
    		length++;
    	float low = Float.parseFloat(str.substring(index, index + length));

    	return new float[] {high, average, low};
    	
    	} catch (Exception e) {
    		//in case the GET request failed and there is no data to extract from return 0 in all values so it won't affect the sum
    		return new float[] {0, 0, 0};
    	}
    }
    
    public static void outputResults (float [] prices, String folderPath) {
        try {
        	String fullPath = folderPath + "\\output.txt";
            File myObj = new File(fullPath);
 
            //write the updated values for the collection
        	FileWriter myWriter = new FileWriter(fullPath);
        	
            myWriter.write(	"Your Yugioh collection's high market value is:  "+ prices[0] + "$\n"
            			+	"Your Yugioh collection's average market value is:  "+ prices[1] + "$\n" 
            			+ 	"Your Yugioh collection's low market value is:  "+ prices[2 ] + "$\n"
            			+	"\n"
            			+	"Created by cijhho123 using the YugiohPrices API.\n"
            			+	"Contact me at cijhho12345@gmail.com"
            			+	"\n"
            			+	"For more information, source code and guide visit my Github page:"
            			+	"https://github.com/cijhho123/Yugioh-Collection-Price-Calculator"
            			);
            
            myWriter.close();
            
            System.out.println("\nThe file containning all the information have been created.\nIt is located in\t" + fullPath);
            System.out.println("\nHere's a short summery:\n"
            		+ "High market value:" + prices[0] + "$ 	\t\t" 
            		+ "Average market value:" + prices[1] + "$	\t\t" 
            		+ "Low market value:" + prices[2] + "$ 		\t\t" 
            		);
            
          } catch (IOException e) {
            System.out.println("An error occurred. results will be printed in the console instead.");
            
        	System.out.println("Your Yugioh collection's high market value is:  "+ prices[0] + "$\n"
		        			+	"Your Yugioh collection's average market value is:  "+ prices[1] + "$\n" 
		        			+ 	"Your Yugioh collection's low market value is:  "+ prices[2 ] + "$\n"
		        			+	"\n"
		        			+	"Created by cijhho123 using the YugiohPrices API.\n"
		        			+	"Contact me at cijhho12345@gmail.com"
		        			+	"\n"
		        			+	"For more information, source code and guide visit my Github page:"
		        			+	"https://github.com/cijhho123/Yugioh-Collection-Price-Calculator"
		        			);
	        
        	System.out.println("If you want to help me make the program better please send me via email the text files you tried to process and the error message shown bellow:");
            e.printStackTrace();
          }
    }
}