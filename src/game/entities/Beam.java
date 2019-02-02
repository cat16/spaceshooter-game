package game.entities;

import java.awt.image.BufferedImage;

import game.Main;

public class Beam extends Entity {
	
	private double xd;
	private double yd;
	
	public Beam(double x, double y, double xd, double yd, double scale, BufferedImage image) {
		super(x, y, Main.rotate(x, y, xd, yd), scale, image);
		this.xd=xd;
		this.yd=yd;
	}

	public double getXd() {
		return xd;
	}

	public void setXd(double xd) {
		this.xd = xd;
	}

	public double getYd() {
		return yd;
	}

	public void setYd(double yd) {
		this.yd = yd;
	}
	
	public void update(double x, double y, double xd, double yd){
		this.setAngle(Main.rotate(x, y, xd, yd));
		this.setX(x);
		this.setY(y);
		this.xd=xd;
		this.yd=yd;
	}
	
	public double getDistance(){
		return Main.pythag(this.getX()-this.xd, this.getY()-this.yd);
	}
}
