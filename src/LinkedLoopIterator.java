///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  ImageLoopEditor.java
// File:             LinkedLoopIterator.java
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


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that defines an iterator of the nodes. It moves from the current
 * node to the next one. If the next one is null, the current is the tail of 
 * the list.  
 * @author Xingmin Zhang
 *
 * @param <E>
 */
public class LinkedLoopIterator<E> implements Iterator<E> {
	
	private DblListnode<E> current;  //current node
	private E result; //next node
	
	
	protected LinkedLoopIterator(DblListnode<E> listnode) {
		
		this.current = listnode;
		
	}

	@Override
	/**
	 * A method to determine whether there is a next node. 
	 * If the next is null (head), the current is tail and should return false.
	 * @return false if the next node's item is null (head). 
	 */
	public boolean hasNext() {
		
		return current.getNext().getData() != null;
		
	}

	@Override
	/**
	 * A method to return the next item. 
	 * @return the next item. 
	 */
	public E next() {
		
		if (this.hasNext()) {
			current = current.getNext();
			return current.getData();
		} else {
			return null;
		}
		
	}
	
	
	@Override
	/**
	 * Method NOT supported. 
	 */
	public void remove() {
		
		throw new UnsupportedOperationException();
		
	}

}
