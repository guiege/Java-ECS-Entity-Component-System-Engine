package com.Component;

import com.tomo.Component;
import com.tomo.Window;
import com.util.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class Player extends Component {

    Sprite layerOne, layerTwo, layerThree;
    public int width, height;
    public boolean onGround = true;
    public int numAttempts = 1;

    public Player(Sprite layerOne, Sprite layerTwo, Sprite layerThree, Color colorOne, Color colorTwo) {
        this.width = Constants.PLAYER_WIDTH;
        this.height = Constants.PLAYER_HEIGHT;
        this.layerOne = layerOne;
        this.layerTwo = layerTwo;
        this.layerThree = layerThree;

        int threshold = 200; // RGB range from 0 - 255. White is 255. We are trying to make the white portions of the image that color
        for (int y = 0; y < layerOne.image.getWidth(); y++) {
            for (int x = 0; x < layerOne.image.getHeight(); x++) {
                Color color = new Color(layerOne.image.getRGB(x, y));
                if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                    layerOne.image.setRGB(x, y, colorOne.getRGB());
                }
            }
        }
        if(Constants.PLAYER_ICON > 7 || Constants.PLAYER_ICON == 1){
            for (int y = 0; y < layerThree.image.getWidth(); y++) {
                for (int x = 0; x < layerThree.image.getHeight(); x++) {
                    Color color = new Color(layerThree.image.getRGB(x, y));
                    if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                        layerThree.image.setRGB(x, y, colorTwo.getRGB());
                    }
                }
            }
        }
        else {
            for (int y = 0; y < layerTwo.image.getWidth(); y++) {
                for (int x = 0; x < layerTwo.image.getHeight(); x++) {
                    Color color = new Color(layerTwo.image.getRGB(x, y));
                    if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                        layerTwo.image.setRGB(x, y, colorTwo.getRGB());
                    }
                }
            }
        }
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void update(double dt){
        if(Window.getWindow().isInEditor == false) {
            if (onGround && (Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_SPACE)
                    || Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_UP))) {
                addJumpForce();
                this.onGround = false;
            }
        }

        if(!onGround){
            gameObject.transform.rotation += 560f * dt; // Tested a lot of degree values, this seems most accurate to the real game
        } else{
            gameObject.transform.rotation = (int)gameObject.transform.rotation % 360; // Snap and set the rotation between 0 and 360 degrees
            if(gameObject.transform.rotation <= 0){
                gameObject.transform.rotation = 0;
            } else if(gameObject.transform.rotation > 0 && gameObject.transform.rotation <= 90){
                gameObject.transform.rotation = 90;
            } else if(gameObject.transform.rotation > 90 && gameObject.transform.rotation <= 180) {
                gameObject.transform.rotation = 180;
            } else if(gameObject.transform.rotation > 180 && gameObject.transform.rotation <= 270){
                gameObject.transform.rotation = 270;
            } else if(gameObject.transform.rotation > 270 && gameObject.transform.rotation <= 360){
                gameObject.transform.rotation = 360;
            }

        }
    }

    private void addJumpForce() {
        gameObject.getComponent(Rigidbody.class).velocity.y = Constants.JUMP_FORCE;
    }

    public void die(){
        Music.playSfx("assets/music/death_sound.wav");
        gameObject.transform.position.x = 0;
        gameObject.transform.position.y = Constants.GROUND_Y - Constants.PLAYER_HEIGHT;
        gameObject.transform.rotation = 0;
        Window.getWindow().getCurrentScene().camera.position.x = 0; // Moving player and camera back to the start of the level
        Music.restartMusic();
        numAttempts++;
    }

    @Override
    public void draw(Graphics2D g2) {
        try {
            Font gdFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/PUSAB___.TTF"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(gdFont);
            g2.setFont(gdFont);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font f = new Font("Pusab", Font.PLAIN, 27);
        g2.setFont(f);
        g2.setColor(Color.white);
        g2.drawString("Attempt " + numAttempts, 100, 200);

        AffineTransform transform = new AffineTransform(); // Transforms create an invisible coordinate plane which you can use to change properties of an object and call it back to its original position
        transform.setToIdentity(); // Resets transform
        transform.translate(gameObject.transform.position.x, gameObject.transform.position.y);
        transform.rotate(gameObject.transform.rotation * Math.PI / 180.0f,
                width * gameObject.transform.scale.x / 2.0,
                height * gameObject.transform.scale.y/ 2.0); // Only accepts radians, did a quick conversion. Anchored around the center of the player
        transform.scale(gameObject.transform.scale.x, gameObject.transform.scale.y);
        g2.drawImage(layerOne.image, transform, null);
        g2.drawImage(layerTwo.image, transform, null);
        g2.drawImage(layerThree.image, transform, null);
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}