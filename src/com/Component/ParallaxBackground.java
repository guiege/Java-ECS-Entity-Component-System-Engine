package com.Component;

import com.dataStructure.AssetPool;
import com.tomo.Component;
import com.tomo.GameObject;
import com.tomo.Window;
import com.util.Constants;

import java.awt.*;

public class ParallaxBackground extends Component {

    public int width, height;
    public Sprite sprite;
    public GameObject[] backgrounds;
    public int timeStep = 0;
    private float speed = 80.0f;

    private Ground ground;
    private boolean followGround;

    public static Color gColor;

    public ParallaxBackground(String file, GameObject[] backgrounds, Ground ground, boolean followGround, Color groundColor){
        this.sprite = AssetPool.getSprite(file);
        this.width = this.sprite.width;
        this.height = this.sprite.height;
        this.backgrounds = backgrounds;
        this.ground = ground;
        this.gColor = groundColor;

        if(followGround) this.speed = Constants.PLAYER_SPEED - 35; // If we are following ground, it follows the player speed
        this.followGround = followGround;
    }

    @Override
    public void update(double dt){
        this.timeStep++;

        this.gameObject.transform.position.x -= dt * speed;
        this.gameObject.transform.position.x = (float)Math.floor(this.gameObject.transform.position.x); // Locking it to an integer to remove pixel boundaries when transitioning to another background image
        if(this.gameObject.transform.position.x < - width){ // Completely outside of the screen and we cant see it
            float maxX = 0;
            int otherTimeStep = 0;
            for(GameObject go : backgrounds){ // Searching for the max x position
                if(go.transform.position.x > maxX){
                    maxX = go.transform.position.x;
                }
                otherTimeStep = go.getComponent(ParallaxBackground.class).timeStep; // Records timeStep so we know if it is on the same timeStep as the current background
            }
            if(otherTimeStep == this.timeStep){
                this.gameObject.transform.position.x  = maxX + width;
            }
            else{
                this.gameObject.transform.position.x = (float) Math.floor((maxX + width) - (dt * speed));
            }
        }
        if(this.followGround){
            this.gameObject.transform.position.y = ground.gameObject.transform.position.y;
        }
    }

    public static void setgColor(Color color){
        gColor = color;
    }

    @Override
    public void draw(Graphics2D g2){
        if(followGround) {
            g2.drawImage(this.sprite.image, (int) this.gameObject.transform.position.x,
                    (int) (this.gameObject.transform.position.y - Window.getWindow().getCurrentScene().camera.position.y), width, height, null);
        }
        else {
            int height = Math.min((int) (ground.gameObject.transform.position.y - Window.getWindow().getCurrentScene().camera.position.y), Constants.SCREEN_HEIGHT);

            g2.drawImage(this.sprite.image, (int) this.gameObject.transform.position.x, (int) this.gameObject.transform.position.y, width, Constants.SCREEN_HEIGHT, null);
            g2.setColor(gColor);
            g2.fillRect((int) this.gameObject.transform.position.x, height, width, Constants.SCREEN_HEIGHT);
        }
    }

    @Override
    public String serialize(int tabSize) {
        return null;
    }

    @Override
    public Component copy() {
        return null;
    }
}
