package com.teamhenry.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.teamhenry.game.FlappyDemo;

/**
 * Created by edwardszc on 1/5/16.
 */
public class HUD implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private static Integer score;
    private static Label scoreLabel;

    public HUD(){
        score = -1;
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //viewport = new ScreenViewport(FlappyDemo.WIDTH, FlappyDemo.HEIGHT, new OrthographicCamera());
        viewport = new ScreenViewport(new OrthographicCamera());


        scoreLabel = new Label(String.format("%d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stage = new Stage(viewport);

        table.add(scoreLabel).expandX().padTop(10);
        // You can use table.row(); to create a new row


        stage.addActor(table);

    }

    public static void updateScore() {
        score++;
        scoreLabel.setText(String.format("%d", score));
    }

    public Integer getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
        scoreLabel.setText(String.format("%d", score));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
