package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
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
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;

public class NameScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
    
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
        
        root.defaults().pad(20);
        var label = new Label("A NAME HAS POWER", lBig);
        root.add(label);
        
        root.row();
        var textField = new TextField("Hero", skin);
        textField.setAlignment(Align.center);
        textField.selectAll();
        root.add(textField).width(620);
        stage.setKeyboardFocus(textField);
        
        root.row();
        var table = new Table();
        root.add(table);
        
        table.defaults().space(60);
        var characterTable = new Table();
        characterTable.setBackground(skin.getDrawable("character-box-10"));
        table.add(characterTable).size(152, 102);
        
        var characterLabel = new TypingLabel("Hero", lDefault);
        characterLabel.setWrap(true);
        characterLabel.setAlignment(Align.center);
        characterLabel.skipToTheEnd();
        characterTable.add(characterLabel).grow();
        
        var tagLabel = new Label("a DUMBASS", skin);
        table.add(tagLabel);
        
        root.row();
        var textButton = new TextButton("Begin Mission", skin);
        root.add(textButton);
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                bgm.stop();
                core.transition(new GameScreen());
            }
        });
        
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                var name = textField.getText();
                characterLabel.setText(name);
                var index = matchTagToName(name.toLowerCase(Locale.ROOT));
                tagLabel.setText("a " + tags.get(index).toUpperCase(Locale.ROOT));
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
    
    public static int matchTagToName(String name) {
        for (int i = 0; i < tagNameMatches.size; i++) {
            var tag = tagNameMatches.get(i);
            var keywords = tag.split(",");
            for (var keyword : keywords) {
                if (name.contains(keyword)) return i;
            }
        }
    
        int score = 0;
        for (int j = 0; j < name.length(); j++) score += name.charAt(j);
        
        return score * name.length() % (tags.size - 1) + 1;
    }
}
