package game.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

import game.scores.Team;

public class Asteroid extends Killable {
	
	private double distance;
	
	public Asteroid(double x, double y, double angle, double maxrvel, double health, double distance, double scale, BufferedImage image) {
		super(x, y, angle, maxrvel, health, new Team(Color.gray, -3), scale, image);
		this.distance=distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	/*@Override
	public Rectangle getBounds(){
		double scale;
		BufferedImage image = this.getImage();
		if(image==null){
			image=Main.noimage;
		}
		if(image.getWidth()>image.getHeight()){
			scale = (float) ((Main.defaultsize*this.getScale())/image.getWidth());
		}else{
			scale = (float) ((Main.defaultsize*this.getScale())/image.getHeight());
		}
		double newW = (image.getWidth() * scale);
		double newH = (image.getHeight() * scale);
		double newX = (this.getX())-(newW/2);
		double newY = (this.getY())-(newH/2);
		if(this.getDistance()>0){
			newX-=Main.xoffset*this.getDistance();
			newY-=Main.xoffset*this.getDistance();
		}else{
			newX-=Main.xoffset/Math.abs(this.getDistance()-1);
			newY-=Main.xoffset/Math.abs(this.getDistance()-1);
		}
		double xa = this.getX();
		double ya = this.getY();
		double theta = this.getAngle();
		KNList<Double> xs = new KNList<Double>();
		KNList<Double> ys = new KNList<Double>();
		
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
	}*/
}
