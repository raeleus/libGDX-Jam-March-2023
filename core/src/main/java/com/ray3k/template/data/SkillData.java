package com.ray3k.template.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;
import com.ray3k.template.screens.*;

public class SkillData {
    public String name;
    public String description;
    public int level = 1;
    public int maxLevel = 10;
    private static final Vector2 temp = new Vector2();
    
    public SkillData() {
    }
    
    public SkillData(SkillData other) {
        this.name = other.name;
        this.description = other.description;
        this.level = other.level;
        this.maxLevel = other.maxLevel;
    }
    
    public void execute(BattleScreen battleScreen, CharacterData character, Array<Table> tiles, Table target, Runnable runnable) {
        var stage = battleScreen.stage;
        var targetTiles = selectTiles(tiles, target);
        
        switch (name) {
            case "punch":
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
                    
                    var spineDrawable = new SpineDrawable(Core.skeletonRenderer, SpineStrike.skeletonData, SpineStrike.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineStrike.animationAnimation, false);
                    spineDrawable.setCrop(-10, -10, 20, 20);
                    battleScreen.spineDrawables.add(spineDrawable);
                    
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
                            battleScreen.spineDrawables.removeValue(spineDrawable, true);
                            if (enemy != null) {
                                int damage = MathUtils.floor(10 * (1 + level / maxLevel * 20.f));
                                enemy.health -= damage;
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            default:
                stage.addAction(Actions.sequence(Actions.delay(.5f), Actions.run(runnable)));
                break;
        }
    }
    
    public Array<Table> selectTiles(Array<Table> tiles, Table target) {
        var selected = new Array<Table>();
        
        selected.add(target);
        
        return selected;
    }
}
