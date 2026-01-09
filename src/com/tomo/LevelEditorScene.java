package com.tomo;

import com.Component.*;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.file.Parser;
import com.ui.MainContainer;
import com.util.Constants;
import com.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LevelEditorScene extends Scene{

    public GameObject swagPlayer;
    public GameObject swagGround;
    public Grid swagGrid;
    public CameraControls cameraController;
    public GameObject swagCursor;
    public MainContainer editingButtons;


    public LevelEditorScene(String name)
    {
        super(name);
    }

    @Override
    public void init() {
        initAssetPool();
        editingButtons = new MainContainer();

        swagGrid = new Grid();
        cameraController = new CameraControls();
        editingButtons.start();

        swagCursor = new GameObject("Swag Cursor", new Transform(new Vector2()), 10);
        swagCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH, Constants.TILE_HEIGHT));

        swagPlayer = new GameObject("Player", new Transform(new Vector2(0, Constants.GROUND_Y - Constants.PLAYER_HEIGHT)), 0);
        Spritesheet layerOne = AssetPool.getSpriteSheet("assets/player/layerOne.png");
        Spritesheet layerTwo = AssetPool.getSpriteSheet("assets/player/layerTwo.png");
        Spritesheet layerThree = AssetPool.getSpriteSheet("assets/player/layerThree.png");
        Player playerComp = new Player(layerOne.sprites.get(Constants.PLAYER_ICON), layerTwo.sprites.get(Constants.PLAYER_ICON), layerThree.sprites.get(Constants.PLAYER_ICON), Constants.PLAYER_COLOR_SECONDARY, Constants.PLAYER_COLOR_PRIMARY);
        swagPlayer.addComponent(playerComp);

        swagGround = new GameObject("Ground", new Transform(new Vector2(0, Constants.GROUND_Y)), 1);
        swagGround.addComponent(new Ground());

        swagGround.setNonserializable();
        swagPlayer.setNonserializable();
        addGameObject(swagPlayer);
        addGameObject(swagGround);
    }

    public void initAssetPool(){
        AssetPool.addSpriteSheet("assets/player/layerOne.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpriteSheet("assets/player/layerTwo.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpriteSheet("assets/player/layerThree.png", 42, 42, 2, 13, 13 * 5);
        AssetPool.addSpriteSheet("assets/groundSprites.png", 42, 42, 2, 6, 12);
        AssetPool.addSpriteSheet("assets/ui/buttonSprites.png", 60, 60, 2, 2, 2);
    }

    @Override
    public void update(double dt) {
        if(camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y){
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        for(GameObject g : gameObjects) {
            g.update(dt);
        }

        cameraController.update(dt);
        editingButtons.update(dt);
        swagCursor.update(dt);
        swagGrid.update(dt);

        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F1)){
            export("Test");
        } else if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F2)){
            importLevel("Test");
        } else if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F3)){
            Window.getWindow().changeScene(1);
        } else if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_A)){ // sussy
            LevelScene.iconNum = 1;
            LevelScene.filepath = "assets/music/susSong.wav";
            LevelScene.bgPath = "assets/backgrounds/susBG.png";
            LevelScene.groundPath = "assets/grounds/susGround.png";
            Music.playSfx("assets/music/drip.wav");
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

    private void export(String filename){ // Part of serialization which we mostly used a tutorial/references for so expect a lot of copypastad code. I don't even know what is fully happening here but i get what it is doing in general.
        try {
            FileOutputStream fos = new FileOutputStream("levels/" + filename + ".zip"); // The file path for the zipped file
            ZipOutputStream zos = new ZipOutputStream(fos); // Creates a zip file outputStream

            zos.putNextEntry(new ZipEntry(filename + ".json")); // Using a .json file format to encode data

            int i = 0;
            for (GameObject go : gameObjects) {
                String str = go.serialize(0);
                if(str.compareTo("") != 0) { // Empty string is a flag for a game object we don't want to serialize
                    zos.write(str.getBytes()); // Writing to the zip outputStream
                    if(i != gameObjects.size() - 1) {
                        zos.write(",\n".getBytes());
                    }
                }
                i++;
            }
            zos.closeEntry();
            zos.close();
            fos.close();
            }catch(IOException e){
                e.printStackTrace();
                System.exit(-1);
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(1.0f, 1.0f, 1.0f));
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        renderer.render(g2);
        swagGrid.draw(g2);
        editingButtons.draw(g2);
        swagCursor.draw(g2);
    }
}
