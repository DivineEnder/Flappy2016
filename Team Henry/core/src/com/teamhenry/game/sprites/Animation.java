package com.teamhenry.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ender on 1/3/2016.
 */
public class Animation
{
    private float maxFrameTime;
    private float currentFrameTime;

    private int frameCount;
    private int frame;

    private Array<TextureRegion> frames;

    public Animation(Texture texture, int frameCount, float cycleTime)
    {
        int frameWidth = texture.getWidth() / frameCount;

        frames = new Array<TextureRegion>();
        for (int i = 0; i < frameCount; i++)
            frames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));

        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt)
    {
        currentFrameTime += dt;

        if (currentFrameTime > maxFrameTime)
        {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount)
            frame = 0;
    }

    public TextureRegion getFrame() { return frames.get(frame); }
}
