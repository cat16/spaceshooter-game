package game.tools.knlist;

public class KeyNode<E> {
	
	private KeyNode<E> NEXT;
	private E DATA;
	private String KEY;
	
	public KeyNode(String key, E data){
		KEY=key;
		DATA=data;
	}
	
	public E data(){
		return DATA;
	}
	
	public void setData(E data){
		DATA=data;
	}
	
	public String key(){
		return KEY;
	}
	
	public KeyNode<E> next(){
		return NEXT;
	}
	
	public void setNext(KeyNode<E> node){
		NEXT=node;
	}
	
}
