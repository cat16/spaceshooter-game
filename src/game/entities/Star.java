package game.entities;

public class Star {
	
	private double x;
	private double y;
	private double size;
	
	public Star(double x, double y, double size){
		this.x=x;
		this.y=y;
		this.size=size;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getSize(){
		return size;
	}
}
