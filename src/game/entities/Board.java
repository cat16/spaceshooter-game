package game.entities;

import java.awt.image.BufferedImage;

import game.tools.knlist.KNList;

public class Board extends Entity {
	
	private KNList<Button> buttons;
	private KNList<Text> text;
	private boolean hidden;
	
	public Board(double x, double y, boolean hidden, double scale, BufferedImage image) {
		super(x, y, 0, scale, image);
		this.hidden=hidden;
		this.buttons=new KNList<Button>();
		this.text=new KNList<Text>();
		
	}

	public KNList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(KNList<Button> buttons) {
		this.buttons = buttons;
	}

	public KNList<Text> getText() {
		return text;
	}

	public void setText(KNList<Text> text) {
		this.text = text;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
