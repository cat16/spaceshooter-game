package game.entities;

import java.awt.image.BufferedImage;

public class Projectile extends Moveable {
	
	private double time;
	private double damage;
	private double vel2;
	private int owner;
	
	public Projectile(Projectile e){
		super(e);
		this.time=new Double(e.getTime());
		this.damage=new Double(e.getDamage());
		this.vel2=new Double(e.getVel2());
		this.owner=new Integer(e.getOwner());
	}
	
	public Projectile(double x, double y, double angle, double maxrvel, double time, double vel, double damage, double scale, BufferedImage image){
		super(x, y, angle, maxrvel, scale, image);
		this.time=time;
		this.damage=damage;
		this.setXVel(Math.cos(angle)*vel);
		this.setYVel(Math.sin(angle)*vel);
		this.vel2=vel;
	}
	
	public double getTime(){
		return time;
	}
	
	public void setTime(double time){
		this.time=time;
	}

	public double getDamage() {
		return damage;
	}
	
	public void setDamage(double damage) {
		this.damage=damage;
	}

	public double getVel2() {
		return vel2;
	}

	public void setVel2(double vel2) {
		this.vel2 = vel2;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
}
