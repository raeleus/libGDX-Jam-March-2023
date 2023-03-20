package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.*;

import java.util.Locale;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.GameData.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;

public class MapScreen extends JamScreen {
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
        
        if (row - 1 >= 0) {
            var nextRoom = GameData.getRoom(column, row - 1);
            
            var table = new Table();
            root.add(table).colspan(5).uniformY();
    
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    row--;
                    core.transition(new MapScreen());
                }
            };
            
            var upRoomButton = new Button(bMapColor);
            table.add(upRoomButton);
            upRoomButton.setColor(nextRoom.marker);
            upRoomButton.addListener(listener);
            Core.fetchPixel(column, row - 1, color -> {
                System.out.println(color);
                nextRoom.marker = color;
                upRoomButton.setColor(color);
            });
            
            table.row();
            var label = new Label("(" + column + "," + (row - 1) + ")", lButton);
            table.add(label).uniform();
    
            root.row();
            var button = new Button(bCompassUp);
            root.add(button).colspan(5);
            button.addListener(listener);
        } else {
            root.add().colspan(5).uniformY();
        }
    
        
        root.row();
        if (column - 1 >= 0) {
            var nextRoom = GameData.getRoom(column - 1, row);
            
            var table = new Table();
            root.add(table).uniform();
    
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    column--;
                    core.transition(new MapScreen());
                }
            };
    
            final var leftRoomButton = new Button(bMapColor);
            table.add(leftRoomButton);
            leftRoomButton.setColor(nextRoom.marker);
            leftRoomButton.addListener(listener);
            Core.fetchPixel(column, row - 1, color -> {
                System.out.println(color);
                nextRoom.marker = color;
                leftRoomButton.setColor(color);
            });
    
            table.row();
            var label = new Label("(" + (column - 1) + "," + row + ")", lButton);
            table.add(label);
    
            var button = new Button(bCompassLeft);
            root.add(button);
            button.addListener(listener);
        } else {
            root.add().uniform();
        }
    
        var table = new Table();
        root.add(table).uniform();
    
        var thisRoomButton = new Button(bMapColor);
        table.add(thisRoomButton);
        thisRoomButton.setColor(room.marker);
        Core.fetchPixel(column, row - 1, color -> {
            System.out.println(color);
            room.marker = color;
            thisRoomButton.setColor(color);
        });
    
        table.row();
        var label = new Label("(" + column + "," + row + ")", lButton);
        table.add(label);
    
        if (column + 1 < 50) {
            var nextRoom = GameData.getRoom(column + 1, row);
    
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    column++;
                    core.transition(new MapScreen());
                }
            };
            
            var button = new Button(bCompassRight);
            root.add(button);
            button.addListener(listener);
    
            table = new Table();
            root.add(table).uniform();
    
            var rightRoomButton = new Button(bMapColor);
            table.add(rightRoomButton);
            rightRoomButton.setColor(nextRoom.marker);
            rightRoomButton.addListener(listener);
            Core.fetchPixel(column, row - 1, color -> {
                System.out.println(color);
                nextRoom.marker = color;
                rightRoomButton.setColor(color);
            });
    
            table.row();
            label = new Label("(" + (column + 1) + "," + row + ")", lButton);
            table.add(label);
        } else {
            root.add().uniform();
        }
        
        root.row();
        if (row + 1 < 50) {
            var nextRoom = GameData.getRoom(column, row + 1);
    
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    row++;
                    core.transition(new MapScreen());
                }
            };
            
            var button = new Button(bCompassDown);
            root.add(button).colspan(5);
            button.addListener(listener);
    
            root.row();
            table = new Table();
            root.add(table).colspan(5).uniformY();
    
            var downRoomButton = new Button(bMapColor);
            table.add(downRoomButton);
            downRoomButton.setColor(nextRoom.marker);
            downRoomButton.addListener(listener);
            Core.fetchPixel(column, row - 1, color -> {
                System.out.println(color);
                nextRoom.marker = color;
                downRoomButton.setColor(color);
            });
    
            table.row();
            label = new Label("(" + column + "," + (row + 1) + ")", lButton);
            table.add(label);
        } else {
            root.add().uniformY();
        }
        
        root = new Table();
        root.setFillParent(true);
        root.pad(20);
        root.top();
        stage.addActor(root);
        
        label = new Label("Choose your destination", skin);
        root.add(label).expandX().left();
        
        var textButton = new TextButton("Retire", skin);
        root.add(textButton).expandX().right();
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
