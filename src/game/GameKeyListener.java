package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.tools.knlist.KNList;

public class GameKeyListener implements KeyListener {
	
	public static KNList<Integer> special = new KNList<Integer>();
	
	public static void setSpecials(){
		special.add("space", KeyEvent.VK_SPACE);
		special.add("esc", KeyEvent.VK_ESCAPE);
		special.add("shift", KeyEvent.VK_SHIFT);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		String KEY_PRESSED=Character.toString((char)key.getKeyCode());
		if(special.containsData(key.getKeyCode())){
			if(!Main.keys.contains(special.getKey(key.getKeyCode()))){
				Main.keys.add(special.getKey(key.getKeyCode()));
			}
		}else{
			if(key.getKeyCode()==KeyEvent.VK_SHIFT){
				if(!Main.keys.contains(KEY_PRESSED.toUpperCase())){
					Main.keys.add(KEY_PRESSED.toUpperCase());
				}
			}else{
				if(!Main.keys.contains(KEY_PRESSED.toLowerCase())){
					Main.keys.add(KEY_PRESSED.toLowerCase());
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		String KEY_RELEASED=Character.toString((char)key.getKeyCode());
		if(special.containsData(key.getKeyCode())){
			Main.keys.remove(special.getKey(key.getKeyCode()));
		}else{
			Main.keys.remove(KEY_RELEASED.toLowerCase());
			Main.keys.remove(KEY_RELEASED.toUpperCase());
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		//String KEY_TYPED=Character.toString(key.getKeyChar());
		//System.out.println(KEY_TYPED);
	}

}
