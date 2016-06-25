package com.xyxean.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xyxean.MainGame;
import com.xyxean.Tools.WorldContactListener;
import com.xyxean.scenes.DifficultyHud;
import com.xyxean.scenes.StoreHud;

/**
 * Created by justinwei on 4/3/2016.
 */
public class StoreScreen extends InputListener implements Screen {
    public MainGame game;
    private TextureAtlas buttonAtlas;

    //basic playscreen variables
    public static OrthographicCamera gamecam;
    private Viewport gamePort;


    //Box2D VARIABLES
    private World world;
    private Box2DDebugRenderer b2dr; //graphical representation of fixtures and bodies in box2d
    private StoreHud hud;


    public StoreScreen(MainGame game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        hud = new StoreHud(game, game.batch);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true); //gravity
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());


    }


    @Override
    public void show() {

    }


    public void update(float dt){
        world.step(1 / 60f, 6, 2);
        gamecam.update();
    }

    @Override
    public void render(float delta) { //called repeatedly

        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //Viewport is adjusted to know what the screen size is
        gamePort.update(width, height);
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
