package com.tomo;

import com.file.Serialize;

import java.awt.Graphics2D;

public abstract class Component<T> extends Serialize {

    public GameObject gameObject;

    public abstract Component copy();

    public void update(double dt){
        return;
    } // Doing a return inside of a void function means that the compiler ignores it outright(at least that's what i read on stack overflow) so this shouldn't impact performance

    public void draw(Graphics2D g2){
        return;
    }

    public void start(){
        return;
    }
}
