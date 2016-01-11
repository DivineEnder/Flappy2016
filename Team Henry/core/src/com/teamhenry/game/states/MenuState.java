package com.teamhenry.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamhenry.game.FlappyDemo;
import com.teamhenry.game.Scenes.HUD;

/**
 * Created by Ender on 1/3/2016.
 */
public class MenuState extends State
{
    //Textures for menu sprites
    private Texture background;
    private Texture playBtn;

    //Heads up display for high score
    private HUD hud;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);

        //Setting camera view (causing issue with play button currently)
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        //Getting textures from file
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");

        //Creating hud
        hud = new HUD();
        //Setting hud display to global high score
        hud.setScore((Integer) gsm.getGlobal("High Score").getVar());
    }

    @Override
    public void handleInput()
    {
        //Starts the game when touched (mouse click or actual touch)
        if (Gdx.input.justTouched())
            gsm.set(new PlayState(gsm));
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        //Sets drawing realtive to camera
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //Draws background
        sb.draw(background, 0, 0);
        //Draws play button (current either offscreen or not drawing correct)
        sb.draw(playBtn, cam.position.x - (playBtn.getWidth() / 2), cam.position.y);

        //Draws hud
        hud.stage.draw();

        sb.end();
    }

    @Override
    public void dispose()
    {
        background.dispose();
        playBtn.dispose();

        System.out.println("Menu State Disposed");
    }
}
