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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.tommyettinger.textra.TextraLabel;
import com.ray3k.template.*;
import com.ray3k.template.data.CharacterData;
import com.ray3k.template.stripe.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.SkinSkinStyles.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.data.GameData.*;

public class PreBattleScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private TextButton fightButton;
    private Array<Table> characterCells = new Array<>();
    
    @Override
    public void show() {
        super.show();
    
        var numberOfEnemies = MathUtils.floor(MathUtils.clamp(difficulty / 4f + 1, 1, 6));
        enemyTeam.clear();
        
        var newEnemyPositions = new IntArray(new int[]{0, 1, 2, 3, 4, 5});
        newEnemyPositions.shuffle();
        
        for (int i = 0; i < numberOfEnemies; i++) {
            var enemy = new CharacterData(enemyTemplates.random());
            enemy.healthMax = (difficulty * .05f + .5f) * enemy.healthMax;
            enemy.health = enemy.healthMax;
            enemy.position = newEnemyPositions.pop();
            
            for (var skill : enemy.skills) {
                skill.level = MathUtils.clamp(MathUtils.floor(difficulty * .5f), 0, skill.maxLevel);
            }
            enemyTeam.add(enemy);
        }
        
        for (var hero : playerTeam) {
            hero.stunEnemyOnNextHit = false;
            hero.stunned = false;
            hero.extraDamageIfNotHurt = 0;
            hero.extraDamageNextTurn = 0;
            hero.extraDamage = 0;;
            hero.damageMitigation = 0;
            hero.blockNextAttack = false;
            hero.counterNextAttack = 0;
            hero.delayedDamage = 0;
            hero.poison = 0;
            
            for (var skill : hero.skills) {
                if (skill.regenerateUses) skill.uses = skill.usesMax;
            }
        }
    
        final Music music = bgm_game;
        if (!music.isPlaying()) {
            music.play();
            music.setVolume(bgm * .6f);
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
        
        table.add().uniformX().expandX();
        
        root.row();
        var image = new Image(skin, "setup-divider-10");
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
        playerCell0.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell0);
        playerCell0.setTouchable(Touchable.enabled);
    
        var playerCell1 = new Table();
        playerCell1.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell1);
        playerCell1.setTouchable(Touchable.enabled);
    
        var playerCell2 = new Table();
        playerCell2.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell2);
        playerCell2.setTouchable(Touchable.enabled);
    
        subTable.row().padTop(20);
        var playerCell3 = new Table();
        playerCell3.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell3);
        playerCell3.setTouchable(Touchable.enabled);
    
        var playerCell4 = new Table();
        playerCell4.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell4);
        playerCell4.setTouchable(Touchable.enabled);
    
        var playerCell5 = new Table();
        playerCell5.setBackground(skin.getDrawable("character-box-empty-10"));
        subTable.add(playerCell5);
        playerCell5.setTouchable(Touchable.enabled);
    
        fightButton = new TextButton("FIGHT!", skin);
        fightButton.setVisible(false);
        table.add(fightButton).uniformX().expandX();
        fightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sfx_click.play(sfx);
                core.transition(new BattleScreen());
            }
        });
    
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
                sfx_click.play(sfx);
                music.stop();
                core.transition(new GameOverScreen());
            }
        });
        
        var dnd = new DragAndDrop();
        dnd.setDragActorPosition(76, -51);
        dnd.addTarget(new DragTarget(playerCell0, 0));
        dnd.addTarget(new DragTarget(playerCell1, 1));
        dnd.addTarget(new DragTarget(playerCell2, 2));
        dnd.addTarget(new DragTarget(playerCell3, 3));
        dnd.addTarget(new DragTarget(playerCell4, 4));
        dnd.addTarget(new DragTarget(playerCell5, 5));
        
        var offset = 50;
        
        for (var hero : playerTeam) {
            var characterCell = new Table();
            characterCell.setBackground(skin.getDrawable("character-box-10"));
            characterCell.setTouchable(Touchable.enabled);
            stage.addActor(characterCell);
            
            var textra = new TextraLabel(hero.name, skin);
            textra.setAlignment(Align.center);
            textra.setWrap(true);
            characterCell.add(textra).growX();
            
            var listener = new PopTableHoverListener(Align.top, Align.top, new PopTable.PopTableStyle(wPointerDown));
            characterCell.addListener(listener);
            
            var pop = listener.getPopTable();
            
            pop.defaults().space(10);
            label = new Label("Skills:", lButton);
            pop.add(label);
            
            for (var skill : hero.skills) {
                pop.row();
                pop = listener.getPopTable();
                var textraLabel = new TextraLabel("[*]" + skill.name + "[*]: " + skill.description, lLog);
                textraLabel.setWrap(true);
                pop.add(textraLabel).width(250);
            }
            
            characterCell.pack();
            characterCell.setPosition(stage.getWidth() - 30, offset, Align.bottomRight);
            offset += 25;
            dnd.addSource(new DragSource(characterCell, hero));
            characterCells.add(characterCell);
        }
    }
    
    private class DragSource extends Source {
        CharacterData hero;
        public DragSource(Actor actor, CharacterData hero) {
            super(actor);
            this.hero = hero;
        }
    
        @Override
        public Payload dragStart(InputEvent event, float x, float y, int pointer) {
            fightButton.setVisible(false);
            
            var payload = new Payload();
            payload.setObject(hero);
            
            if (getActor().getParent() instanceof Table) {
                var table = (Table) getActor().getParent();
                table.setBackground(skin.getDrawable("character-box-empty-10"));
            }
            
            stage.addActor(getActor());
            payload.setDragActor(getActor());
    
            if (getActor().getParent() instanceof Table) {
                var table = (Table) getActor().getParent();
                table.clearChildren();
            }
            return payload;
        }
    }
    
    private class DragTarget extends Target {
        int position;
        public DragTarget(Actor actor, int position) {
            super(actor);
            this.position = position;
        }
    
        @Override
        public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
            return true;
        }
    
        @Override
        public void drop(Source source, Payload payload, float x, float y, int pointer) {
            var table = (Table) getActor();
            var iter = table.getChildren().iterator();
            for (var child : iter) {
                stage.addActor(child);
                child.setPosition(stage.getWidth() - 30, 50, Align.bottomRight);
            }
            table.clearChildren();
            table.add(source.getActor());
            table.setBackground((Drawable) null);
            var hero = (CharacterData) payload.getObject();
            hero.position = position;
            
            var readyToGo = true;
            for (var playerCell : characterCells) {
                if (playerCell.getParent() == stage.getRoot()) {
                    readyToGo = false;
                    break;
                }
            }
            
            fightButton.setVisible(readyToGo);
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
