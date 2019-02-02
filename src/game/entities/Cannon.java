package game.entities;

import java.awt.image.BufferedImage;

public class Cannon extends Moveable {
	
	private Projectile type;
	private double baseAngle;
	private int owner;
	private double cooldown;
	private double startcool;
	
	public Cannon(double x, double y, double angle, Projectile type, double startcool, double scale) {
		super(x, y, angle, 0, scale);
		this.type = type;
		this.baseAngle=angle;
		this.startcool=startcool;
	}
	
	public Cannon(double x, double y, double angle, Projectile type, double startcool, double scale, BufferedImage image) {
		super(x, y, angle, 0, scale, image);
		this.type = type;
		this.baseAngle=angle;
		this.startcool=startcool;
	}

	public Cannon(Cannon c) {
		super(c);
		this.type=new Projectile(c.getType());
		this.baseAngle=new Double(c.getBaseAngle());
		this.owner=new Integer(c.getOwner());
		this.cooldown=new Double(c.getCooldown());
		this.startcool=new Double(c.getStartcool());
	}

	public Projectile getType() {
		return type;
	}

	public void setType(Projectile type) {
		this.type = type;
	}
	
	public Projectile createProjectile(BufferedImage image){
		Projectile p = new Projectile(this.getType());
		p.setAngle(this.getAngle()+this.baseAngle);
		p.setX(this.getX());
		p.setY(this.getY());
		p.setImage(image);
		p.setOwner(this.owner);
		return p;
	}
	
	public void setMove(Ship s){
		this.setX(s.getX());
		this.setY(s.getY());
	}

	public double getBaseAngle() {
		return baseAngle;
	}

	public void setBaseAngle(double baseAngle) {
		this.baseAngle = baseAngle;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public double getStartcool() {
		return startcool;
	}
}
