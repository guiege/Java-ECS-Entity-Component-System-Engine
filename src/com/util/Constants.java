package com.util;

import java.awt.*;

public class Constants {
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 700;
    public static final String SCREEN_TITLE = "Impossible Game: Java Edition";

    public static final int PLAYER_WIDTH = 42;
    public static final int PLAYER_HEIGHT = 42;
    public static final int JUMP_FORCE = -710;
    public static final int PLAYER_SPEED = 400;
    public static final int PLAYER_ICON = 41; // Customize player icon here. Look at the spritesheet in assets to see options
    public static final Color PLAYER_COLOR_PRIMARY = Color.pink; // Customize player colors here
    public static final Color PLAYER_COLOR_SECONDARY = Color.cyan;
    public static final double COLLISION_OFFSET = 10;

    public static final int GROUND_Y = 714;
    public static final int CAMERA_OFFSET_X = 300;
    public static final int CAMERA_OFFSET_Y = 325;
    public static final int CAMERA_OFFSET_GROUND_Y = 150;

    public static final float GRAVITY = 3090;
    public static final float TERMINAL_VELOCITY = 1900;

    public static final int TILE_WIDTH = 42;
    public static final int TILE_HEIGHT = 42;

    public static final int BUTTON_OFFSET_X = 400;
    public static final int BUTTON_OFFSET_Y = 560;
    public static final int BUTTON_SPACING_HZ = 10;
    public static final int BUTTON_SPACING_VT = 5;
    public static final int BUTTON_WIDTH = 60;
    public static final int BUTTON_HEIGHT = 60;

    public static final Color BG_COLOR = new Color(142.0f / 255.0f, 46.0f / 255.0f, 239.0f / 255.0f, 1.0f);
    public static final Color GROUND_COLOR = new Color(80f / 255.0f, 15.0f / 255.0f, 136.0f / 255.0f, 1.0f);
    public static final String BACKGROUND_PATH = "assets/backgrounds/bg04-hd.png";
    public static final String GROUND_PATH = "assets/grounds/ground05.png";
    public static final Color COLOR_NO_1 = new Color(0,197,79, 255);
    public static final Color COLOR_NO_2 = new Color(235,38,181);
    public static final Color COLOR_NO_3 = new Color(0,189,1);
    public static final Color COLOR_NO_4 = new Color(79,70,235);
    public static final Color COLOR_NO_5 = new Color(155,51,195);
    public static final Color COLOR_NO_6 = new Color(243,24,37);

    public static final int COLOR_TRIGGER_1 = 5400; // The camera position where the bg and g colors are switched, press r during the levelScene to record camera position in console
    public static final int COLOR_TRIGGER_2 = 6865;
    public static final int COLOR_TRIGGER_3 = 8321;
    public static final int COLOR_TRIGGER_4 = 9758;
    public static final int COLOR_TRIGGER_5 = 11181;
    public static final int COLOR_TRIGGER_6 = 16676;

    public static final String MUSIC_PATH = "assets/music/17.Waterflame - Blast Processing.wav"; // Feel free to add in custom music!
}
