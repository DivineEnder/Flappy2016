package com.teamhenry.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Ender on 1/3/2016.
 */
public class Tube
{
    private Random rand;

    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;

    public static final int TUBE_WIDTH = 52;

    private Rectangle boundsTop, boundsBot;

    private static Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;

    private boolean scored;

    public Tube(float x)
    {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        rand = new Random();
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());

        resetScored();
    }

    public void reposition(float x)
    {
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player) { return player.overlaps(boundsTop) || player.overlaps(boundsBot); }
    public boolean checkPass(Rectangle player)
    {
        if (!scored && player.getX() > boundsTop.getX() + boundsTop.getWidth())
        {
            scored = true;
            return true;
        }
        return false;
    }

    public Texture getTopTube() { return topTube; }
    public Texture getBottomTube() { return bottomTube; }
    public Vector2 getPosTopTube() { return posTopTube; }
    public Vector2 getPosBotTube() { return posBotTube; }
    public boolean scored() { return scored; }

    public void resetScored() { scored = false; }

    public void dispose()
    {
        //topTube.dispose();
        //bottomTube.dispose();
    }
}
