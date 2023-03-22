package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.data.*;
import com.ray3k.template.stripe.PopTable;
import com.ray3k.template.*;

import java.util.Locale;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.data.GameData.*;
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
            label.setAlignment(Align.center);
            root.add(label).growX();
            root.row();
        }
        
        var label = new Label("Room (" + column + "," + row + ")", lButton);
        root.add(label);
        
        root.row();
        label = new Label(room.description, lLog);
        label.setAlignment(Align.center);
        label.setWrap(true);
        root.add(label).growX();
    
        root.row();
        var colorName = colorToName(room.marker);
        var markerLabel = new Label("A mysterious " + colorName.toLowerCase(Locale.ROOT) + " marker rests here.", lLog);
        root.add(markerLabel);
        
        root.row();
        var textButton = new TextButton("Hack marker node", skin);
        root.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                var pop = new PopTable(wDefault);
                pop.setModal(true);
                pop.setKeepCenteredInWindow(true);
                pop.setKeepSizedWithinStage(true);
            
                pop.defaults().space(10);
                var label = new Label("Choose a color:", skin);
                pop.add(label);
            
                pop.row();
                var horizontalGroup = new HorizontalGroup();
                horizontalGroup.wrap();
                horizontalGroup.align(Align.center);
                horizontalGroup.space(10);
                horizontalGroup.wrapSpace(5);
                pop.add(horizontalGroup).width(350f);
            
                for (var name : colorNames) {
                    var textButton = new TextButton(name, skin);
                    horizontalGroup.addActor(textButton);
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            var index = colorNames.indexOf(textButton.getText().toString(), false);
                            var color = colors.get(index);
                            setPixel(column, row, color);
                            room.marker = color;
                            markerLabel.setText("A mysterious " + textButton.getText() + " marker rests here.");
                            pop.hide();
                        }
                    });
                }
            
                pop.row();
                var textButton = new TextButton("Cancel", skin);
                pop.add(textButton);
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        pop.hide();
                    }
                });
            
                pop.show(stage);
            }
        });
        
        root.row();
        var labelTable = new Table();
        root.add(labelTable);
        labelTable.defaults().space(10);
    
        if (room.upgrade || room.tag || room.hero != null) {
            root.row();
            label = new Label(
                    "There is only enough ephemeral energy to commit to one of the following tasks. You may...", lLog);
            root.add(label);
    
            root.row();
            var buttonTable = new Table();
            root.add(buttonTable);
            buttonTable.defaults().space(10);
    
            if (room.upgrade) {
                labelTable.row();
                label = new Label("An upgrade cube hums in the corner.", lLog);
                labelTable.add(label);
        
                buttonTable.row();
                textButton = new TextButton("Take the upgrade", skin);
                buttonTable.add(textButton);
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        var pop = new PopTable(wDefault);
                        pop.setKeepCenteredInWindow(true);
                        pop.setModal(true);
                        pop.pad(10);
    
                        pop.defaults().space(10);
                        var label = new Label("Which hero?", lButton);
                        pop.add(label);
    
                        for (var teamHero : playerTeam) {
                            pop.row();
                            var textButton = new TextButton(teamHero.name, skin);
                            pop.add(textButton);
                            textButton.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    pop.hide();
                                    
                                    var skillPop = new PopTable(wDefault);
                                    skillPop.setKeepCenteredInWindow(true);
                                    skillPop.setModal(true);
                                    skillPop.pad(10);
                                    
                                    for (var skill : teamHero.skills) {
                                        var textButton = new TextButton(skill, skin);
                                        skillPop.add(textButton);
                                        textButton.addListener(new ChangeListener() {
                                            @Override
                                            public void changed(ChangeEvent event, Actor actor) {
                                                var skillData = findSkill(skill);
                                                skillData.level++;
                                                
                                                room.upgrade = false;
                                                room.tag = false;
                                                room.hero = null;
                                                core.transition(new MapScreen());
                                            }
                                        });
                                    }
                                    
                                    skillPop.show(stage);
                                }
                            });
                        }
    
                        pop.row();
                        var textButton = new TextButton("Cancel", skin);
                        pop.add(textButton);
                        textButton.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                pop.hide();
                            }
                        });
    
                        pop.show(stage);
                    }
                });
            }
    
            if (room.tag) {
                labelTable.row();
                label = new Label("A powerful tag is within reach.", lLog);
                labelTable.add(label);
        
                buttonTable.row();
                textButton = new TextButton("Add a tag to a hero", skin);
                buttonTable.add(textButton);
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        room.upgrade = false;
                        room.tag = false;
                        room.hero = null;
                        core.transition(new MapScreen());
                    }
                });
            }
    
            if (room.hero != null) {
                labelTable.row();
                label = new Label("A hero, " + room.hero + ", is trapped in a hyperbolic phase cell.", lLog);
                labelTable.add(label);
        
                buttonTable.row();
                textButton = new TextButton("Release " + room.hero, skin);
                buttonTable.add(textButton);
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        var hero = new CharacterData(GameData.findHeroTemplate(room.hero));
    
                        var congratsPop = new PopTable(wDefault);
                        congratsPop.setKeepCenteredInWindow(true);
                        congratsPop.setModal(true);
                        congratsPop.pad(10);
    
                        congratsPop.defaults().space(10);
                        var label = new Label(room.hero + " has joined the Justice Buddies!", lButton);
                        congratsPop.add(label);
    
                        congratsPop.row();
                        var textButton = new TextButton("OK", skin);
                        congratsPop.add(textButton);
                        textButton.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                playerTeam.add(hero);
                                room.upgrade = false;
                                room.tag = false;
                                room.hero = null;
                                core.transition(new MapScreen());
                            }
                        });
                        
                        if (playerTeam.size < 4) {
                            congratsPop.show(stage);
                        } else {
                            var pop = new PopTable(wDefault);
                            pop.setKeepCenteredInWindow(true);
                            pop.setModal(true);
                            pop.pad(10);
    
                            pop.defaults().space(10);
                            label = new Label("You must kick someone from the team first...", lButton);
                            pop.add(label);
    
                            for (var teamHero : playerTeam) {
                                pop.row();
                                textButton = new TextButton(teamHero.name, skin);
                                pop.add(textButton);
                                textButton.addListener(new ChangeListener() {
                                    @Override
                                    public void changed(ChangeEvent event, Actor actor) {
                                        playerTeam.removeValue(teamHero, true);
                                        pop.hide();
                                        congratsPop.show(stage);
                                    }
                                });
                            }
                            
                            pop.row();
                            textButton = new TextButton("Cancel", skin);
                            pop.add(textButton);
                            textButton.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    pop.hide();
                                }
                            });
    
                            pop.show(stage);
                        }
                    }
                });
            }
    
            buttonTable.row();
            textButton = new TextButton("Just leave and forget about it", skin);
            buttonTable.add(textButton);
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.input.setInputProcessor(null);
                    Core.fetchPixel(column, row, (c, r, color) -> {
                        room.marker = color;
                        core.transition(new MapScreen());
                    });
                }
            });
        } else {
            root.row();
            textButton = new TextButton("Leave", skin);
            root.add(textButton);
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.input.setInputProcessor(null);
                    Core.fetchPixel(column, row, (c, r, color) -> {
                        room.marker = color;
                        core.transition(new MapScreen());
                    });
                }
            });
        }
        
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
}
