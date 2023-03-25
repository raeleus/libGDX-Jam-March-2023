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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.data.*;
import com.ray3k.template.stripe.*;
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
    
                                    var upgradeChoicePop = new PopTable(wDefault);
                                    upgradeChoicePop.setKeepCenteredInWindow(true);
                                    upgradeChoicePop.setModal(true);
                                    upgradeChoicePop.pad(10);
                                    
                                    upgradeChoicePop.defaults().space(10);
                                    var textButton = new TextButton("Upgrade Skill", skin);
                                    upgradeChoicePop.add(textButton);
                                    textButton.addListener(new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            upgradeChoicePop.hide();
                                            
                                            var skillPop = new PopTable(wDefault);
                                            skillPop.setKeepCenteredInWindow(true);
                                            skillPop.setModal(true);
                                            skillPop.pad(10);
    
                                            skillPop.defaults().space(10);
                                            var label = new Label("No upgradeable skills...", lButton);
                                            skillPop.add(label);
                                            
                                            for (var skill : teamHero.skills) {
                                                if (skill.level + 1 < skill.maxLevel) {
                                                    label.setText("Upgrade Skill:");
                                                    
                                                    skillPop.row();
                                                    var textButton = new TextButton(skill.name + " (" + skill.level + "/" + skill.maxLevel + ")", skin);
                                                    skillPop.add(textButton);
                                                    textButton.addListener(new ChangeListener() {
                                                        @Override
                                                        public void changed(ChangeEvent event, Actor actor) {
                                                            skill.level++;
    
                                                            room.upgrade = false;
                                                            room.tag = false;
                                                            room.hero = null;
                                                            core.transition(new MapScreen());
                                                        }
                                                    });
                                                }
                                            }
                                            
                                            skillPop.row();
                                            var textButton = new TextButton("Cancel", skin);
                                            skillPop.add(textButton);
                                            textButton.addListener(new ChangeListener() {
                                                @Override
                                                public void changed(ChangeEvent event, Actor actor) {
                                                    skillPop.hide();
                                                }
                                            });
    
                                            skillPop.show(stage);
                                        }
                                    });
    
                                    upgradeChoicePop.row();
                                    textButton = new TextButton("Upgrade Tag", skin);
                                    upgradeChoicePop.add(textButton);
                                    textButton.addListener(new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            upgradeChoicePop.hide();
            
                                            var tagPop = new PopTable(wDefault);
                                            tagPop.setKeepCenteredInWindow(true);
                                            tagPop.setModal(true);
                                            tagPop.pad(10);
            
                                            tagPop.defaults().space(10);
                                            var label = new Label("No upgradeable tags...", lButton);
                                            tagPop.add(label);
    
                                            
                                            for (var tag : teamHero.tags) {
                                                if (tag.availableSkills.size > 0) {
                                                    label.setText("Upgrade Tag:");
                    
                                                    tagPop.row();
                                                    var textButton = new TextButton(tag.name + " (" + tag.level + "/" + (tag.availableSkills.size + tag.level) + ")", skin);
                                                    tagPop.add(textButton);
                                                    textButton.addListener(new ChangeListener() {
                                                        @Override
                                                        public void changed(ChangeEvent event, Actor actor) {
                                                            tagPop.hide();
                                                            
                                                            if (teamHero.skills.size >= 6) {
                                                                var deleteSkillPop = new PopTable(wDefault);
                                                                deleteSkillPop.setKeepCenteredInWindow(true);
                                                                deleteSkillPop.setModal(true);
                                                                deleteSkillPop.pad(10);
    
                                                                deleteSkillPop.defaults().space(10);
                                                                var label = new Label("You can only have 6 skills equipped.\nChoose a skill to remove:", lButton);
                                                                label.setAlignment(Align.center);
                                                                deleteSkillPop.add(label);
                                                                
                                                                for (var skill : teamHero.skills) {
                                                                    deleteSkillPop.row();
                                                                    var textButton = new TextButton(skill.name + " (" + skill.level + "/" + skill.maxLevel + ")", skin);
                                                                    deleteSkillPop.add(textButton);
                                                                    textButton.addListener(new ChangeListener() {
                                                                        @Override
                                                                        public void changed(ChangeEvent event,
                                                                                            Actor actor) {
                                                                            deleteSkillPop.hide();
                                                                            
                                                                            teamHero.skills.removeValue(skill, true);
    
                                                                            showConfirmSkillPop(tag, teamHero, room);
                                                                        }
                                                                    });
                                                                }
    
                                                                deleteSkillPop.row();
                                                                var textButton = new TextButton("Cancel", skin);
                                                                deleteSkillPop.add(textButton);
                                                                textButton.addListener(new ChangeListener() {
                                                                    @Override
                                                                    public void changed(ChangeEvent event,
                                                                                        Actor actor) {
                                                                        deleteSkillPop.hide();
                                                                    }
                                                                });
                                                                
                                                                deleteSkillPop.show(stage);
                                                            } else {
                                                                showConfirmSkillPop(tag, teamHero, room);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
            
                                            tagPop.row();
                                            var textButton = new TextButton("Cancel", skin);
                                            tagPop.add(textButton);
                                            textButton.addListener(new ChangeListener() {
                                                @Override
                                                public void changed(ChangeEvent event, Actor actor) {
                                                    tagPop.hide();
                                                }
                                            });
            
                                            tagPop.show(stage);
                                        }
                                    });
    
                                    upgradeChoicePop.row();
                                    textButton = new TextButton("Cancel", skin);
                                    upgradeChoicePop.add(textButton);
                                    textButton.addListener(new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            upgradeChoicePop.hide();
                                        }
                                    });
                                    
                                    upgradeChoicePop.show(stage);
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
    
                                    showConfirmTagPop(teamHero, room);
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
                        showConfirmHeroPop(room);
                    }
                });
                var listener = new PopTableHoverListener(Align.top, Align.top, new PopTable.PopTableStyle(wPointerDown));
                textButton.addListener(listener);
                var pop = listener.getPopTable();
    
                pop.defaults().space(10);
                var description = findHeroTemplate(room.hero).description;
                label = new Label(description, lLog);
                label.setWrap(true);
                label.setAlignment(Align.center);
                pop.add(label).growX();
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
    
    private void showConfirmSkillPop(TagData tag, CharacterData teamHero, RoomData room) {
        tag.level++;
        var skill = tag.availableSkills.random();
        tag.availableSkills.removeValue(skill, false);
        teamHero.addSkill(skill);
    
        var tagConfirmationPop = new PopTable(wDefault);
        tagConfirmationPop.setKeepCenteredInWindow(true);
        tagConfirmationPop.setModal(true);
        tagConfirmationPop.pad(10);
    
        tagConfirmationPop.defaults().space(10);
        var label = new Label("Added skill: " + skill, lButton);
        tagConfirmationPop.add(label);
    
        tagConfirmationPop.row();
        var textButton = new TextButton("OK", skin);
        tagConfirmationPop.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                room.upgrade = false;
                room.tag = false;
                room.hero = null;
                core.transition(new MapScreen());
            }
        });
        tagConfirmationPop.show(stage);
    }
    
    private void showConfirmTagPop(CharacterData teamHero, RoomData room) {
        var tags = new Array<TagData>();
        tags.addAll(tagTemplates);
        var iter = tags.iterator();
        while (iter.hasNext()) {
            var tag = iter.next();
            for (var heroTag : teamHero.tags) {
                if (tag.name.equals(heroTag.name)) iter.remove();
            }
        }
        
        var tag = tags.random();
        teamHero.addTag(tag.name, false);
        
        var tagConfirmationPop = new PopTable(wDefault);
        tagConfirmationPop.setKeepCenteredInWindow(true);
        tagConfirmationPop.setModal(true);
        tagConfirmationPop.pad(10);
        
        tagConfirmationPop.defaults().space(10);
        var label = new Label("Added tag: " + tag.name, lButton);
        tagConfirmationPop.add(label);
        
        tagConfirmationPop.row();
        var textButton = new TextButton("OK", skin);
        tagConfirmationPop.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                room.upgrade = false;
                room.tag = false;
                room.hero = null;
                core.transition(new MapScreen());
            }
        });
        tagConfirmationPop.show(stage);
    }
    
    private void showConfirmHeroPop(RoomData room) {
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
