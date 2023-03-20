package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.template.*;

import java.util.Locale;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.GameData.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;

public class RoomScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
        
        var room = getRoom();
    
        final Music bgm = bgm_menu;
        if (!bgm.isPlaying()) {
            bgm.play();
            bgm.setVolume(core.bgm);
            bgm.setLooping(true);
        }
        
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        
        var root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.defaults().space(20).padLeft(20).padRight(20);
        root.pad(10);
        
        if (victoryText != null) {
            var label = new Label(victoryText, lLog);
            label.setWrap(true);
            root.add(label).growX();
            root.row();
        }
        
        var label = new Label("Room (" + column + "," + row + ")", lButton);
        root.add(label);
        
        root.row();
        label = new Label(room.description, lLog);
        label.setWrap(true);
        root.add(label).growX();
        
        root.row();
        var labelTable = new Table();
        root.add(labelTable);
        labelTable.defaults().space(10);
    
        root.row();
        label = new Label("There is only enough ephemeral energy to commit to one task. You may...", lLog);
        root.add(label);
        
        root.row();
        var buttonTable = new Table();
        root.add(buttonTable);
        buttonTable.defaults().space(10);
        
        var colorName = colorToName(room.marker);
        label = new Label("A mysterious " + colorName.toLowerCase(Locale.ROOT) + " marker rests here.", lLog);
        labelTable.add(label);
        
        var textButton = new TextButton("Change the marker color", skin);
        buttonTable.add(textButton);
        
        if (room.upgrade) {
            labelTable.row();
            label = new Label("An upgrade cube hums in the corner.", lLog);
            labelTable.add(label);
    
            buttonTable.row();
            textButton = new TextButton("Take the upgrade", skin);
            buttonTable.add(textButton);
        }
    
        if (room.tag) {
            labelTable.row();
            label = new Label("A powerful tag is within reach.", lLog);
            labelTable.add(label);
    
            buttonTable.row();
            textButton = new TextButton("Add a tag to a hero", skin);
            buttonTable.add(textButton);
        }
        
        if (room.hero != null) {
            labelTable.row();
            label = new Label("A hero, " + room.hero + ", is trapped in a hyperbolic phase cell.", lLog);
            labelTable.add(label);
    
            buttonTable.row();
            textButton = new TextButton("Release " + room.hero, skin);
            buttonTable.add(textButton);
        }
    
        buttonTable.row();
        textButton = new TextButton("Just leave and forget about it", skin);
        buttonTable.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                core.transition(new MapScreen());
            }
        });
        
        root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.top().right();
        stage.addActor(root);
        textButton = new TextButton("Retire", skin);
        root.add(textButton);
    
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                core.transition(new MenuScreen());
            }
        });
    }
    
    @Override
    public void act(float delta) {
        stage.act(delta);
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
