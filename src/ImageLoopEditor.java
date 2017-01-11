///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            CS367 Assignment 2
// Files:            DblListnode.java, EmptyLoopException.java, Image.java
//							ImageLoopEditor.java, LinkedLoop.java, 
//							LinkedLoopItterator.java, LoopADT.java, testClass.java
// Semester:         
//
// Author:           Xingmin Zhang 
// Email:            xzhang66@wisc.edu
// CS Login:         
// Lecturer's Name:  
// Lab Section:      
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          None
//
// Online sources:   None
//////////////////////////// 80 columns wide //////////////////////////////////


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

public class ImageLoopEditor {
	
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		boolean exit = false; //whether user want to exit
		boolean inputValid = false; //whether input is a valid choice
		String inputLine = ""; //takes user input
		char operation = ' '; //first character of user input
		String remainder = ""; //remainder of user input after the first character
		LinkedLoop<Image> imagelist = new LinkedLoop<>(); //create an imagelist
		final String IMAGEPATH = "images/"; //default path to the image folder
		
		//repeatedly asks users to type in for new operations
		while (!exit || !inputValid) {
			
			System.out.print("enter command (? for help)> ");
			inputLine = scnr.nextLine().trim(); //trim leading and trailing spaces
		   operation = inputLine.charAt(0); //first character is the choice 
		   										   //of operation

			switch (operation) {
			
				case '?': //display help information 
					inputValid = true;
					displayHelp();
					break;
					
				case 's':
					//if first character is 's', check whether remaining input 
					//is valid; if it is, save the images
					if(inputCheck(inputLine)) {
						inputValid = true;
						save(imagelist, inputLine);
					}
					break;
					
				case 'l':
					//if first character is 'l', check whether remaining input 
					//is valid; if it is, load images to the list
					if(inputCheck(inputLine)) {
						inputValid = true;
						load(imagelist, inputLine, IMAGEPATH);
					}			
					break;
					
				case 'd':
					//if first character is 'd', delete current image from the list
					inputValid = true;
					display(imagelist);
					break;
					
				case 'p':
					//if first character is 'p', show the current image
					inputValid = true;
					picture(imagelist);
					break;
					
				case 'f':
					//if first character is 'f', move to the next image and display
					//the previous image, the current one, and the next one
					inputValid = true;
					forward(imagelist);
					break;
					
				case 'b':
					//if first character is 'b', move to the previous image and 
					//display the previous image, the current one, and the next one
					inputValid = true;
					backward(imagelist);
					break;
					
				case 'j':
					//if the first character is 'j', determine whether the remaining
					//input is an integer; if it is, jump forward or backward and 
					//display the previous image, the current one, and the next one
					if(inputLine.length() > 1 
							&& Character.isWhitespace(inputLine.charAt(1))
							&& isInt(inputLine.substring(1))) {
						inputValid = true;
						jump(imagelist, inputLine);
					}
					break;
					
				case 't':
					//if the first character is 't', display images in a window for
					//specified duration
					inputValid = true;
					test(imagelist);
					break;
					
				case 'r':
					//if the first character is 'r', remove the current image from
					//the list
					inputValid = true;
					remove(imagelist);
					break;
					
				case 'a':
					//if the first character is 'a', determine if the remaining 
					//input is valid; if it is, add an image after the current image 
					//and display the previous image, the current one, and the next 
					//one
					if(inputCheck(inputLine)) {
						inputValid = true;
						addAfterCurrent(imagelist, inputLine, IMAGEPATH);
					}
					break;
					
				case 'i':
					//if the first character is 'i', determine if the remaining input
					//is valid; if it is, insert an image before the current image 
					//and display the previous image, the current one, and the next 
					//one
					if(inputCheck(inputLine)) {
						inputValid = true;
						insertBeforeCurrent(imagelist, inputLine, IMAGEPATH);
					}
					break;
					
				case 'e':
					//if the first character is 'e', edit the title of the current
					//image to the remaining of input
					inputValid = true;
					edit(imagelist, inputLine);
					break;
					
				case 'c':
					//if the first character is 'c', determine if it is followed by a
					//whitespace and other strings(could be empty); if it is, 
					//find the image that contains the strings in its title
					if (inputLine.length() > 1 &&
							Character.isWhitespace(inputLine.charAt(1))) {
						inputValid = true;
						contains(imagelist, inputLine);
					}
					break;
					
				case 'u':
					//if the first character is 'u', determine whether it is
					//followed by a whitespace and then a positive integer;
				   //if it is, update the duration of the current image to the 
					//specified duration time (seconds)
					if(inputLine.length() > 1 
							&& Character.isWhitespace(inputLine.charAt(1))
							&& isPosInt(inputLine.substring(1))) {
						inputValid = true;
						update(imagelist, inputLine);
					}
					break;
					
				case 'x':
					//if the first character is 'x', terminate the program
					inputValid = true;
					exit = true;
					break;
			}
			
		//if user input is not valid, display a message	
		if(!inputValid) {
			System.out.println("invalid command");	
		} 
		
		//if the operation is not exit, reset the parameter for input check
		if (operation != 'x') {
			inputValid = false;
		}
		}
	}

	
	/**
	 * A method to check whether the input is valid for operations s l a i
	 * @param str: input line to check
	 * @return true if the first character is followed by at least one space 
	 * and the remainder of the line contains only letters, digits, 
	 * underscores, periods, slashes and dashes
	 *
	 */
	private static boolean inputCheck(String str) {
		boolean inputValid = true;
		if(str.trim().length() == 1) {
			inputValid = false;
			} else {
				String substring = str.substring(1, str.length());
				if(!Character.isWhitespace(substring.charAt(0))) {
					inputValid = false;
				} else {
					String strTrimmed = substring.trim();//Maybe I should only trim 
																//the left end? How?
					for (int i = 0; i < strTrimmed.length(); i++) {
						char aChar = strTrimmed.charAt(i);
						if(!Character.isLetter(aChar) && !Character.isDigit(aChar)
								&& aChar != '_' && aChar != '.' && aChar != '/' 
								&& aChar != '-') {	
							inputValid = false;
							break;
						}
				}
				}
			}
		return inputValid;
	}
	
	
	/**
	 * A helper method to determine whether a string input has internal 
	 * whitespace
	 * @param str: a string to check
	 * @return true if a string has spaces, not considering leading and 
	 * tailing spaces
	 */
	private static boolean isInt(String str) {
		if(allDigit(str)) {
			double num = Double.parseDouble(str.trim());
			return num <= Integer.MAX_VALUE && num > Integer.MIN_VALUE;
		}
		return false;
	}
	
	/**
	 * A helper method to determine whether a string is a positive integer
	 * @param str: a string input
	 * @return true if the string is a positive integer
	 */
	private static boolean isPosInt(String str) {
		if(allDigit(str)) {
			double num = Double.parseDouble(str.trim());
			return num <= Integer.MAX_VALUE && num > 0;
		}
		return false;
	}
	
	/**
	 * A helper method to determine if an input string contains only digits
	 * @param str: an input string
	 * @return true is the input contains only digits and '-' (negative number)
	 */
	private static boolean allDigit(String str) {
		boolean allDigit = true;
		String trimed = str.trim();
		//first determine whether the first character is '-' 
		if(!Character.isDigit(trimed.charAt(0)) && trimed.charAt(0) != '-') {
			allDigit = false;
		} else {
			//remaining characters should be only digits
			for (int i = 1; i < trimed.length(); i++) {
				if (!Character.isDigit(trimed.charAt(i))) {
					allDigit = false;
				   break;
				}
			}
		}
		return allDigit;
	}
	
	/**
	 * A method to display the menu of operations
	 */
	private static void displayHelp() {
		System.out.println("s (save)      l (load)       d (display)        p (picture)");
		System.out.println("f (forward)   b (backward)   j (jump)           t (test)");
		System.out.println("r (remove)    a (add after)  i (insert before)  e (retitle)");
		System.out.println("c (contains)  u (update)     x (exit)");	
	}
	
	/**
	 * This is loading a folder, which is not used in the app; but may be used
	 * @param imagelist
	 * @param inputstring
	 */
	private static void loadFolder(LinkedLoop<Image> imagelist, String inputstring) {
		File folder = new File(inputstring.substring(1).trim());
		if (!folder.exists()) {
			System.out.println("unable to load");
		} else {
			File[] images = folder.listFiles();
			for (File image : images) {
				Image newImage = new Image(image.getName());
				imagelist.add(newImage);
			}
		}
	}
	
	/**
	 * A method to load images that are listed in a file
	 * @param imagelist: list of images that new images should be added to
	 * @param inputstring: input
	 * @param imagepath: the path to the image folder
	 */
	private static void load(LinkedLoop<Image> imagelist, String inputstring, String imagepath) {
		File file = new File(inputstring.substring(1).trim());
		try {
			Scanner inFile = new Scanner(file);
			while(inFile.hasNextLine()) {
				String newLine = inFile.nextLine(); //each line in the file
				String [] strs = newLine.split(" "); //split at spaces
				String filename = strs[0]; //first word is filename
				int duration = Integer.parseInt(strs[1]); //second word is duration
																	//parse string to an integer
				String title = strs[2]; //remaining words are title
				for (int i = 3; i < strs.length; i++) {
					title += (" " + strs[i]);
				}
				//remove the quote symbols from the title
				String titleToShow = title.substring(1, title.length()-1);
				
				//if the image exist in the image folder, add the image with 
				//specified title and duration to the image list
				if(new File(imagepath + filename).exists()) {
					Image newImage = new Image(filename, titleToShow, duration);
					imagelist.add(newImage);
				//otherwise, display a message that the image does not exist
				} else {
					System.out.println("Warning: " + filename + 
							" is not in images folder");
				}	
			}
			inFile.close();
			//if user types in a file that does not exist, display a message
		} catch (FileNotFoundException e) {
			System.out.println("unable to load");
		}
					
	}
	
	/**
	 * A method to display the all the images in a list.
	 * @param imagelist: an image list
	 */
	private static void display(LinkedLoop<Image> imagelist) {
		
		if (imagelist.size() == 0) {
			System.out.println("no images");
		}
		
		Iterator<Image> itr = imagelist.iterator();
		while(itr.hasNext()) {
			Image anImage = itr.next();
			//Ideally, the toString in Image class should be able to 
			//format the string information. But we are not allowed to 
			//change the Image class. Silly. 
			System.out.println(anImage.getTitle() + " [" 
			+ anImage.getDuration() + "," + anImage.getFile() + "]");
		}
	}
	
	/**
	 * A method to show a single image (current) in a window
	 * @param imagelist: an image list
	 */
	private static void picture(LinkedLoop<Image> imagelist){
		try {
			imagelist.getCurrent().displayImage();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}
	}
	
	/**
	 * A method to test an image list by showing all images one by one with 
	 * specified duration
	 * @param imagelist: an image list
	 */
	private static void test(LinkedLoop<Image> imagelist) {
		if (imagelist.isEmpty()) {
			System.out.println("no images");
		} else {
			Iterator<Image> itr = imagelist.iterator();
			while(itr.hasNext()) {
				try {
					itr.next().displayImage();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * A method to show the previous, current and next image in an image list
	 * @param imagelist: an image list
	 * @throws EmptyLoopException
	 */
	private static void currentInfo(LinkedLoop<Image> imagelist) throws EmptyLoopException {
		//if there is only one image in the list, display it
		if(imagelist.size() == 1) {
			System.out.println("--> " + imagelist.getCurrent() + "<--");
			return;
		}
		//if there are two images in the list, display them
		if(imagelist.size() == 2) {
			System.out.println("--> " + imagelist.getCurrent() + "<--");
			imagelist.next();
			System.out.println("    " + imagelist.getCurrent() + "   ");
			return;
		}
		//if there are more than two images, display previous, current and next
		imagelist.previous();
		System.out.println("    " + imagelist.getCurrent() + "   ");
		imagelist.next();
		System.out.println("--> " + imagelist.getCurrent() + "<--");
		imagelist.next();
		System.out.println("    " + imagelist.getCurrent() + "   ");
		imagelist.previous();
		return;
	}
	
	/**
	 * A method to make the next image as the current image and display the
	 * previous, current and next image information
	 * @param imagelist: an image list
	 */
	private static void forward(LinkedLoop<Image> imagelist) {
		imagelist.next();
		try {
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}
	}

	/**
	 * A method to make the previous image as the current image and display the
	 * previous, current and next image information
	 * @param imagelist: an image list
	 */
	private static void backward(LinkedLoop<Image> imagelist) {
		imagelist.previous();
		try {
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}
	}
	
	/**
	 * A method to jump forward or backward and display the previous, current 
	 * and next image's information
	 * @param imagelist: an image list
	 * @param inputLine: user input that contain the steps of jump
	 */
	private static void jump(LinkedLoop<Image> imagelist, String inputLine) {
		//extract the steps (integer) from user input
		int steps = Integer.parseInt(inputLine.substring(1).trim());
		//jump forward if the step is a positive integer
		if (steps >= 0) {
			for (int i = 0; i < steps; i++) {
				imagelist.next();
			}
		}
		//jump backward if the step is a negative integer
		if (steps < 0) {
			for (int i = 0; i < (-steps); i++) {
				imagelist.previous();
			}
		}
		//display the current information
		try {
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * A method to remove current image from the list
	 * @param imagelist: an image list
	 */
	private static void remove(LinkedLoop<Image> imagelist) {
		try {
			imagelist.removeCurrent();
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}

	}
	
	/**
	 * A method to add an image after the current image
	 * @param imagelist: an image list
	 * @param inputLine: user input that defines which image to add
	 * @param imagepath: path to the folder to look for the new image
	 */
	private static void addAfterCurrent(LinkedLoop<Image> imagelist, String inputLine, String imagepath) {
		String remainder = inputLine.substring(1).trim();
		File file = new File(imagepath + remainder);
		if (file.exists()) {
			Image newImage = new Image(file.getName(), "", 5);
			imagelist.add(newImage);
			try {
				currentInfo(imagelist);
			} catch (EmptyLoopException e) {
				System.out.println("no images");
			}
		} else {
			System.out.println("Warning: " + remainder +" is not in images folder");
		}
	}
	
	/**
	 * A method that insert an image before the current one.
	 * @param imagelist: an image list
	 * @param inputLine: user input that defines which image to insert
	 * @param imagepath: the path to the image folder to look for the new image
	 */
	private static void insertBeforeCurrent(LinkedLoop<Image> imagelist, String inputLine, String imagepath) {
		imagelist.previous();
		addAfterCurrent(imagelist, inputLine, imagepath);
	}
	
	/**
	 * A method to find an image whose title contains a particular string
	 * @param imagelist: an image list
	 * @param inputLine: an input line that defines which string to look up
	 */
	private static void contains(LinkedLoop<Image> imagelist, String inputLine){
		boolean found = false;
		try {
			String remainder = inputLine.substring(1).trim();
			//check whether string is quoted
			String remainderRmQuote;
			if(remainder.startsWith("\"") && remainder.endsWith("\"")) {
				remainderRmQuote = remainder.substring(1, remainder.length() - 1);
			} else {
				remainderRmQuote = remainder;
			}
			
			for (int i = 0; i < imagelist.size(); i++) {
				if(imagelist.getCurrent().getTitle().contains(remainderRmQuote)) {
					found = true;
					break;
				} else {
					imagelist.next();
				}
			}
			
			if(found) {
				currentInfo(imagelist);
			} else {
				System.out.println("not found");
			}
		} catch (EmptyLoopException e) {
			System.out.println("no image");
		}	
	}
	
	/**
	 * A method to update the duration of current image
	 * @param imagelist: an image list
	 * @param inputLine: input line that specifies new duration time
	 */
	private static void update(LinkedLoop<Image> imagelist, String inputLine) {
		try {
			int duration = Integer.parseInt(inputLine.substring(1).trim());
			imagelist.getCurrent().setDuration(duration);
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no image");
		}
	}
	
	/**
	 * A method to edit the title of current image
	 * @param imagelist: an image list
	 * @param inputLine: input line that contains the new title
	 */
	private static void edit(LinkedLoop<Image> imagelist, String inputLine) {
		try {
			String remainder;
			String title;
			if(inputLine.length() > 1) {
				remainder = inputLine.substring(1).trim();
				if(remainder.startsWith("\"") && remainder.endsWith("\"")) {
					title = remainder.substring(1, remainder.length() - 1);
				} else {
					title = remainder;
				}
			} else {
				title = "";
			}

			imagelist.getCurrent().setTitle(title);
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no image");
		}
	}
	
	/**
	 * A method to save the images' information to a file
	 * @param imagelist: an image list
	 * @param inputLine: input line that contains the name of the file
	 */
	private static void save(LinkedLoop<Image> imagelist, String inputLine) {
		if (imagelist.isEmpty()) {
			System.out.println("no images to save");
			return;
		}
		
		try {
			String filename = inputLine.substring(1).trim();
			File file = new File(filename);
			if (file.exists()) {
				System.out.println("warning: file already exists, will be overwritten");
			}
			FileOutputStream output = new FileOutputStream(filename);
			PrintWriter writer = new PrintWriter(output);
			//iterate through the list and output each image's information
			Iterator<Image> itr = imagelist.iterator();
			while(itr.hasNext()) {
				Image anImage = itr.next();
				writer.println(anImage.getFile() + " " + anImage.getDuration() +
						" " + anImage.getTitle());
			}
			writer.close();
			output.close();
		} catch (FileNotFoundException e) {
				System.out.println("unable to save");
		} catch (IOException e) {
			
			System.out.println("unable to save");
		}
	}
}
