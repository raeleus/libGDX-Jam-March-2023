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
import static com.ray3k.template.data.GameData.*;

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
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        if (ad.sound != null) ad.sound.play(sfx);
        for (var tile : targetTiles) {
            var enemy = (CharacterData) tile.getUserObject();
            
            directDamage(ad, enemy);
        }
    }
    
    public void directDamage(AttackData ad, CharacterData character) {
        var stage = ad.battleScreen.stage;
        
        var tile = ad.battleScreen.findTile(character);
        
        playSpine(ad, tile, new AnimationStateAdapter() {
            @Override
            public void complete(TrackEntry entry) {
                if (character != null) {
                    var characterTile = ad.battleScreen.findTile(ad.character);
                    if (character.stunEnemyOnHit || character.stunEnemyOnNextHit) {
                        character.stunEnemyOnNextHit = false;
                        ad.character.stunned = true;
                        ad.battleScreen.showStun(characterTile);
                        ad.battleScreen.showTextEffectNormal(characterTile, ad.character);
                    }
                    
                    if (character.counterNextAttack != 0) {
                        int damage = MathUtils.round(character.counterNextAttack);
                        ad.character.health -= Math.max(damage + character.extraDamage - ad.character.damageMitigation, 0);
                        if (ad.character.damageMitigation > 0)
                            ad.character.damageMitigation -= Math.max(damage, 0);
                        ad.character.extraDamageIfNotHurt = 0;
                        
                        ad.battleScreen.showDamage(characterTile, ad.character, damage);
                        ad.battleScreen.showTextEffectHurt(characterTile, ad.character);
                        
                        character.counterNextAttack = 0;
                    }
                    
                    if (character.blockNextAttack) {
                        ad.battleScreen.showBlocked(tile);
                        character.blockNextAttack = false;
                    } else {
                        int damage = MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        character.health -= Math.max(damage + ad.character.extraDamage - character.damageMitigation, 0);
                        if (character.damageMitigation > 0)
                            character.damageMitigation -= Math.max(damage + ad.character.extraDamage, 0);
                        character.extraDamageIfNotHurt = 0;
                        
                        ad.battleScreen.showDamage(tile, character, MathUtils.floor(Math.max(damage + ad.character.extraDamage, 0)));
                        ad.battleScreen.showTextEffectHurt(tile, character);
                    }
                }
                if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                ad.runnable = null;
            }
        });
    }
    
    public void normalHeal(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
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
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalExtraDamageBuff(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        if (ad.sound != null) ad.sound.play(sfx);
        
        for (var tile : targetTiles) {
            var targetCharacter = (CharacterData) tile.getUserObject();
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (targetCharacter != null) {
                        var value = ad.value + (float) level / maxLevel * ad.value;
                        targetCharacter.extraDamageNextTurn += MathUtils.round(value);
                        if (value > 0) ad.battleScreen.showBuff(tile, (int) value);
                        else ad.battleScreen.showDebuff(tile, (int) value);
                        ad.battleScreen.showTextEffectHeal(tile, targetCharacter);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalExtraDamageIfNotHurtBuff(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        if (ad.sound != null) ad.sound.play(sfx);
        
        for (var tile : targetTiles) {
            var targetCharacter = (CharacterData) tile.getUserObject();
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (targetCharacter != null) {
                        var value = ad.value + (float) level / maxLevel * ad.value;
                        targetCharacter.extraDamageIfNotHurt += MathUtils.round(value);
                        if (value > 0) ad.battleScreen.showBuff(tile, (int) value);
                        else ad.battleScreen.showDebuff(tile, (int) value);
                        ad.battleScreen.showTextEffectHeal(tile, targetCharacter);
                        if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                        ad.runnable = null;
                    }
                }
            });
        }
    }
    
    public void normalDamageMitigation(AttackData ad) {
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        if (ad.sound != null) ad.sound.play(sfx);
        
        for (var tile : targetTiles) {
            var targetCharacter = (CharacterData) tile.getUserObject();
        
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (targetCharacter != null) {
                        targetCharacter.damageMitigation += MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        if (targetCharacter.damageMitigation > 0) ad.battleScreen.showDamageMitigation(tile, (int) targetCharacter.damageMitigation);
                        else ad.battleScreen.showDamageVulnerable(tile, (int) targetCharacter.damageMitigation);
                        ad.battleScreen.showTextEffectNormal(tile, targetCharacter);
                        if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                        ad.runnable = null;
                    }
                }
            });
        }
    }
    
    public void normalMove(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
    
        for (var tile : targetTiles) {
            var newPosition = ad.battleScreen.positionOfTile(ad.target);
            ad.battleScreen.moveCharacter(ad.character, newPosition, ad.isPlayerTeam);
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(ad.delay), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalStun(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
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
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalStunEnemyOnNextHit(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        character.stunEnemyOnNextHit = true;
                        ad.battleScreen.showCold(tile);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalBlock(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        character.blockNextAttack = true;
                        ad.battleScreen.showBlocking(tile);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalCounterNextAttack(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        int value = MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        character.counterNextAttack = value;
                        ad.battleScreen.showCounterNextAttack(tile);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalDelayedDamage(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        int value = MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        character.delayedDamage = value;
                        ad.battleScreen.showDelayedDamage(tile, value);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalPoison(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        int value = MathUtils.round(ad.value + (float) level / maxLevel * ad.value);
                        character.poison = value;
                        ad.battleScreen.showPoisonDamage(tile, value);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalClearBuffs(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        character.poison = 0;
                        character.delayedDamage = 0;
                        character.blockNextAttack = false;
                        character.counterNextAttack = 0;
                        character.damageMitigation = 0;
                        character.extraDamage = 0;
                        character.extraDamageIfNotHurt = 0;
                        character.extraDamageNextTurn = 0;
                        character.stunned = false;
                        character.stunEnemyOnNextHit = false;
                        character.stunEnemyOnHit = false;
                        ad.battleScreen.showRemoveBuffs(tile);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalCopyProperties(AttackData ad, CharacterData copyCharacter) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        character.copyProperties(copyCharacter);
                        ad.battleScreen.showCopyBuffs(tile);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalCopySkills(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var target = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (target != null) {
                        ad.character.skills.clear();
                        for (var skill : target.skills) {
                            ad.character.skills.add(new SkillData(skill));
                        }
                        ad.battleScreen.showTextEffectNormal(tile, target);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalRestoreMagic(AttackData ad) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var character = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (character != null) {
                        for (var skill : character.skills) {
                            if (skill.usesMax >= 0 && skill.regenerateUses) skill.uses = skill.usesMax;
                        }
                        
                        ad.battleScreen.showRestoreMagic(tile);
                        ad.battleScreen.showTextEffectNormal(tile, character);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalSpawn(AttackData ad, CharacterData newCharacter) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            var enemy = (CharacterData) tile.getUserObject();
            
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (enemy == null) {
                        var newPosition = ad.battleScreen.positionOfTile(ad.target);
                        ad.battleScreen.moveCharacter(newCharacter, newPosition, ad.isPlayerTeam);
                        ad.battleScreen.showTextEffectNormal(ad.target, newCharacter);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public void normalKillCharacter(AttackData ad, CharacterData killCharacter) {
        if (ad.sound != null) ad.sound.play(sfx);
        
        var stage = ad.battleScreen.stage;
        var targetTiles = chooseTiles(ad.battleScreen, ad.target, ad.isPlayerTeam, ad);
        
        for (var tile : targetTiles) {
            playSpine(ad, tile, new AnimationStateAdapter() {
                @Override
                public void complete(TrackEntry entry) {
                    if (killCharacter != null) {
                        killCharacter.health = 0;
                        ad.battleScreen.showTextEffectNormal(tile, killCharacter);
                    }
                    if (ad.runnable != null) stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(ad.runnable)));
                    ad.runnable = null;
                }
            });
        }
    }
    
    public static class AttackData {
        public Sound sound;
        public SkeletonData skeletonData;
        public AnimationStateData animationStateData;
        public Animation animation;
        public BattleScreen battleScreen;
        public CharacterData character;
        public Table target;
        public boolean isPlayerTeam;
        public Runnable runnable;
        public int value;
        public float delay;
        
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
                ad.delay = .5f;
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
                ad.delay = .5f;
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
                ad.delay = .5f;
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
            case "stalk"://player tested
                ad.sound = sfx_creep;
                ad.skeletonData = SpineBlastGray.skeletonData;
                ad.animationStateData = SpineBlastGray.animationData;
                ad.animation = SpineBlastGray.animationAnimation;
                ad.value = 25;
                normalExtraDamageIfNotHurtBuff(ad);
                break;
            case "hairball"://player tested
                ad.sound = sfx_hairball;
                ad.skeletonData = SpineExplosionBrown.skeletonData;
                ad.animationStateData = SpineExplosionBrown.animationData;
                ad.animation = SpineExplosionBrown.animationAnimation;
                var newCharacter = new CharacterData();
                newCharacter.killOnBattleCompletion = true;
                newCharacter.healthMax = 5;
                newCharacter.health = newCharacter.healthMax;
                newCharacter.name = "hairball";
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                normalSpawn(ad, newCharacter);
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
            case "yogurt"://player tested
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
                ad.delay = .5f;
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
                                battleScreen.showBuff(tile, 5);
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
            case "patented ice cube system"://player tested
                ad.sound = sfx_sprayPaintShake;
                ad.skeletonData = SpinePortalBlue.skeletonData;
                ad.animationStateData = SpinePortalBlue.animationData;
                ad.animation = SpinePortalBlue.animationAnimation;
                normalStunEnemyOnNextHit(ad);
                break;
            case "slide"://player tested
                ad.sound = sfx_sprayPaint;
                ad.skeletonData = SpineSwipeLeft.skeletonData;
                ad.animationStateData = SpineSwipeLeft.animationData;
                ad.animation = SpineSwipeLeft.animationAnimation;
                normalMove(ad);
                break;
            case "ice-clone"://player tested
                ad.sound = sfx_frozen;
                ad.skeletonData = SpineSplashBlue.skeletonData;
                ad.animationStateData = SpineSplashBlue.animationData;
                ad.animation = SpineSplashBlue.animationAnimation;
                newCharacter = new CharacterData();
                newCharacter.killOnBattleCompletion = true;
                newCharacter.healthMax = 5;
                newCharacter.health = newCharacter.healthMax;
                newCharacter.name = character.name + " ice-clone";
                newCharacter.stunEnemyOnHit = true;
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                normalSpawn(ad, newCharacter);
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
                ad.delay = .5f;
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
            case "finisher"://player tested
                var enemyTarget = (CharacterData) target.getUserObject();
                
                ad.sound = sfx_magic;
                ad.skeletonData = SpineStrikeDown.skeletonData;
                ad.animationStateData = SpineStrikeDown.animationData;
                ad.animation = SpineStrikeDown.animationAnimation;
                ad.value = MathUtils.ceil(enemyTarget.health);
                normalAttack(ad);
                break;
            case "hook"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_punch;
                ad.skeletonData = SpineStrikeLeft.skeletonData;
                ad.animationStateData = SpineStrikeLeft.animationData;
                ad.animation = SpineStrikeLeft.animationAnimation;
                ad.value = 15;
                ad.delay = .5f;
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
            case "uppercut"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_powerPunch;
                ad.skeletonData = SpineSlashWhiteUp.skeletonData;
                ad.animationStateData = SpineSlashWhiteUp.animationData;
                ad.animation = SpineSlashWhiteUp.animationAnimation;
                ad.value = 25;
                ad.delay = .5f;
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
                    if (targetIndex < 3) targetIndex += 3;
                    
                    var enemyTiles = battleScreen.getTeamTiles(ad2.character);
                    var tile = enemyTiles.get(targetIndex);
                    if (tile.getUserObject() == null) ad2.target = tile;
                    
                    normalMove(ad2);
                };
                normalAttack(ad);
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
            case "parry"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineShieldYellow.skeletonData;
                ad.animationStateData = SpineShieldYellow.animationData;
                ad.animation = SpineShieldYellow.animationAnimation;
                normalBlock(ad);
                break;
            case "riposte"://player tested
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineSlashWhite.skeletonData;
                ad.animationStateData = SpineSlashWhite.animationData;
                ad.animation = SpineSlashWhite.animationAnimation;
                ad.value = 15;
                normalCounterNextAttack(ad);
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
            case "blast"://player tested
                ad.sound = sfx_grenade;
                ad.skeletonData = SpineShot.skeletonData;
                ad.animationStateData = SpineShot.animationData;
                ad.animation = SpineShot.animationAnimation;
                ad.value = 18;
                normalAttack(ad);
                break;
            case "covering fire"://player tested
                ad.sound = sfx_burstGun;
                ad.skeletonData = SpineShieldYellow.skeletonData;
                ad.animationStateData = SpineShieldYellow.animationData;
                ad.animation = SpineShieldYellow.animationAnimation;
                ad.value = 15;
                normalCounterNextAttack(ad);
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
            case "grenade"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineSplashBrown.skeletonData;
                ad.animationStateData = SpineSplashBrown.animationData;
                ad.animation = SpineSplashBrown.animationAnimation;
                ad.value = 25;
                normalDelayedDamage(ad);
                break;
            case "tripwire"://player tested
                ad.sound = sfx_twang;
                ad.skeletonData = SpineSplashBrown.skeletonData;
                ad.animationStateData = SpineSplashBrown.animationData;
                ad.animation = SpineSplashBrown.animationAnimation;
                ad.value = 25;
                normalDelayedDamage(ad);
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
            case "siphon filter"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_magic;
                ad.skeletonData = SpineHealthRed.skeletonData;
                ad.animationStateData = SpineHealthRed.animationData;
                ad.animation = SpineHealthRed.animationAnimation;
                ad.value = 10;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.target = battleScreen.findTile(character);
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalRestoreMagic(ad2);
                };
                normalAttack(ad);
                break;
            case "contort"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_magic;
                ad.skeletonData = SpinePortalRed.skeletonData;
                ad.animationStateData = SpinePortalRed.animationData;
                ad.animation = SpinePortalRed.animationAnimation;
                ad.value = 5;
                ad.delay = .5f;
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
            case "multiply"://player tested
                ad.sound = sfx_multiplying;
                ad.skeletonData = SpineBlastPink.skeletonData;
                ad.animationStateData = SpineBlastPink.animationData;
                ad.animation = SpineBlastPink.animationAnimation;
                newCharacter = new CharacterData();
                newCharacter.killOnBattleCompletion = true;
                newCharacter.healthMax = 15;
                newCharacter.health = newCharacter.healthMax;
                newCharacter.name = "baby bunny";
                newCharacter.addSkill("nibble");
                newCharacter.addSkill("hop");
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                normalSpawn(ad, newCharacter);
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
                                battleScreen.showBuff(tile, 100);
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
            case "grapnel hook"://player tested
                ad.sound = sfx_swordClink;
                ad.skeletonData = SpineStrikeGray.skeletonData;
                ad.animationStateData = SpineStrikeGray.animationData;
                ad.animation = SpineStrikeGray.animationAnimation;
                ad.character = (CharacterData) target.getUserObject();
                ad.isPlayerTeam = !ad.isPlayerTeam;
                var originalIndex = battleScreen.positionOfTile(target);
                var targetIndex = originalIndex;
                if (targetIndex >= 3) targetIndex -= 3;
                
                var enemyTiles = battleScreen.getTeamTiles(ad.character);
                var tileTemp = enemyTiles.get(targetIndex);
                if (tileTemp.getUserObject() == null) ad.target = tileTemp;
                normalMove(ad);
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
            case "caltrops"://player tested
                ad.sound = sfx_bullets;
                ad.skeletonData = SpineShotGray.skeletonData;
                ad.animationStateData = SpineShotGray.animationData;
                ad.animation = SpineShotGray.animationAnimation;
                ad.value = 20;
                normalCounterNextAttack(ad);
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
            case "scanner"://player tested
                ad.sound = sfx_beep;
                ad.skeletonData = SpineHealthRed.skeletonData;
                ad.animationStateData = SpineHealthRed.animationData;
                ad.animation = SpineHealthRed.animationAnimation;
                ad.value = -10;
                normalHeal(ad);
                break;
            case "hack"://player tested
                ad.sound = sfx_beep;
                ad.skeletonData = SpinePortalGreen.skeletonData;
                ad.animationStateData = SpinePortalGreen.animationData;
                ad.animation = SpinePortalGreen.animationAnimation;
                ad.value = 8;
                normalAttack(ad);
                break;
            case "nerf"://player tested
                ad.sound = sfx_darkMagic;
                ad.skeletonData = SpinePortalRed.skeletonData;
                ad.animationStateData = SpinePortalRed.animationData;
                ad.animation = SpinePortalRed.animationAnimation;
                ad.value = -10;
                normalExtraDamageBuff(ad);
                break;
            case "dupe"://player tested
                ad.sound = sfx_multiplying;
                ad.skeletonData = SpineLightningWhite.skeletonData;
                ad.animationStateData = SpineLightningWhite.animationData;
                ad.animation = SpineLightningWhite.animationAnimation;
                newCharacter = new CharacterData((CharacterData) target.getUserObject());
                newCharacter.killOnBattleCompletion = true;
                newCharacter.healthMax /= 3;
                newCharacter.health = newCharacter.healthMax;
                newCharacter.name = newCharacter.name + " " +  MathUtils.random(10);
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                var teamTiles = battleScreen.getTeamTiles(newCharacter);
                for (var tile : teamTiles) {
                    if (tile.getUserObject() == null) {
                        ad.target = tile;
                        break;
                    }
                }
                normalSpawn(ad, newCharacter);
                break;
            case "crypto"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_darkMagic;
                ad.skeletonData = SpineHealth.skeletonData;
                ad.animationStateData = SpineHealth.animationData;
                ad.animation = SpineHealth.animationAnimation;
                ad.value = 60;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad.sound = null;
                    ad.skeletonData = null;
                    ad2.value = 15;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalPoison(ad2);
                };
                normalHeal(ad);
                break;
            case "fanboy"://player tested
                ad.sound = sfx_harp;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                normalCopySkills(ad);
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
                ad.delay = .5f;
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
            case "incendiary grenade"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineSplashBrown.skeletonData;
                ad.animationStateData = SpineSplashBrown.animationData;
                ad.animation = SpineSplashBrown.animationAnimation;
                ad.value = 35;
                normalDelayedDamage(ad);
                break;
            case "brimstone"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineExplosion.skeletonData;
                ad.animationStateData = SpineExplosion.animationData;
                ad.animation = SpineExplosion.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "torch"://player tested
                ad.sound = sfx_thunder;
                ad.skeletonData = SpineBurn.skeletonData;
                ad.animationStateData = SpineBurn.animationData;
                ad.animation = SpineBurn.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "flame shotgun"://player tested
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
            case "compute"://player tested
                ad.sound = sfx_darkMagic;
                ad.skeletonData = SpineHealth.skeletonData;
                ad.animationStateData = SpineHealth.animationData;
                ad.animation = SpineHealth.animationAnimation;
                ad.value = 10;
                normalExtraDamageBuff(ad);
                break;
            case "fuel up":
                ad.sound = sfx_magic;
                ad.skeletonData = SpineHealth.skeletonData;
                ad.animationStateData = SpineHealth.animationData;
                ad.animation = SpineHealth.animationAnimation;
                ad.value = 20;
                normalExtraDamageBuff(ad);
                break;
            case "acceleron":
                ad.sound = sfx_harp;
                ad.skeletonData = SpinePortalBlue.skeletonData;
                ad.animationStateData = SpinePortalBlue.animationData;
                ad.animation = SpinePortalBlue.animationAnimation;
                ad.value = 10;
                normalDamageMitigation(ad);
                break;
            case "beam"://player tested
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineStrikeBlue.skeletonData;
                ad.animationStateData = SpineStrikeBlue.animationData;
                ad.animation = SpineStrikeBlue.animationAnimation;
                ad.value = 15;
                normalAttack(ad);
                break;
            case "self-destruct"://player tested
                ad.sound = sfx_dying;
                ad.skeletonData = SpineExplosion.skeletonData;
                ad.animationStateData = SpineExplosion.animationData;
                ad.animation = SpineExplosion.animationAnimation;
                ad.value = 1000;
                normalAttack(ad);
                break;
            case "gear"://player tested
                ad.sound = sfx_magic;
                ad.skeletonData = SpineShotGray.skeletonData;
                ad.animationStateData = SpineShotGray.animationData;
                ad.animation = SpineShotGray.animationAnimation;
                ad.value = 12;
                normalAttack(ad);
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
            case "spinal tap"://player tested
                skillRunnable = ad.runnable;
                ad.sound = sfx_sprayPaintShake;
                ad.skeletonData = SpineBlastYellow.skeletonData;
                ad.animationStateData = SpineBlastYellow.animationData;
                ad.animation = SpineBlastYellow.animationAnimation;
                ad.value = 10;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.value = 5;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    ad2.target = battleScreen.findTile(character);
                    normalHeal(ad2);
                };
                normalAttack(ad);
                break;
            case "rigor mortis"://player tested
                ad.sound = sfx_dying;
                ad.skeletonData = SpinePortalRed.skeletonData;
                ad.animationStateData = SpinePortalRed.animationData;
                ad.animation = SpinePortalRed.animationAnimation;
                ad.value = 10;
                normalDamageMitigation(ad);
                break;
            case "dead eye"://player tested
                ad.sound = sfx_dying;
                ad.skeletonData = SpineSplashBrown.skeletonData;
                ad.animationStateData = SpineSplashBrown.animationData;
                ad.animation = SpineSplashBrown.animationAnimation;
                ad.value = -10;
                normalDamageMitigation(ad);
                break;
            case "tombstone smack"://player tested
                ad.sound = sfx_grunt;
                ad.skeletonData = SpineStrikeWhite.skeletonData;
                ad.animationStateData = SpineStrikeWhite.animationData;
                ad.animation = SpineStrikeWhite.animationAnimation;
                ad.value = 25;
                normalAttack(ad);
                break;
            case "bury"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_dying;
                ad.skeletonData = SpineSplashBrown.skeletonData;
                ad.animationStateData = SpineSplashBrown.animationData;
                ad.animation = SpineSplashBrown.animationAnimation;
                ad.value = 1000;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    normalStun(ad2);
                };
                normalDamageMitigation(ad);
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
                ad.delay = .5f;
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
            case "panda smash"://player tested
                ad.sound = sfx_hulk;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.delay = 1f;
                ad.character = (CharacterData) target.getUserObject();
                ad.isPlayerTeam = !ad.isPlayerTeam;
                originalIndex = battleScreen.positionOfTile(target);
                targetIndex = originalIndex;
                if (targetIndex < 3) targetIndex += 3;
                
                enemyTiles = battleScreen.getTeamTiles(ad.character);
                var pandaTile = enemyTiles.get(targetIndex);
                if (pandaTile.getUserObject() == null) ad.target = pandaTile;
                
                normalMove(ad);
                break;
            case "eat"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_bite;
                ad.skeletonData = SpineBlood.skeletonData;
                ad.animationStateData = SpineBlood.animationData;
                ad.animation = SpineBlood.animationAnimation;
                ad.value = 15;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    ad2.target = battleScreen.findTile(character);
                    normalHeal(ad2);
                };
                normalAttack(ad);
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
            case "scrub"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_dishes;
                ad.skeletonData = SpineSparkWhite.skeletonData;
                ad.animationStateData = SpineSparkWhite.animationData;
                ad.animation = SpineSparkWhite.animationAnimation;
                ad.value = 5;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalClearBuffs(ad2);
                };
                normalAttack(ad);
                break;
            case "buff"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_dishes;
                ad.skeletonData = SpineBlastBlue.skeletonData;
                ad.animationStateData = SpineBlastBlue.animationData;
                ad.animation = SpineBlastBlue.animationAnimation;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.value = 10;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalExtraDamageBuff(ad2);
                };
                normalClearBuffs(ad);
                break;
            case "squeeze":
                ad.sound = sfx_darkMagic;
                ad.skeletonData = SpineInfinity.skeletonData;
                ad.animationStateData = SpineInfinity.animationData;
                ad.animation = SpineInfinity.animationAnimation;
                normalCopyProperties(ad, character);
                break;
            case "rinse"://player tested
                ad.sound = sfx_dishes;
                ad.skeletonData = SpineBlastBlue.skeletonData;
                ad.animationStateData = SpineBlastBlue.animationData;
                ad.animation = SpineBlastBlue.animationAnimation;
                normalClearBuffs(ad);
                break;
            case "absorb":
                ad.sound = sfx_darkMagic;
                ad.skeletonData = SpineLightningReverseWhite.skeletonData;
                ad.animationStateData = SpineLightningReverseWhite.animationData;
                ad.animation = SpineLightningReverseWhite.animationAnimation;
                ad.target = battleScreen.findTile(character);
                normalCopyProperties(ad, (CharacterData) target.getUserObject());
                break;
            case "spatula"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_swordDraw;
                ad.skeletonData = SpineStrikeGray.skeletonData;
                ad.animationStateData = SpineStrikeGray.animationData;
                ad.animation = SpineStrikeGray.animationAnimation;
                ad.value = 5;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.character = (CharacterData) target.getUserObject();
                    ad2.isPlayerTeam = !ad2.isPlayerTeam;
                    var origIndex = battleScreen.positionOfTile(target);
                    var newIndex = origIndex;
                    if (newIndex >= 3) newIndex -= 3;
                    
                    var eTiles = battleScreen.getTeamTiles(ad2.character);
                    var tTemp = eTiles.get(newIndex);
                    if (tTemp.getUserObject() == null) ad2.target = tTemp;
                    
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalMove(ad2);
                };
                normalAttack(ad);
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
            case "crocodile roll"://player tested
                ad.sound = sfx_thrash;
                ad.skeletonData = SpinePortalGreen.skeletonData;
                ad.animationStateData = SpinePortalGreen.animationData;
                ad.animation = SpinePortalGreen.animationAnimation;
                normalMove(ad);
                break;
            case "tiny arm smack"://player tested
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = 30;
                normalAttack(ad);
                break;
            case "tail sweep"://player tested
                ad.sound = sfx_dodge;
                ad.skeletonData = SpineStrikeLeft.skeletonData;
                ad.animationStateData = SpineStrikeLeft.animationData;
                ad.animation = SpineStrikeLeft.animationAnimation;
                normalStun(ad);
                break;
            case "thrash"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_thrash;
                ad.skeletonData = SpineStrikeRed.skeletonData;
                ad.animationStateData = SpineStrikeRed.animationData;
                ad.animation = SpineStrikeRed.animationAnimation;
                ad.value = MathUtils.ceil(character.health);
                ad.delay = .5f;
                ad.runnable = () -> {
                    character.health = 1;
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    ad2.value = 50;
                    normalExtraDamageBuff(ad2);
                };
                normalAttack(ad);
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
            case "juice up"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_hulk;
                ad.skeletonData = SpineBlastGreen.skeletonData;
                ad.animationStateData = SpineBlastGreen.animationData;
                ad.animation = SpineBlastGreen.animationAnimation;
                ad.value = 20;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.value = -20;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalDamageMitigation(ad2);
                };
                normalExtraDamageBuff(ad);
                break;
            case "block"://player tested
                ad.sound = sfx_blocking;
                ad.skeletonData = SpineShield.skeletonData;
                ad.animationStateData = SpineShield.animationData;
                ad.animation = SpineShield.animationAnimation;
                normalBlock(ad);
                break;
            case "defend"://player tested
                ad.sound = sfx_swordClink;
                ad.skeletonData = SpineShieldYellow.skeletonData;
                ad.animationStateData = SpineShieldYellow.animationData;
                ad.animation = SpineShieldYellow.animationAnimation;
                ad.value = 20;
                normalDamageMitigation(ad);
                break;
            case "deflect"://player tested
                ad.sound = sfx_swordClink;
                ad.skeletonData = SpineShieldYellow.skeletonData;
                ad.animationStateData = SpineShieldYellow.animationData;
                ad.animation = SpineShieldYellow.animationAnimation;
                ad.value = 20;
                normalDamageMitigation(ad);
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
            case "divide"://player tested
                ad.sound = sfx_multiplying;
                ad.skeletonData = SpinePortal.skeletonData;
                ad.animationStateData = SpinePortal.animationData;
                ad.animation = SpinePortal.animationAnimation;
                character.health /= 2;
                for (var skill : character.skills) {
                    if (skill.usesMax > 0) {
                        skill.uses /= 2;
                    }
                }
                battleScreen.updateProgressBars(battleScreen.findTile(character));
                newCharacter = new CharacterData(character);
                newCharacter.name = newCharacter.name + " " +  MathUtils.random(10);
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                normalSpawn(ad, newCharacter);
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
            case "goopicide"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_slime;
                ad.skeletonData = SpineExplosionWhite.skeletonData;
                ad.animationStateData = SpineExplosionWhite.animationData;
                ad.animation = SpineExplosionWhite.animationAnimation;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = .5f;
                    ad2.runnable = skillRunnable;
                    normalKillCharacter(ad2, character);
                };
                normalStun(ad);
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
                                battleScreen.showBuff(tile, -10);
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
            case "howl"://player tested
                ad.sound = sfx_howl;
                ad.skeletonData = SpineBlastGray.skeletonData;
                ad.animationStateData = SpineBlastGray.animationData;
                ad.animation = SpineBlastGray.animationAnimation;
                newCharacter = new CharacterData();
                newCharacter.killOnBattleCompletion = true;
                newCharacter.healthMax = 15;
                newCharacter.health = newCharacter.healthMax;
                newCharacter.name = "cute wolf";
                newCharacter.addSkill("nibble");
                newCharacter.addSkill("trot");
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                normalSpawn(ad, newCharacter);
                break;
            case "trot"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_trot;
                ad.skeletonData = SpineSwipeLeft.skeletonData;
                ad.animationStateData = SpineSwipeLeft.animationData;
                ad.animation = SpineSwipeLeft.animationAnimation;
                ad.delay = .5f;
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
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_harp;
                ad.skeletonData = SpinePortalRed.skeletonData;
                ad.animationStateData = SpinePortalRed.animationData;
                ad.animation = SpinePortalRed.animationAnimation;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.skeletonData = SpineHealth.skeletonData;
                    ad2.animationStateData = SpineHealth.animationData;
                    ad2.animation = SpineHealth.animationAnimation;
                    ad2.value = 10;
                    ad2.target = battleScreen.findTile(character);
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalExtraDamageBuff(ad2);
                };
                normalMove(ad);
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
            case "focused beam"://player tested
                skillRunnable = ad.runnable;
                
                ad.sound = sfx_lightBeam;
                ad.skeletonData = SpineStrikePurple.skeletonData;
                ad.animationStateData = SpineStrikePurple.animationData;
                ad.animation = SpineStrikePurple.animationAnimation;
                ad.value = 10;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.value = -10;
                    ad2.delay = 1f;
                    normalExtraDamageBuff(ad2);
                };
                normalAttack(ad);
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
                ad.sound = sfx_darkMagic;
                ad.skeletonData = SpineBlastRed.skeletonData;
                ad.animationStateData = SpineBlastRed.animationData;
                ad.animation = SpineBlastRed.animationAnimation;
                ad.value = 10;
                normalPoison(ad);
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
                skillRunnable = runnable;
                
                ad.sound = sfx_scream;
                ad.skeletonData = SpineInfinityWhite.skeletonData;
                ad.animationStateData = SpineInfinityWhite.animationData;
                ad.animation = SpineInfinityWhite.animationAnimation;
                ad.value = -10;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalExtraDamageBuff(ad2);
                };
                normalDamageMitigation(ad);
                break;
            case "rage out":
                ad.sound = sfx_hulk;
                ad.skeletonData = SpineBlood.skeletonData;
                ad.animationStateData = SpineBlood.animationData;
                ad.animation = SpineBlood.animationAnimation;
                ad.value = 10;
                normalHeal(ad);
                break;
            case "rind":
                ad.sound = sfx_salad;
                ad.skeletonData = SpineBlastOrange.skeletonData;
                ad.animationStateData = SpineBlastOrange.animationData;
                ad.animation = SpineBlastOrange.animationAnimation;
                ad.value = 10;
                normalDamageMitigation(ad);
                break;
            case "tasty snack"://player tested
                skillRunnable = runnable;
                
                ad.sound = sfx_salad;
                ad.skeletonData = SpineHealth.skeletonData;
                ad.animationStateData = SpineHealth.animationData;
                ad.animation = SpineHealth.animationAnimation;
                ad.value = 10;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    directDamage(ad2, character);
                };
                normalHeal(ad);
                break;
            case "vitamin c":
                skillRunnable = runnable;
                
                ad.sound = sfx_salad;
                ad.skeletonData = SpineExplosionYellow.skeletonData;
                ad.animationStateData = SpineExplosionYellow.animationData;
                ad.animation = SpineExplosionYellow.animationAnimation;
                ad.value = 20;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.value = 10;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalAttack(ad2);
                };
                normalExtraDamageBuff(ad);
                break;
            case "crown of thorns":
                skillRunnable = runnable;
                
                ad.sound = sfx_dragon;
                ad.skeletonData = SpineHealthRed.skeletonData;
                ad.animationStateData = SpineHealthRed.animationData;
                ad.animation = SpineHealthRed.animationAnimation;
                ad.value = 5;
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.runnable = skillRunnable;
                    ad2.delay = 1f;
                    normalStun(ad2);
                };
                normalExtraDamageBuff(ad);
                break;
            case "flay and bake"://player tested
                skillRunnable = ad.runnable;
                
                var enemy = (CharacterData) target.getUserObject();
                
                ad.sound = sfx_dragon;
                ad.skeletonData = SpineBurn.skeletonData;
                ad.animationStateData = SpineBurn.animationData;
                ad.animation = SpineBurn.animationAnimation;
                ad.value = MathUtils.ceil(enemy.health);
                ad.delay = .5f;
                ad.runnable = () -> {
                    var ad2 = new AttackData(ad);
                    ad2.sound = null;
                    ad2.skeletonData = null;
                    ad2.delay = 1f;
                    ad2.runnable = skillRunnable;
                    ad2.target = battleScreen.findTile(character);
                    normalHeal(ad2);
                };
                normalAttack(ad);
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
            case "fox friend":
                ad.sound = sfx_howl;
                ad.skeletonData = SpineBlastGray.skeletonData;
                ad.animationStateData = SpineBlastGray.animationData;
                ad.animation = SpineBlastGray.animationAnimation;
                newCharacter = new CharacterData();
                newCharacter.killOnBattleCompletion = true;
                newCharacter.healthMax = 15;
                newCharacter.health = newCharacter.healthMax;
                newCharacter.name = "cute fox";
                newCharacter.addSkill("nibble");
                newCharacter.addSkill("trot");
                (isPlayerTeam ? playerTeam : enemyTeam).add(newCharacter);
                normalSpawn(ad, newCharacter);
                break;
            default:
                stage.addAction(Actions.sequence(Actions.delay(.5f), Actions.run(runnable)));
                break;
        }
    }
    
    public Array<Table> chooseTiles(BattleScreen battleScreen, Table target, boolean playerTeam) {
        return chooseTiles(battleScreen, target, playerTeam, null);
    }
    
    public Array<Table> chooseTiles(BattleScreen battleScreen, Table target, boolean playerTeam, AttackData ad) {
        var selected = new Array<Table>();
        var allies = playerTeam ? battleScreen.getPlayerTiles() : battleScreen.getEnemyTiles();
        var enemies = playerTeam ? battleScreen.getEnemyTiles() : battleScreen.getPlayerTiles();
        
        switch (name) {
            //target's column
            case "icicle":
            case "line piece":
            case "devastating beam":
                selected.add(target);
                var index = enemies.indexOf(target, true);
                if (index < 3 && enemies.get(index + 3).getUserObject() != null) selected.add(enemies.get(index + 3));
                else if (index >= 3 && enemies.get(index - 3).getUserObject() != null) selected.add(enemies.get(index - 3));
                break;
            //target and adjacent
            case "round-house kick":
            case "ricochet":
            case "torch":
                selected.add(target);
                index = enemies.indexOf(target, true);
                if (index < 3) index += 3;
                
                if (index + 1 < 6 && enemies.get(index + 1).getUserObject() != null) selected.add(enemies.get(index + 1));
                else if (index - 1 >= 3 && enemies.get(index - 1).getUserObject() != null) selected.add(enemies.get(index - 1));
                break;
            //target and all adjacent
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
            //all allies
            case "free samples":
            case "ice cream social":
            case "covering fire":
                for (var tile : allies) {
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            //allies front row
            case "defend":
                for (int i = 0; i < 3; i++) {
                    var tile = allies.get(i);
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            //all enemies
            case "flash":
            case "square":
            case "circumference":
            case "perimeter":
                for (var tile : enemies) {
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            //enemy front row
            case "triple kick":
            case "tripwire":
            case "panda smash":
                for (int i = 0; i < 3; i++) {
                    var tile = enemies.get(i);
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                break;
            //all enemies and target
            case "self-destruct":
            case "goopicide":
                for (var tile : enemies) {
                    if (tile.getUserObject() != null) selected.add(tile);
                }
                selected.add(target);
                break;
            //target and self
            case "gear":
                selected.add(target);
                if (ad != null) selected.add(battleScreen.findTile(ad.character));
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
    
    public Array<Table> collectAvailableTiles(BattleScreen battleScreen, boolean isPlayerTeam, int characterPosition) {
        if (name.equals("divide") && playerTeam.size >= 4) return new Array<>();
        if (name.equals("dupe") && playerTeam.size >= 6) return new Array<>();
        
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
            case "rind":
            case "cat-nap":
            case "cat-nip":
            case "stalk":
            case "caltrops":
            case "crypto":
                return Selector.selectSelf(battleScreen, isPlayerTeam, characterPosition);
            case "lunge":
            case "energy blast":
                return Selector.selectEnemyColumnDirectlyInFront(battleScreen, isPlayerTeam, characterPosition);
            case "leap":
            case "hairball":
            case "ice-clone":
            case "fly":
            case "hop":
            case "multiply":
            case "divide":
            case "howl":
            case "fox friend":
                return Selector.selectAnyEmptyAlly(battleScreen, isPlayerTeam);
            case "slide":
            case "flutter":
            case "crocodile roll":
            case "trot":
                return Selector.selectEmptyAllyAdjacent(battleScreen, isPlayerTeam,characterPosition);
            case "advance":
            case "charge":
                return Selector.selectEmptyAllyForward(battleScreen, isPlayerTeam, characterPosition);
            case "retreat":
                return Selector.selectEmptyAllyBackward(battleScreen, isPlayerTeam, characterPosition);
            case "belly crawl":
                return Selector.selectEmptyAllyForwardOrBackward(battleScreen, isPlayerTeam, characterPosition);
            case "pirouette":
                return Selector.selectEmptyAllySide(battleScreen, isPlayerTeam, characterPosition);
            case "fanboy":
            case "tasty snack":
            case "vitamin c":
                return Selector.selectAnyAllyNotSelf(battleScreen, isPlayerTeam, characterPosition);
            case "yogurt":
            case "sherbet":
            case "banana sundae":
            case "free samples":
            case "ice cream social":
            case "siphon filter":
            case "snuggle":
            case "throw weight around":
            case "buff":
            case "dino bop":
            case "block":
            case "merge":
            case "grumble":
                return Selector.selectAnyAlly(battleScreen, isPlayerTeam);
            case "defend":
                return Selector.selectAnyAllyFrontRow(battleScreen, isPlayerTeam);
            case "deflect":
                return Selector.selectAnyAllyBackRow(battleScreen, isPlayerTeam);
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
            case "sticky tape":
            case "scanner":
            case "hack":
            case "nerf":
            case "dupe":
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
            case "heat vision":
                return Selector.selectAnyEnemy(battleScreen, isPlayerTeam);
            case "flay and bake":
                var teamTiles = Selector.selectAnyEnemy(battleScreen, isPlayerTeam);
                var iter = teamTiles.iterator();
                while (iter.hasNext()) {
                    var enemyTile = iter.next();
                    var enemy = (CharacterData) enemyTile.getUserObject();
                    if (enemy.health / enemy.healthMax > .5f) iter.remove();
                }
                return teamTiles;
            case "combo":
                return Selector.selectMeleeDirectlyInFront(battleScreen, isPlayerTeam, characterPosition);
            case "triple kick":
            case "tripwire":
            case "panda smash":
            case "pie slice":
            case "tail sweep":
            case "ricochet":
            case "rage out":
                return Selector.selectAnyEnemyFrontRow(battleScreen, isPlayerTeam);
            case "grapnel hook":
            case "spatula":
                return Selector.selectAnyEnemyBackRow(battleScreen, isPlayerTeam);
            case "tee":
                return Selector.selectTee(battleScreen, isPlayerTeam);
            case "pie graph":
                return Selector.selectMeleeDiagonalOnly(battleScreen, isPlayerTeam, characterPosition);
            case "radial menu":
            case "radius":
                return Selector.selectAnyEnemyCorners(battleScreen, isPlayerTeam);
            case "finisher":
                teamTiles = Selector.selectMelee(battleScreen, isPlayerTeam, characterPosition);
                iter = teamTiles.iterator();
                while (iter.hasNext()) {
                    var enemyTile = iter.next();
                    var enemy = (CharacterData) enemyTile.getUserObject();
                    if (enemy.health / enemy.healthMax > .1f) iter.remove();
                }
                return teamTiles;
            default:
                return Selector.selectMelee(battleScreen, isPlayerTeam, characterPosition);
        }
    }
}
