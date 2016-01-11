package com.teamhenry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamhenry.game.states.GameStateManager;
import com.teamhenry.game.states.MenuState;

public class FlappyDemo extends ApplicationAdapter
{
	//Creates width and height variables as desktop constants
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	//Creates title string
	public static final String TITLE = "FlappyBird";

	//New GameStateManager to manage game states (basic stack of State classes)
	private GameStateManager gsm;

	//SpriteBatch in order to render to the string
	private SpriteBatch batch;

	//Music to play in the background of the game constantly
    private Music music;
	
	@Override
	public void create ()
	{
		//Initializations
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		//Creates new music from file, loops it, sets volume, and starts it playing
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

		//Sets the clear color of the screen
		Gdx.gl.glClearColor(1, 0, 0, 1);

		//Creates the High Score global variable (accessible across all states without unnecessary passing)
		gsm.addGlobal("High Score", new Integer(0));

		//Starts the game by pushing a menu state to the GameStateManager's stack
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render ()
	{
		//Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Updates game state
		gsm.update(Gdx.graphics.getDeltaTime());
		//Renders game state
		gsm.render(batch);
	}

    @Override
    public void dispose()
    {
        super.dispose();

        music.dispose();
    }
}
