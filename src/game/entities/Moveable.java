package game.entities;

import java.awt.image.BufferedImage;

public class Moveable extends Entity {
	
	private double xvel;
	private double yvel;
	private double rvel;
	private double maxrvel;
	
	public Moveable(Moveable e){
		super(e);
		this.rvel=new Double(e.getRVel());
		this.xvel=new Double(e.getXVel());
		this.yvel=new Double(e.getYVel());
		this.maxrvel=new Double(e.getMaxrvel());
	}
	
	public Moveable(double x, double y, double angle, double maxrvel, double scale) {
		super(x, y, angle, scale);
		this.rvel=0;
		this.xvel=0;
		this.yvel=0;
		this.maxrvel=maxrvel;
	}
	
	public Moveable(double x, double y, double angle, double maxrvel, double scale, BufferedImage image) {
		super(x, y, angle, scale, image);
		this.rvel=0;
		this.xvel=0;
		this.yvel=0;
		this.maxrvel=maxrvel;
	}

	public double getXVel() {
		return xvel;
	}

	public void setXVel(double xvel) {
		this.xvel = xvel;
	}

	public double getYVel() {
		return yvel;
	}

	public void setYVel(double yvel) {
		this.yvel = yvel;
	}

	public double getRVel() {
		return rvel;
	}

	public void setRVel(double rvel) {
		this.rvel = rvel;
	}
	
	public double getVel(){
		return Math.sqrt((xvel*xvel)+(yvel*yvel));
	}

	public double getMaxrvel() {
		return maxrvel;
	}

	public void setMaxrvel(double maxrvel) {
		this.maxrvel = maxrvel;
	}
}
