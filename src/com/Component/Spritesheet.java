package com.Component;

import com.dataStructure.AssetPool;

import java.util.ArrayList;

public class Spritesheet {
    public ArrayList<Sprite> sprites;
    public Sprite sprite;
    public int tileWidth;
    public int tileHeight;
    public int spacing;

    public Spritesheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size){
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.spacing = spacing;

        Sprite parent = AssetPool.getSprite(pictureFile);
        sprites = new ArrayList<>();
        int row = 0;
        int count = 0; // Number of sprites counted
        while(count < size){
            for(int column = 0; column < columns; column++){
                int imgX = (column * tileWidth) + (column * spacing);
                int imgY = (row * tileHeight) + (row * spacing);

                sprites.add(new Sprite(parent.image.getSubimage(imgX, imgY, tileWidth, tileHeight), row, column, count, pictureFile));
                count++;
                if(count > size - 1){
                    break;
                }
            }
            row++; // Every time you go through the columns on a row, advance the row
        }
    }
}
