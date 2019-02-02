package game.entities;

import java.awt.Color;

import game.Main;
public class Button extends Entity {
	
	private Text text;
	private boolean hidden;
	private int state;
	public static final int UNPRESSED=0;
	public static final int HIGHLIGHTED=1;
	public static final int PRESSED=2;
	private double alpha;
	
	public Button(){
		super(0, 0, 0, 0, null);
		this.text=new Text("label", 0, 0, "space", 0, Color.white, 0);
		this.state=0;
	}
	
	public Button(double x, double y, double angle, String text, boolean hide, double alpha, double scale) {
		super(x, y, angle, scale, Main.button_unpressed);
		this.text=new Text(text, x, y, "label", 0, Color.white, scale*10);
		this.text.setAlpha(0.5);
		this.hidden=hide;
		this.state=0;
		this.alpha=alpha;
	}
	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void update() {
		switch(this.state){
		case 0:
			this.setImage(Main.button_unpressed);
			break;
		case 1:
			this.setImage(Main.button_highlighted);
			break;
		case 2:
			this.setImage(Main.button_pressed);
			break;
		default:
			this.setImage(Main.button_unpressed);
			break;
		}
		this.text.setX(this.getX());
		this.text.setY(this.getY());
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
}
