package com.Component;

import com.tomo.*;
import com.tomo.Component;
import com.tomo.Window;
import com.util.Constants;

import java.awt.*;
import java.awt.geom.Line2D;


public class Grid extends Component {

    Camera camera;
    public int gridWidth, gridHeight;
    private int numYLines = 31;
    private int numXLines = 20;

    public Grid(){
        this.camera = Window.getWindow().getCurrentScene().camera;
        this.gridHeight = Constants.TILE_HEIGHT;
        this.gridWidth = Constants.TILE_WIDTH;
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void update(double dt){

    }

    @Override
    public void draw(Graphics2D g2){
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(Color.cyan);

        float bottom = Math.min(Constants.GROUND_Y - camera.position.y, Constants.SCREEN_HEIGHT); // Whichever is greater, the screen height or the position of the ground relative to the camera's (0, 0)
        float startx = (float) (Math.floor(camera.position.x / gridWidth) * gridWidth - camera.position.x);
        float starty = (float) (Math.floor(camera.position.y / gridHeight) * gridHeight - camera.position.y);

        for (int c = 0; c <= numYLines; c++){
            g2.draw(new Line2D.Float(startx, 0, startx, bottom));
            startx += gridWidth;
        }

        for (int r = 0; r <= numXLines; r++){
            if(camera.position.y + starty < Constants.GROUND_Y){ // If we are below the groundY
                g2.draw(new Line2D.Float(0, starty, Constants.SCREEN_WIDTH, starty));
                starty += gridHeight;
            }
        }
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
