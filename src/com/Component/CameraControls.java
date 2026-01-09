package com.Component;

import com.tomo.Component;
import com.tomo.Window;

import java.awt.event.MouseEvent;

public class CameraControls extends Component {

    public float prevMx, prevMy;

    public CameraControls(){
        prevMx = 0.0f;
        prevMy = 0.0f;
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void update(double dt){
        if(Window.getWindow().mouseListener.mousePressed && Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON3){

            float dx = (Window.getWindow().mouseListener.x + Window.getWindow().mouseListener.dx - prevMx);
            float dy = (Window.getWindow().mouseListener.y + Window.getWindow().mouseListener.dy) - prevMy;

            Window.getWindow().getCurrentScene().camera.position.x -= dx;
            Window.getWindow().getCurrentScene().camera.position.y -= dy;
        }
        prevMx = Window.getWindow().mouseListener.x + Window.getWindow().mouseListener.dx;
        prevMy = Window.getWindow().mouseListener.y + Window.getWindow().mouseListener.dy;
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
