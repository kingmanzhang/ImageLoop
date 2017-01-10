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
		boolean exit = false;
		boolean inputValid = false;
		String inputLine = "";
		char operation = ' ';
		String remainder = "";
		LinkedLoop<Image> imagelist = new LinkedLoop<>();
		final String IMAGEPATH = "images/";
		while (!exit || !inputValid) {
			System.out.print("enter command (? for help)> ");
			inputLine = scnr.nextLine().trim();
		   operation = inputLine.charAt(0);

			switch (operation) {
				case '?':
					inputValid = true;
					displayHelp();
					break;
				case 's':
					if(inputCheck(inputLine)) {
						inputValid = true;
						
						save(imagelist, inputLine);
					}
					break;
				case 'l':
					if(inputCheck(inputLine)) {
						inputValid = true;
						load(imagelist, inputLine, IMAGEPATH);
					}			
					break;
				case 'd':
					inputValid = true;
					display(imagelist);
					break;
				case 'p':
					inputValid = true;
					picture(imagelist);
					break;
				case 'f':
					inputValid = true;
					forward(imagelist);
					break;
				case 'b':
					inputValid = true;
					backward(imagelist);
					break;
				case 'j':
					if(inputLine.length() > 1 
							&& Character.isWhitespace(inputLine.charAt(1))
							&& isInt(inputLine.substring(1))) {
						inputValid = true;
						jump(imagelist, inputLine);
					}
					break;
				case 't':
					inputValid = true;
					test(imagelist);
					break;
				case 'r':
					inputValid = true;
					remove(imagelist);
					break;
				case 'a':
					if(inputCheck(inputLine)) {
						inputValid = true;
						addAfterCurrent(imagelist, inputLine, IMAGEPATH);
					}
					break;
				case 'i':
					if(inputCheck(inputLine)) {
						inputValid = true;
						insertBeforeCurrent(imagelist, inputLine, IMAGEPATH);
					}
					break;
				case 'e':
					inputValid = true;
					edit(imagelist, inputLine);
					break;
				case 'c':
					if (inputLine.length() > 1 &&
							Character.isWhitespace(inputLine.charAt(1))) {
						inputValid = true;
						contains(imagelist, inputLine);
					}
					break;
				case 'u':
					if(inputLine.length() > 1 
							&& Character.isWhitespace(inputLine.charAt(1))
							&& isPosInt(inputLine.substring(1))) {
						inputValid = true;
						update(imagelist, inputLine);
					}
					break;
				case 'x':
					inputValid = true;

					exit = true;
					break;
			}
			
		if(!inputValid) {
			System.out.println("invalid command");	
		} else {
System.out.println("pass");
		}
		
		if (operation != 'x') {
			inputValid = false;
		}
		}
	}

	
	/**
	 * A method to check whether the input is valid for operations s l a i
	 * @param str: input line to check
	 * @return true if the first character is followed by at least one space and the remainder of the line contains only letters, digits, underscores, periods, slashes and dashes
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
					String strTrimmed = substring.trim();//Maybe I should only trim the left end
					for (int i = 0; i < strTrimmed.length(); i++) {
						char aChar = strTrimmed.charAt(i);
						if(!Character.isLetter(aChar) && !Character.isDigit(aChar)
								&& aChar != '_' && aChar != '.' && aChar != '/' && aChar != '-') {	
							inputValid = false;
							break;
						}
				}
				}
			}
		return inputValid;
	}
	
	
	/**
	 * A helper method to determine whether a string input has internal whitespace
	 * @param str: a string to check
	 * @return true if a string has spaces, not considering leading and tailing spaces
	 */
	private static boolean isInt(String str) {
		if(allDigit(str)) {
			double num = Double.parseDouble(str.trim());
			return num <= Integer.MAX_VALUE && num > Integer.MIN_VALUE;
		}
		return false;
	}
	
	private static boolean isPosInt(String str) {
		if(allDigit(str)) {
			double num = Double.parseDouble(str.trim());
			return num <= Integer.MAX_VALUE && num > 0;
		}
		return false;
	}
	
	private static boolean allDigit(String str) {
		boolean allDigit = true;
		String trimed = str.trim();
		if(!Character.isDigit(trimed.charAt(0)) && trimed.charAt(0) != '-') {
			allDigit = false;
		} else {
			for (int i = 1; i < trimed.length(); i++) {
				if (!Character.isDigit(trimed.charAt(i))) {
					allDigit = false;
				   break;
				}
			}
		}
		return allDigit;
	}
	
	private static void displayHelp() {
		System.out.println("s (save)      l (load)       d (display)        p (picture)");
		System.out.println("f (forward)   b (backward)   j (jump)           t (test)");
		System.out.println("r (remove)    a (add after)  i (insert before)  e (retitle)");
		System.out.println("c (contains)  u (update)     x (exit)");	
	}
	
	/**
	 * This is loading a folder, which is not what I want to do
	 * Delete afterword
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
//System.out.println("total images in the list " + imagelist.size());
		}
	}
	
	private static void load(LinkedLoop<Image> imagelist, String inputstring, String imagepath) {
		File file = new File(inputstring.substring(1).trim());
		try {
			Scanner inFile = new Scanner(file);
			while(inFile.hasNextLine()) {
				String newLine = inFile.nextLine();
//System.out.println(newLine);
				String [] strs = newLine.split(" ");
				
				String filename = strs[0];
				int duration = Integer.parseInt(strs[1]);
				String title = strs[2];
				for (int i = 3; i < strs.length; i++) {
					title += (" " + strs[i]);
				}
				String titleToShow = title.substring(1, title.length()-1);
//System.out.println("file name " + filename + " duration " + duration + " title " + titleToShow );
				if(new File(imagepath + filename).exists()) {
					Image newImage = new Image(filename, titleToShow, duration);
					imagelist.add(newImage);
//System.out.println(filename + " exists in folder. Adding..." );

				} else {
					System.out.println("Warning: " + filename + " is not in images folder");
				}
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("unable to load");
		}
			
//System.out.println("total images in the list " + imagelist.size());
		
	}
	
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
	
	private static void picture(LinkedLoop<Image> imagelist){
		try {
			imagelist.getCurrent().displayImage();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}
	}
	
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
	
	private static void currentInfo(LinkedLoop<Image> imagelist) throws EmptyLoopException {
		if(imagelist.size() == 1 && imagelist.getCurrent() != null) {
			System.out.println("--> " + imagelist.getCurrent() + "<--");
			return;
		}
		if(imagelist.size() == 2 && imagelist.getCurrent() != null) {
			System.out.println("--> " + imagelist.getCurrent() + "<--");
			imagelist.next();
			System.out.println("    " + imagelist.getCurrent() + "   ");
			return;
		}
		imagelist.previous();
		System.out.println("    " + imagelist.getCurrent() + "   ");
		imagelist.next();
		System.out.println("--> " + imagelist.getCurrent() + "<--");
		imagelist.next();
		System.out.println("    " + imagelist.getCurrent() + "   ");
		imagelist.previous();
		return;
	}
	
	private static void forward(LinkedLoop<Image> imagelist) {
		imagelist.next();
		try {
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}
	}
	
	private static void backward(LinkedLoop<Image> imagelist) {
		imagelist.previous();
		try {
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}
	}
	
	private static void jump(LinkedLoop<Image> imagelist, String inputLine) {

		int steps = Integer.parseInt(inputLine.substring(1).trim());
		if (steps >= 0) {
			for (int i = 0; i < steps; i++) {
				imagelist.next();
			}
		}
		if (steps < 0) {
			for (int i = 0; i < (-steps); i++) {
				imagelist.previous();
			}
		}
		try {
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			e.printStackTrace();
		}	
	}
	
	private static void remove(LinkedLoop<Image> imagelist) {
		try {
			imagelist.removeCurrent();
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no images");
		}

	}
	
	private static void addAfterCurrent(LinkedLoop<Image> imagelist, String inputLine, String imagepath) {
		String remainder = inputLine.substring(1).trim();
//System.out.println("input is " + inputLine);
//System.out.println("remainder is " + remainder);
		File file;
		
		file = new File(imagepath + remainder);
		if (file.exists()) {
//System.out.println("file found");
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
	
	private static void insertBeforeCurrent(LinkedLoop<Image> imagelist, String inputLine, String imagepath) {
		imagelist.previous();
		addAfterCurrent(imagelist, inputLine, imagepath);
	}
	
	private static void contains(LinkedLoop<Image> imagelist, String inputLine){
		boolean found = false;
		try {
			String remainder = inputLine.substring(1).trim();
			//No need to check whether string is quoted
			String remainderRmQuote;
			if(remainder.startsWith("\"") && remainder.endsWith("\"")) {
				remainderRmQuote = remainder.substring(1, remainder.length() - 1);
			} else {
				remainderRmQuote = remainder;
			}
			
			for (int i = 0; i < imagelist.size(); i++) {
				if(imagelist.getCurrent().getTitle().contains(remainder)) {
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
	
	private static void update(LinkedLoop<Image> imagelist, String inputLine) {
		try {
			int duration = Integer.parseInt(inputLine.substring(1).trim());
			imagelist.getCurrent().setDuration(duration);
			currentInfo(imagelist);
		} catch (EmptyLoopException e) {
			System.out.println("no image");
		}
	}
	
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
			
			Iterator<Image> itr = imagelist.iterator();
			while(itr.hasNext()) {
				Image anImage = itr.next();
				writer.println(anImage.getFile() + " " + anImage.getDuration() + " " + anImage.getTitle());
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
