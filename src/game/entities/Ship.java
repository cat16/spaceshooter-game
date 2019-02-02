package game.entities;

import java.util.ArrayList;

import game.scores.Team;

public class Ship extends Killable {

	private boolean attack;
	private ArrayList<Cannon> cannons;
	private ArrayList<Beam> beams;
	private ArrayList<Item> cargo;
	private int beam;

	public Ship(Ship s) {
		super(s);
		ArrayList<Cannon> k = new ArrayList<Cannon>();
		for (int cs = 0; cs < s.getCannons().size(); cs++) {
			Cannon c = s.getCannons().get(cs);
			k.add(new Cannon(c));
		}
		this.cannons = k;
		this.attack = new Boolean(s.isAttack());
		this.beams = new ArrayList<Beam>();
		ArrayList<Item> e = new ArrayList<Item>();
		for (int cs = 0; cs < s.getCargo().size(); cs++) {
			Item i = s.getCargo().get(cs);
			e.add(new Item(i, false));
		}
		this.cargo = e;
	}

	public Ship(double x, double y, double angle, ArrayList<Cannon> cannons, double maxrvel, double health, Team team,
			double scale) {
		super(x, y, angle, maxrvel, team, scale, health);
		this.cannons = cannons;
		this.attack = false;
		this.beams = new ArrayList<Beam>();
		this.cargo = new ArrayList<Item>();
	}

	public ArrayList<Cannon> getCannons() {
		return cannons;
	}

	public void setCannons(ArrayList<Cannon> cannons) {
		this.cannons = cannons;
	}

	public void addCannon(Cannon c) {
		c.setOwner(this.getTeam().getAlliance());
		cannons.add(c);
	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
	}

	public ArrayList<Beam> getBeams() {
		return beams;
	}

	public void setBeams(ArrayList<Beam> beams) {
		this.beams = beams;
	}

	public ArrayList<Item> getCargo() {
		return cargo;
	}

	public void setCargo(ArrayList<Item> cargo) {
		this.cargo = cargo;
	}

	public int getBeam() {
		return beam;
	}

	public void setBeam(int beam) {
		this.beam = beam;
	}

}
