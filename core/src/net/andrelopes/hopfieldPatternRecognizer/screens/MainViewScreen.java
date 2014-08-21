package net.andrelopes.hopfieldPatternRecognizer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.andrelopes.hopfieldPatternRecognizer.Assets;
import net.andrelopes.hopfieldPatternRecognizer.logic.Hopfield;
import net.andrelopes.hopfieldPatternRecognizer.utils.ColorTextureGenerator;
import net.andrelopes.hopfieldPatternRecognizer.utils.Settings;

/**
 * revised by DermetFan :) Or Robin?
 *
 * @author André Vinícius Lopes
 */
public class MainViewScreen extends ScreenAdapter {

    public Hopfield hopfield;

    private Stage stage = new Stage();
    private Table imageGrid = new Table();
    private Table buttonsTable = new Table();
    private Array<Disposable> disposables = new Array<Disposable>();

    private Texture off = ColorTextureGenerator.rgb(0, 0, 0);
    private Texture on = ColorTextureGenerator.rgb(0.5f, 1.0f, 0.15f);

    private final Drawable offDrawable = new TextureRegionDrawable(new TextureRegion(off));
    private final Drawable onDrawable = new TextureRegionDrawable(new TextureRegion(on));

    @Override
    public void show() {
        hopfield = new Hopfield();

        Gdx.input.setInputProcessor(stage);
        imageGrid.debug();
        imageGrid.setFillParent(true);
        stage.addActor(imageGrid);

        disposables.add(off);
        disposables.add(on);

        ClickListener cl = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!(event.getListenerActor() instanceof Image)) // safety
                {
                    return;
                }
                Image image = ((Image) event.getListenerActor());
                if (image.getDrawable() == offDrawable) {
                    image.setDrawable(onDrawable);
                } else {
                    image.setDrawable(offDrawable);
                }
            }

        };

        for (int x = Settings.getInputColumns(), y = Settings.getInputRows(); y > 0; x--) {
            Image image = new Image(offDrawable);
            image.addListener(cl);
            @SuppressWarnings("unchecked")
            Cell<Image> cell = imageGrid.add(image).size(25);
            if (x == 1) {
                cell.row();
                y--;
                x = Settings.getInputColumns() + 1;
            }
        }

        Skin skin = Assets.getSkin();

        TextButton train = new TextButton("Train", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton presentPattern = new TextButton("Present Pattern", skin);
        TextButton options = new TextButton("Options", skin);

        train.setSize(5, 15);
        options.setSize(5, 15);
        exit.setSize(5, 15);
        presentPattern.setSize(5, 15);

        buttonsTable.setPosition(Gdx.graphics.getWidth() / 2, 30);
        buttonsTable.add(train).size(Gdx.graphics.getWidth() / 3, 30);
        buttonsTable.add(presentPattern).size(Gdx.graphics.getWidth() / 3, 30);
        buttonsTable.add(exit).size(Gdx.graphics.getWidth() / 3, 30);
        buttonsTable.row();
        buttonsTable.add(options).size(Gdx.graphics.getWidth() / 3, 30);

        stage.addActor(buttonsTable);

        /**
         * Add listeners
         */
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        train.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hopfield.train(getPattern());
            }
        });

        presentPattern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean[] result = hopfield.present(getPattern());
                showResultPattern(result);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        for (Disposable d : disposables) {
            d.dispose();
        }
    }

    public void showResultPattern(boolean[] pattern) {
        for (int index = 0; index < (Settings.getInputRows() * Settings.getInputColumns()); index++) {
            Cell currentCell = imageGrid.getCells().get(index);
            Image image;

            if (!pattern[index]) {
                image = new Image(offDrawable);

                currentCell.setActor(image).size(35);

            } else {
                image = new Image(onDrawable);
                currentCell.setActor(image).size(35);
            }

            ClickListener cl = new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!(event.getListenerActor() instanceof Image)) // safety
                    {
                        return;
                    }
                    Image image = ((Image) event.getListenerActor());
                    if (image.getDrawable() == offDrawable) {
                        image.setDrawable(onDrawable);
                    } else {
                        image.setDrawable(offDrawable);
                    }
                }

            };

            image.addListener(cl);
        }
    }

    public boolean[] getPattern() {
        boolean[] patternFromGrid = new boolean[Settings.getInputRows() * Settings.getInputColumns()];

        for (int index = 0; index < (Settings.getInputRows() * Settings.getInputColumns()); index++) {

            Cell currentCell = imageGrid.getCells().get(index);

            if (currentCell.getActor() instanceof Image) {
                Image image = (Image) currentCell.getActor();

                if (image.getDrawable() == onDrawable) {
                    System.out.println("Index " + index + " = On");
                    patternFromGrid[index] = true;
                } else {
                    System.out.println("Index " + index + " = Off");
                    patternFromGrid[index] = false;
                }
            }
        }

        return patternFromGrid;
    }

}
