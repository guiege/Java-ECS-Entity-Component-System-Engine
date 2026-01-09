package com.tomo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL extends KeyAdapter implements KeyListener {
    private boolean keyPressed[] = new boolean[128]; // 128 Characters in the ASCII character set

    @Override
    public void keyPressed(KeyEvent e)
    {
        keyPressed[e.getKeyCode()] = true; // Gets the KeyCode when a key is pressed and sets that key as active
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyPressed[e.getKeyCode()] = false; // Deactivates a key
    }

    public boolean isKeyPressed(int keyCode)
    {
        return keyPressed[keyCode]; // Returns state of specified keyCode
    }


}
