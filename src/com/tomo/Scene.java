package com.tomo;

import com.util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public abstract class Scene {
    public String name;
    public Camera camera;
    public static ArrayList<GameObject> gameObjects;
    public Renderer renderer;

    public Scene(String name)
    {
        this.name = name;
        this.camera = new Camera(new Vector2());
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer(this.camera);
    }

    public void init(){

    }

    public void addGameObject(GameObject g){ // Does both of the necessary things to display a gameObject in one method
        gameObjects.add(g);
        renderer.submit(g);
        for(Component c : g.getAllComponents()){
            c.start(); // Runs the lines in the start method of a gameObjects components before their update is called
        }
    }

    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);
}
