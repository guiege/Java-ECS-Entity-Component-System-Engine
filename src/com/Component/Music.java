package com.Component;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {

    public static Clip clip;

    public static void playMusic(String filepath){
        try
        {
            File musicPath = new File(filepath);

            if(musicPath.exists()){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

            } else{
                System.out.println("File " + filepath + " does not exist");
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public void stopMusic(){
        clip.stop();
    }

    public static void restartMusic(){
        clip.setFramePosition(0);
    }

    public static void playSfx(String filepath) {
        try {
            File musicPath = new File(filepath);

            if (musicPath.exists()) {
                Clip clip;
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();

            } else {
                System.out.println("File " + filepath + " does not exist");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

}
