package game.entities;

public class Item extends Entity {
	
	private String name;
	private String location;
	private boolean visible;
	
	public Item(Item i, boolean visible){
		super(i);
		this.name=new String(i.getName());
		this.visible=visible;
	}
	
	public Item(String name, double x, double y, boolean visible, double scale, String location) {
		super(x, y, 0, scale);
		this.name=name;
		this.location=location;
		this.visible=visible;
	}
	
	public Item(String name, boolean visible, double scale, String location) {
		super(0, 0, 0, scale);
		this.name=name;
		this.location=location;
		this.visible=visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation(){
		return location;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
