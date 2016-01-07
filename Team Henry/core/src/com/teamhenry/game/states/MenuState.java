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
    private Texture background;
    private Texture playBtn;

    private BitmapFont font;
    private Integer score;

    private HUD hud;

    public MenuState(GameStateManager gsm, int score)
    {
        super(gsm);

        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");

        font = new BitmapFont();

        hud = new HUD();
        hud.setScore(score);

        this.score = score;
    }

    @Override
    public void handleInput()
    {
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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(background, 0, 0);
        sb.draw(playBtn, cam.position.x - (playBtn.getWidth() / 2), cam.position.y);

        font.setColor(Color.BLACK);
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
