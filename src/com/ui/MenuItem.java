package com.ui;

import com.Component.SnapToGrid;
import com.Component.Sprite;
import com.tomo.Component;
import com.tomo.GameObject;
import com.tomo.LevelEditorScene;
import com.tomo.Window;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends Component {
    private int x, y, width, height;
    private Sprite buttonSprite, hoverSprite, menuItemSprite;
    public boolean isSelected = false;

    private int bufferX, bufferY;

    public MenuItem(int x, int y, int width, int height, Sprite buttonSprite, Sprite hoveredSprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonSprite = buttonSprite;
        this.hoverSprite = hoveredSprite;
    }

    @Override
    public void update(double dt) {
        if (!isSelected &&
                Window.getWindow().mouseListener.x > this.x && Window.getWindow().mouseListener.x <= this.x + this.width &&
                Window.getWindow().mouseListener.y > this.y && Window.getWindow().mouseListener.y <= this.y + height) {
            if (Window.getWindow().mouseListener.mousePressed && Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON1) {
                //Clicked inside of the button
                GameObject obj = gameObject.copy(); // Copies the gameObject and removes the menuItem class which is unwanted and adds the snapToGrid class which is wanted
                obj.removeComponent(MenuItem.class);
                LevelEditorScene scene = (LevelEditorScene) Window.getWindow().getCurrentScene();

                SnapToGrid snapToGrid = scene.swagCursor.getComponent(SnapToGrid.class);
                obj.addComponent(snapToGrid);
                scene.swagCursor = obj;
                isSelected = true;
            }
        }
    }

    @Override
    public void start(){
        menuItemSprite = gameObject.getComponent(Sprite.class);

        this.bufferX = (int)((this.width / 2.0) - (menuItemSprite.width / 2.0)); // Used to center the image in the box(it was really bothering me)
        this.bufferY = (int)((this.height / 2.0) - (menuItemSprite.height / 2.0));
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(this.buttonSprite.image, this.x, this.y, this.width, this.height, null);
        g2.drawImage(menuItemSprite.image, this.x + bufferX, this.y + bufferY, menuItemSprite.width, menuItemSprite.height, null);
        if(isSelected){
            g2.drawImage(this.hoverSprite.image, this.x, this.y, this.width, this.height, null);
        }

    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
