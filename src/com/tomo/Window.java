package com.tomo;

import com.util.Constants;
import com.util.Time;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable{ // Pretty standard window class with mouse listener and key listener, most functions here are copypastad from awt documentation

    public ML mouseListener;
    public KL keyListener;
    public boolean isInEditor = true;

    private static Window window = null;
    private boolean isRunning = true;
    private Scene currentScene = null;
    private Image doubleBufferImage = null;
    private Graphics doubleBufferGraphics = null;

    public Window(){
        this.mouseListener = new ML();
        this.keyListener = new KL();
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);    //Settings for the window
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setLocationRelativeTo(null); //Makes sure the window is centered
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/Geometry_Dash_Logo.PNG");
        this.setIconImage(icon);
    }

    public void init (){
        changeScene(0);
    }

    public void changeScene(int scene){ // Only unique method in this class made by us. Everything else is somewhat standard
        switch (scene){
            case 0:
                currentScene = new LevelEditorScene("Level Editor");
                isInEditor = true;
                currentScene.init();
                break;
            case 1:
                isInEditor = false;
                currentScene = new LevelScene("Level");
                currentScene.init();
                break;
            default:
                System.out.println("Do not know what this scene is");
                currentScene = null;
                break;
        }
    }

    public static Window getWindow(){ // Returns window and instantiates it if it is null
        if(Window.window == null)
        {
            window.window = new Window();
        }

        return Window.window;
    }

    public Scene getCurrentScene(){
        return currentScene;
    }

    public void update(double dt){ // Game loop
        currentScene.update(dt);
        draw(getGraphics());
    }

    public void draw(Graphics g) { // Simple bufferedImage draw class
        if(doubleBufferImage == null){
            doubleBufferImage = createImage(getWidth(), getHeight());
            doubleBufferGraphics = doubleBufferImage.getGraphics();
        }

        renderOffScreen(doubleBufferGraphics);

        g.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void renderOffScreen(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        currentScene.draw(g2);
    }

    @Override
    public void run(){
        double lastFrameTime = 0.0; // Time taken to render the last frame
        try {
            while(isRunning) {
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime; // Elapsed time between frames
                lastFrameTime = time; // Updates last frame to current frame

                update(deltaTime);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); // Makes sure that if frame time cant be calculated, the application will close rather than breaking
        }
    }
}
