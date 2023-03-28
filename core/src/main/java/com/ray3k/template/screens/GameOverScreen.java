package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.template.*;
import com.ray3k.template.data.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.data.GameData.*;

public class GameOverScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private Pixmap pixmap;
    private Texture texture;
    private Image image;
    private SpineDrawable spine;
    private Image spineImage;
    
    @Override
    public void show() {
        super.show();
    
        final Music music = bgm_menu;
        if (!music.isPlaying()) {
            music.play();
            music.setVolume(bgm);
            music.setLooping(true);
        }
        
        stage = new Stage(new FitViewport(1024, 576), batch);
        Gdx.input.setInputProcessor(stage);
        
        var root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.defaults().space(20).padLeft(20).padRight(20);
        root.pad(10);
        
        if (failureText != null) {
            var label = new Label(failureText, lButton);
            root.add(label);
            root.row();
        }
        
        pixmap = new Pixmap(50, 50, Format.RGBA8888);
        pixmap.setColor(Color.CLEAR);
        pixmap.drawRectangle(0, 0, 50, 50);
        texture = new Texture(pixmap);
        
        var table = new Table();
        table.setBackground(skin.getDrawable("image-outline-10"));
        root.add(table);
        
        image = new Image(texture);
        table.add(image).size(300, 300);
        
        root.row();
        var label = new Label("(highlight to ping)", lLog);
        root.add(label);
        label.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                spineImage.setVisible(true);
                spine.getAnimationState().setAnimation(0, SpinePing.animationAnimation, true);
            }
    
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                spineImage.setVisible(false);
            }
        });
        
        root.row();
        var textButton = new TextButton("Try again", skin);
        root.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                core.transition(new NameScreen());
            }
        });
        
        root.pack();
        spine = new SpineDrawable(skeletonRenderer, SpinePing.skeletonData, SpinePing.animationData);
        spineImage = new Image(spine);
        var temp = new Vector2();
        image.localToStageCoordinates(temp);
        var factor = image.getWidth() / 50;
        spineImage.setPosition(temp.x + column * factor, temp.y + (50 - row) * factor, Align.center);
        spine.getAnimationState().setAnimation(0, SpinePing.animationAnimation, true);
        stage.addActor(spineImage);
        spineImage.setVisible(false);
    
        var handler = new FetchPixelHandler() {
            @Override
            public void handle(int c, int r, Color color) {
                Gdx.app.postRunnable(() -> {
                    pixmap.setColor(color);
                    pixmap.drawPixel(c, r);
                    texture.dispose();
                    texture = new Texture(pixmap);
                    image.setDrawable(new TextureRegionDrawable(texture));
                });
            }
        };
        
        Core.fetchPixelBatch(0, 10, handler);
        Core.fetchPixelBatch(10, 20, handler);
        Core.fetchPixelBatch(20, 30, handler);
        Core.fetchPixelBatch(30, 40, handler);
        Core.fetchPixelBatch(40, 50, handler);
    }
    
    @Override
    public void act(float delta) {
        stage.act(delta);
        spine.update(delta);
    }
    
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
