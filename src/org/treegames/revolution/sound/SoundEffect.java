package org.treegames.revolution.sound;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.WaveData;

import javax.sound.sampled.AudioInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;

public class SoundEffect {
    private IntBuffer buffer;
    private IntBuffer source;

    public void setData(IntBuffer buffer, IntBuffer source) {
        this.buffer = buffer;
        this.source = source;
    }

    public static SoundEffect makeSound(InputStream stream) {
        SoundEffect soundEffect = new SoundEffect();
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        alGenBuffers(buffer);
        if (alGetError() != AL_NO_ERROR) {
            System.err.println("Error while creating sound!");
            return null;
        }

        WaveData waveFile = WaveData.create(stream);
        alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose();

        IntBuffer source = BufferUtils.createIntBuffer(1);
        alGenSources(source);
        if (alGetError() != AL_NO_ERROR) {
            System.err.println("Error while creating sound!");
            return null;
        }

        alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
        alSourcef(source.get(0), AL_PITCH, 1.0f);
        alSourcef(source.get(0), AL_GAIN, 1.0f);

        if (alGetError() != AL_NO_ERROR) {
            System.err.println("Error while creating sound!");
            return null;
        }

        soundEffect.setData(buffer, source);

        return soundEffect;
    }

    public void play() {
        alSourcePlay(source.get(0));
    }

    public void stop() {
        alSourceStop(source.get(0));
    }

    public void pause() {
        alSourcePause(source.get(0));
    }

    public void destroy() {
        alDeleteBuffers(buffer);
        alDeleteSources(source);
    }
}
