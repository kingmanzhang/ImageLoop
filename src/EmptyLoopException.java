///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  ImageLoopEditor.java
// File:             EmptyLoopException.java
// Semester:         
//
// Author:           Xingmin Zhang xzhang66@wisc.edu
// CS Login:         (your login name)
// Lecturer's Name:  (name of your lecturer)
// Lab Section:      (your lab section number)
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          None
//                   
//
// Online sources:   none
//////////////////////////// 80 columns wide //////////////////////////////////


/**
 * The EmptyLoopException class defines a checked Exception that will be thrown
 * when an empty loop is accessed by a method. 
 * @author Xingmin Zhang
 *
 */
public class EmptyLoopException extends Exception {
	
	public EmptyLoopException() {
		super();
	}
	
	public EmptyLoopException(String str) {
		super(str);
	}

}
