package game.scores;

import java.awt.Color;

import game.Main;

public class Team {
	
	private Color color;
	private int alliance;
	
	public Team(Color c, int a){
		this.color=c;
		if(a==0){
			this.alliance=Main.alcount;
		}else{
			this.alliance=a;
		}
		Main.alcount++;
	}

	public Team(Team t) {
		this.color=t.getColor();
		this.alliance=new Integer(t.getAlliance());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int a) {
		if(a==0){
			this.alliance=Main.alcount;
		}else{
			this.alliance=a;
		}
		Main.alcount++;
	}
	
}
