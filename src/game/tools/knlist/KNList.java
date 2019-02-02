package game.tools.knlist;

public class KNList<E> {
	
	private KeyNode<E> head;
	private int count;
	
	public KNList(){
		head=new KeyNode<E>(null, null);
		count=0;
	}
	
	public void add(String key, E data){
		KeyNode<E> n = new KeyNode<E>(key, data);
		KeyNode<E> current = head;
		n.setNext(null);
		while(current.next()!=null){
			current=current.next();
		}
		current.setNext(n);
		count++;
	}
	
	public void addReplace(String key, E data, boolean replace){
		KeyNode<E> n = new KeyNode<E>(key, data);
		KeyNode<E> current = head;
		KeyNode<E> bcurrent;
		boolean replaced=false;
		n.setNext(null);
		while(current.next()!=null){
			bcurrent=current;
			current=current.next();
			if(current.key().equals(key)){
				if(!replace){return;}
				bcurrent.setNext(n);
				replaced=true;
				break;
			}
		}
		if(!replaced){
			current.setNext(n);
		}
		count++;
	}
	
	public void add(E data){
		KeyNode<E> n = new KeyNode<E>(null, data);
		KeyNode<E> current = head;
		n.setNext(null);
		while(current.next()!=null){
			current=current.next();
		}
		current.setNext(n);
		count++;
	}
	
	public void add(E[] data){
		KNList<E> l= new KNList<E>();
		for(int s=1; s<data.length+1; s++){
			l.add(data[s-1]);
		}
		add(l);
	}
	
	public void add(KNList<E> data){
		if(data.isEmpty()){
			return;
		}
		for(int s=1;s<data.size()+1;s++){
			add(data.get(s));
		}
	}
	
	public void addOther(KNList<? extends E> data){
		if(data.isEmpty()){
			return;
		}
		for(int s=1;s<data.size()+1;s++){
			add(data.get(s));
		}
		
	}
	
	public void removeKey(String key){
		if(count==0){
			return;
		}
		KeyNode<E> current=head;
		KeyNode<E> bcurrent;
		for(int x = count;x>0;x--){
			bcurrent=current;
			current=current.next();
			if(current.key().equals(key)){
				bcurrent.setNext(current.next());
				count--;
			}
		}
	}
	
	public void removeData(E data){
		if(count==0){
			return;
		}
		KeyNode<E> current=head;
		KeyNode<E> bcurrent;
		for(int x = count;x>0;x--){
			bcurrent=current;
			current=current.next();
			if(current.data().equals(data)){
				bcurrent.setNext(current.next());
				count--;
			}
		}
	}
	
	public void remove(int index){
		if(count<index){
			return;
		}
		KeyNode<E> current=head;
		KeyNode<E> bcurrent=head;
		for(int x = index;x>0;x--){
			bcurrent=current;
			current=current.next();
		}
		bcurrent.setNext(current.next());
		count--;
	}
	
	public void set(int index, E data){
		if(count<index){
			return;
		}
		KeyNode<E> current=head;
		for(int x = index;x>0;x--){
			current=current.next();
		}
		current.setData(data);
	}
	
	public E get(String key){
		if(count==0){
			return null;
		}
		KeyNode<E> current=head;
		for(int x = count;x>0;x--){
			current=current.next();
			if(current.key().equals(key)){
				return current.data();
			}
		}
		return null;
	}
	
	public KeyNode<E> getNode(String key){
		if(count==0){
			return null;
		}
		KeyNode<E> current=head;
		for(int x = count;x>0;x--){
			current=current.next();
			if(current.key().equals(key)){
				return current;
			}
		}
		return null;
	}
	
	public E get(int index){
		if(count<index){
			return null;
		}
		KeyNode<E> current=head;
		for(int x = index;x>0;x--){
			current=current.next();
		}
		return current.data();
	}
	
	public String getKey(E data){
		if(count==0){
			return null;
		}
		KeyNode<E> current=head;
		for(int x = count;x>0;x--){
			current=current.next();
			if(current.data().equals(data)){
				return current.key();
			}
		}
		return null;
	}
	
	public KeyNode<E> getNode(int index){
		if(count<index){
			return null;
		}
		KeyNode<E> current=head;
		for(int x = index;x>0;x--){
			current=current.next();
		}
		return current;
	}
	
	public boolean containsKey(String key){
		if(count==0){
			return false;
		}
		KeyNode<E> current=head;
		for(int x = count;x>0;x--){
			current=current.next();
			if(current.key().equals(key)){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsData(E data){
		if(count==0){
			return false;
		}
		KeyNode<E> current=head;
		for(int x = 1;x<count+1;x++){
			current=current.next();
			if(current.data().equals(data)){
				return true;
			}
		}
		return false;
	}
	
	public int size(){
		return count;
	}
	
	public void clear(){
		head.setNext(null);
		count=0;
	}
	
	public boolean isEmpty(){
		if(count==0){
			return true;
		}
		return false;
	}
	
	public String returnAll(){
		String s = "";
		KeyNode<?> current = head.next();
		while(current!=null){
			s=String.join(s, ","+current.key()+":"+current.data());
			current=current.next();
		}
		return s;
	}
}
