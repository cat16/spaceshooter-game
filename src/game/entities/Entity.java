package game.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Main;
import game.tools.Rectangle;

public abstract class Entity {
	
	private double x;
	private double y;
	private double angle;
	private BufferedImage image;
	private double scale;
	private Chunk chunk;
	
	public Entity(Entity e){
		this.x=new Double(e.getX());
		this.y=new Double(e.getY());
		this.angle=new Double(e.getAngle());
		this.image=e.getImage();
		this.scale=new Double(e.getScale());
	}
	
	public Entity(double x, double y, double angle, double scale){
		this.x=x;
		this.y=y;
		this.angle=angle;
		this.scale=scale;
	}
	
	public Entity(double x, double y, double angle, double scale, BufferedImage image){
		this.x=x;
		this.y=y;
		this.angle=angle;
		this.image=image;
		this.scale=scale;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public Rectangle getBounds(){
		double scale;
		if(image==null){
			image=Main.noimage;
		}
		if(image.getWidth()>image.getHeight()){
			scale = (float) ((Main.defaultsize*this.scale)/image.getWidth());
		}else{
			scale = (float) ((Main.defaultsize*this.scale)/image.getHeight());
		}
		double newW = (image.getWidth() * scale);
		double newH = (image.getHeight() * scale);
		double newX = (this.getX())-(newW/2);
		double newY = (this.getY())-(newH/2);
		double xa = this.getX();
		double ya = this.getY();
		double theta = this.getAngle();
		ArrayList<Double> xs = new ArrayList<Double>();
		ArrayList<Double> ys = new ArrayList<Double>();
		
		xs.add(xa+(newX-xa)*Math.cos(theta)+(newY-ya)*Math.sin(theta));
		xs.add(xa+(newX+newW-xa)*Math.cos(theta)+(newY-ya)*Math.sin(theta));
		xs.add(xa+(newX-xa)*Math.cos(theta)+(newY+newH-ya)*Math.sin(theta));
		xs.add(xa+(newX+newW-xa)*Math.cos(theta)+(newY+newH-ya)*Math.sin(theta));
		
		ys.add(ya-(newX-xa)*Math.sin(theta)+(newY-ya)*Math.cos(theta));
		ys.add(ya-(newX+newW-xa)*Math.sin(theta)+(newY-ya)*Math.cos(theta));
		ys.add(ya-(newX-xa)*Math.sin(theta)+(newY+newH-ya)*Math.cos(theta));
		ys.add(ya-(newX+newW-xa)*Math.sin(theta)+(newY+newH-ya)*Math.cos(theta));
		
		Rectangle r = new Rectangle((int)Main.least(xs, false), (int)Main.least(ys, false), (int)(Main.least(xs, true)-Main.least(xs, false)), (int)(Main.least(ys, true)-Main.least(ys, false)));
		return r;
	}
	
	public void setChunk(Chunk c){
		this.chunk=c;
	}
	
	public Chunk getChunk(){
		return chunk;
	}
}
