///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  ImageLoopEditor.java
// File:             DblListnode.java
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
 * Generic doubly linked list node. It serves as the basic building block for 
 * storing data in doubly linked chains of nodes.
 * 
 * <b>Do not modify this file in any way!</b>
 */
class DblListnode<E> {
	private DblListnode<E> prev;   // connection to previous node
	private E data;                // data to be stored 
	private DblListnode<E> next;   // connnection to next node

	/**
	 * Constructs a new list nodes with no links to neighboring nodes.
	 * @param data the data to be stored in this node
	 */
	DblListnode(E data) {
		this(null, data, null);
	}
	
	/**
	 * Constructs a new list node with links to neighboring nodes.
	 * @param prev the node before this one
	 * @param data the data to be stored in this node
	 * @param next the node after this one
	 */
	DblListnode(DblListnode<E> prev, E data, DblListnode<E> next) {
		this.prev = prev;
		this.data = data;
		this.next = next;
	}

	/**
	 * Returns the current data.
	 * @return the current data
	 */
	E getData() {
		return data;
	}

	/**
	 * Returns the current next node.
	 * @return the current next node
	 */
	DblListnode<E> getNext() {
		return next;
	}

	/**
	 * Returns the current previous node.
	 * @return the current previous node
	 */
	DblListnode<E> getPrev() {
		return prev;
	}

	/**
	 * Sets the data to the given new value.
	 * @param data the new data
	 */
	void setData(E data) {
		this.data = data;
	}

	/**
	 * Sets the next node to the given new value.
	 * @param next the new next node
	 */
	void setNext(DblListnode<E> next) {
		this.next = next;
	}

	/**
	 * Sets the previous node to the given new value.
	 * @param prev the new previous node
	 */
	void setPrev(DblListnode<E> prev) {
		this.prev = prev;
	}
}
