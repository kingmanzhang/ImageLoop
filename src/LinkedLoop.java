///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  ImageLoopEditor.java
// File:             LinkedLoop.java
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

/**
 * A LinkedLoop class that implements the LoopADT. The nodes are circularly 
 * double linked. The list also has an empty head node.  
 * @author Xingmin Zhang
 *
 * @param <E>
 */
public class LinkedLoop<E> implements LoopADT<E> {
	
	DblListnode<E> current; //current node
	DblListnode<E> head; //a pointer to head node
	DblListnode<E> tail; //a pointer to tail node
	int itemCount; //keep track of nodes
	
	public LinkedLoop () {
		this.head = new DblListnode<E>(null); //initiate head to an null node
		this.tail = head; //tail also point to the node
		head.setNext(tail);//head node is self linked
		tail.setPrev(head);
		this.current = null;//current node is null when list is empty
		itemCount = 0;//initiate node (excluding dummy head node)
	}

	@Override
	/**
	 * A method to add nodes to the list. New nodes is added right after the
	 * current node. 
	 * @parameter an item to add to list. 
	 */
	public void add(E item) {
		
		DblListnode<E> temp = new DblListnode<E>(item);
		
		//if an item is null, it cannot be added. This is required to make
		//sure the iterator works, as the iterator thinks null only occurs at
		//head node. 
		if (item == null) {
			System.out.println("cannot add null item");
			return;
		}
		
		//if list is empty or current node is at the tail
		if (current == null || current == tail) {
			//connect new item to the tail node
		   tail.setNext(temp);
		   temp.setPrev(tail);
		   //move tail to the new item
		   tail = temp;
		   //reset connection from new tail to the head
		   tail.setNext(head);
		   head.setPrev(tail);
		   //set the new item as the current node
		   current = tail;
			itemCount++;
		//if list is not empty and current is not the last node of the list
		} else {
			//connect the new node to the next node after current
			temp.setNext(current.getNext());
			current.getNext().setPrev(temp);
			//connect the new node to current
			current.setNext(temp);
			temp.setPrev(current);
			//set the new node as the current 
			current = temp;
			itemCount++;
		}
		
	   
	}
	

	@Override
	/**
	 * A method to return the current node
	 * @return the current node. Throw EmptyLoopException is the list is empty.
	 */
	public E getCurrent() throws EmptyLoopException {
		
		if (itemCount == 0) {
			throw new EmptyLoopException();
		}
		return current.getData();
		
	}
	

	@Override
	/**
	 * A method to remove the current node. 
	 * @return the removed node. Throw an EmptyLoopException is the list 
	 * is empty.
	 */
	public E removeCurrent() throws EmptyLoopException {
		
		if(itemCount == 0) {
			throw new EmptyLoopException();
		}
	
		//keep a copy of the removed node, the node before and after the 
		//removed node
		DblListnode<E> removed = current;;
	   DblListnode<E> pre = current.getPrev();
	   DblListnode<E> after = current.getNext();
	   //don't move the tail unless the node to be removed is tail
	   if(removed == tail) {
			tail = pre;
		}
	   next(); //before breaking the links of current, 
	           //move current to the next position
	   pre.setNext(after); //connect the node before and after the removed node
	   after.setPrev(pre);
	   itemCount--;

	   return removed.getData();
	   
	}

	
	@Override
	/**
	 * A method to move the current node to the next one
	 */
	public void next() {
		
		if (itemCount == 0) {
			current = null;
			return;
		}
		//move current node
		current = current.getNext();
		//if current is the tail, the next one if the first node (skip the head)
		if(current == head) {
			current = head.getNext();
		}
		
	}

	
	@Override
	/**
	 * A method to move the current node to the previous one
	 */
	public void previous() {
		if (itemCount == 0) {
			current = null;
			return;
		}
		
		//move the current node to the previous one
		current = current.getPrev();
		//if the current node is the first node, the previous one is the tail 
		//(skip the head)
		if (current == head) {
			current = tail;
		}
		return;
	}
	

	@Override
	/**
	 * A method to determine whether the list is empty.
	 * @return true is there is 0 items (head node does not count).
	 */
	public boolean isEmpty() {
		
		return itemCount == 0;
	}

	
	@Override
	/**
	 * A method to get the size of the list. 
	 * @return the count of nodes (head does not count).
	 */
	public int size() {
		
		return itemCount;
	}
	

	@Override
	/**
	 * A method to get an iterator of nodes. The iterator iterates from the 
	 * first item (node after head) to the last item. 
	 * @parameter: the head node. 
	 * @return an iterator for the list.
	 * 
	 */
	public Iterator<E> iterator() {
		
		LinkedLoopIterator<E> itr = new LinkedLoopIterator<E>(head);
		return itr;
		
	}
	
}
