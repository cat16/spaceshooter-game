package game.entities;

import java.awt.Color;

public class Text {
	
	private String text;
	private double x;
	private double y;
	private String type;
	private double time;
	private double alpha;
	private double scale;
	private Color color;
	private double alphaTime;
	
	public Text(String text, double x, double y, String type, double time, Color color, double scale){
		this.text=text;
		this.x=x;
		this.y=y;
		this.type=type;
		this.time=time;
		this.alpha=1;
		this.alphaTime=time;
		this.color=color;
		this.scale=scale;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getAlphaTime() {
		return alphaTime;
	}
}
