import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedLoopIterator<E> implements Iterator<E> {
	private DblListnode<E> current;
	private E result;
	
	
	protected LinkedLoopIterator(DblListnode<E> listnode) {
		this.current = listnode;
		
	}

	@Override
	public boolean hasNext() {
		
		return current.getNext().getData() != null;
	}

	@Override
	public E next() {
		if (this.hasNext()) {
			current = current.getNext();
			return current.getData();
		} else {
			return null;
		}
	}
	
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
