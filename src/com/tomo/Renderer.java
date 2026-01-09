package com.tomo;

import com.dataStructure.Transform;
import com.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Renderer {
    Map<Integer, ArrayList<GameObject>> gameObjects;
    Camera camera;

    public Renderer(Camera camera){
        this.camera = camera;
        this.gameObjects = new HashMap<>(); // Using a hash map because of constant lookup time because everything has a unique underlying implementation of the hashMap
    }

    public void submit(GameObject gameObject){ // If there is no zIndex ArrayList in the hashMap of the gameObjects zIndex, put in a new one or if it exists, add to it
        gameObjects.computeIfAbsent(gameObject.zIndex, k -> new ArrayList<>()); // Lambda function idk how it works but it gets the job done(https://www.geeksforgeeks.org/hashmap-computeifabsent-method-in-java-with-examples/)
        gameObjects.get(gameObject.zIndex).add(gameObject); // Adds the game object to its zIndex to be rendered in layers
    }

    public void render(Graphics2D g2) { // Renders objects relative to the camera and relative to each others zIndex
        int lowestZIndex = Integer.MAX_VALUE; // Max and min int value just to make sure its less or greater
        int highestZIndex = Integer.MIN_VALUE;
        for (Integer i : gameObjects.keySet()) { // We have to use Integer because our map contains Integer objects rather than ints. Key set kind of acts like a .size in a array loop by iterating through all values
            if (i < lowestZIndex) lowestZIndex = i;
            if (i > highestZIndex) highestZIndex = i;
        }

        int currentZIndex = lowestZIndex; // We start at smallest til we reach highest zIndex so we render the lesser zIndexes in the background
        while (currentZIndex <= highestZIndex) {
            if (gameObjects.get(currentZIndex) == null) {// If there is no container in the map at the current point...
                currentZIndex++;
                continue;
            }

            for (GameObject g : gameObjects.get(currentZIndex)) { // Renders objects on the list at the currentZIndex
                if (g.isUi) {
                    g.draw(g2);
                } else {
                    Transform oldTransform = new Transform(g.transform.position); // Rendering the gameWorld objects first in normal(old) position before rendering them as the camera needs them for the scene and then putting them back
                    oldTransform.rotation = g.transform.rotation;
                    oldTransform.scale = g.transform.scale;
                    g.transform.position = new Vector2(g.transform.position.x - camera.position.x, g.transform.position.y - camera.position.y); // Moves the game object relative to the cameras position as needed
                    g.draw(g2);
                    g.transform = oldTransform; // Resets the transform after rendering in the correct position
                }
            }
            currentZIndex++;
        }
    }
}
