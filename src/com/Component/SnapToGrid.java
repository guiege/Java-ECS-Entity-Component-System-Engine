package com.Component;

import com.tomo.Component;
import com.tomo.GameObject;
import com.tomo.Window;
import com.util.Constants;
import com.util.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SnapToGrid extends Component {
    private float clickCooldownTime = 0.2f; // We don't want the user spam clicking and breaking the game, minecraft has this problem. This is a timer in between clicks
    private float clickCooldownLeft = 0.0f;
    int gridWidth;
    int gridHeight;

    public SnapToGrid(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void update(double dt) {
        clickCooldownLeft -= dt; // Updates time to detect when click cooldown is done

        if (this.gameObject.getComponent(Sprite.class) != null) {
            // Mouse position + camera position + position of the mouse once it moved = world position / gridWidth = number of spaces to the closest square
            float x = (float) Math.floor((Window.getWindow().mouseListener.x + Window.getWindow().getCurrentScene().camera.position.x + Window.getWindow().mouseListener.dx) / gridWidth);
            float y = (float) Math.floor((Window.getWindow().mouseListener.y + Window.getWindow().getCurrentScene().camera.position.y + Window.getWindow().mouseListener.dy) / gridWidth);

            this.gameObject.transform.position.x = x * gridWidth - Window.getWindow().getCurrentScene().camera.position.x; // Transformed so it is at the world space but local to the window
            this.gameObject.transform.position.y = y * gridWidth - Window.getWindow().getCurrentScene().camera.position.y;

            if(Window.getWindow().mouseListener.y < Constants.BUTTON_OFFSET_Y && // Fixes a problem we had with the buttonSprites where clicking on the button would place a block underneath
                    Window.getWindow().mouseListener.mousePressed &&             // by limiting the y values at which a block can be placed so that below the menu ui, it doesn't work
                    Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON1 &&
                    clickCooldownLeft < 0){
                clickCooldownLeft = clickCooldownTime;
                GameObject object = gameObject.copy();
                Window.getWindow().getCurrentScene().addGameObject(object);
                object.transform.position = new Vector2(x * gridWidth, y * gridHeight);
            }
        }
    }

    public void draw(Graphics2D g2) {
        Sprite sprite = gameObject.getComponent(Sprite.class); // Lifts the component off the player class if its a sprite class
        if (sprite != null) {
            float alpha = 0.5f; // Transparency effect before the object is placed to indicate its still placeable
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha); // SRC_OVER means over the entire image, a lot of this stuff is directly from awt api
            g2.setComposite(ac); // Setting the composite for the graphics2D so it has transparency
            g2.drawImage(sprite.image, (int) gameObject.transform.position.x, (int) gameObject.transform.position.y, sprite.width, sprite.height, null);
            alpha = 1.0f;
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(ac); // You have to reset the composite afterwards so that way it doesn't draw future things at half transparency
        }
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
