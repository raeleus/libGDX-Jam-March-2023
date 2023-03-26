package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.template.*;
import com.ray3k.template.battle.*;
import com.ray3k.template.data.CharacterData;
import com.ray3k.template.data.*;
import com.ray3k.template.stripe.*;
import com.ray3k.template.stripe.PopTable.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Utils.*;
import static com.ray3k.template.data.GameData.*;

public class BattleScreen extends JamScreen {
    public Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private Array<Table> enemyTiles = new Array<>();
    private Array<Table> playerTiles = new Array<>();
    private int turn;
    private final PopTable popTable = new PopTable();
    private Image dividerImage;
    private Array<Button> selectables = new Array<>();
    private Array<Image> highlights = new Array<>();
    private Label playOrderLabel;
    public Array<SpineDrawable> spineDrawables = new Array<>();
    public Music music;
    
    @Override
    public void show() {
        super.show();
        var room = getRoom();
    
        music = bgm_game;
        if (!music.isPlaying()) {
            music.play();
            music.setVolume(bgm);
            music.setLooping(true);
        }
        
        characterOrder.clear();
        calculateOrder(turn);
        
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        
        popTable.setStyle(new PopTableStyle(wPointerDown));
        
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
                enemyCell3.setUserObject(enemy);
    
                addCharacterToCell(enemy, enemyCell3);
                break;
            }
        }
        if (!found) enemyCell3.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell3);
        enemyTiles.add(enemyCell3);
    
        var enemyCell4 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 4) {
                found = true;
                enemyCell4.setBackground(skin.getDrawable("character-box-10"));
                enemyCell4.setUserObject(enemy);
    
                addCharacterToCell(enemy, enemyCell4);
                break;
            }
        }
        if (!found) enemyCell4.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell4);
        enemyTiles.add(enemyCell4);
    
        var enemyCell5 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 5) {
                found = true;
                enemyCell5.setBackground(skin.getDrawable("character-box-10"));
                enemyCell5.setUserObject(enemy);
    
                addCharacterToCell(enemy, enemyCell5);
                break;
            }
        }
        if (!found) enemyCell5.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell5);
        enemyTiles.add(enemyCell5);
    
        subTable.row().padTop(20);
        var enemyCell0 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 0) {
                found = true;
                enemyCell0.setBackground(skin.getDrawable("character-box-10"));
                enemyCell0.setUserObject(enemy);
    
                addCharacterToCell(enemy, enemyCell0);
                break;
            }
        }
        if (!found) enemyCell0.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell0);
        enemyTiles.insert(0, enemyCell0);
    
        var enemyCell1 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 1) {
                found = true;
                enemyCell1.setBackground(skin.getDrawable("character-box-10"));
                enemyCell1.setUserObject(enemy);
    
                addCharacterToCell(enemy, enemyCell1);
                break;
            }
        }
        if (!found) enemyCell1.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell1);
        enemyTiles.insert(1, enemyCell1);
    
        var enemyCell2 = new Table();
        found = false;
        for (var enemy : enemyTeam) {
            if (enemy.position == 2) {
                found = true;
                enemyCell2.setBackground(skin.getDrawable("character-box-10"));
                enemyCell2.setUserObject(enemy);
    
                addCharacterToCell(enemy, enemyCell2);
                break;
            }
        }
        if (!found) enemyCell2.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(enemyCell2);
        enemyTiles.insert(2, enemyCell2);
        
        table.add().uniformX().expandX();
        
        root.row();
        dividerImage = new Image(skin, "battle-divider-10");
        root.add(dividerImage).growX();
        
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
                playerCell0.setUserObject(hero);
            
                addCharacterToCell(hero, playerCell0);
                break;
            }
        }
        if (!found) playerCell0.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell0);
        playerCell0.setTouchable(Touchable.enabled);
        playerTiles.add(playerCell0);
    
        var playerCell1 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 1) {
                found = true;
                playerCell1.setBackground(skin.getDrawable("character-box-10"));
                playerCell1.setUserObject(hero);
    
                addCharacterToCell(hero, playerCell1);
                break;
            }
        }
        if (!found) playerCell1.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell1);
        playerCell1.setTouchable(Touchable.enabled);
        playerTiles.add(playerCell1);
    
        var playerCell2 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 2) {
                found = true;
                playerCell2.setBackground(skin.getDrawable("character-box-10"));
                playerCell2.setUserObject(hero);
    
                addCharacterToCell(hero, playerCell2);
                break;
            }
        }
        if (!found) playerCell2.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell2);
        playerCell2.setTouchable(Touchable.enabled);
        playerTiles.add(playerCell2);
    
        subTable.row().padTop(20);
        var playerCell3 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 3) {
                found = true;
                playerCell3.setBackground(skin.getDrawable("character-box-10"));
                playerCell3.setUserObject(hero);
    
                addCharacterToCell(hero, playerCell3);
                break;
            }
        }
        if (!found) playerCell3.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell3);
        playerCell3.setTouchable(Touchable.enabled);
        playerTiles.add(playerCell3);
    
        var playerCell4 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 4) {
                found = true;
                playerCell4.setBackground(skin.getDrawable("character-box-10"));
                playerCell4.setUserObject(hero);
    
                addCharacterToCell(hero, playerCell4);
                break;
            }
        }
        if (!found) playerCell4.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell4);
        playerCell4.setTouchable(Touchable.enabled);
        playerTiles.add(playerCell4);
    
        var playerCell5 = new Table();
        found = false;
        for (var hero : playerTeam) {
            if (hero.position == 5) {
                found = true;
                playerCell5.setBackground(skin.getDrawable("character-box-10"));
                playerCell5.setUserObject(hero);
    
                addCharacterToCell(hero, playerCell5);
                break;
            }
        }
        if (!found) playerCell5.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell5);
        playerCell5.setTouchable(Touchable.enabled);
        playerTiles.add(playerCell5);
        
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
        
        playOrderLabel = new Label("", lLog);
        playOrderLabel.setWrap(true);
        root.add(playOrderLabel).width(180);
        
        conductTurn();
    }
    
    private void addCharacterToCell(CharacterData character, Table cell) {
        var stack = new Stack();
        cell.add(stack).grow();
    
        var textra = new TypingLabel(character.name, skin);
        textra.setAlignment(Align.center);
        textra.setWrap(true);
        textra.skipToTheEnd();
        stack.add(textra);
    
        var progressBar = new ProgressBar(0, character.healthMax, 1, false, pHealth);
        progressBar.setValue(character.health);
        progressBar.setAnimateDuration(.5f);
        var container = new Container(progressBar);
        container.bottom();
        stack.add(container);
    }
    
    public void conductTurn() {
        var playerOrder = "";
        calculateOrder(turn);
        var order = new Array<CharacterData>();
        for (int i = turn; i < characterOrder.size; i++) {
            order.add(characterOrder.get(i));
        }
        order.addAll(nextOrder());
    
        for (var character : order) {
            playerOrder += character.name +  "\n";
        }
        playOrderLabel.setText(playerOrder);

        if (turn < characterOrder.size) {
            var character = characterOrder.get(turn);
            for (var cell : playerTiles) {
                if (cell.getUserObject() == character) {
                    conductPlayerTurn(character, cell);
                }
            }
            
            for (int i = 0; i < enemyTiles.size; i++) {
                var cell = enemyTiles.get(i);
                if (cell.getUserObject() == character) {
                    conductEnemyTurn(character, cell);
                }
            }
        }
    }
    
    public void conductPlayerTurn(CharacterData hero, Table cell) {
        popTable.setColor(Color.WHITE);
        popTable.clear();
        popTable.setStyle(new PopTableStyle(wPointerDown));
        popTable.attachToActor(cell, Align.top, Align.top);
        
        popTable.defaults().space(10);
        var label = new Label(hero.name + "'s turn...", lButton);
        popTable.add(label);
        
        popTable.row();
        var table = new Table();
        popTable.add(table);
        
        table.defaults().space(10);
        for (var skill : hero.skills) {
            var textButton = new TextButton(skill.name, skin);
            textButton.setDisabled(skill.usesMax != -1 && skill.uses <= 0);
            table.add(textButton);

            if (skill.usesMax > 0) {
                textButton.row();
                var progressBar = new ProgressBar(0, skill.usesMax, 1, false,
                        skill.regenerateUses ? pMagic : pGear);
                progressBar.setValue(skill.uses);
                textButton.add(progressBar).growX().space(2);
            }

            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    popTable.hide();
                    stage.addAction(sequence(delay(.5f), run(() -> {
                        playerSelectTarget(hero, skill, cell);
                    })));
                }
            });

            var hoverListener = new PopTableHoverListener(Align.top, Align.top, new PopTableStyle(wPointerDown));
            textButton.addListener(hoverListener);
            var skillPop = hoverListener.getPopTable();
            label = new Label(skill.description, lLog);
            label.setWrap(true);
            label.setAlignment(Align.center);
            skillPop.add(label).growX().width(200);
        }
        
        popTable.row();
        var textButton = new TextButton("Sleep", skin);
        popTable.add(textButton);
        onChange(textButton, () -> {
            popTable.hide();
            stage.addAction(sequence(delay(.5f), run(this::finishTurn)));
        });
    
        var hoverListener = new PopTableHoverListener(Align.top, Align.top, new PopTableStyle(wPointerDown));
        textButton.addListener(hoverListener);
        var skillPop = hoverListener.getPopTable();
        label = new Label("Pass turn and wait.", lLog);
        label.setWrap(true);
        label.setAlignment(Align.center);
        skillPop.add(label).growX().width(200);
        
        popTable.show(stage);
    }
    
    public void playerSelectTarget(CharacterData hero, SkillData skill, Table cell) {
        popTable.clear();
        popTable.setStyle(new PopTableStyle(wDefault));
        popTable.attachToActor(dividerImage, Align.center, Align.center);
    
        popTable.defaults().space(10);
        var label = new Label("Select target", lButton);
        popTable.add(label);
        
        var selectableTiles = Selector.selectAnyEnemy(this, true);
        selectables.clear();
        highlights.clear();
        for (var tile : selectableTiles) {
            var stack = (Stack) tile.getChild(0);
            
            var button = new Button(bCharacterSelectable);
            stack.add(button);
            selectables.add(button);
            onChange(button, () -> {
                for (var selectable : selectables) {
                    if (selectable != button) selectable.addAction(sequence(fadeOut(.1f), removeActor()));
                }
                button.addAction(sequence(fadeOut(.5f), run(() -> {
                    popTable.hide();
                    conductSkill(hero, skill, playerTiles.contains(tile, true) ? playerTiles : enemyTiles, tile);
                }), removeActor()));
            });
            button.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    var stack = (Stack) button.getParent();
                    skill.selectTiles(playerTiles.contains(tile, true) ? playerTiles : enemyTiles, tile);
                    var image = new Image(skin.getDrawable("character-highlight-10"));
                    image.setTouchable(Touchable.disabled);
                    stack.add(image);
                    highlights.add(image);
                }
        
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    for (var highlight : highlights) {
                        highlight.remove();
                    }
                    highlights.clear();
                }
            });
        }
        
        var textButton = new TextButton("Cancel", skin);
        popTable.add(textButton);
        onChange(textButton, () -> {
            for (var selectable : selectables) {
                selectable.addAction(sequence(fadeOut(.1f), removeActor()));
            }
            selectables.clear();
            for (var highlight : highlights) {
                highlight.addAction(sequence(fadeOut(.1f), removeActor()));
            }
            highlights.clear();
            popTable.hide();
            stage.addAction(sequence(delay(.5f), run(this::conductTurn)));
        });
        
        popTable.show(stage);
    }
    
    public void conductEnemyTurn(CharacterData enemy, Table cell) {
        var skill = enemy.skills.random();
    
        var selectableTiles = Selector.selectAnyEnemy(this, false);
        
        popTable.clear();
        popTable.setStyle(new PopTableStyle(wDefault));
        popTable.attachToActor(dividerImage, Align.center, Align.center);
    
        popTable.defaults().space(10);
        var label = new Label(enemy.name + " casts " + skill.name, lButton);
        popTable.add(label);
    
        popTable.addAction(sequence(delay(2f), fadeOut(.5f), run(() -> {
            popTable.hide();
            var tile = selectableTiles.random();
            conductSkill(enemy, skill, playerTiles.contains(tile, true) ? playerTiles : enemyTiles, tile);
        }), removeActor()));
    
        popTable.show(stage);
    }
    
    public void conductSkill(CharacterData character, SkillData skill, Array<Table> tiles, Table target) {
        skill.execute(this, character, tiles, target, () -> {
            checkForDead();
        });
    }
    
    private static final Vector2 temp = new Vector2();
    
    public void showDamage(Table tile, CharacterData enemy, int damage) {
        var label = new Label("-" + damage, lNamesake);
        label.setColor(Color.RED);
        stage.addActor(label);
        label.pack();
        
        temp.set(tile.getWidth() / 2, tile.getHeight() / 2);
        tile.localToStageCoordinates(temp);
        label.setPosition(temp.x, temp.y, Align.center);
    
        label.addAction(sequence(parallel(moveBy(0, 50, 1.0f, Interpolation.sineOut), fadeOut(1.0f)), removeActor()));
        
        var progressBar = (ProgressBar) ((Container) ((Stack) tile.getChild(0)).getChild(1)).getActor();
        progressBar.setValue(enemy.health);
    }
    
    public void showTextEffectClear(Table tile, CharacterData enemy) {
        var label = (TypingLabel) ((Stack)tile.getChild(0)).getChild(0);
        label.setText(enemy.name);
    }
    
    public void showTextEffectHurt(Table tile, CharacterData enemy) {
        var label = (TypingLabel) ((Stack)tile.getChild(0)).getChild(0);
        label.setText("{SHAKE}" + enemy.name);
        label.addAction(sequence(delay(1f),run(() -> showTextEffectClear(tile, enemy))));
    }
    
    public void checkForDead() {
        var foundDead = false;
    
        for (var tile : enemyTiles) {
            var character = (CharacterData) tile.getUserObject();
            if (character != null && character.health <= 0) {
                applyBlood(tile);
                
                foundDead = true;
                tile.setUserObject(null);
                tile.clearChildren();
                tile.setBackground(skin.getDrawable("character-box-empty-10"));
                enemyTeam.removeValue(character, true);
            }
        }
    
        for (var tile : playerTiles) {
            var character = (CharacterData) tile.getUserObject();
            if (character != null && character.health <= 0) {
                applyBlood(tile);
            
                foundDead = true;
                tile.setUserObject(null);
                tile.clearChildren();
                playerTeam.removeValue(character, true);
            }
        }
        
        if (!foundDead) finishTurn();
        else {
            stage.addAction(Actions.sequence(Actions.delay(3f), Actions.run(this::finishTurn)));
        }
    }
    
    public void applyBlood(Table tile) {
        var spineDrawable = new SpineDrawable(Core.skeletonRenderer, SpineBlood.skeletonData, SpineBlood.animationData);
        spineDrawable.getAnimationState().setAnimation(0, SpineBlood.animationAnimation, false);
        spineDrawable.setCrop(-10, -10, 20, 20);
        spineDrawables.add(spineDrawable);
    
        Image image = new Image(spineDrawable);
        image.setTouchable(Touchable.disabled);
        stage.addActor(image);
    
        temp.set(76, 51);
        tile.localToStageCoordinates(temp);
        image.setPosition(temp.x, temp.y, Align.center);
    
        spineDrawable.getAnimationState().addListener(new AnimationStateAdapter() {
            @Override
            public void complete(TrackEntry entry) {
                image.remove();
                spineDrawables.removeValue(spineDrawable, true);
            }
        });
    }
    
    public void finishTurn() {
        if (playerTeam.size == 0) {
            music.stop();
            core.transition(new GameOverScreen());
        } else if (enemyTeam.size == 0) {
            music.stop();
            var room = GameData.getRoom();
            room.hasEnemies = false;
            core.transition(new RoomScreen());
        }
        
        turn++;
        conductTurn();
    }
    
    @Override
    public void act(float delta) {
        stage.act(delta);
        for (var spineDrawable : spineDrawables) {
            spineDrawable.update(delta);
        }
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
    
    public Array<Table> getEnemyTiles() {
        return enemyTiles;
    }
    
    public Array<Table> getPlayerTiles() {
        return playerTiles;
    }
}
