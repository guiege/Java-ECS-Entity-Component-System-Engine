package com.tomo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ML extends MouseAdapter {

    public boolean mousePressed = false;
    public boolean mouseDragged = false;
    public int mouseButton = -1;
    public float x = -1.0f, y = -1.0f;
    public float dx = -1.0f, dy = -1.0f; // Keeps track of the distance travelled since the last frame when the mouse is dragged

    @Override
    public void mousePressed(MouseEvent mouseEvent){
        this.mousePressed = true;
        this.mouseButton = mouseEvent.getButton(); // Listens for mouse input
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent){
        this.mousePressed = false;
        this.mouseDragged = false;
        this.dx = 0;
        this.dy = 0; // No space to drag if you arent dragging the mouse
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent){
        this.x = mouseEvent.getX();
        this.y = mouseEvent.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent){
        this.mouseDragged = true;
        this.dx = mouseEvent.getX() - this.x; // Current position minus the position when dragging started
        this.dy = mouseEvent.getY() - this.y;

    }

}
