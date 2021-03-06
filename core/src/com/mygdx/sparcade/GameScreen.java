package com.mygdx.sparcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen implements Screen
{
    final Space game;

    Texture playerpic, enemypic, shipPic, shipPicLeft, shipPicRight, fon;
    private int frames = 0;

    private OrthographicCamera camera;
    private Music wind;
    private Enemy enemy;
    private Starship ship;

    public GameScreen(final Space gam)
    {
        this.game = gam;

        // create starship
        enemy = new Enemy(new Vector2(800/2-64/2, 200));
        ship = new Starship(new Vector2(800 - 170 - 10, 480/2 - 50));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        enemypic = new Texture(Gdx.files.internal(enemy.filename));

        shipPic = new Texture(Gdx.files.internal(ship.pic));
        shipPicLeft = new Texture(Gdx.files.internal(ship.pic2));
        shipPicRight = new Texture(Gdx.files.internal(ship.pic3));

        fon = new Texture(Gdx.files.internal("space_fon.jpg"));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(fon, 0, 0);
        game.font.draw(game.batch, "Enemy class test, bounds: x=" + (enemy.bounds.x) + ", y= " + (enemy.bounds.y)+", enemy speed= " + (enemy.speed)+" enemy state: "+enemy.state, 0, 470);
        game.batch.draw(enemypic, enemy.bounds.x, enemy.bounds.y);


        if (Gdx.input.isTouched() || Gdx.input.isTouched(1)) {       // Управление кораблем, не хорошо что весь код в  одной куче
            Vector3 touch = new Vector3();
            Vector3 touch2 = new Vector3();
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            game.font.draw(game.batch, "Touch 1 x - "+Gdx.input.getX()+"Touch 1 y - "+Gdx.input.getX(), 0, 80);
            if (Gdx.input.isTouched(1)) {
                touch2.set(Gdx.input.getX(1), Gdx.input.getY(1), 0);
                camera.unproject(touch2);
                game.font.draw(game.batch, "Touch 2 x - "+Gdx.input.getX(1)+"Touch 2 y - "+Gdx.input.getX(1), 0, 20);
                if (touch2.x > 600 && touch.x > 600)
                    touch = touch2;
                //else
                //if (touch2.x > 400)

            }

            if ((int) (touch.x) > 600 && ((int) (touch.y) < 200)) {  // Влево
                ship.bounds.y -= 2;
                game.batch.draw(shipPicLeft, ship.bounds.x, ship.bounds.y);
            }
            else
            if ((int) (touch.x) > 600 && ((int) (touch.y) > 200)) {  // Вправо
                ship.bounds.y += 2;
                game.batch.draw(shipPicRight, ship.bounds.x, ship.bounds.y);
            }
            else
            if ((int) (touch.x) > 600 && ((int) (touch.y) < 200)) {
                ship.bounds.y -= 2;
                game.batch.draw(shipPicLeft, ship.bounds.x, ship.bounds.y);
            }
            else
            if ((int) (touch.x) > 600 && ((int) (touch.y) > 200)) {
                ship.bounds.y += 2;
                game.batch.draw(shipPicRight, ship.bounds.x, ship.bounds.y);
            }
            else
                game.batch.draw(shipPic, ship.bounds.x, ship.bounds.y);
        }
        else
            game.batch.draw(shipPic, ship.bounds.x, ship.bounds.y);
        game.batch.end();

        // moving player if touched
        if (Gdx.input.isTouched()) {
            Vector3 position = new Vector3();
            position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(position);
        }

        enemy.logic();

        if (ship.bounds.y < 0)
            ship.bounds.y = 0;
        if (ship.bounds.y > 480 - 100)
            ship.bounds.y = 480 - 100;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        wind.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose()
    {
        playerpic.dispose();
        enemypic.dispose();
        shipPic.dispose();
        wind.dispose();
    }
}
