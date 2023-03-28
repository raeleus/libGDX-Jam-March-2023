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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ray3k.template.*;
import com.ray3k.template.data.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.data.GameData.*;

public class MapScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    Music music;
    
    @Override
    public void show() {
        super.show();
        
        var room = getRoom();
    
        music = bgm_menu;
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
        
        var table = new Table();
        root.add(table).colspan(5).uniformY();
        
        var upRoomButton = new Button(bMapColor);
        table.add(upRoomButton);
        
        table.row();
        var upRoomLabel = new Label("(" + column + "," + (row - 1) + ")", lButton);
        table.add(upRoomLabel).uniform();
        
        root.row();
        var button = new Button(bCompassUp);
        root.add(button).colspan(5);
        
        if (row - 1 >= 0) {
            var nextRoom = GameData.getRoom(column, row - 1);
            upRoomButton.setColor(nextRoom.marker);
            
            if (nextRoom.hasEnemies) {
                var label = new Label("DANGER", lLog);
                label.setColor(Color.FIREBRICK);
                upRoomButton.add(label);
            } else if (nextRoom.upgrade || nextRoom.tag || nextRoom.hero != null) {
                var label = new Label("LOOT", lLog);
                label.setColor(Color.GOLD);
                upRoomButton.add(label);
            }
            
            if (nextRoom.restoration && !nextRoom.hasEnemies) {
                upRoomButton.row();
                var label = new Label("HEART", lLog);
                label.setColor(Color.PINK);
                upRoomButton.add(label);
            }
            
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    row--;
                    loadNextRoom();
                }
            };
            
            upRoomButton.addListener(listener);
            Core.fetchPixel(column, row - 1, (c, r, color) -> {
                nextRoom.marker = color;
                upRoomButton.setColor(color);
            });
            
            button.addListener(listener);
        } else {
            button.setVisible(false);
            upRoomButton.setVisible(false);
            upRoomLabel.setVisible(false);
        }
        
        root.row();
        
        table = new Table();
        root.add(table).uniform();
        
        final var leftRoomButton = new Button(bMapColor);
        table.add(leftRoomButton);
        
        table.row();
        var leftRoomLabel = new Label("(" + (column - 1) + "," + row + ")", lButton);
        table.add(leftRoomLabel);
        
        button = new Button(bCompassLeft);
        root.add(button);
        
        if (column - 1 >= 0) {
            var nextRoom = GameData.getRoom(column - 1, row);
            leftRoomButton.setColor(nextRoom.marker);
    
            if (nextRoom.hasEnemies) {
                var label = new Label("DANGER", lLog);
                label.setColor(Color.FIREBRICK);
                leftRoomButton.add(label);
            } else if (nextRoom.upgrade || nextRoom.tag || nextRoom.hero != null) {
                var label = new Label("LOOT", lLog);
                label.setColor(Color.GOLD);
                leftRoomButton.add(label);
            }
    
            if (nextRoom.restoration && !nextRoom.hasEnemies) {
                leftRoomButton.row();
                var label = new Label("HEART", lLog);
                label.setColor(Color.PINK);
                leftRoomButton.add(label);
            }
            
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    column--;
                    loadNextRoom();
                }
            };
            
            leftRoomButton.addListener(listener);
            Core.fetchPixel(column -1, row, (c, r, color) -> {
                nextRoom.marker = color;
                leftRoomButton.setColor(color);
            });
            
            button.addListener(listener);
        } else {
            button.setVisible(false);
            leftRoomButton.setVisible(false);
            leftRoomLabel.setVisible(false);
        }
        
        table = new Table();
        root.add(table).uniform();
        
        var thisRoomButton = new Button(bMapColor);
        table.add(thisRoomButton);
        thisRoomButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sfx_click.play(sfx);
                loadNextRoom();
            }
        });
        thisRoomButton.setColor(room.marker);
    
        if (room.hasEnemies) {
            var label = new Label("DANGER", lLog);
            label.setColor(Color.FIREBRICK);
            thisRoomButton.add(label);
        } else if (room.upgrade || room.tag || room.hero != null) {
            var label = new Label("LOOT", lLog);
            label.setColor(Color.GOLD);
            thisRoomButton.add(label);
        }
    
        if (room.restoration && !room.hasEnemies) {
            thisRoomButton.row();
            var label = new Label("HEART", lLog);
            label.setColor(Color.PINK);
            thisRoomButton.add(label);
        }
        
        table.row();
        var label = new Label("(" + column + "," + row + ")", lButton);
        table.add(label);
        
        button = new Button(bCompassRight);
        root.add(button);
        
        table = new Table();
        root.add(table).uniform();
        
        var rightRoomButton = new Button(bMapColor);
        table.add(rightRoomButton);
        
        table.row();
        var rightRoomLabel = new Label("(" + (column + 1) + "," + row + ")", lButton);
        table.add(rightRoomLabel);
        
        if (column + 1 < 50) {
            var nextRoom = GameData.getRoom(column + 1, row);
            
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    column++;
                    loadNextRoom();
                }
            };
            
            button.addListener(listener);
            
            rightRoomButton.setColor(nextRoom.marker);
            if (nextRoom.hasEnemies) {
                label = new Label("DANGER", lLog);
                label.setColor(Color.FIREBRICK);
                rightRoomButton.add(label);
            } else if (nextRoom.upgrade || nextRoom.tag || nextRoom.hero != null) {
                label = new Label("LOOT", lLog);
                label.setColor(Color.GOLD);
                rightRoomButton.add(label);
            }
    
            if (nextRoom.restoration && !nextRoom.hasEnemies) {
                upRoomButton.row();
                label = new Label("HEART", lLog);
                label.setColor(Color.PINK);
                rightRoomButton.add(label);
            }
            
            rightRoomButton.addListener(listener);
            Core.fetchPixel(column + 1, row, (c, r, color) -> {
                nextRoom.marker = color;
                rightRoomButton.setColor(color);
            });
        } else {
            button.setVisible(false);
            rightRoomButton.setVisible(false);
            rightRoomLabel.setVisible(false);
        }
        
        root.row();
        button = new Button(bCompassDown);
        root.add(button).colspan(5);
        
        root.row();
        table = new Table();
        root.add(table).colspan(5).uniformY();
        
        var downRoomButton = new Button(bMapColor);
        table.add(downRoomButton);
        
        table.row();
        var downRoomLabel = new Label("(" + column + "," + (row + 1) + ")", lButton);
        table.add(downRoomLabel);
        
        if (row + 1 < 50) {
            var nextRoom = GameData.getRoom(column, row + 1);
            
            var listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    row++;
                    loadNextRoom();
                }
            };
            
            button.addListener(listener);
            
            downRoomButton.setColor(nextRoom.marker);
            if (nextRoom.hasEnemies) {
                label = new Label("DANGER", lLog);
                label.setColor(Color.FIREBRICK);
                downRoomButton.add(label);
            } else if (nextRoom.upgrade || nextRoom.tag || nextRoom.hero != null) {
                label = new Label("LOOT", lLog);
                label.setColor(Color.GOLD);
                downRoomButton.add(label);
            }
    
            if (nextRoom.restoration && !nextRoom.hasEnemies) {
                downRoomButton.row();
                label = new Label("HEART", lLog);
                label.setColor(Color.PINK);
                downRoomButton.add(label);
            }
            
            downRoomButton.addListener(listener);
            Core.fetchPixel(column, row + 1, (c, r, color) -> {
                nextRoom.marker = color;
                downRoomButton.setColor(color);
            });
        } else {
            button.setVisible(false);
            downRoomButton.setVisible(false);
            downRoomLabel.setVisible(false);
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
                sfx_click.play(sfx);
                core.transition(new GameOverScreen());
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
    
    private void loadNextRoom() {
        var room = getRoom();
        if (room.hasEnemies) {
            core.transition(new PreBattleScreen());
            music.stop();
        } else {
            core.transition(new RoomScreen());
        }
    }
}
