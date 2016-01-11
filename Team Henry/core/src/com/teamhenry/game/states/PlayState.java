package com.teamhenry.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.teamhenry.game.FlappyDemo;
import com.teamhenry.game.Scenes.HUD;
import com.teamhenry.game.sprites.Bird;
import com.teamhenry.game.sprites.Tube;

/**
 * Created by Ender on 1/3/2016.
 */
public class PlayState extends State
{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    //Bird class with position, drawing, collision, etc.
    private Bird bird;

    //List of tubes across screen
    private Array<Tube> tubes;

    //Various sprite textures
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    //Heads up display for score
    private HUD hud;

    protected PlayState(GameStateManager gsm)
    {
        super(gsm);

        //New bird starting at given (x,y) coordinates
        bird = new Bird(75, 300);
        //Sets the camera
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

        //Creates hud
        hud = new HUD();

        //Creates background texture from file
        bg = new Texture("bg.png");

        //Creates ground texture from file and sets positions
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - (cam.viewportWidth / 2)) + ground.getWidth(), GROUND_Y_OFFSET);

        //Creates tubes across screen
        tubes = new Array<Tube>();
        for (int i = 0; i < TUBE_COUNT; i++)
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
    }

    @Override
    protected void handleInput()
    {
        //Makes bird jump when screen is touched (mouse click or actual touch)
        if (Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt)
    {
        handleInput();

        updateGround();

        //Updates birds drawing position (gravity mainly)
        bird.update(dt);
        //Updates camera to center on bird
        cam.position.x = bird.getPosition().x + 80;

        //Checks each tube
        for (Tube tube : tubes)
        {
            //Repositions tubes once offscreen to appear again
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
            {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                tube.resetScored();
            }

            //Checks to see if the bird has passed the tube
            if (tube.checkPass(bird.getBounds()))
                hud.updateScore();

            //Checks for bird collision with tube
            if (tube.collides(bird.getBounds()))
            {
                //Checks for high score
                if (hud.getScore().compareTo((Integer) gsm.getGlobal("High Score").getVar()) > 0)
                    gsm.getGlobal("High Score").setVar(new Integer(hud.getScore()));

                //Moves to menu state
                gsm.set(new MenuState(gsm));
            }
        }

        //Checks for bird collision with ground
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
        {
            //Checks for high score
            if (hud.getScore().compareTo((Integer) gsm.getGlobal("High Score").getVar()) > 0)
                gsm.getGlobal("High Score").setVar(new Integer(hud.getScore()));

            //Moves to menu state
            gsm.set(new MenuState(gsm));
        }

        //Updates camera to follow the bird
        cam.update();
    }

    private void updateGround()
    {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

    @Override
    public void render(SpriteBatch sb)
    {
        //Draws based upon the camera location (coordinates relative)
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //Draws background
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        //Draws bird
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        //Draws tubes
        for (Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        //Draws ground
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        //Draws relative to hud camera
        sb.setProjectionMatrix(hud.stage.getCamera().combined);
        //Draws hud
        hud.stage.draw();

        sb.end();
    }

    @Override
    public void dispose()
    {
        bg.dispose();
        ground.dispose();
        bird.dispose();
        for (int i = 0; i < tubes.size; i++)
            tubes.get(i).dispose();

        System.out.println("Play State Disposed");
    }
}
