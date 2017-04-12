package ar.com.larreta.rest.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JSONableCollectionBody<E> extends JSONable implements List<E> {
	
	private List<E> source;

	public JSONableCollectionBody(List<E> source){
		this();
		if (source!=null){
			this.source = source;
		}
	}
	
	public JSONableCollectionBody(){
		this.source = new ArrayList<E>();
	}
	
	@Override
	public int size() {
		return source.size();
	}

	@Override
	public boolean isEmpty() {
		return source.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return source.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return source.iterator();
	}

	@Override
	public Object[] toArray() {
		return source.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return source.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return source.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return source.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return source.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return source.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return source.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return source.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return source.retainAll(c);
	}

	@Override
	public void clear() {
		source.clear();
	}

	@Override
	public E get(int index) {
		return source.get(index);
	}

	@Override
	public E set(int index, E element) {
		return source.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		source.add(index, element);
	}

	@Override
	public E remove(int index) {
		return source.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return source.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return source.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return source.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return source.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return source.subList(fromIndex, toIndex);
	}

}
