package org.treegames.revolution.sound;

import java.util.HashMap;
import java.util.Map;

public class Sounds {
    private static Map<String, SoundEffect> soundMap = new HashMap<String, SoundEffect>();

    public static void initDefaultSounds() {
        addSound("footstep", "/sprites/player/footstep.wav");
    }

    public static void addSound(String name, String path) {
        SoundEffect effect = SoundEffect.makeSound(Sounds.class.getResourceAsStream("/sound" + path));
        soundMap.put(name, effect);
    }

    public static SoundEffect getSound(String name) {
        return soundMap.get(name);
    }

    public static void playSound(String name) {
        getSound(name).play();
    }

    public static void destroySounds() {
        for (String effectName : soundMap.keySet()) {
             soundMap.get(effectName).destroy();
        }
    }
}
