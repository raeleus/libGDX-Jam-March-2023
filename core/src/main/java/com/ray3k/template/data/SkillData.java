package com.ray3k.template.data;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState.AnimationStateAdapter;
import com.esotericsoftware.spine.AnimationState.AnimationStateListener;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.ray3k.template.*;
import com.ray3k.template.battle.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;

public class SkillData {
    public String name;
    public String description;
    public int level = 1;
    public int maxLevel = 10;
    public int uses;
    public int usesMax = -1;
    public boolean regenerateUses;
    
    private static final Vector2 temp = new Vector2();
    
    public SkillData() {
    }
    
    public SkillData(SkillData other) {
        this.name = other.name;
        this.description = other.description;
        this.level = other.level;
        this.maxLevel = other.maxLevel;
        this.uses = other.uses;
        this.usesMax = other.usesMax;
        this.regenerateUses = other.regenerateUses;
    }
    
    private void playSpine(AttackData ad, Table tile, AnimationStateListener listener) {
        if (ad.skeletonData != null && ad.animationStateData != null && ad.animation != null) {
            var stage = ad.battleScreen.stage;
            var spineDrawable = new SpineDrawable(skeletonRenderer, ad.skeletonData, ad.animationStateData);
            spineDrawable.getAnimationState().setAnimation(0, ad.animation, false);
            spineDrawable.setCrop(-10, -10, 20, 20);
            ad.battleScreen.spineDrawables.add(spineDrawable);
    
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
                    ad.battleScreen.spineDrawables.removeValue(spineDrawable, true);
                }
            });
            spineDrawable.getAnimationState().addListener(listener);
        } else {
            listener.complete(null);
        }
    }
    
    private void normalAttack(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam);
        
        if (ad.sound != null) ad.sound.play(sfx);
        for (var tile : targetTiles) {
            var enemy = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (enemy != null) {
                        int damage = MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        enemy.health -= Math.max(damage + ad.character.extraDamage - enemy.damageMitigation, 0);
                        if (enemy.damageMitigation > 0) enemy.damageMitigation -= Math.max(damage + ad.character.extraDamage, 0);
                        ad.battleScreen.showDamage(tile, enemy, MathUtils.floor(damage + ad.character.extraDamage));
                        ad.battleScreen.showTextEffectHurt(tile, enemy);
                    }
                    stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                }
            });
        }
    }
    
    public void normalHeal(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam);
        if (ad.sound != null) ad.sound.play(sfx);
        
        for (var tile : targetTiles) {
            var targetCharacter = (CharacterData) tile.getUserObject();
            playSpine(ad, tile,new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (targetCharacter != null) {
                        int heal = MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        targetCharacter.health += heal;
                        if (targetCharacter.health > targetCharacter.healthMax) targetCharacter.health = ad.character.healthMax;
                        ad.battleScreen.showHeal(tile, targetCharacter, heal);
                        ad.battleScreen.showTextEffectHeal(tile, targetCharacter);
                    }
                    stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                }
            });
        }
    }
    
    public void normalExtraDamageBuff(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam);
        if (ad.sound != null) ad.sound.play(sfx);
        
        for (var tile : targetTiles) {
            var targetCharacter = (CharacterData) tile.getUserObject();
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (targetCharacter != null) {
                        targetCharacter.extraDamageNextTurn += MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        ad.battleScreen.showBuff(tile, targetCharacter, 5);
                        ad.battleScreen.showTextEffectHeal(tile, targetCharacter);
                    }
                    stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                }
            });
        }
    }
    
    public void normalDamageMitigation(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam);
        if (ad.sound != null) ad.sound.play(sfx);
        
        for (var tile : targetTiles) {
            var targetCharacter = (CharacterData) tile.getUserObject();
        
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (targetCharacter != null) {
                        targetCharacter.damageMitigation += MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        ad.battleScreen.showBuff(tile, targetCharacter, (int) targetCharacter.damageMitigation);
                        ad.battleScreen.showTextEffectNormal(tile, targetCharacter);
                    }
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                }
            });
        }
    }
    
    public void normalMove(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam);
    
        for (var tile : targetTiles) {
            var newPosition = ad.battleScreen.positionOfTile(ad.target);
            ad.battleScreen.moveCharacter(ad.character, newPosition, ad.isPlayerTeam);
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                }
            });
        }
    }
    
    public void normalStun(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam);
        
        for (var tile : targetTiles) {
            var enemy = (CharacterData) tile.getUserObject();
        
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (enemy != null) {
                        enemy.stunned = true;
                        ad.battleScreen.showStun(tile);
                        ad.battleScreen.showTextEffectNormal(tile, enemy);
                    }
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                }
            });
        }
    }
    
    private static class AttackData {
        Sound sound;
        SkeletonData skeletonData;
        AnimationStateData animationStateData;
        Animation animation;
        BattleScreen battleScreen;
        CharacterData character;
        Table target;
        boolean isPlayerTeam;
        Runnable runnable;
        int value;
        float delay;
        
        public AttackData() {
        
        }
        
        public AttackData(AttackData o) {
            sound = o.sound;
            skeletonData = o.skeletonData;
            animationStateData = o.animationStateData;
            animation = o.animation;
            battleScreen = o.battleScreen;
            character = o.character;
            target = o.target;
            isPlayerTeam = o.isPlayerTeam;
            runnable = o.runnable;
            value = o.value;
            delay = o.delay;
        }
    }
    
    public void execute(BattleScreen battleScreen, CharacterData character, Array<Table> tiles, Table target, boolean isPlayerTeam, Runnable runnable) {
        if (usesMax > 0) uses--;
        
        var stage = battleScreen.stage;
        var targetTiles = chooseTiles(battleScreen, target, isPlayerTeam);
    
        AttackData ad = new AttackData();
        ad.battleScreen = battleScreen;
        ad.character = character;
        ad.target = target;
        ad.isPlayerTeam = isPlayerTeam;
        ad.runnable = runnable;
        ad.delay = 1f;
        
        switch (name) {
            case "punch"://player tested
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrike.skeletonData;
                ad.animationStateData = SpineStrike.animationData;
                ad.animation = SpineStrike.animationAnimation;
                ad.value = 10;
                normalAttack(ad);
                break;
            case "heavy punch"://player tested
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "scratch head"://player tested
                ad.sound = sfx_hmm;
                ad.skeletonData = SpineBlastGray.skeletonData;
                ad.animationStateData = SpineBlastGray.animationData;
                ad.animation = SpineBlastGray.animationAnimation;
                ad.value = 10;
                normalHeal(ad);
                break;
            case "grumble"://player tested
                ad.sound = sfx_hulk;
                ad.skeletonData = SpineBlastPink.skeletonData;
                ad.animationStateData = SpineBlastPink.animationData;
                ad.animation = SpineBlastPink.animationAnimation;
                ad.value = 5;
                normalExtraDamageBuff(ad);
                break;
            case "charge"://player tested
                var newPosition = character.position - 3;
                var skillRunnable = ad.runnable;
                
                ad.sound = sfx_hulk;
                ad.skeletonData = SpineSwipeUp.skeletonData;
                ad.animationStateData = SpineSwipeUp.animationData;
                ad.animation = SpineSwipeUp.animationAnimation;
                var enemyTile = (isPlayerTeam ? battleScreen.getEnemyTiles() : battleScreen.getPlayerTiles()).get(newPosition);
                if (enemyTile.getUserObject() != null) {
                    ad.runnable = () -> {
                        var ad2 = new AttackData(ad);
                        ad2.sound = null;
                        ad2.skeletonData = null;
                        ad2.target = enemyTile;
                        ad2.value = 30;
                        ad2.runnable = skillRunnable;
                        normalAttack(ad2);
                    };
                }
                normalMove(ad);
                break;
            case "slap"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrikeLeft.skeletonData;
                ad.animationStateData = SpineStrikeLeft.animationData;
                ad.animation = SpineStrikeLeft.animationAnimation;
                ad.value = 5;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.value = -20;
                    ad2.delay = 1f;
                    normalExtraDamageBuff(ad2);
                };
                normalAttack(ad);
                break;
            case "tickle"://player tested
                ad.sound = sfx_giggle;
                ad.skeletonData = SpineBlastPink.skeletonData;
                ad.animationStateData = SpineBlastPink.animationData;
                ad.animation = SpineBlastPink.animationAnimation;
                normalStun(ad);
                break;
            case "headbutt"://player tested
                skillRunnable = ad.runnable;
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineStrikeUp.skeletonData;
                ad.animationStateData = SpineStrikeUp.animationData;
                ad.animation = SpineStrikeUp.animationAnimation;
                ad.value = 30;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.target = battleScreen.findTile(character);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    normalAttack(ad2);
                };
                normalAttack(ad);
                
                break;
            case "scratch"://player tested
                ad.sound = sfx_scratch;
                ad.skeletonData = SpineSlashx3.skeletonData;
                ad.animationStateData = SpineSlashx3.animationData;
                ad.animation = SpineSlashx3.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "leap"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineSpark.skeletonData;
                ad.animationStateData = SpineSpark.animationData;
                ad.animation = SpineSpark.animationAnimation;
                normalMove(ad);
                break;
            case "cat-nap"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_cat;
                ad.skeletonData = SpineBlastWhite.skeletonData;
                ad.animationStateData = SpineBlastWhite.animationData;
                ad.animation = SpineBlastWhite.animationAnimation;
                ad.value = 30;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalStun(ad2);
                };
                normalHeal(ad);
                break;
            case "cat-nip"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_cat;
                ad.skeletonData = SpineSplashBrown.skeletonData;
                ad.animationStateData = SpineSplashBrown.animationData;
                ad.animation = SpineSplashBrown.animationAnimation;
                ad.value = 10;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.value = -10;
                    ad2.delay = 1f;
                    normalDamageMitigation(ad2);
                };
                normalExtraDamageBuff(ad);
                break;
            case "meow":
                ad.sound = sfx_cat;
                ad.skeletonData = SpineBlastWhite.skeletonData;
                ad.animationStateData = SpineBlastWhite.animationData;
                ad.animation = SpineBlastWhite.animationAnimation;
                ad.value = -10;
                normalExtraDamageBuff(ad);
                break;
            case "stalk":
                sfx_creep.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastGray.skeletonData, SpineBlastGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "hairball":
                sfx_hairball.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionBrown.skeletonData, SpineExplosionBrown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionBrown.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "bite"://player tested
                ad.sound = sfx_bite;
                ad.skeletonData = SpineBite.skeletonData;
                ad.animationStateData = SpineBite.animationData;
                ad.animation = SpineBite.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "scoop"://player tested
                ad.sound = sfx_squish;
                ad.skeletonData = SpineExplosionPink.skeletonData;
                ad.animationStateData = SpineExplosionPink.animationData;
                ad.animation = SpineExplosionPink.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "ice-scream":
                skillRunnable = runnable;
                
                ad.sound = sfx_scream;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.value = 20;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.value = 6;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalAttack(ad2);
                };
                normalExtraDamageBuff(ad);
                break;
            case "pistachio"://player tested
                ad.sound = sfx_crunch;
                ad.skeletonData = SpineStrikeGreen.skeletonData;
                ad.animationStateData = SpineStrikeGreen.animationData;
                ad.animation = SpineStrikeGreen.animationAnimation;
                ad.value = 12;
                normalAttack(ad);
                break;
            case "yogurt":
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineSplashWhite.skeletonData;
                ad.animationStateData = SpineSplashWhite.animationData;
                ad.animation = SpineSplashWhite.animationAnimation;
                ad.value = 15;
                normalDamageMitigation(ad);
                break;
            case "sherbet":
                sfx_squish.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSplashPurple.skeletonData, SpineSplashPurple.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSplashPurple.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "banana sundae":
                ad.sound = sfx_squish;
                ad.skeletonData = SpineExplosionYellow.skeletonData;
                ad.animationStateData = SpineExplosionYellow.animationData;
                ad.animation = SpineExplosionYellow.animationAnimation;
                ad.value = 10;
                normalExtraDamageBuff(ad);
                break;
            case "choco syrup"://player tested
                skillRunnable = runnable;
    
                ad.sound = sfx_squish;
                ad.skeletonData = SpineExplosionBrown.skeletonData;
                ad.animationStateData = SpineExplosionBrown.animationData;
                ad.animation = SpineExplosionBrown.animationAnimation;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.value = 20;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalDamageMitigation(ad2);
                };
                normalStun(ad);
                break;
            case "free samples":
                ad.sound = sfx_iceCream;
                ad.skeletonData = SpineBlastBlue.skeletonData;
                ad.animationStateData = SpineBlastBlue.animationData;
                ad.animation = SpineBlastBlue.animationAnimation;
                ad.value = 20;
                normalHeal(ad);
                sfx_iceCream.play(sfx);
                break;
            case "ice cream social":
                sfx_iceCream.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealth.skeletonData, SpineHealth.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealth.animationAnimation, false);
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
                                enemy.extraDamageNextTurn += 5;
                                battleScreen.showBuff(tile, enemy, 5);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "snowball"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineExplosionBlue.skeletonData;
                ad.animationStateData = SpineExplosionBlue.animationData;
                ad.animation = SpineExplosionBlue.animationAnimation;
                ad.value = 5;
                normalAttack(ad);
                break;
            case "icicle"://player tested
                ad.sound = sfx_frozen;
                ad.skeletonData = SpineStrikeBlue.skeletonData;
                ad.animationStateData = SpineStrikeBlue.animationData;
                ad.animation = SpineStrikeBlue.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "freeze ray"://player tested
                ad.sound = sfx_frozen;
                ad.skeletonData = SpineShotBlue.skeletonData;
                ad.animationStateData = SpineShotBlue.animationData;
                ad.animation = SpineShotBlue.animationAnimation;
                normalStun(ad);
                break;
            case "patented ice cube system":
                sfx_sprayPaintShake.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalBlue.skeletonData, SpinePortalBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalBlue.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "slide"://player tested
                ad.sound = sfx_sprayPaint;
                ad.skeletonData = SpineSwipeLeft.skeletonData;
                ad.animationStateData = SpineSwipeLeft.animationData;
                ad.animation = SpineSwipeLeft.animationAnimation;
                normalMove(ad);
                break;
            case "ice-clone":
                sfx_frozen.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSplashBlue.skeletonData, SpineSplashBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSplashBlue.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "round-house kick"://player tested
                ad.sound = sfx_kick;
                ad.skeletonData = SpineStrikeRight.skeletonData;
                ad.animationStateData = SpineStrikeRight.animationData;
                ad.animation = SpineStrikeRight.animationAnimation;
                ad.value = 13;
                normalAttack(ad);
                break;
            case "grapple"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_thrash;
                ad.skeletonData = SpineInfinityBrown.skeletonData;
                ad.animationStateData = SpineInfinityBrown.animationData;
                ad.animation = SpineInfinityBrown.animationAnimation;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.target = battleScreen.findTile(character);
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalStun(ad2);
                };
                normalStun(ad);
                break;
            case "combo"://player tested
                skillRunnable = ad.runnable;
    
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineStrikeLeft.skeletonData;
                ad.animationStateData = SpineStrikeLeft.animationData;
                ad.animation = SpineStrikeLeft.animationAnimation;
                ad.value = 5;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.skeletonData = SpineStrikeRight.skeletonData;
                    ad2.animationStateData = SpineStrikeRight.animationData;
                    ad2.animation = SpineStrikeRight.animationAnimation;
                    ad2.runnable = () -> {
                        var ad3 = new AttackData(ad2);
                        ad3.skeletonData = SpineStrikeUp.skeletonData;
                        ad3.animationStateData = SpineStrikeUp.animationData;
                        ad3.animation = SpineStrikeUp.animationAnimation;
                        ad3.runnable = skillRunnable;
                        ad3.delay = 1f;
                        normalAttack(ad3);
                    };
                    normalAttack(ad2);
                };
                normalAttack(ad);
                break;
            case "finisher":
                sfx_magic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineStrikeDown.skeletonData, SpineStrikeDown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineStrikeDown.animationAnimation, false);
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
                                int damage = MathUtils.floor(100 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "hook"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrikeLeft.skeletonData;
                ad.animationStateData = SpineStrikeLeft.animationData;
                ad.animation = SpineStrikeLeft.animationAnimation;
                ad.value = 15;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    ad2.character = (CharacterData) target.getUserObject();
                    ad2.isPlayerTeam = !ad.isPlayerTeam;
                    var originalIndex = battleScreen.positionOfTile(target);
                    var targetIndex = originalIndex;
                    var normalizedTargetIndex = targetIndex >= 3 ? targetIndex - 3 : targetIndex;
                    var playerIndex = battleScreen.positionOfCharacter(character);
                    var enemyTiles = battleScreen.getTeamTiles(ad2.character);
                    var dir = Integer.compare(targetIndex, playerIndex);
                    if (dir == 0) dir = normalizedTargetIndex == 0 ? 1 : normalizedTargetIndex == 2 ? -1 : 1;
                    if (targetIndex >= 3) targetIndex = MathUtils.clamp(targetIndex + dir, 3,5);
                    else targetIndex = MathUtils.clamp(targetIndex + dir, 0,2);
                    var tile = enemyTiles.get(targetIndex);
                    if (tile.getUserObject() == null) ad2.target = tile;
                    normalMove(ad2);
                };
                normalAttack(ad);
                break;
            case "uppercut":
                sfx_powerPunch.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSlashWhiteUp.skeletonData, SpineSlashWhiteUp.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSlashWhiteUp.animationAnimation, false);
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
                                int damage = MathUtils.floor(25 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "triple kick"://player tested
                ad.sound = sfx_kick;
                ad.skeletonData = SpineStrikeUp.skeletonData;
                ad.animationStateData = SpineStrikeUp.animationData;
                ad.animation = SpineStrikeUp.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "lunge"://player tested
                ad.sound = sfx_grunt;
                ad.skeletonData = SpineStrikeGray.skeletonData;
                ad.animationStateData = SpineStrikeGray.animationData;
                ad.animation = SpineStrikeGray.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "parry":
                sfx_dodge.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShieldYellow.skeletonData, SpineShieldYellow.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShieldYellow.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "riposte":
                sfx_blocking.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSlashWhite.skeletonData, SpineSlashWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSlashWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "advance"://player tested
                ad.sound = sfx_creep;
                ad.skeletonData = SpineSwipeUp.skeletonData;
                ad.animationStateData = SpineSwipeUp.animationData;
                ad.animation = SpineSwipeUp.animationAnimation;
                normalMove(ad);
                break;
            case "retreat"://player tested
                ad.sound = sfx_trot;
                ad.skeletonData = SpineSwipeDown.skeletonData;
                ad.animationStateData = SpineSwipeDown.animationData;
                ad.animation = SpineSwipeDown.animationAnimation;
                normalMove(ad);
                break;
            case "pirouette"://player tested
                ad.sound = sfx_harp;
                ad.skeletonData = SpineBlastGray.skeletonData;
                ad.animationStateData = SpineBlastGray.animationData;
                ad.animation = SpineBlastGray.animationAnimation;
                normalMove(ad);
                break;
            case "dagger throw"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineShotRed.skeletonData;
                ad.animationStateData = SpineShotRed.animationData;
                ad.animation = SpineShotRed.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
            case "blast":
                ad.sound = sfx_grenade;
                ad.skeletonData = SpineShot.skeletonData;
                ad.animationStateData = SpineShot.animationData;
                ad.animation = SpineShot.animationAnimation;
                ad.value = 18;
                normalAttack(ad);
                break;
            case "covering fire":
                sfx_burstGun.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShot.skeletonData, SpineShot.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShot.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "snipe"://player tested
                ad.sound = sfx_shotgun;
                ad.skeletonData = SpineShot.skeletonData;
                ad.animationStateData = SpineShot.animationData;
                ad.animation = SpineShot.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "burst fire"://player tested
                ad.sound = sfx_burstGun;
                ad.skeletonData = SpineShot3.skeletonData;
                ad.animationStateData = SpineShot3.animationData;
                ad.animation = SpineShot3.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "grenade":
                sfx_grenade.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosion.skeletonData, SpineExplosion.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosion.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "tripwire":
                sfx_twang.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosion.skeletonData, SpineExplosion.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosion.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "pistol-whip"://player tested
                ad.sound = sfx_grunt;
                ad.skeletonData = SpineStrike.skeletonData;
                ad.animationStateData = SpineStrike.animationData;
                ad.animation = SpineStrike.animationAnimation;
                ad.value = 10;
                normalAttack(ad);
                break;
            case "shotgun"://player tested
                ad.sound = sfx_shotgun;
                ad.skeletonData = SpineShotgun.skeletonData;
                ad.animationStateData = SpineShotgun.animationData;
                ad.animation = SpineShotgun.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "flame bolt"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineBurn.skeletonData;
                ad.animationStateData = SpineBurn.animationData;
                ad.animation = SpineBurn.animationAnimation;
                ad.value = 14;
                normalAttack(ad);
                break;
            case "fire-ball"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineExplosion.skeletonData;
                ad.animationStateData = SpineExplosion.animationData;
                ad.animation = SpineExplosion.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
                break;
            case "lightning strike"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineLightning.skeletonData;
                ad.animationStateData = SpineLightning.animationData;
                ad.animation = SpineLightning.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "siphon filter":
                sfx_magic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealthRed.skeletonData, SpineHealthRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealthRed.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "contort":
                sfx_magic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalRed.skeletonData, SpinePortalRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalRed.animationAnimation, false);
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
                                int damage = MathUtils.floor(5 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                enemy.extraDamageNextTurn -= 10;
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "blaspheme":
                sfx_magic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalBlue.skeletonData, SpinePortalBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalBlue.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "plasma beam"://player tested
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineStrikePurple.skeletonData;
                ad.animationStateData = SpineStrikePurple.animationData;
                ad.animation = SpineStrikePurple.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "energy sword"://player tested
                ad.sound = sfx_energyGunShot;
                ad.skeletonData = SpineSlashYellow.skeletonData;
                ad.animationStateData = SpineSlashYellow.animationData;
                ad.animation = SpineSlashYellow.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
                break;
            case "fly"://player tested
                ad.sound = sfx_harp;
                ad.skeletonData = SpineSwipeUp.skeletonData;
                ad.animationStateData = SpineSwipeUp.animationData;
                ad.animation = SpineSwipeUp.animationAnimation;
                normalMove(ad);
                break;
            case "energy blast"://player tested
                ad.sound = null;
                ad.skeletonData = SpineStrikePurple.skeletonData;
                ad.animationStateData = SpineStrikePurple.animationData;
                ad.animation = SpineStrikePurple.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "key blade"://player tested
                ad.sound = sfx_swordDraw;
                ad.skeletonData = SpineSlashYellow.skeletonData;
                ad.animationStateData = SpineSlashYellow.animationData;
                ad.animation = SpineSlashYellow.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "energy staff":
                ad.sound = sfx_energyGunShot;
                ad.skeletonData = SpineSlashBlue.skeletonData;
                ad.animationStateData = SpineSlashBlue.animationData;
                ad.animation = SpineSlashBlue.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "flash":
                ad.sound = sfx_harp;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                normalStun(ad);
                break;
            case "flutter":
                sfx_harp.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSwipeUp.skeletonData, SpineSwipeUp.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSwipeUp.animationAnimation, false);
                    spineDrawable.setCrop(-10, -10, 20, 20);
                    battleScreen.spineDrawables.add(spineDrawable);
            
                    Image image = new Image(spineDrawable);
                    image.setTouchable(Touchable.disabled);
                    stage.addActor(image);
            
                    temp.set(76, 51);
                    tile.localToStageCoordinates(temp);
                    image.setPosition(temp.x, temp.y, Align.center);
    
                    newPosition = battleScreen.positionOfTile(target);
                    battleScreen.moveCharacter(character, newPosition, isPlayerTeam);
                    stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                    spineDrawable.getAnimationState().addListener(new AnimationStateAdapter() {
                        @Override
                        public void complete(TrackEntry entry) {
                            image.remove();
                            battleScreen.spineDrawables.removeValue(spineDrawable, true);
                        }
                    });
                }
                break;
            case "nibble"://player tested
                ad.sound = sfx_bite;
                ad.skeletonData = SpineBiteWhite.skeletonData;
                ad.animationStateData = SpineBiteWhite.animationData;
                ad.animation = SpineBiteWhite.animationAnimation;
                ad.value = 5;
                normalAttack(ad);
                break;
            case "hop":
                ad.sound = null;
                ad.skeletonData = SpineSwipeUp.skeletonData;
                ad.animationStateData = SpineSwipeUp.animationData;
                ad.animation = SpineSwipeUp.animationAnimation;
                normalMove(ad);
                break;
            case "multiply":
                sfx_multiplying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastPink.skeletonData, SpineBlastPink.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastPink.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "lob carrot"://player tested
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineBlastOrange.skeletonData;
                ad.animationStateData = SpineBlastOrange.animationData;
                ad.animation = SpineBlastOrange.animationAnimation;
                ad.value = 5;
                normalAttack(ad);
                break;
            case "pellet blast":
                
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBloodBrown.skeletonData, SpineBloodBrown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBloodBrown.animationAnimation, false);
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
                                int damage = MathUtils.floor(30 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "cute up":
                sfx_giggle.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionPink.skeletonData, SpineExplosionPink.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionPink.animationAnimation, false);
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
                                enemy.damageMitigation += 100;
                                battleScreen.showBuff(tile, enemy, 100);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "fuzzy":
                sfx_powerPunch.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSparkWhite.skeletonData, SpineSparkWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSparkWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "bunny claw"://player tested
                ad.sound = sfx_swordDraw;
                ad.skeletonData = SpineSlashx3.skeletonData;
                ad.animationStateData = SpineSlashx3.animationData;
                ad.animation = SpineSlashx3.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "snuggle"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_giggle;
                ad.skeletonData = SpineInfinityWhite.skeletonData;
                ad.animationStateData = SpineInfinityWhite.animationData;
                ad.animation = SpineInfinityWhite.animationAnimation;
                ad.value = 25;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.target = battleScreen.findTile(character);
                    ad2.runnable = skillRunnable;
                    normalHeal(ad2);
                };
                normalHeal(ad);
                break;
            case "quick punch"://player tested
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrike.skeletonData;
                ad.animationStateData = SpineStrike.animationData;
                ad.animation = SpineStrike.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "double punch"://player tested
                skillRunnable = ad.runnable;
    
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineStrike.skeletonData;
                ad.animationStateData = SpineStrike.animationData;
                ad.animation = SpineStrike.animationAnimation;
                ad.value = 8;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.skeletonData = SpineStrikeRight.skeletonData;
                    ad2.animationStateData = SpineStrikeRight.animationData;
                    ad2.animation = SpineStrikeRight.animationAnimation;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalAttack(ad2);
                };
                normalAttack(ad);
                break;
            case "dash punch":
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineStrikeUp.skeletonData;
                ad.animationStateData = SpineStrikeUp.animationData;
                ad.animation = SpineStrikeUp.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "quick dodge":
                sfx_dodge.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSwipeDown.skeletonData, SpineSwipeDown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSwipeDown.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "phase":
                sfx_lightBeam.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalGreen.skeletonData, SpinePortalGreen.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalGreen.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "speed up":
                sfx_harp.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastWhite.skeletonData, SpineBlastWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "extendo punch":
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineStrikeUp.skeletonData;
                ad.animationStateData = SpineStrikeUp.animationData;
                ad.animation = SpineStrikeUp.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "grapnel hook":
                sfx_swordClink.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineStrikeGray.skeletonData, SpineStrikeGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineStrikeGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "smoke pellet":
                sfx_sprayPaint.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastGray.skeletonData, SpineBlastGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "caltrops":
                sfx_bullets.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShotGray.skeletonData, SpineShotGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShotGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "gadget crisps"://player tested
                ad.sound = sfx_crunch;
                ad.skeletonData = SpineBlastOrange.skeletonData;
                ad.animationStateData = SpineBlastOrange.animationData;
                ad.animation = SpineBlastOrange.animationAnimation;
                ad.value = 25;
                normalHeal(ad);
                break;
            case "sticky tape"://player tested
                ad.sound = sfx_sticky;
                ad.skeletonData = SpineStrikeGray.skeletonData;
                ad.animationStateData = SpineStrikeGray.animationData;
                ad.animation = SpineStrikeGray.animationAnimation;
                normalStun(ad);
                break;
            case "scanner":
                sfx_beep.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealthRed.skeletonData, SpineHealthRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealthRed.animationAnimation, false);
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
                                enemy.damageMitigation -= 10;
                                battleScreen.showBuff(tile, enemy, 10);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "hack"://player tested
                ad.sound = sfx_beep;
                ad.skeletonData = SpinePortalGreen.skeletonData;
                ad.animationStateData = SpinePortalGreen.animationData;
                ad.animation = SpinePortalGreen.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "nerf":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalRed.skeletonData, SpinePortalRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalRed.animationAnimation, false);
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
                                enemy.extraDamageNextTurn -= 10;
                                battleScreen.showBuff(tile, enemy, 10);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "dupe":
                sfx_multiplying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineLightningWhite.skeletonData, SpineLightningWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineLightningWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "crypto":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealth.skeletonData, SpineHealth.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealth.animationAnimation, false);
                    spineDrawable.setCrop(-10, -10, 20, 20);
                    battleScreen.spineDrawables.add(spineDrawable);
            
                    Image image = new Image(spineDrawable);
                    image.setTouchable(Touchable.disabled);
                    stage.addActor(image);
            
                    temp.set(76, 51);
                    tile.localToStageCoordinates(temp);
                    image.setPosition(temp.x, temp.y, Align.center);
    
                    var targetCharacter = (CharacterData) tile.getUserObject();
                    spineDrawable.getAnimationState().addListener(new AnimationStateAdapter() {
                        @Override
                        public void complete(TrackEntry entry) {
                            image.remove();
                            battleScreen.spineDrawables.removeValue(spineDrawable, true);
                            if (targetCharacter != null) {
                                int heal = MathUtils.floor(60 + (float) level / maxLevel * 20.f);
                                targetCharacter.health += heal;
                                if (targetCharacter.health > targetCharacter.healthMax) targetCharacter.health = character.healthMax;
                                battleScreen.showHeal(tile, targetCharacter, heal);
                                battleScreen.showTextEffectHeal(tile, targetCharacter);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "fanboy":
                sfx_harp.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionWhite.skeletonData, SpineExplosionWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "burn"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineBurn.skeletonData;
                ad.animationStateData = SpineBurn.animationData;
                ad.animation = SpineBurn.animationAnimation;
                ad.value = 12;
                normalAttack(ad);
                break;
            case "tail whip"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineStrikeRight.skeletonData;
                ad.animationStateData = SpineStrikeRight.animationData;
                ad.animation = SpineStrikeRight.animationAnimation;
                ad.value = 5;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = 1f;
                    ad.value = 5;
                    ad2.runnable = skillRunnable;
                    normalExtraDamageBuff(ad2);
                };
                normalAttack(ad);
                break;
            case "incendiary grenade":
                sfx_grenade.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionYellow.skeletonData, SpineExplosionYellow.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionYellow.animationAnimation, false);
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
                                int damage = MathUtils.floor(50 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "brimstone"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineExplosion.skeletonData;
                ad.animationStateData = SpineExplosion.animationData;
                ad.animation = SpineExplosion.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "torch":
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineBurn.skeletonData;
                ad.animationStateData = SpineBurn.animationData;
                ad.animation = SpineBurn.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "flame shotgun":
                ad.sound = sfx_shotgun;
                ad.skeletonData = SpineShotgunOrange.skeletonData;
                ad.animationStateData = SpineShotgunOrange.animationData;
                ad.animation = SpineShotgunOrange.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "thwack"://player tested
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "compute":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealth.skeletonData, SpineHealth.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealth.animationAnimation, false);
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
                                enemy.extraDamageNextTurn += 10;
                                battleScreen.showBuff(tile, enemy, 10);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "fuel up":
                sfx_magic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealthBrown.skeletonData, SpineHealthBrown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealthBrown.animationAnimation, false);
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
                                enemy.extraDamageNextTurn = 10;
                                battleScreen.showBuff(tile, enemy, 10);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "acceleron":
                sfx_harp.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalBlue.skeletonData, SpinePortalBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalBlue.animationAnimation, false);
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
                                enemy.damageMitigation -= 10;
                                battleScreen.showBuff(tile, enemy, 10);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "beam"://player tested
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineStrikeBlue.skeletonData;
                ad.animationStateData = SpineStrikeBlue.animationData;
                ad.animation = SpineStrikeBlue.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "self-destruct":
                sfx_dying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosion.skeletonData, SpineExplosion.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosion.animationAnimation, false);
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
                                int damage = MathUtils.floor(2000 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "gear":
                sfx_magic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShotGray.skeletonData, SpineShotGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShotGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(12 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "bone rattle"://player tested
                ad.sound = sfx_sprayPaintShake;
                ad.skeletonData = SpineShotWhite.skeletonData;
                ad.animationStateData = SpineShotWhite.animationData;
                ad.animation = SpineShotWhite.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "bone throw"://player tested
                ad.sound = sfx_bite;
                ad.skeletonData = SpineBlastWhite.skeletonData;
                ad.animationStateData = SpineBlastWhite.animationData;
                ad.animation = SpineBlastWhite.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "knee cap"://player tested
                ad.sound = sfx_sprayPaintShake;
                ad.skeletonData = SpineShotWhite.skeletonData;
                ad.animationStateData = SpineShotWhite.animationData;
                ad.animation = SpineShotWhite.animationAnimation;
                normalStun(ad);
                break;
            case "spinal tap":
                sfx_sprayPaintShake.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastYellow.skeletonData, SpineBlastYellow.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastYellow.animationAnimation, false);
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
                                int damage = MathUtils.floor(5 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "rigor mortis":
                sfx_dying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalRed.skeletonData, SpinePortalRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalRed.animationAnimation, false);
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
                                enemy.damageMitigation += 10;
                                battleScreen.showBuff(tile, enemy, 10);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "dead eye":
                sfx_dying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSplashBrown.skeletonData, SpineSplashBrown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSplashBrown.animationAnimation, false);
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
                                enemy.damageMitigation -= 10;
                                battleScreen.showBuff(tile, enemy, -10);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "tombstone smack"://player tested
                ad.sound = sfx_grunt;
                ad.skeletonData = SpineStrikeWhite.skeletonData;
                ad.animationStateData = SpineStrikeWhite.animationData;
                ad.animation = SpineStrikeWhite.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
                break;
            case "bury":
                sfx_dying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSplashBrown.skeletonData, SpineSplashBrown.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSplashBrown.animationAnimation, false);
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
                                enemy.damageMitigation += 100;
                                battleScreen.showBuff(tile, enemy, 100);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "panda paw"://player tested
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineStrikeGreen.skeletonData;
                ad.animationStateData = SpineStrikeGreen.animationData;
                ad.animation = SpineStrikeGreen.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "bamboo shoot":
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineShotGreen.skeletonData;
                ad.animationStateData = SpineShotGreen.animationData;
                ad.animation = SpineShotGreen.animationAnimation;
                ad.value = 5;
                normalAttack(ad);
                break;
            case "gobsmack":
                ad.sound = sfx_slap;
                ad.skeletonData = SpineBlastGreen.skeletonData;
                ad.animationStateData = SpineBlastGreen.animationData;
                ad.animation = SpineBlastGreen.animationAnimation;
                normalStun(ad);
                break;
            case "snap"://player tested
                ad.sound = sfx_salad;
                ad.skeletonData = SpineBlood.skeletonData;
                ad.animationStateData = SpineBlood.animationData;
                ad.animation = SpineBlood.animationAnimation;
                ad.value = 10;
                normalAttack(ad);
                break;
            case "lay down for a bit"://player tested
                skillRunnable = runnable;
    
                ad.sound = sfx_yawn;
                ad.skeletonData = SpinePortalBlue.skeletonData;
                ad.animationStateData = SpinePortalBlue.animationData;
                ad.animation = SpinePortalBlue.animationAnimation;
                ad.value = 10;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalStun(ad2);
                };
                normalHeal(ad);
                break;
            case "throw weight around":
                sfx_hulk.play(sfx);
                //todo:fix
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSplashBlue.skeletonData, SpineSplashBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSplashBlue.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "panda smash":
                sfx_hulk.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionWhite.skeletonData, SpineExplosionWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(20 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "eat":
                sfx_bite.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlood.skeletonData, SpineBlood.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlood.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "line piece"://player tested
                ad.sound = sfx_tetris;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "j piece"://player tested
                ad.sound = sfx_tetris;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "l piece"://player tested
                ad.sound = sfx_tetris;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "square"://player tested
                ad.sound = sfx_tetris;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "tee"://player tested
                ad.sound = sfx_tetris;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.value = 13;
                normalAttack(ad);
                break;
            case "tetros"://player tested
                ad.sound = null;
                ad.skeletonData = SpineExplosionYellow.skeletonData;
                ad.animationStateData = SpineExplosionYellow.animationData;
                ad.animation = SpineExplosionYellow.animationAnimation;
                ad.value = 40;
                normalAttack(ad);
                break;
            case "scrub":
                sfx_dishes.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSparkWhite.skeletonData, SpineSparkWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSparkWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(5 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "buff":
                sfx_dishes.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastBlue.skeletonData, SpineBlastBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastBlue.animationAnimation, false);
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
                                enemy.extraDamageNextTurn = 10;
                                enemy.stunned = false;
                                enemy.damageMitigation = 0;
                                battleScreen.showDamage(tile, enemy, 10);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "squeeze":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineInfinity.skeletonData,
                            SpineInfinity.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineInfinity.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "rinse":
                sfx_dishes.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastBlue.skeletonData, SpineBlastBlue.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastBlue.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "absorb":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineLightningReverseWhite.skeletonData, SpineLightningReverseWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineLightningReverseWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "spatula":
                sfx_swordDraw.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineStrikeGray.skeletonData, SpineStrikeGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineStrikeGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "pie slice"://player tested
                ad.sound = sfx_hmm;
                ad.skeletonData = SpineSlashWhite.skeletonData;
                ad.animationStateData = SpineSlashWhite.animationData;
                ad.animation = SpineSlashWhite.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "pie graph"://player tested
                ad.sound = sfx_hmm;
                ad.skeletonData = SpineBurnGreen.skeletonData;
                ad.animationStateData = SpineBurnGreen.animationData;
                ad.animation = SpineBurnGreen.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "radial menu"://player tested
                ad.sound = sfx_hmm;
                ad.skeletonData = SpinePortalBlue.skeletonData;
                ad.animationStateData = SpinePortalBlue.animationData;
                ad.animation = SpinePortalBlue.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "circumference"://player tested
                ad.sound = sfx_hmm;
                ad.skeletonData = SpinePortalRed.skeletonData;
                ad.animationStateData = SpinePortalRed.animationData;
                ad.animation = SpinePortalRed.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "radius"://player tested
                ad.sound = sfx_hmm;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
                break;
            case "perimeter"://player tested
                ad.sound = null;
                ad.skeletonData = SpinePortal.skeletonData;
                ad.animationStateData = SpinePortal.animationData;
                ad.animation = SpinePortal.animationAnimation;
                ad.value = 5;
                normalAttack(ad);
                break;
            case "chomp"://player tested
                ad.sound = sfx_bite;
                ad.skeletonData = SpineBite.skeletonData;
                ad.animationStateData = SpineBite.animationData;
                ad.animation = SpineBite.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
                break;
            case "crocodile roll":
                ad.sound = sfx_thrash;
                ad.skeletonData = SpinePortalGreen.skeletonData;
                ad.animationStateData = SpinePortalGreen.animationData;
                ad.animation = SpinePortalGreen.animationAnimation;
                normalMove(ad);
                break;
            case "tiny arm smack":
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "tail sweep":
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineStrikeLeft.skeletonData;
                ad.animationStateData = SpineStrikeLeft.animationData;
                ad.animation = SpineStrikeLeft.animationAnimation;
                normalStun(ad);
                break;
            case "thrash":
                sfx_thrash.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineStrikeRed.skeletonData, SpineStrikeRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineStrikeRed.animationAnimation, false);
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
                                enemy.extraDamageNextTurn += 20;
                                battleScreen.showBuff(tile, enemy, 20);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "dino bop":
                sfx_powerPunch.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineSplashPurple.skeletonData, SpineSplashPurple.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineSplashPurple.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "belly crawl"://player tested
                ad.sound = sfx_creep;
                ad.skeletonData = SpineSwipeUp.skeletonData;
                ad.animationStateData = SpineSwipeUp.animationData;
                ad.animation = SpineSwipeUp.animationAnimation;
                normalMove(ad);
                break;
            case "shield throw"://player tested
                ad.sound = sfx_grunt;
                ad.skeletonData = SpineShieldRed.skeletonData;
                ad.animationStateData = SpineShieldRed.animationData;
                ad.animation = SpineShieldRed.animationAnimation;
                ad.value = 12;
                normalAttack(ad);
                break;
            case "heroic punch":
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineStrikeUpRed.skeletonData;
                ad.animationStateData = SpineStrikeUpRed.animationData;
                ad.animation = SpineStrikeUpRed.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                sfx_powerPunch.play(sfx);
                break;
            case "juice up":
                sfx_hulk.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastGreen.skeletonData, SpineBlastGreen.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastGreen.animationAnimation, false);
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
                                enemy.extraDamageNextTurn += 20;
                                battleScreen.showBuff(tile, enemy, 20);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "block":
                sfx_blocking.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShield.skeletonData, SpineShield.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShield.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "defend":
                sfx_swordClink.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShieldYellow.skeletonData, SpineShieldYellow.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShieldYellow.animationAnimation, false);
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
                                enemy.damageMitigation = 20;
                                battleScreen.showBuff(tile, enemy, 20);
                                battleScreen.showTextEffectHeal(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "deflect":
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineShieldYellow.skeletonData, SpineShieldYellow.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineShieldYellow.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "heroic kick":
                ad.sound = sfx_kick;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 35;
                normalAttack(ad);
                break;
            case "slime slap"://player tested
                ad.sound = sfx_slap;
                ad.skeletonData = SpineSlashWhite.skeletonData;
                ad.animationStateData = SpineSlashWhite.animationData;
                ad.animation = SpineSlashWhite.animationAnimation;
                ad.value = 12;
                normalAttack(ad);
                break;
            case "divide":
                sfx_multiplying.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortal.skeletonData, SpinePortal.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortal.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "merge":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortal.skeletonData, SpinePortal.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortal.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "sticky":
                sfx_sticky.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBurnGreen.skeletonData, SpineBurnGreen.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBurnGreen.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "goo blast":
                ad.sound = sfx_slime;
                ad.skeletonData = SpineBlastWhite.skeletonData;
                ad.animationStateData = SpineBlastWhite.animationData;
                ad.animation = SpineBlastWhite.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "goopicide":
                sfx_slime.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionWhite.skeletonData, SpineExplosionWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(2000);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                                
                                for (var enemy : GameData.enemyTeam) {
                                    enemy.stunned = true;
                                    battleScreen.showTextEffectNormal(battleScreen.findTile(enemy), enemy);
                                }
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "regenerate"://player tested
                ad.sound = sfx_harp;
                ad.skeletonData = SpineHealth.skeletonData;
                ad.animationStateData = SpineHealth.animationData;
                ad.animation = SpineHealth.animationAnimation;
                ad.value = 30;
                normalHeal(ad);
                break;
            case "claw":
                ad.sound = sfx_salad;
                ad.skeletonData = SpineSlashx3.skeletonData;
                ad.animationStateData = SpineSlashx3.animationData;
                ad.animation = SpineSlashx3.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "yip":
                sfx_howl.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineLightning.skeletonData, SpineLightning.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineLightning.animationAnimation, false);
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
                                enemy.damageMitigation -= 10;
                                battleScreen.showBuff(tile, enemy, -10);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "snarl":
                sfx_dragon.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastRed.skeletonData,
                            SpineBlastRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastRed.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "howl":
                sfx_howl.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastGray.skeletonData, SpineBlastGray.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastGray.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "trot"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_trot;
                ad.skeletonData = SpineSwipeLeft.skeletonData;
                ad.animationStateData = SpineSwipeLeft.animationData;
                ad.animation = SpineSwipeLeft.animationAnimation;
                ad.delay = .2f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.skeletonData = SpineHealth.skeletonData;
                    ad2.animationStateData = SpineHealth.animationData;
                    ad2.animation = SpineHealth.animationAnimation;
                    ad2.value = 10;
                    ad2.target = battleScreen.findTile(character);
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalHeal(ad2);
                };
                normalMove(ad);
                break;
            case "dance":
                sfx_harp.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpinePortalRed.skeletonData, SpinePortalRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpinePortalRed.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "laser beam":
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineStrikeUpRed.skeletonData;
                ad.animationStateData = SpineStrikeUpRed.animationData;
                ad.animation = SpineStrikeUpRed.animationAnimation;
                ad.value = 20;
                normalAttack(ad);
                break;
            case "concussive beam"://player tested
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineStrikeUpGray.skeletonData;
                ad.animationStateData = SpineStrikeUpGray.animationData;
                ad.animation = SpineStrikeUpGray.animationAnimation;
                normalStun(ad);
                break;
            case "focused beam":
                sfx_lightBeam.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineStrikePurple.skeletonData, SpineStrikePurple.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineStrikePurple.animationAnimation, false);
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
                                int damage = MathUtils.floor(10 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "devastating beam":
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineExplosion.skeletonData;
                ad.animationStateData = SpineExplosion.animationData;
                ad.animation = SpineExplosion.animationAnimation;
                ad.value = 35;
                normalAttack(ad);
                break;
            case "ricochet":
                ad.sound = sfx_twang;
                ad.skeletonData = SpineSpark.skeletonData;
                ad.animationStateData = SpineSpark.animationData;
                ad.animation = SpineSpark.animationAnimation;
                ad.value = 10;
                normalAttack(ad);
                break;
            case "heat vision":
                sfx_darkMagic.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastRed.skeletonData, SpineBlastRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastRed.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "poke":
                ad.sound = sfx_grunt;
                ad.skeletonData = SpineBlastGray.skeletonData;
                ad.animationStateData = SpineBlastGray.animationData;
                ad.animation = SpineBlastGray.animationAnimation;
                ad.value = 5;
                normalAttack(ad);
                break;
            case "scream":
                sfx_scream.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineInfinityWhite.skeletonData, SpineInfinityWhite.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineInfinityWhite.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "rage out":
                sfx_hulk.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlood.skeletonData, SpineBlood.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlood.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "rind":
                sfx_salad.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBlastOrange.skeletonData, SpineBlastOrange.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBlastOrange.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "tasty snack":
                sfx_salad.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealth.skeletonData, SpineHealth.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealth.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "vitamin c":
                sfx_salad.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineExplosionYellow.skeletonData, SpineExplosionYellow.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineExplosionYellow.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "crown of thorns":
                sfx_dragon.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineHealthRed.skeletonData, SpineHealthRed.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineHealthRed.animationAnimation, false);
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
                                int damage = MathUtils.floor(0 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "flay and bake":
                sfx_dragon.play(sfx);
                for (var tile : targetTiles) {
                    var enemy = (CharacterData) tile.getUserObject();
            
                    var spineDrawable = new SpineDrawable(skeletonRenderer, SpineBurn.skeletonData, SpineBurn.animationData);
                    spineDrawable.getAnimationState().setAnimation(0, SpineBurn.animationAnimation, false);
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
                                int damage = MathUtils.floor(30 + (float) level / maxLevel * 20.f);
                                enemy.health -= Math.max(damage + character.extraDamage - enemy.damageMitigation, 0);
                                battleScreen.showDamage(tile, enemy, damage);
                                battleScreen.showTextEffectHurt(tile, enemy);
                            }
                            stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(runnable)));
                        }
                    });
                }
                break;
            case "slash":
                ad.sound = sfx_salad;
                ad.skeletonData = SpineSlashWhite.skeletonData;
                ad.animationStateData = SpineSlashWhite.animationData;
                ad.animation = SpineSlashWhite.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "admin":
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 2000;
                normalAttack(ad);
                break;
            default:
                stage.addAction(Actions.sequence(Actions.delay(.5f), Actions.run(runnable)));
                break;
        }
    }
    
    public Array<Table> chooseTiles(BattleScreen battleScreen, Table target, boolean playerTeam) {
        var selected = new Array<Table>();
        var allies = playerTeam ? battleScreen.getPlayerTiles() : battleScreen.getEnemyTiles();
        var enemies = playerTeam ? battleScreen.getEnemyTiles() : battleScreen.getPlayerTiles();
        
        switch (name) {
            case "icicle":
            case "line piece":
            case "devastating beam":
                selected.add(target);
                var index = enemies.indexOf(target, true);
                if (index < 3 && enemies.get(index + 3).getUserObject() != null) selected.add(enemies.get(index + 3));
                else if (index >= 3 && enemies.get(index - 3).getUserObject() != null) selected.add(enemies.get(index - 3));
                break;
            case "round-house kick":
            case "ricochet":
                selected.add(target);
                index = enemies.indexOf(target, true);
                if (index < 3) index += 3;
                
                if (index + 1 < 6 && enemies.get(index + 1).getUserObject() != null) selected.add(enemies.get(index + 1));
                else if (index - 1 >= 3 && enemies.get(index - 1).getUserObject() != null) selected.add(enemies.get(index - 1));
                break;
            case "fire-ball":
                selected.add(target);
                index = enemies.indexOf(target, true);
    
                if (index + 1 < 6 && enemies.get(index + 1).getUserObject() != null) selected.add(enemies.get(index + 1));
                else if (index + 3 < 6 && enemies.get(index + 3).getUserObject() != null) selected.add(enemies.get(index + 3));
                else if (index - 1 >= 0 && enemies.get(index - 1).getUserObject() != null) selected.add(enemies.get(index - 1));
                else if (index - 3 >= 0 && enemies.get(index - 3).getUserObject() != null) selected.add(enemies.get(index - 3));
                break;
            case "j piece":
                selected.add(target);
                index = enemies.indexOf(target, true);
                
                if (index + 3 < 6 && enemies.get(index + 3).getUserObject() != null) selected.add(enemies.get(index + 3));
                else if (index - 1 >= 0 && enemies.get(index - 1).getUserObject() != null) selected.add(enemies.get(index - 1));
                break;
            case "l piece":
                selected.add(target);
                index = enemies.indexOf(target, true);
        
                if (index + 1 < 6 && enemies.get(index + 1).getUserObject() != null) selected.add(enemies.get(index + 1));
                else if (index + 3 < 6 && enemies.get(index + 3).getUserObject() != null) selected.add(enemies.get(index + 3));
                break;
            case "free samples":
            case "ice cream social":
                for (var tile : allies) {
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            case "flash":
            case "square":
            case "circumference":
            case "perimeter":
                for (var tile : enemies) {
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            case "triple kick":
                for (int i = 0; i < 3; i++) {
                    var tile = enemies.get(i);
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            case "tetros":
                var start = 0;
                for (int i = 3; i < 6; i++) {
                    var tile = enemies.get(i);
                    if (target == tile) {
                        start = 3;
                        break;
                    }
                }
                
                for (int i = start; i < start + 3; i++) {
                    var tile = enemies.get(i);
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            case "tee":
                for (int i = 3; i < 6; i++) {
                    var tile = enemies.get(i);
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                var tile = enemies.get(1);
                if (tile.getUserObject() != null) selected.add(tile);
                break;
            case "radius":
                tile = enemies.get(0);
                if (tile.getUserObject() != null) selected.add(tile);
                tile = enemies.get(2);
                if (tile.getUserObject() != null) selected.add(tile);
                tile = enemies.get(3);
                if (tile.getUserObject() != null) selected.add(tile);
                tile = enemies.get(5);
                if (tile.getUserObject() != null) selected.add(tile);
                break;
            default:
                selected.add(target);
                break;
        }
        
        return selected;
    }
    
    public Array<Table> collectAvailableTiles(BattleScreen battleScreen, boolean playerTeam, int characterPosition) {
        switch (name) {
            case "scratch head":
            case "cat nap":
            case "cat nip":
            case "ice-scream":
            case "patented ice cube system":
            case "parry":
            case "riposte":
            case "covering fire":
            case "cute up":
            case "fuzzy":
            case "quick dodge":
            case "phase":
            case "speed up":
            case "smoke pellet":
            case "gadget crisps":
            case "compute":
            case "fuel up":
            case "acceleron":
            case "self-destruct":
            case "rigor mortis":
            case "bury":
            case "lay down for a bit":
            case "eat":
            case "rinse":
            case "thrash":
            case "juice up":
            case "goopicide":
            case "regenerate":
            case "dance":
            case "heat vision":
            case "rind":
            case "vitamin c":
            case "crown of thorns":
            case "cat-nap":
            case "cat-nip":
                return Selector.selectSelf(battleScreen, playerTeam, characterPosition);
            case "lunge":
            case "energy blast":
                return Selector.selectEnemyColumnDirectlyInFront(battleScreen, playerTeam, characterPosition);
            case "leap":
            case "hairball":
            case "ice-clone":
            case "fly":
            case "hop":
            case "multiply":
            case "divide":
            case "howl":
                return Selector.selectAnyEmptyAlly(battleScreen, playerTeam);
            case "slide":
            case "flutter":
            case "crocodile roll":
            case "trot":
                return Selector.selectEmptyAllyAdjacent(battleScreen, playerTeam,characterPosition);
            case "advance":
            case "charge":
                return Selector.selectEmptyAllyForward(battleScreen, playerTeam, characterPosition);
            case "retreat":
                return Selector.selectEmptyAllyBackward(battleScreen, playerTeam, characterPosition);
            case "belly crawl":
                return Selector.selectEmptyAllyForwardOrBackward(battleScreen, playerTeam, characterPosition);
            case "pirouette":
                return Selector.selectEmptyAllySide(battleScreen, playerTeam, characterPosition);
            case "yogurt":
            case "sherbet":
            case "banana sundae":
            case "free samples":
            case "ice cream social":
            case "siphon filter":
            case "snuggle":
            case "fanboy":
            case "throw weight around":
            case "buff":
            case "dino bop":
            case "block":
            case "merge":
            case "tasty snack":
            case "grumble":
                return Selector.selectAnyAlly(battleScreen, playerTeam);
            case "defend":
                return Selector.selectAnyAllyFrontRow(battleScreen, playerTeam);
            case "deflect":
                return Selector.selectAnyAllyBackRow(battleScreen, playerTeam);
            case "pistachio":
            case "choco syrup":
            case "snowball":
            case "icicle":
            case "freeze ray":
            case "dagger throw":
            case "blast":
            case "snipe":
            case "burst fire":
            case "grenade":
            case "shotgun":
            case "flame bolt":
            case "fire-ball":
            case "lightning strike":
            case "contort":
            case "blaspheme":
            case "plasma beam":
            case "flash":
            case "lob carrot":
            case "pellet blast":
            case "caltrops":
            case "sticky tape":
            case "scanner":
            case "hack":
            case "nerf":
            case "dupe":
            case "crypto":
            case "burn":
            case "incendiary grenade":
            case "brimstone":
            case "torch":
            case "flame shotgun":
            case "beam":
            case "gear":
            case "bone throw":
            case "knee cap":
            case "dead eye":
            case "bamboo shoot":
            case "tee":
            case "tetros":
            case "squeeze":
            case "absorb":
            case "circumference":
            case "perimeter":
            case "shield throw":
            case "sticky":
            case "goo blast":
            case "yip":
            case "snarl":
            case "laser beam":
            case "concussive beam":
            case "focused beam":
            case "devastating beam":
            case "scream":
            case "flay and bake":
                return Selector.selectAnyEnemy(battleScreen, playerTeam);
            case "combo":
                return Selector.selectMeleeDirectlyInFront(battleScreen, playerTeam, characterPosition);
            case "triple kick":
            case "tripwire":
            case "panda smash":
            case "pie slice":
            case "tail sweep":
            case "ricochet":
            case "rage out":
                return Selector.selectAnyEnemyFrontRow(battleScreen, playerTeam);
            case "grapnel hook":
            case "spatula":
                return Selector.selectAnyEnemyBackRow(battleScreen, playerTeam);
            case "pie graph":
                return Selector.selectMeleeDiagonalOnly(battleScreen, playerTeam, characterPosition);
            case "radial menu":
            case "radius":
                return Selector.selectAnyEnemyCorners(battleScreen, playerTeam);
            default:
                return Selector.selectMelee(battleScreen, playerTeam, characterPosition);
        }
    }
}
