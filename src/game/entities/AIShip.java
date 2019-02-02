package game.entities;

import java.util.ArrayList;

import game.scores.Team;

public class AIShip extends Ship {
	
	private double range;
	private boolean slow;
	
	public AIShip(double x, double y, double angle, ArrayList<Cannon> cannons, double maxrvel, double health, double range, Team team, double scale) {
		super(x, y, angle, cannons, maxrvel, health, team, scale);
		this.range=range;
		this.slow=true;
	}

	public AIShip(AIShip enemy) {
		super(enemy);
		this.range=new Double(enemy.getRange());
		this.slow=new Boolean(enemy.slow);
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public boolean isSlow() {
		return slow;
	}

	public void setSlow(boolean slow) {
		this.slow = slow;
	}
	
}
