package com.tomo;

import com.util.Vector2;

public class Camera {

    public Vector2 position;

    public Camera(Vector2 position) { // Literally just a position, everything just moves relative to it
        this.position = position;
    }
}
