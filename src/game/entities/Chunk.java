package game.entities;

import java.util.ArrayList;

import game.tools.Rectangle;

public class Chunk {
	
	private int x;
	private int y;
	private int size;
	private ArrayList<Star> stars;
	
	public Chunk(int x, int y, int size){
		this.x=x;
		this.y=y;
		this.size=size;
		this.stars=new ArrayList<Star>();
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public ArrayList<Star> getStars(){
		return stars;
	}
	
	public int getSide(){
		return size;
	}
	
	public Rectangle getRect(){
		double newW = this.size;
		double newH = this.size;
		double newX = (this.getX())-(newW/2);
		double newY = (this.getY())-(newH/2);
		return new Rectangle(newX, newY, newW, newH);
	}
}
