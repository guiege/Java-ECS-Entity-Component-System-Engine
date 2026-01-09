package com.ui;

import com.Component.BoxBounds;
import com.Component.Sprite;
import com.Component.Spritesheet;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.tomo.Camera;
import com.tomo.Component;
import com.tomo.GameObject;
import com.tomo.Window;
import com.util.Constants;
import com.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainContainer extends Component {
    public List<GameObject> menuItems;
    public Camera camera;

    public MainContainer(){
        this.menuItems = new ArrayList<>();
        this.camera = Window.getWindow().getCurrentScene().camera;
        init();
    }

    public void init(){
        Spritesheet groundSprites = AssetPool.getSpriteSheet("assets/groundSprites.png");
        Spritesheet buttonSprites = AssetPool.getSpriteSheet("assets/ui/buttonSprites.png");

        for(int i = 0; i < groundSprites.sprites.size(); i++){
            Sprite currentSprite = groundSprites.sprites.get(i);
            int x = Constants.BUTTON_OFFSET_X + (currentSprite.col * Constants.BUTTON_WIDTH) + (currentSprite.col * Constants.BUTTON_SPACING_HZ);
            int y = Constants.BUTTON_OFFSET_Y + (currentSprite.row * Constants.BUTTON_HEIGHT) + (currentSprite.row * Constants.BUTTON_SPACING_VT);

            GameObject obj = new GameObject("Generated", new Transform(new Vector2(x, y)), -1);
            obj.addComponent(currentSprite.copy());
            MenuItem menuItem = new MenuItem(x, y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, buttonSprites.sprites.get(0), buttonSprites.sprites.get(1));
            obj.addComponent(menuItem);
            obj.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
            menuItems.add(obj);
        }
    }

    @Override
    public void start(){
        for(GameObject g : menuItems){
            for(Component c : g.getAllComponents()){
                c.start();
            }
        }
    }

    @Override
    public void update(double dt){
        for(GameObject g : menuItems){
            g.update(dt);
        }
    }

    @Override
    public void draw(Graphics2D g2){
        for(GameObject g : menuItems){
            g.draw(g2);
        }
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
