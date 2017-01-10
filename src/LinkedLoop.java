import java.util.Iterator;

public class LinkedLoop<E> implements LoopADT<E> {
	DblListnode<E> current; 
	DblListnode<E> head; 
	DblListnode<E> tail; 
	
	int itemCount;
	
	public LinkedLoop () {
		this.head = new DblListnode<E>(null);
		this.tail = head;
		head.setNext(tail);
		tail.setPrev(head);
		this.current = null; 
		itemCount = 0;
	}

	@Override
	public void add(E item) {
		DblListnode<E> temp = new DblListnode<E>(item);
		if (current == null || current == tail) {
		   tail.setNext(temp);
		   temp.setPrev(tail);
		   tail = temp;
		   tail.setNext(head);
		   head.setPrev(tail);
		   current = tail;
			itemCount++;
			if (itemCount == 1) {
				current = head.getNext();
			}
		} else {
			temp.setNext(current.getNext());
			current.getNext().setPrev(temp);
			current.setNext(temp);
			temp.setPrev(current);
			current = temp;
			itemCount++;
		}
		
	   
	}

	@Override
	public E getCurrent() throws EmptyLoopException {
		if (itemCount == 0) {
			throw new EmptyLoopException();
		}
		return current.getData();
	}

	@Override
	public E removeCurrent() throws EmptyLoopException {
		if(itemCount == 0) {
			throw new EmptyLoopException();
		}
		/*
		 //this is not a special case
		if(itemCount == 1) {
			current = null;
			return head.getNext().getData();
		}
		*/

		//if removing the tail node, move the tail to the previous node and reset the link between tail and head
		
		
		DblListnode<E> removed = current;;
	   DblListnode<E> pre = current.getPrev();
	   DblListnode<E> after = current.getNext();
	   //don't move the tail unless the node to be removed is tail
	   if(removed == tail) {
			tail = pre;
		}

	   next(); //before breaking the links of current, move current to the next position
	   pre.setNext(after);
	   after.setPrev(pre);
	   itemCount--;
		
  
	   return removed.getData();
	}

	@Override
	public void next() {
		if (itemCount == 0) {
			current = null;
			return;
		}
		
		current = current.getNext();
		
		if(current == head) {
			current = head.getNext();
		}
	}

	@Override
	public void previous() {
		if (itemCount == 0) {
			current = null;
			return;
		}
		current = current.getPrev();
		if (current == head) { //skip head
			current = tail;
		}
		return;
	}

	@Override
	public boolean isEmpty() {
		
		return itemCount == 0;
	}

	@Override
	public int size() {
		
		return itemCount;
	}

	@Override
	public Iterator<E> iterator() {
		LinkedLoopIterator<E> itr = new LinkedLoopIterator<E>(head);
		return itr;
	}
	
	
	

}
