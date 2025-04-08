package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	//Checks if key was pressed. If pressed updates value to true.
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();//returns integer associated with key press
		
		//VK_? <- ? being any key. Starting with W for WASD
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
	}

	@Override
	//Checks if key was released. If no signal updates value to false.
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

}
