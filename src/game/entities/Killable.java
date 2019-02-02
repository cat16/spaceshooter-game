package game.entities;

import java.awt.image.BufferedImage;

import game.scores.Team;

public abstract class Killable extends Moveable {
	
	public double health;
	private Team team;
	
	public Killable(Killable k){
		super(k);
		this.team=new Team(k.getTeam());
		this.health=new Double(k.getHealth());
	}
	
	public Killable(double x, double y, double angle, double maxrvel, double health, Team t, double scale, BufferedImage image) {
		super(x, y, angle, maxrvel, scale, image);
		this.health=health;
		this.team=t;
	}
	
	public Killable(double x, double y, double angle, double maxrvel, Team t, double scale, double health) {
		super(x, y, angle, maxrvel, scale);
		this.health=health;
		this.team=t;
	}
	
	public void setHealth(double health){
		this.health=health;
	}
	
	public double getHealth(){
		return health;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
