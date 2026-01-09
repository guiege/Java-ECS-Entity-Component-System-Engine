package com.tomo;

import com.Component.*;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.file.Parser;
import com.util.Constants;
import com.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class LevelScene extends Scene{
    static LevelScene currentScene; // For other classes to reference variables in the scene

    public GameObject player;
    public BoxBounds playerBounds;
    public Music loudArabicMusic = new Music();
    public static String filepath = Constants.MUSIC_PATH;
    public Color bgColor = Constants.BG_COLOR;
    public Color gColor = Constants.GROUND_COLOR;
    public boolean noclip = false;
    public static int iconNum = Constants.PLAYER_ICON;
    public static String bgPath = Constants.BACKGROUND_PATH;
    public static String groundPath = Constants.GROUND_PATH;
    HashMap<Integer, Color> triggerMap = new HashMap<Integer, Color>();

    public LevelScene(String name)
    {
        super(name);
    }

    @Override
    public void init() {
        initAssetPool();

        for(GameObject g : LevelEditorScene.gameObjects){
            addGameObject(g);
        }

        player = new GameObject("Player", new Transform(new Vector2(0, Constants.GROUND_Y - Constants.PLAYER_HEIGHT)), 0);
        Spritesheet layerOne = AssetPool.getSpriteSheet("assets/player/layerOne.png");
        Spritesheet layerTwo = AssetPool.getSpriteSheet("assets/player/layerTwo.png");
        Spritesheet layerThree = AssetPool.getSpriteSheet("assets/player/layerThree.png");
        Player playerComp = new Player(layerOne.sprites.get(iconNum), layerTwo.sprites.get(iconNum), layerThree.sprites.get(iconNum), Constants.PLAYER_COLOR_SECONDARY, Constants.PLAYER_COLOR_PRIMARY);
        player.addComponent(playerComp);
        player.addComponent(new Rigidbody(new Vector2(Constants.PLAYER_SPEED, 0)));
        player.addComponent(new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        playerBounds = new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
        player.addComponent(playerBounds);

        renderer.submit(player);

        initBackgrounds();
        initTriggerMap();

        importLevel("Test");
        loudArabicMusic.playMusic(filepath);
    }

    public void initTriggerMap(){
        triggerMap.put(Constants.COLOR_TRIGGER_1, Constants.COLOR_NO_1);
        triggerMap.put(Constants.COLOR_TRIGGER_2, Constants.COLOR_NO_2);
        triggerMap.put(Constants.COLOR_TRIGGER_3, Constants.COLOR_NO_3);
        triggerMap.put(Constants.COLOR_TRIGGER_4, Constants.COLOR_NO_4);
        triggerMap.put(Constants.COLOR_TRIGGER_5, Constants.COLOR_NO_5);
        triggerMap.put(Constants.COLOR_TRIGGER_6, Constants.COLOR_NO_6);
    }

    public static LevelScene getScene(){
        if(currentScene == null){
            LevelScene.currentScene = new LevelScene("Scene");
        }
        return LevelScene.currentScene;
    }

    public void initBackgrounds(){
        GameObject ground;
        ground = new GameObject("Ground", new Transform(new Vector2(0, Constants.GROUND_Y)), 1);
        ground.addComponent(new Ground());
        addGameObject(ground);

        int numBackgrounds = 7;
        GameObject[] backgrounds = new GameObject[numBackgrounds];
        GameObject[] groundBgs = new GameObject[numBackgrounds];
        for(int i = 0; i < numBackgrounds; i++){
            ParallaxBackground bg = new ParallaxBackground(bgPath, backgrounds, ground.getComponent(Ground.class), false, gColor);
            int x = i * bg.sprite.width;
            int y = 0;

            GameObject go = new GameObject("Background", new Transform(new Vector2(x, y)), -10);
            go.setUi(true);
            go.addComponent(bg);
            backgrounds[i] = go;

            ParallaxBackground groundBg = new ParallaxBackground(groundPath, groundBgs, ground.getComponent(Ground.class), true, gColor);
            x = i * groundBg.sprite.width;
            y = bg.sprite.height;
            GameObject groundGo = new GameObject("GroundBg", new Transform(new Vector2(x, y)), -9);
            groundGo.addComponent(groundBg);
            groundGo.setUi(true);
            groundBgs[i] = groundGo;

            addGameObject(go);
            addGameObject(groundGo);
        }

    }

    public void initAssetPool(){
        AssetPool.addSpriteSheet("assets/player/layerOne.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpriteSheet("assets/player/layerTwo.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpriteSheet("assets/player/layerThree.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpriteSheet("assets/groundSprites.png", 42, 42, 2, 6, 12);
    }

    @Override
    public void update(double dt) {

        if(player.transform.position.x - camera.position.x > Constants.CAMERA_OFFSET_X){
            camera.position.x = player.transform.position.x - Constants.CAMERA_OFFSET_X; // Moves player with the camera in sync
        }

        if(player.transform.position.y - camera.position.y > Constants.CAMERA_OFFSET_Y){ // Only works when player is falling. Maybe don't need to figure out when rising?
            camera.position.y = player.transform.position.y - Constants.CAMERA_OFFSET_Y;
        }

        if(camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y){
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        player.update(dt);
        player.getComponent(Player.class).onGround = false;
        for(GameObject g : gameObjects) {
            g.update(dt);

            if(noclip != true) {
                Bounds b = g.getComponent(Bounds.class);
                if (b != null) { // If the current game object is the player and has bounds...
                    if (Bounds.checkCollision(playerBounds, b)) { // If there is a collision with any game object...
                        Bounds.resolveCollision(b, player);
                    }
                }
            }
        }

        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_ESCAPE)){
            Window.getWindow().changeScene(0);
            loudArabicMusic.stopMusic();
        } else if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_R)){
            System.out.println(camera.position.x);
        } else if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_E)){
            noclip = true;
        }

        if(camera.position.x < 10){
            bgColor = Constants.BG_COLOR;
            ParallaxBackground.setgColor(Constants.GROUND_COLOR);
        }

        for(Integer i : triggerMap.keySet()){
            if(camera.position.x > i && camera.position.x < i + Constants.TILE_WIDTH){
                bgColor = triggerMap.get(i);
                ParallaxBackground.setgColor(triggerMap.get(i));
            }
        }
    }

    private void importLevel(String filename){
        Parser.openFile(filename);

        GameObject go = Parser.parseGameObject();
        while(go != null){
            addGameObject(go);
            go = Parser.parseGameObject(); // Parses gameObjects until there are no more to parse
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(bgColor);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        renderer.render(g2);
    }

}
