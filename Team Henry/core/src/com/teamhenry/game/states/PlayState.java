package com.teamhenry.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.teamhenry.game.FlappyDemo;
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

    private Bird bird;

    private Array<Tube> tubes;

    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    BitmapFont font;

    public Integer score;

    protected PlayState(GameStateManager gsm)
    {
        super(gsm);

        bird = new Bird(75, 300);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

        bg = new Texture("bg.png");

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - (cam.viewportWidth / 2)) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Tube>();
        for (int i = 0; i < TUBE_COUNT; i++)
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));

        font = new BitmapFont();

        score = -1;
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt)
    {
        handleInput();

        updateGround();

        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for (Tube tube : tubes)
        {
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
            {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                tube.resetScored();
            }

            if (tube.checkPass(bird.getBounds()))
                score++;
            if (tube.collides(bird.getBounds()))
                gsm.set(new MenuState(gsm, score));
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new MenuState(gsm, score));

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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        font.draw(sb, score.toString(), cam.position.x + cam.viewportWidth/2 - 30, cam.position.y * 2);

        sb.end();
    }

    @Override
    public void dispose()
    {
        bg.dispose();
        ground.dispose();
        font.dispose();
        bird.dispose();
        for (int i = 0; i < tubes.size; i++)
            tubes.get(i).dispose();

        System.out.println("Play State Disposed");
    }
}
