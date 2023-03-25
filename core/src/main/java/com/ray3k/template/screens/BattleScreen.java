package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TextraLabel;
import com.ray3k.template.*;
import com.ray3k.template.data.CharacterData;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.data.GameData.*;

public class BattleScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private Array<Table> enemyCells = new Array<>();
    private Array<Table> characterCells = new Array<>();
    
    @Override
    public void show() {
        super.show();
        var room = getRoom();
    
        final Music music = bgm_game;
        if (!music.isPlaying()) {
            music.play();
            music.setVolume(bgm);
            music.setLooping(true);
        }
        
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        
        var root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.defaults().space(20).padLeft(20).padRight(20);
        root.pad(10);
        
        var table = new Table();
        root.add(table).grow();
        
        var label = new Label("THEM", lDefault);
        table.add(label).uniformX().expandX();
        
        var subTable = new Table();
        table.add(subTable);
    
        subTable.defaults().space(20).size(152, 102);
        var enemyCell3 = new Table();
        boolean found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 3) {
                found = true;
                enemyCell3.setBackground(skin.getDrawable("character-box-10"));
    
                var textra = new TextraLabel(enemy.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                enemyCell3.add(textra).growX();
                break;
            }
        }
        if (!found) enemyCell3.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell3);
        enemyCells.add(enemyCell3);
    
        var enemyCell4 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 4) {
                found = true;
                enemyCell4.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(enemy.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                enemyCell4.add(textra).growX();
                break;
            }
        }
        if (!found) enemyCell4.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell4);
        enemyCells.add(enemyCell4);
    
        var enemyCell5 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 5) {
                found = true;
                enemyCell5.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(enemy.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                enemyCell5.add(textra).growX();
                break;
            }
        }
        if (!found) enemyCell5.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell5);
        enemyCells.add(enemyCell5);
    
        subTable.row().padTop(20);
        var enemyCell0 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 0) {
                found = true;
                enemyCell0.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(enemy.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                enemyCell0.add(textra).growX();
                break;
            }
        }
        if (!found) enemyCell0.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell0);
        enemyCells.insert(0, enemyCell0);
    
        var enemyCell1 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 1) {
                found = true;
                enemyCell1.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(enemy.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                enemyCell1.add(textra).growX();
                break;
            }
        }
        if (!found) enemyCell1.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell1);
        enemyCells.insert(1, enemyCell1);
    
        var enemyCell2 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 2) {
                found = true;
                enemyCell2.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(enemy.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                enemyCell2.add(textra).growX();
                break;
            }
        }
        if (!found) enemyCell2.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell2);
        enemyCells.insert(2, enemyCell2);
        
        table.add().uniformX().expandX();
        
        root.row();
        var image = new Image(skin, "battle-divider-10");
        root.add(image).growX();
        
        root.row();
        table = new Table();
        root.add(table).grow();
    
        label = new Label("US", lDefault);
        table.add(label).uniformX().expandX();
    
        subTable = new Table();
        table.add(subTable);
    
        subTable.defaults().space(20).size(152, 102);
        var playerCell0 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 0) {
                found = true;
                playerCell0.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(hero.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                playerCell0.add(textra).growX();
                break;
            }
        }
        if (!found) playerCell0.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell0);
        playerCell0.setTouchable(Touchable.enabled);
    
        var playerCell1 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 1) {
                found = true;
                playerCell1.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(hero.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                playerCell1.add(textra).growX();
                break;
            }
        }
        if (!found) playerCell1.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell1);
        playerCell1.setTouchable(Touchable.enabled);
    
        var playerCell2 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 2) {
                found = true;
                playerCell2.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(hero.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                playerCell2.add(textra).growX();
                break;
            }
        }
        if (!found) playerCell2.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell2);
        playerCell2.setTouchable(Touchable.enabled);
    
        subTable.row().padTop(20);
        var playerCell3 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 3) {
                found = true;
                playerCell3.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(hero.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                playerCell3.add(textra).growX();
                break;
            }
        }
        if (!found) playerCell3.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell3);
        playerCell3.setTouchable(Touchable.enabled);
    
        var playerCell4 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 4) {
                found = true;
                playerCell4.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(hero.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                playerCell4.add(textra).growX();
                break;
            }
        }
        if (!found) playerCell4.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell4);
        playerCell4.setTouchable(Touchable.enabled);
    
        var playerCell5 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 5) {
                found = true;
                playerCell5.setBackground(skin.getDrawable("character-box-10"));
            
                var textra = new TextraLabel(hero.name, skin);
                textra.setAlignment(Align.center);
                textra.setWrap(true);
                playerCell5.add(textra).growX();
                break;
            }
        }
        if (!found) playerCell5.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell5);
        playerCell5.setTouchable(Touchable.enabled);
        
        table.add().uniformX().expandX();
    
        root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.top().right();
        stage.addActor(root);
        
        var textButton = new TextButton("Retire", skin);
        root.add(textButton);
    
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.stop();
                core.transition(new GameOverScreen());
            }
        });
    
        root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.bottom().right();
        stage.addActor(root);
        
        label = new Label("", lLog);
        label.setWrap(true);
        root.add(label).width(180);
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
