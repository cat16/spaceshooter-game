package game.entities;

import java.awt.image.BufferedImage;

public class Laser extends Projectile {
	
	public Laser(double x, double y, double angle, double maxrvel, double time, double vel, double damage, double scale, BufferedImage image){
		super(x, y, angle, maxrvel, time, vel, damage, scale, image);
	}
	
	public Laser(double time, double vel, double damage, double scale, BufferedImage image){
		super(0, 0, 0, 0, time, vel, damage, scale, image);
	}
} 
