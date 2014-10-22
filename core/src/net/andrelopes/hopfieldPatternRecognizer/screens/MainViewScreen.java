package net.andrelopes.hopfieldPatternRecognizer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
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
    private Window settingsWindow;

    private Label infoLabel;

    @Override
    public void show() {
        hopfield = new Hopfield();

        Gdx.input.setInputProcessor(stage);
        imageGrid.debug();
        imageGrid.setFillParent(true);
        stage.addActor(imageGrid);

        disposables.add(off);
        disposables.add(on);
        resetGrid();
        Skin skin = Assets.getSkin();

        TextButton train = new TextButton("Train", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton presentPattern = new TextButton("Present Pattern", skin);
        TextButton options = new TextButton("Options", skin);

        infoLabel = new Label("Information ", skin);
        infoLabel.setColor(Color.BLACK);

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
        buttonsTable.add(infoLabel).colspan(2).fillX();
        stage.addActor(buttonsTable);

        /**
         * Add listeners
         */
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsWindow.setVisible(!settingsWindow.isVisible());
                settingsWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
                System.out.println(settingsWindow.isVisible());
                settingsWindow.top();

            }
        });

        train.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showInfo("Training Hopfield Neural Network..Please Wait!");
                hopfield.train(getPattern());
                showInfo("Finished Training Hopfield Neural Network!");
            }
        });

        presentPattern.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showInfo("Presenting Pattern...");
                boolean[] result = hopfield.present(getPattern());
                if (result != null) {
                    showResultPattern(result);
                }
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        /**
         * Build Settings Window
         */
        settingsWindow = new Window("Settings", skin);
        settingsWindow.setVisible(false);
        settingsWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);

        /**
         * Create Actors for Settings Window
         */
        Label gridWidthLabel = new Label("Grid Width ", skin);
        Label gridHeightLabel = new Label("Grid Height ", skin);

        TextField.TextFieldFilter textFieldFilter = new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {

                if (textField.getText().isEmpty()) {
                    if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
                        return true;
                    }
                } else if (!textField.getText().isEmpty()) {
                    if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '0') {
                        return true;
                    }
                }
                return false;
            }
        };

        /**
         * Accept Size of the Grid/Matrix
         */
        final TextField gridWidtheTextField = new TextField("", skin);
        gridWidtheTextField.setTextFieldFilter(textFieldFilter);

        final TextField gridHeightTextField = new TextField("", skin);
        gridHeightTextField.setTextFieldFilter(textFieldFilter);

        /**
         * Apply Change Size of the Grid Based on the TextField
         */
        TextButton applyGridSizeTextButton = new TextButton("Apply", skin);
        applyGridSizeTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    int width = Integer.parseInt(gridWidtheTextField.getText());
                    Settings.setInputColumns(width);

                    int height = Integer.parseInt(gridHeightTextField.getText());
                    Settings.setInputRows(height);

                    showInfo("Resetting Grid...");
                    resetGrid();
                } catch (java.lang.NumberFormatException ex) {
                    showInfo("Invalid Grid Size!");
                    ex.printStackTrace();
                }
            }
        });

        /**
         * Hide Window
         */
        TextButton closeWindow = new TextButton("Close Window", skin);
        closeWindow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsWindow.setVisible(false);
            }
        });

        /**
         * Add Actors to Settings Window
         */
        //First row
        settingsWindow.add(gridWidthLabel).expand().padRight(5);
        settingsWindow.add(gridWidtheTextField).expand().row();

        //Second Row
        settingsWindow.add(gridHeightLabel).expand().padRight(5);
        settingsWindow.add(gridHeightTextField).expand().row();

        settingsWindow.add(applyGridSizeTextButton).fillX().expand().colspan(2).row().pad(5);
        settingsWindow.add(closeWindow).expand().colspan(2).fillX().pad(5);

        /**
         * Pack
         */
        settingsWindow.pack();

        /**
         * Add Window to Stage
         */
        stage.addActor(settingsWindow);

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

        showInfo("Showing Result...");
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

    private void resetGrid() {

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

        if (imageGrid != null) {
            imageGrid.clear();
        }
        
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
    }

    public void showInfo(String msg) {
        infoLabel.setText(msg);
    }

}
