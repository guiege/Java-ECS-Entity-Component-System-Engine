package com.dataStructure;

import com.Component.Sprite;
import com.Component.Spritesheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    static Map<String, Sprite> sprites = new HashMap<>();
    static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static boolean hasSprite(String pictureFile) {
        File tmp = new File(pictureFile);
        return AssetPool.sprites.containsKey(tmp.getAbsolutePath());
    }

    public static boolean hasSpriteSheet(String pictureFile){
        File tmp = new File(pictureFile);
        return AssetPool.spritesheets.containsKey(tmp.getAbsolutePath());
    }

    public static Sprite getSprite(String pictureFile){ // If sprite is already in the hashmap, return its path. Else, add it and return its path
        File file = new File(pictureFile);
        if(AssetPool.hasSprite(file.getAbsolutePath())){
            return AssetPool.sprites.get(file.getAbsolutePath());
        } else{
            Sprite sprite = new Sprite(pictureFile);
            AssetPool.addSprite(pictureFile, sprite);
            return AssetPool.sprites.get(file.getAbsolutePath());
        }
    }

    public static Spritesheet getSpriteSheet(String pictureFile){
        File file = new File(pictureFile);
        if(AssetPool.hasSpriteSheet(file.getAbsolutePath())) {
            return AssetPool.spritesheets.get(file.getAbsolutePath());
        } else{
            System.out.println("Spritesheet " + pictureFile + " doesn't exist");
            System.exit(-1);
        }
        return null;
    }

    public static void addSprite(String pictureFile, Sprite sprite){
        File file = new File(pictureFile);
        if(!AssetPool.hasSprite(file.getAbsolutePath())){
            AssetPool.sprites.put(file.getAbsolutePath(), sprite); // Adds the sprite to the hashmap associated with its path
        }
        else{
            System.out.println("The asset: " + file.getAbsolutePath() + " is already in the asset pool");
            System.exit(-1);
        }
    }

    public static void addSpriteSheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size){
        File file = new File(pictureFile);
        if(!AssetPool.hasSpriteSheet(file.getAbsolutePath())){
            Spritesheet spritesheet = new Spritesheet(pictureFile, tileWidth, tileHeight, spacing, columns, size);
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

}
