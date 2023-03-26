package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.BoneData;
import com.esotericsoftware.spine.EventData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SlotData;
import java.lang.String;

public class Resources {
    public static TextureAtlas textures_textures;

    public static Skin skin_skin;

    public static Sound sfx_click;

    public static Sound sfx_libgdx01;

    public static Sound sfx_libgdx02;

    public static Sound sfx_libgdx03;

    public static Sound sfx_libgdx04;

    public static Sound sfx_libgdx05;

    public static Sound sfx_libgdx06;

    public static Sound sfx_libgdx07;

    public static Sound sfx_libgdx08;

    public static Sound sfx_libgdx09;

    public static Sound sfx_libgdx10;

    public static Sound sfx_libgdx11;

    public static Sound sfx_ray3k01;

    public static Sound sfx_ray3k02;

    public static Sound sfx_ray3k03;

    public static Sound sfx_ray3k04;

    public static Music bgm_audioSample;

    public static Music bgm_game;

    public static Music bgm_menu;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
        SpineBiteWhite.skeletonData = assetManager.get("spine/bite-white.json");
        SpineBiteWhite.animationData = assetManager.get("spine/bite-white.json-animation");
        SpineBiteWhite.animationAnimation = SpineBiteWhite.skeletonData.findAnimation("animation");
        SpineBiteWhite.boneRoot = SpineBiteWhite.skeletonData.findBone("root");
        SpineBiteWhite.boneGameBite = SpineBiteWhite.skeletonData.findBone("game/bite");
        SpineBiteWhite.slotGameBite = SpineBiteWhite.skeletonData.findSlot("game/bite");
        SpineBiteWhite.skinDefault = SpineBiteWhite.skeletonData.findSkin("default");
        SpineBite.skeletonData = assetManager.get("spine/bite.json");
        SpineBite.animationData = assetManager.get("spine/bite.json-animation");
        SpineBite.animationAnimation = SpineBite.skeletonData.findAnimation("animation");
        SpineBite.boneRoot = SpineBite.skeletonData.findBone("root");
        SpineBite.boneGameBite = SpineBite.skeletonData.findBone("game/bite");
        SpineBite.slotGameBite = SpineBite.skeletonData.findSlot("game/bite");
        SpineBite.skinDefault = SpineBite.skeletonData.findSkin("default");
        SpineBlastBlue.skeletonData = assetManager.get("spine/blast-blue.json");
        SpineBlastBlue.animationData = assetManager.get("spine/blast-blue.json-animation");
        SpineBlastBlue.animationAnimation = SpineBlastBlue.skeletonData.findAnimation("animation");
        SpineBlastBlue.boneRoot = SpineBlastBlue.skeletonData.findBone("root");
        SpineBlastBlue.slotGameBlast01 = SpineBlastBlue.skeletonData.findSlot("game/blast-01");
        SpineBlastBlue.skinDefault = SpineBlastBlue.skeletonData.findSkin("default");
        SpineBlastGray.skeletonData = assetManager.get("spine/blast-gray.json");
        SpineBlastGray.animationData = assetManager.get("spine/blast-gray.json-animation");
        SpineBlastGray.animationAnimation = SpineBlastGray.skeletonData.findAnimation("animation");
        SpineBlastGray.boneRoot = SpineBlastGray.skeletonData.findBone("root");
        SpineBlastGray.slotGameBlast01 = SpineBlastGray.skeletonData.findSlot("game/blast-01");
        SpineBlastGray.skinDefault = SpineBlastGray.skeletonData.findSkin("default");
        SpineBlastGreen.skeletonData = assetManager.get("spine/blast-green.json");
        SpineBlastGreen.animationData = assetManager.get("spine/blast-green.json-animation");
        SpineBlastGreen.animationAnimation = SpineBlastGreen.skeletonData.findAnimation("animation");
        SpineBlastGreen.boneRoot = SpineBlastGreen.skeletonData.findBone("root");
        SpineBlastGreen.slotGameBlast01 = SpineBlastGreen.skeletonData.findSlot("game/blast-01");
        SpineBlastGreen.skinDefault = SpineBlastGreen.skeletonData.findSkin("default");
        SpineBlastOrange.skeletonData = assetManager.get("spine/blast-orange.json");
        SpineBlastOrange.animationData = assetManager.get("spine/blast-orange.json-animation");
        SpineBlastOrange.animationAnimation = SpineBlastOrange.skeletonData.findAnimation("animation");
        SpineBlastOrange.boneRoot = SpineBlastOrange.skeletonData.findBone("root");
        SpineBlastOrange.slotGameBlast01 = SpineBlastOrange.skeletonData.findSlot("game/blast-01");
        SpineBlastOrange.skinDefault = SpineBlastOrange.skeletonData.findSkin("default");
        SpineBlastPink.skeletonData = assetManager.get("spine/blast-pink.json");
        SpineBlastPink.animationData = assetManager.get("spine/blast-pink.json-animation");
        SpineBlastPink.animationAnimation = SpineBlastPink.skeletonData.findAnimation("animation");
        SpineBlastPink.boneRoot = SpineBlastPink.skeletonData.findBone("root");
        SpineBlastPink.slotGameBlast01 = SpineBlastPink.skeletonData.findSlot("game/blast-01");
        SpineBlastPink.skinDefault = SpineBlastPink.skeletonData.findSkin("default");
        SpineBlastRed.skeletonData = assetManager.get("spine/blast-red.json");
        SpineBlastRed.animationData = assetManager.get("spine/blast-red.json-animation");
        SpineBlastRed.animationAnimation = SpineBlastRed.skeletonData.findAnimation("animation");
        SpineBlastRed.boneRoot = SpineBlastRed.skeletonData.findBone("root");
        SpineBlastRed.slotGameBlast01 = SpineBlastRed.skeletonData.findSlot("game/blast-01");
        SpineBlastRed.skinDefault = SpineBlastRed.skeletonData.findSkin("default");
        SpineBlastWhite.skeletonData = assetManager.get("spine/blast-white.json");
        SpineBlastWhite.animationData = assetManager.get("spine/blast-white.json-animation");
        SpineBlastWhite.animationAnimation = SpineBlastWhite.skeletonData.findAnimation("animation");
        SpineBlastWhite.boneRoot = SpineBlastWhite.skeletonData.findBone("root");
        SpineBlastWhite.slotGameBlast01 = SpineBlastWhite.skeletonData.findSlot("game/blast-01");
        SpineBlastWhite.skinDefault = SpineBlastWhite.skeletonData.findSkin("default");
        SpineBlastYellow.skeletonData = assetManager.get("spine/blast-yellow.json");
        SpineBlastYellow.animationData = assetManager.get("spine/blast-yellow.json-animation");
        SpineBlastYellow.animationAnimation = SpineBlastYellow.skeletonData.findAnimation("animation");
        SpineBlastYellow.boneRoot = SpineBlastYellow.skeletonData.findBone("root");
        SpineBlastYellow.slotGameBlast01 = SpineBlastYellow.skeletonData.findSlot("game/blast-01");
        SpineBlastYellow.skinDefault = SpineBlastYellow.skeletonData.findSkin("default");
        SpineBloodBrown.skeletonData = assetManager.get("spine/blood-brown.json");
        SpineBloodBrown.animationData = assetManager.get("spine/blood-brown.json-animation");
        SpineBloodBrown.animationAnimation = SpineBloodBrown.skeletonData.findAnimation("animation");
        SpineBloodBrown.boneRoot = SpineBloodBrown.skeletonData.findBone("root");
        SpineBloodBrown.slotGameBlood01 = SpineBloodBrown.skeletonData.findSlot("game/blood-01");
        SpineBloodBrown.skinDefault = SpineBloodBrown.skeletonData.findSkin("default");
        SpineBloodWhite.skeletonData = assetManager.get("spine/blood-white.json");
        SpineBloodWhite.animationData = assetManager.get("spine/blood-white.json-animation");
        SpineBloodWhite.animationAnimation = SpineBloodWhite.skeletonData.findAnimation("animation");
        SpineBloodWhite.boneRoot = SpineBloodWhite.skeletonData.findBone("root");
        SpineBloodWhite.slotGameBlood01 = SpineBloodWhite.skeletonData.findSlot("game/blood-01");
        SpineBloodWhite.skinDefault = SpineBloodWhite.skeletonData.findSkin("default");
        SpineBlood.skeletonData = assetManager.get("spine/blood.json");
        SpineBlood.animationData = assetManager.get("spine/blood.json-animation");
        SpineBlood.animationAnimation = SpineBlood.skeletonData.findAnimation("animation");
        SpineBlood.boneRoot = SpineBlood.skeletonData.findBone("root");
        SpineBlood.slotGameBlood01 = SpineBlood.skeletonData.findSlot("game/blood-01");
        SpineBlood.skinDefault = SpineBlood.skeletonData.findSkin("default");
        SpineBurn.skeletonData = assetManager.get("spine/burn.json");
        SpineBurn.animationData = assetManager.get("spine/burn.json-animation");
        SpineBurn.animationAnimation = SpineBurn.skeletonData.findAnimation("animation");
        SpineBurn.boneRoot = SpineBurn.skeletonData.findBone("root");
        SpineBurn.slotGameBurn01 = SpineBurn.skeletonData.findSlot("game/burn-01");
        SpineBurn.skinDefault = SpineBurn.skeletonData.findSkin("default");
        SpineBurnGreen.skeletonData = assetManager.get("spine/burnGreen.json");
        SpineBurnGreen.animationData = assetManager.get("spine/burnGreen.json-animation");
        SpineBurnGreen.animationAnimation = SpineBurnGreen.skeletonData.findAnimation("animation");
        SpineBurnGreen.boneRoot = SpineBurnGreen.skeletonData.findBone("root");
        SpineBurnGreen.slotGameBurn01 = SpineBurnGreen.skeletonData.findSlot("game/burn-01");
        SpineBurnGreen.skinDefault = SpineBurnGreen.skeletonData.findSkin("default");
        SpineExplosionBlue.skeletonData = assetManager.get("spine/explosion-blue.json");
        SpineExplosionBlue.animationData = assetManager.get("spine/explosion-blue.json-animation");
        SpineExplosionBlue.animationAnimation = SpineExplosionBlue.skeletonData.findAnimation("animation");
        SpineExplosionBlue.boneRoot = SpineExplosionBlue.skeletonData.findBone("root");
        SpineExplosionBlue.slotGameExplosion01 = SpineExplosionBlue.skeletonData.findSlot("game/explosion-01");
        SpineExplosionBlue.skinDefault = SpineExplosionBlue.skeletonData.findSkin("default");
        SpineExplosionBrown.skeletonData = assetManager.get("spine/explosion-brown.json");
        SpineExplosionBrown.animationData = assetManager.get("spine/explosion-brown.json-animation");
        SpineExplosionBrown.animationAnimation = SpineExplosionBrown.skeletonData.findAnimation("animation");
        SpineExplosionBrown.boneRoot = SpineExplosionBrown.skeletonData.findBone("root");
        SpineExplosionBrown.slotGameExplosion01 = SpineExplosionBrown.skeletonData.findSlot("game/explosion-01");
        SpineExplosionBrown.skinDefault = SpineExplosionBrown.skeletonData.findSkin("default");
        SpineExplosionGray.skeletonData = assetManager.get("spine/explosion-gray.json");
        SpineExplosionGray.animationData = assetManager.get("spine/explosion-gray.json-animation");
        SpineExplosionGray.animationAnimation = SpineExplosionGray.skeletonData.findAnimation("animation");
        SpineExplosionGray.boneRoot = SpineExplosionGray.skeletonData.findBone("root");
        SpineExplosionGray.slotGameExplosion01 = SpineExplosionGray.skeletonData.findSlot("game/explosion-01");
        SpineExplosionGray.skinDefault = SpineExplosionGray.skeletonData.findSkin("default");
        SpineExplosionGray2.skeletonData = assetManager.get("spine/explosion-gray2.json");
        SpineExplosionGray2.animationData = assetManager.get("spine/explosion-gray2.json-animation");
        SpineExplosionGray2.animationAnimation = SpineExplosionGray2.skeletonData.findAnimation("animation");
        SpineExplosionGray2.boneRoot = SpineExplosionGray2.skeletonData.findBone("root");
        SpineExplosionGray2.slotGameExplosion01 = SpineExplosionGray2.skeletonData.findSlot("game/explosion-01");
        SpineExplosionGray2.skinDefault = SpineExplosionGray2.skeletonData.findSkin("default");
        SpineExplosionGreen.skeletonData = assetManager.get("spine/explosion-green.json");
        SpineExplosionGreen.animationData = assetManager.get("spine/explosion-green.json-animation");
        SpineExplosionGreen.animationAnimation = SpineExplosionGreen.skeletonData.findAnimation("animation");
        SpineExplosionGreen.boneRoot = SpineExplosionGreen.skeletonData.findBone("root");
        SpineExplosionGreen.slotGameExplosion01 = SpineExplosionGreen.skeletonData.findSlot("game/explosion-01");
        SpineExplosionGreen.skinDefault = SpineExplosionGreen.skeletonData.findSkin("default");
        SpineExplosionPink.skeletonData = assetManager.get("spine/explosion-pink.json");
        SpineExplosionPink.animationData = assetManager.get("spine/explosion-pink.json-animation");
        SpineExplosionPink.animationAnimation = SpineExplosionPink.skeletonData.findAnimation("animation");
        SpineExplosionPink.boneRoot = SpineExplosionPink.skeletonData.findBone("root");
        SpineExplosionPink.slotGameExplosion01 = SpineExplosionPink.skeletonData.findSlot("game/explosion-01");
        SpineExplosionPink.skinDefault = SpineExplosionPink.skeletonData.findSkin("default");
        SpineExplosionWhite.skeletonData = assetManager.get("spine/explosion-white.json");
        SpineExplosionWhite.animationData = assetManager.get("spine/explosion-white.json-animation");
        SpineExplosionWhite.animationAnimation = SpineExplosionWhite.skeletonData.findAnimation("animation");
        SpineExplosionWhite.boneRoot = SpineExplosionWhite.skeletonData.findBone("root");
        SpineExplosionWhite.slotGameExplosion01 = SpineExplosionWhite.skeletonData.findSlot("game/explosion-01");
        SpineExplosionWhite.skinDefault = SpineExplosionWhite.skeletonData.findSkin("default");
        SpineExplosionYellow.skeletonData = assetManager.get("spine/explosion-yellow.json");
        SpineExplosionYellow.animationData = assetManager.get("spine/explosion-yellow.json-animation");
        SpineExplosionYellow.animationAnimation = SpineExplosionYellow.skeletonData.findAnimation("animation");
        SpineExplosionYellow.boneRoot = SpineExplosionYellow.skeletonData.findBone("root");
        SpineExplosionYellow.slotGameExplosion01 = SpineExplosionYellow.skeletonData.findSlot("game/explosion-01");
        SpineExplosionYellow.skinDefault = SpineExplosionYellow.skeletonData.findSkin("default");
        SpineExplosion.skeletonData = assetManager.get("spine/explosion.json");
        SpineExplosion.animationData = assetManager.get("spine/explosion.json-animation");
        SpineExplosion.animationAnimation = SpineExplosion.skeletonData.findAnimation("animation");
        SpineExplosion.boneRoot = SpineExplosion.skeletonData.findBone("root");
        SpineExplosion.slotGameExplosion01 = SpineExplosion.skeletonData.findSlot("game/explosion-01");
        SpineExplosion.skinDefault = SpineExplosion.skeletonData.findSkin("default");
        SpineHealthBrown.skeletonData = assetManager.get("spine/health-brown.json");
        SpineHealthBrown.animationData = assetManager.get("spine/health-brown.json-animation");
        SpineHealthBrown.animationAnimation = SpineHealthBrown.skeletonData.findAnimation("animation");
        SpineHealthBrown.boneRoot = SpineHealthBrown.skeletonData.findBone("root");
        SpineHealthBrown.boneGameHealth2 = SpineHealthBrown.skeletonData.findBone("game/health2");
        SpineHealthBrown.boneGameHealth4 = SpineHealthBrown.skeletonData.findBone("game/health4");
        SpineHealthBrown.boneGameHealth3 = SpineHealthBrown.skeletonData.findBone("game/health3");
        SpineHealthBrown.boneGameHealth5 = SpineHealthBrown.skeletonData.findBone("game/health5");
        SpineHealthBrown.boneGameHealth6 = SpineHealthBrown.skeletonData.findBone("game/health6");
        SpineHealthBrown.boneGameHealth7 = SpineHealthBrown.skeletonData.findBone("game/health7");
        SpineHealthBrown.boneGameHealth8 = SpineHealthBrown.skeletonData.findBone("game/health8");
        SpineHealthBrown.boneGameHealth9 = SpineHealthBrown.skeletonData.findBone("game/health9");
        SpineHealthBrown.slotGameHealth2 = SpineHealthBrown.skeletonData.findSlot("game/health2");
        SpineHealthBrown.slotGameHealth9 = SpineHealthBrown.skeletonData.findSlot("game/health9");
        SpineHealthBrown.slotGameHealth3 = SpineHealthBrown.skeletonData.findSlot("game/health3");
        SpineHealthBrown.slotGameHealth8 = SpineHealthBrown.skeletonData.findSlot("game/health8");
        SpineHealthBrown.slotGameHealth4 = SpineHealthBrown.skeletonData.findSlot("game/health4");
        SpineHealthBrown.slotGameHealth7 = SpineHealthBrown.skeletonData.findSlot("game/health7");
        SpineHealthBrown.slotGameHealth5 = SpineHealthBrown.skeletonData.findSlot("game/health5");
        SpineHealthBrown.slotGameHealth6 = SpineHealthBrown.skeletonData.findSlot("game/health6");
        SpineHealthBrown.slotHealthpath = SpineHealthBrown.skeletonData.findSlot("healthpath");
        SpineHealthBrown.slotHealthpath2 = SpineHealthBrown.skeletonData.findSlot("healthpath2");
        SpineHealthBrown.skinDefault = SpineHealthBrown.skeletonData.findSkin("default");
        SpineHealthRed.skeletonData = assetManager.get("spine/health-red.json");
        SpineHealthRed.animationData = assetManager.get("spine/health-red.json-animation");
        SpineHealthRed.animationAnimation = SpineHealthRed.skeletonData.findAnimation("animation");
        SpineHealthRed.boneRoot = SpineHealthRed.skeletonData.findBone("root");
        SpineHealthRed.boneGameHealth2 = SpineHealthRed.skeletonData.findBone("game/health2");
        SpineHealthRed.boneGameHealth4 = SpineHealthRed.skeletonData.findBone("game/health4");
        SpineHealthRed.boneGameHealth3 = SpineHealthRed.skeletonData.findBone("game/health3");
        SpineHealthRed.boneGameHealth5 = SpineHealthRed.skeletonData.findBone("game/health5");
        SpineHealthRed.boneGameHealth6 = SpineHealthRed.skeletonData.findBone("game/health6");
        SpineHealthRed.boneGameHealth7 = SpineHealthRed.skeletonData.findBone("game/health7");
        SpineHealthRed.boneGameHealth8 = SpineHealthRed.skeletonData.findBone("game/health8");
        SpineHealthRed.boneGameHealth9 = SpineHealthRed.skeletonData.findBone("game/health9");
        SpineHealthRed.slotGameHealth2 = SpineHealthRed.skeletonData.findSlot("game/health2");
        SpineHealthRed.slotGameHealth9 = SpineHealthRed.skeletonData.findSlot("game/health9");
        SpineHealthRed.slotGameHealth3 = SpineHealthRed.skeletonData.findSlot("game/health3");
        SpineHealthRed.slotGameHealth8 = SpineHealthRed.skeletonData.findSlot("game/health8");
        SpineHealthRed.slotGameHealth4 = SpineHealthRed.skeletonData.findSlot("game/health4");
        SpineHealthRed.slotGameHealth7 = SpineHealthRed.skeletonData.findSlot("game/health7");
        SpineHealthRed.slotGameHealth5 = SpineHealthRed.skeletonData.findSlot("game/health5");
        SpineHealthRed.slotGameHealth6 = SpineHealthRed.skeletonData.findSlot("game/health6");
        SpineHealthRed.slotHealthpath = SpineHealthRed.skeletonData.findSlot("healthpath");
        SpineHealthRed.slotHealthpath2 = SpineHealthRed.skeletonData.findSlot("healthpath2");
        SpineHealthRed.skinDefault = SpineHealthRed.skeletonData.findSkin("default");
        SpineHealth.skeletonData = assetManager.get("spine/health.json");
        SpineHealth.animationData = assetManager.get("spine/health.json-animation");
        SpineHealth.animationAnimation = SpineHealth.skeletonData.findAnimation("animation");
        SpineHealth.boneRoot = SpineHealth.skeletonData.findBone("root");
        SpineHealth.boneGameHealth2 = SpineHealth.skeletonData.findBone("game/health2");
        SpineHealth.boneGameHealth4 = SpineHealth.skeletonData.findBone("game/health4");
        SpineHealth.boneGameHealth3 = SpineHealth.skeletonData.findBone("game/health3");
        SpineHealth.boneGameHealth5 = SpineHealth.skeletonData.findBone("game/health5");
        SpineHealth.boneGameHealth6 = SpineHealth.skeletonData.findBone("game/health6");
        SpineHealth.boneGameHealth7 = SpineHealth.skeletonData.findBone("game/health7");
        SpineHealth.boneGameHealth8 = SpineHealth.skeletonData.findBone("game/health8");
        SpineHealth.boneGameHealth9 = SpineHealth.skeletonData.findBone("game/health9");
        SpineHealth.slotGameHealth2 = SpineHealth.skeletonData.findSlot("game/health2");
        SpineHealth.slotGameHealth9 = SpineHealth.skeletonData.findSlot("game/health9");
        SpineHealth.slotGameHealth3 = SpineHealth.skeletonData.findSlot("game/health3");
        SpineHealth.slotGameHealth8 = SpineHealth.skeletonData.findSlot("game/health8");
        SpineHealth.slotGameHealth4 = SpineHealth.skeletonData.findSlot("game/health4");
        SpineHealth.slotGameHealth7 = SpineHealth.skeletonData.findSlot("game/health7");
        SpineHealth.slotGameHealth5 = SpineHealth.skeletonData.findSlot("game/health5");
        SpineHealth.slotGameHealth6 = SpineHealth.skeletonData.findSlot("game/health6");
        SpineHealth.slotHealthpath = SpineHealth.skeletonData.findSlot("healthpath");
        SpineHealth.slotHealthpath2 = SpineHealth.skeletonData.findSlot("healthpath2");
        SpineHealth.skinDefault = SpineHealth.skeletonData.findSkin("default");
        SpineInfinityBrown.skeletonData = assetManager.get("spine/infinity-brown.json");
        SpineInfinityBrown.animationData = assetManager.get("spine/infinity-brown.json-animation");
        SpineInfinityBrown.animationAnimation = SpineInfinityBrown.skeletonData.findAnimation("animation");
        SpineInfinityBrown.boneRoot = SpineInfinityBrown.skeletonData.findBone("root");
        SpineInfinityBrown.slotGameInfinity01 = SpineInfinityBrown.skeletonData.findSlot("game/infinity-01");
        SpineInfinityBrown.skinDefault = SpineInfinityBrown.skeletonData.findSkin("default");
        SpineInfinityWhite.skeletonData = assetManager.get("spine/infinity-white.json");
        SpineInfinityWhite.animationData = assetManager.get("spine/infinity-white.json-animation");
        SpineInfinityWhite.animationAnimation = SpineInfinityWhite.skeletonData.findAnimation("animation");
        SpineInfinityWhite.boneRoot = SpineInfinityWhite.skeletonData.findBone("root");
        SpineInfinityWhite.slotGameInfinity01 = SpineInfinityWhite.skeletonData.findSlot("game/infinity-01");
        SpineInfinityWhite.skinDefault = SpineInfinityWhite.skeletonData.findSkin("default");
        SpineInfinity.skeletonData = assetManager.get("spine/infinity.json");
        SpineInfinity.animationData = assetManager.get("spine/infinity.json-animation");
        SpineInfinity.animationAnimation = SpineInfinity.skeletonData.findAnimation("animation");
        SpineInfinity.boneRoot = SpineInfinity.skeletonData.findBone("root");
        SpineInfinity.slotGameInfinity01 = SpineInfinity.skeletonData.findSlot("game/infinity-01");
        SpineInfinity.skinDefault = SpineInfinity.skeletonData.findSkin("default");
        SpineLibgdx.skeletonData = assetManager.get("spine/libgdx.json");
        SpineLibgdx.animationData = assetManager.get("spine/libgdx.json-animation");
        SpineLibgdx.animationAnimation = SpineLibgdx.skeletonData.findAnimation("animation");
        SpineLibgdx.animationStand = SpineLibgdx.skeletonData.findAnimation("stand");
        SpineLibgdx.eventSfxLibgdx01 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx01");
        SpineLibgdx.eventSfxLibgdx02 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx02");
        SpineLibgdx.eventSfxLibgdx03 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx03");
        SpineLibgdx.eventSfxLibgdx04 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx04");
        SpineLibgdx.eventSfxLibgdx05 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx05");
        SpineLibgdx.eventSfxLibgdx06 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx06");
        SpineLibgdx.eventSfxLibgdx07 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx07");
        SpineLibgdx.eventSfxLibgdx08 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx08");
        SpineLibgdx.eventSfxLibgdx09 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx09");
        SpineLibgdx.eventSfxLibgdx10 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx10");
        SpineLibgdx.eventSfxLibgdx11 = SpineLibgdx.skeletonData.findEvent("sfx/libgdx11");
        SpineLibgdx.boneRoot = SpineLibgdx.skeletonData.findBone("root");
        SpineLibgdx.boneLogoLibgdxMetalBoard = SpineLibgdx.skeletonData.findBone("logo/libgdx_metal-board");
        SpineLibgdx.boneLogoLibgdxBall = SpineLibgdx.skeletonData.findBone("logo/libgdx_ball");
        SpineLibgdx.boneLogoLibgdxFist = SpineLibgdx.skeletonData.findBone("logo/libgdx_fist");
        SpineLibgdx.boneLogoLibgdxL = SpineLibgdx.skeletonData.findBone("logo/libgdx-l");
        SpineLibgdx.boneLogoLibgdxDomino = SpineLibgdx.skeletonData.findBone("logo/libgdx_domino");
        SpineLibgdx.boneLogoLibgdxDomino2 = SpineLibgdx.skeletonData.findBone("logo/libgdx_domino2");
        SpineLibgdx.boneLogoLibgdxDomino3 = SpineLibgdx.skeletonData.findBone("logo/libgdx_domino3");
        SpineLibgdx.boneLogoLibgdxDomino4 = SpineLibgdx.skeletonData.findBone("logo/libgdx_domino4");
        SpineLibgdx.boneLogoLibgdxDomino5 = SpineLibgdx.skeletonData.findBone("logo/libgdx_domino5");
        SpineLibgdx.boneLogoLibgdxIBody = SpineLibgdx.skeletonData.findBone("logo/libgdx_i-body");
        SpineLibgdx.boneLogoLibgdxIDot = SpineLibgdx.skeletonData.findBone("logo/libgdx_i-dot");
        SpineLibgdx.boneLogoLibgdxBoard = SpineLibgdx.skeletonData.findBone("logo/libgdx_board");
        SpineLibgdx.boneLogoLibgdxCup2 = SpineLibgdx.skeletonData.findBone("logo/libgdx_cup2");
        SpineLibgdx.boneLogoLibgdxMarble = SpineLibgdx.skeletonData.findBone("logo/libgdx_marble");
        SpineLibgdx.boneLogoLibgdxCup = SpineLibgdx.skeletonData.findBone("logo/libgdx_cup");
        SpineLibgdx.boneLogoLibgdxMarble2 = SpineLibgdx.skeletonData.findBone("logo/libgdx_marble2");
        SpineLibgdx.boneLogoLibgdxMarble3 = SpineLibgdx.skeletonData.findBone("logo/libgdx_marble3");
        SpineLibgdx.boneLogoLibgdxAnvil = SpineLibgdx.skeletonData.findBone("logo/libgdx_anvil");
        SpineLibgdx.boneLogoLibgdxB = SpineLibgdx.skeletonData.findBone("logo/libgdx-b");
        SpineLibgdx.boneLogoBalloon = SpineLibgdx.skeletonData.findBone("logo/balloon");
        SpineLibgdx.boneLogoLibgdxKickstand = SpineLibgdx.skeletonData.findBone("logo/libgdx_kickstand");
        SpineLibgdx.boneLogoLibgdxCog = SpineLibgdx.skeletonData.findBone("logo/libgdx_cog");
        SpineLibgdx.boneLogoLibgdxCogSmall = SpineLibgdx.skeletonData.findBone("logo/libgdx_cog-small");
        SpineLibgdx.boneLogoLibgdxD = SpineLibgdx.skeletonData.findBone("logo/libgdx-d");
        SpineLibgdx.boneLogoLibgdxKnife = SpineLibgdx.skeletonData.findBone("logo/libgdx_knife");
        SpineLibgdx.boneLogoLibgdxX = SpineLibgdx.skeletonData.findBone("logo/libgdx-x");
        SpineLibgdx.boneLogoLibgdxG = SpineLibgdx.skeletonData.findBone("logo/libgdx-g");
        SpineLibgdx.boneLogoLibgdxBlood = SpineLibgdx.skeletonData.findBone("logo/libgdx-blood");
        SpineLibgdx.slotLogoLibgdxBlood = SpineLibgdx.skeletonData.findSlot("logo/libgdx-blood");
        SpineLibgdx.slotLogoLibgdxL = SpineLibgdx.skeletonData.findSlot("logo/libgdx-l");
        SpineLibgdx.slotLogoLibgdxI = SpineLibgdx.skeletonData.findSlot("logo/libgdx-i");
        SpineLibgdx.slotLogoLibgdxB = SpineLibgdx.skeletonData.findSlot("logo/libgdx-b");
        SpineLibgdx.slotLogoLibgdxD = SpineLibgdx.skeletonData.findSlot("logo/libgdx-d");
        SpineLibgdx.slotLogoLibgdxX = SpineLibgdx.skeletonData.findSlot("logo/libgdx-x");
        SpineLibgdx.slotLogoLibgdxBall = SpineLibgdx.skeletonData.findSlot("logo/libgdx_ball");
        SpineLibgdx.slotLogoLibgdxBoard = SpineLibgdx.skeletonData.findSlot("logo/libgdx_board");
        SpineLibgdx.slotClip = SpineLibgdx.skeletonData.findSlot("clip");
        SpineLibgdx.slotLogoLibgdxChain = SpineLibgdx.skeletonData.findSlot("logo/libgdx_chain");
        SpineLibgdx.slotLogoLibgdxChain2 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_chain2");
        SpineLibgdx.slotLogoLibgdxG = SpineLibgdx.skeletonData.findSlot("logo/libgdx-g");
        SpineLibgdx.slotLogoLibgdxCogSmall = SpineLibgdx.skeletonData.findSlot("logo/libgdx_cog-small");
        SpineLibgdx.slotLogoLibgdxCog = SpineLibgdx.skeletonData.findSlot("logo/libgdx_cog");
        SpineLibgdx.slotLogoLibgdxMarble = SpineLibgdx.skeletonData.findSlot("logo/libgdx_marble");
        SpineLibgdx.slotLogoLibgdxMarble2 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_marble2");
        SpineLibgdx.slotLogoLibgdxMarble3 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_marble3");
        SpineLibgdx.slotLogoLibgdxIDot = SpineLibgdx.skeletonData.findSlot("logo/libgdx_i-dot");
        SpineLibgdx.slotLogoLibgdxCup = SpineLibgdx.skeletonData.findSlot("logo/libgdx_cup");
        SpineLibgdx.slotLogoLibgdxCup2 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_cup2");
        SpineLibgdx.slotLogoLibgdxFist = SpineLibgdx.skeletonData.findSlot("logo/libgdx_fist");
        SpineLibgdx.slotLogoLibgdxDomino = SpineLibgdx.skeletonData.findSlot("logo/libgdx_domino");
        SpineLibgdx.slotLogoLibgdxDomino2 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_domino2");
        SpineLibgdx.slotLogoLibgdxDomino3 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_domino3");
        SpineLibgdx.slotLogoLibgdxDomino4 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_domino4");
        SpineLibgdx.slotLogoLibgdxDomino5 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_domino5");
        SpineLibgdx.slotLogoLibgdxIBody = SpineLibgdx.skeletonData.findSlot("logo/libgdx_i-body");
        SpineLibgdx.slotLogoLibgdxKnife = SpineLibgdx.skeletonData.findSlot("logo/libgdx_knife");
        SpineLibgdx.slotLogoLibgdxLighter = SpineLibgdx.skeletonData.findSlot("logo/libgdx_lighter");
        SpineLibgdx.slotLogoLibgdxLighterLight = SpineLibgdx.skeletonData.findSlot("logo/libgdx_lighter-light");
        SpineLibgdx.slotLogoLibgdxLog = SpineLibgdx.skeletonData.findSlot("logo/libgdx_log");
        SpineLibgdx.slotLogoLibgdxMetalBoard = SpineLibgdx.skeletonData.findSlot("logo/libgdx_metal-board");
        SpineLibgdx.slotLogoLibgdxRope = SpineLibgdx.skeletonData.findSlot("logo/libgdx_rope");
        SpineLibgdx.slotLogoLibgdxAnvil = SpineLibgdx.skeletonData.findSlot("logo/libgdx_anvil");
        SpineLibgdx.slotLogoLibgdxShoe = SpineLibgdx.skeletonData.findSlot("logo/libgdx_shoe");
        SpineLibgdx.slotLogoBalloon = SpineLibgdx.skeletonData.findSlot("logo/balloon");
        SpineLibgdx.slotLogoLibgdxKickstand = SpineLibgdx.skeletonData.findSlot("logo/libgdx_kickstand");
        SpineLibgdx.slotLogoLibgdxBoard2 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_board2");
        SpineLibgdx.slotLogoLibgdxBoard3 = SpineLibgdx.skeletonData.findSlot("logo/libgdx_board3");
        SpineLibgdx.skinDefault = SpineLibgdx.skeletonData.findSkin("default");
        SpineLightningReverseRed.skeletonData = assetManager.get("spine/lightning-reverse-red.json");
        SpineLightningReverseRed.animationData = assetManager.get("spine/lightning-reverse-red.json-animation");
        SpineLightningReverseRed.animationAnimation = SpineLightningReverseRed.skeletonData.findAnimation("animation");
        SpineLightningReverseRed.boneRoot = SpineLightningReverseRed.skeletonData.findBone("root");
        SpineLightningReverseRed.slotGameLightning09 = SpineLightningReverseRed.skeletonData.findSlot("game/lightning-09");
        SpineLightningReverseRed.skinDefault = SpineLightningReverseRed.skeletonData.findSkin("default");
        SpineLightningReverseWhite.skeletonData = assetManager.get("spine/lightning-reverse-white.json");
        SpineLightningReverseWhite.animationData = assetManager.get("spine/lightning-reverse-white.json-animation");
        SpineLightningReverseWhite.animationAnimation = SpineLightningReverseWhite.skeletonData.findAnimation("animation");
        SpineLightningReverseWhite.boneRoot = SpineLightningReverseWhite.skeletonData.findBone("root");
        SpineLightningReverseWhite.slotGameLightning09 = SpineLightningReverseWhite.skeletonData.findSlot("game/lightning-09");
        SpineLightningReverseWhite.skinDefault = SpineLightningReverseWhite.skeletonData.findSkin("default");
        SpineLightningWhite.skeletonData = assetManager.get("spine/lightning-white.json");
        SpineLightningWhite.animationData = assetManager.get("spine/lightning-white.json-animation");
        SpineLightningWhite.animationAnimation = SpineLightningWhite.skeletonData.findAnimation("animation");
        SpineLightningWhite.boneRoot = SpineLightningWhite.skeletonData.findBone("root");
        SpineLightningWhite.slotGameLightning09 = SpineLightningWhite.skeletonData.findSlot("game/lightning-09");
        SpineLightningWhite.skinDefault = SpineLightningWhite.skeletonData.findSkin("default");
        SpineLightning.skeletonData = assetManager.get("spine/lightning.json");
        SpineLightning.animationData = assetManager.get("spine/lightning.json-animation");
        SpineLightning.animationAnimation = SpineLightning.skeletonData.findAnimation("animation");
        SpineLightning.boneRoot = SpineLightning.skeletonData.findBone("root");
        SpineLightning.slotGameLightning09 = SpineLightning.skeletonData.findSlot("game/lightning-09");
        SpineLightning.skinDefault = SpineLightning.skeletonData.findSkin("default");
        SpinePing.skeletonData = assetManager.get("spine/ping.json");
        SpinePing.animationData = assetManager.get("spine/ping.json-animation");
        SpinePing.animationAnimation = SpinePing.skeletonData.findAnimation("animation");
        SpinePing.boneRoot = SpinePing.skeletonData.findBone("root");
        SpinePing.boneGamePing = SpinePing.skeletonData.findBone("game/ping");
        SpinePing.slotGamePing = SpinePing.skeletonData.findSlot("game/ping");
        SpinePing.skinDefault = SpinePing.skeletonData.findSkin("default");
        SpinePortalBlue.skeletonData = assetManager.get("spine/portal-blue.json");
        SpinePortalBlue.animationData = assetManager.get("spine/portal-blue.json-animation");
        SpinePortalBlue.animationAnimation = SpinePortalBlue.skeletonData.findAnimation("animation");
        SpinePortalBlue.boneRoot = SpinePortalBlue.skeletonData.findBone("root");
        SpinePortalBlue.slotGamePortal01 = SpinePortalBlue.skeletonData.findSlot("game/portal-01");
        SpinePortalBlue.skinDefault = SpinePortalBlue.skeletonData.findSkin("default");
        SpinePortalGreen.skeletonData = assetManager.get("spine/portal-green.json");
        SpinePortalGreen.animationData = assetManager.get("spine/portal-green.json-animation");
        SpinePortalGreen.animationAnimation = SpinePortalGreen.skeletonData.findAnimation("animation");
        SpinePortalGreen.boneRoot = SpinePortalGreen.skeletonData.findBone("root");
        SpinePortalGreen.slotGamePortal01 = SpinePortalGreen.skeletonData.findSlot("game/portal-01");
        SpinePortalGreen.skinDefault = SpinePortalGreen.skeletonData.findSkin("default");
        SpinePortalRed.skeletonData = assetManager.get("spine/portal-red.json");
        SpinePortalRed.animationData = assetManager.get("spine/portal-red.json-animation");
        SpinePortalRed.animationAnimation = SpinePortalRed.skeletonData.findAnimation("animation");
        SpinePortalRed.boneRoot = SpinePortalRed.skeletonData.findBone("root");
        SpinePortalRed.slotGamePortal01 = SpinePortalRed.skeletonData.findSlot("game/portal-01");
        SpinePortalRed.skinDefault = SpinePortalRed.skeletonData.findSkin("default");
        SpinePortal.skeletonData = assetManager.get("spine/portal.json");
        SpinePortal.animationData = assetManager.get("spine/portal.json-animation");
        SpinePortal.animationAnimation = SpinePortal.skeletonData.findAnimation("animation");
        SpinePortal.boneRoot = SpinePortal.skeletonData.findBone("root");
        SpinePortal.slotGamePortal01 = SpinePortal.skeletonData.findSlot("game/portal-01");
        SpinePortal.skinDefault = SpinePortal.skeletonData.findSkin("default");
        SpineRay3k.skeletonData = assetManager.get("spine/ray3k.json");
        SpineRay3k.animationData = assetManager.get("spine/ray3k.json-animation");
        SpineRay3k.animationAnimation = SpineRay3k.skeletonData.findAnimation("animation");
        SpineRay3k.animationStand = SpineRay3k.skeletonData.findAnimation("stand");
        SpineRay3k.eventSfxRay3k01 = SpineRay3k.skeletonData.findEvent("sfx/ray3k01");
        SpineRay3k.eventSfxRay3k02 = SpineRay3k.skeletonData.findEvent("sfx/ray3k02");
        SpineRay3k.eventSfxRay3k03 = SpineRay3k.skeletonData.findEvent("sfx/ray3k03");
        SpineRay3k.eventSfxRay3k04 = SpineRay3k.skeletonData.findEvent("sfx/ray3k04");
        SpineRay3k.boneRoot = SpineRay3k.skeletonData.findBone("root");
        SpineRay3k.boneLogoRay3kShadow2 = SpineRay3k.skeletonData.findBone("logo/ray3k-shadow2");
        SpineRay3k.boneLogoRay3kShadow = SpineRay3k.skeletonData.findBone("logo/ray3k-shadow");
        SpineRay3k.boneLogoRay3kNeck = SpineRay3k.skeletonData.findBone("logo/ray3k-neck");
        SpineRay3k.boneLogoRay3kNeck2 = SpineRay3k.skeletonData.findBone("logo/ray3k-neck2");
        SpineRay3k.boneLogoRay3kA = SpineRay3k.skeletonData.findBone("logo/ray3k-a");
        SpineRay3k.boneLogoRay3kLamp00 = SpineRay3k.skeletonData.findBone("logo/ray3k-lamp-00");
        SpineRay3k.slotBg = SpineRay3k.skeletonData.findSlot("bg");
        SpineRay3k.slotLogoRay3kShadow = SpineRay3k.skeletonData.findSlot("logo/ray3k-shadow");
        SpineRay3k.slotLogoRay3kShadow2 = SpineRay3k.skeletonData.findSlot("logo/ray3k-shadow2");
        SpineRay3k.slotLogoRay3kShadow3 = SpineRay3k.skeletonData.findSlot("logo/ray3k-shadow3");
        SpineRay3k.slotLogoRay3kShadow4 = SpineRay3k.skeletonData.findSlot("logo/ray3k-shadow4");
        SpineRay3k.slotLogoRay3kShadow5 = SpineRay3k.skeletonData.findSlot("logo/ray3k-shadow5");
        SpineRay3k.slotLogoRay3kShadow6 = SpineRay3k.skeletonData.findSlot("logo/ray3k-shadow6");
        SpineRay3k.slotLogoRay3kR = SpineRay3k.skeletonData.findSlot("logo/ray3k-r");
        SpineRay3k.slotLogoRay3kA = SpineRay3k.skeletonData.findSlot("logo/ray3k-a");
        SpineRay3k.slotLogoRay3kY = SpineRay3k.skeletonData.findSlot("logo/ray3k-y");
        SpineRay3k.slotLogoRay3k3 = SpineRay3k.skeletonData.findSlot("logo/ray3k-3");
        SpineRay3k.slotLogoRay3kK = SpineRay3k.skeletonData.findSlot("logo/ray3k-k");
        SpineRay3k.slotLogoRay3kBase = SpineRay3k.skeletonData.findSlot("logo/ray3k-base");
        SpineRay3k.slotLogoRay3kNeck = SpineRay3k.skeletonData.findSlot("logo/ray3k-neck");
        SpineRay3k.slotLogoRay3kLamp00 = SpineRay3k.skeletonData.findSlot("logo/ray3k-lamp-00");
        SpineRay3k.skinDefault = SpineRay3k.skeletonData.findSkin("default");
        SpineShieldRed.skeletonData = assetManager.get("spine/shield-red.json");
        SpineShieldRed.animationData = assetManager.get("spine/shield-red.json-animation");
        SpineShieldRed.animationAnimation = SpineShieldRed.skeletonData.findAnimation("animation");
        SpineShieldRed.boneRoot = SpineShieldRed.skeletonData.findBone("root");
        SpineShieldRed.boneGameShield = SpineShieldRed.skeletonData.findBone("game/shield");
        SpineShieldRed.slotGameShield = SpineShieldRed.skeletonData.findSlot("game/shield");
        SpineShieldRed.skinDefault = SpineShieldRed.skeletonData.findSkin("default");
        SpineShieldYellow.skeletonData = assetManager.get("spine/shield-yellow.json");
        SpineShieldYellow.animationData = assetManager.get("spine/shield-yellow.json-animation");
        SpineShieldYellow.animationAnimation = SpineShieldYellow.skeletonData.findAnimation("animation");
        SpineShieldYellow.boneRoot = SpineShieldYellow.skeletonData.findBone("root");
        SpineShieldYellow.boneGameShield = SpineShieldYellow.skeletonData.findBone("game/shield");
        SpineShieldYellow.slotGameShield = SpineShieldYellow.skeletonData.findSlot("game/shield");
        SpineShieldYellow.skinDefault = SpineShieldYellow.skeletonData.findSkin("default");
        SpineShield.skeletonData = assetManager.get("spine/shield.json");
        SpineShield.animationData = assetManager.get("spine/shield.json-animation");
        SpineShield.animationAnimation = SpineShield.skeletonData.findAnimation("animation");
        SpineShield.boneRoot = SpineShield.skeletonData.findBone("root");
        SpineShield.boneGameShield = SpineShield.skeletonData.findBone("game/shield");
        SpineShield.slotGameShield = SpineShield.skeletonData.findSlot("game/shield");
        SpineShield.skinDefault = SpineShield.skeletonData.findSkin("default");
        SpineShot3.skeletonData = assetManager.get("spine/shot-3.json");
        SpineShot3.animationData = assetManager.get("spine/shot-3.json-animation");
        SpineShot3.animationAnimation = SpineShot3.skeletonData.findAnimation("animation");
        SpineShot3.boneRoot = SpineShot3.skeletonData.findBone("root");
        SpineShot3.slotGameShot01 = SpineShot3.skeletonData.findSlot("game/shot-01");
        SpineShot3.slotGameShot02 = SpineShot3.skeletonData.findSlot("game/shot-02");
        SpineShot3.slotGameShot03 = SpineShot3.skeletonData.findSlot("game/shot-03");
        SpineShot3.skinDefault = SpineShot3.skeletonData.findSkin("default");
        SpineShotBlue.skeletonData = assetManager.get("spine/shot-blue.json");
        SpineShotBlue.animationData = assetManager.get("spine/shot-blue.json-animation");
        SpineShotBlue.animationAnimation = SpineShotBlue.skeletonData.findAnimation("animation");
        SpineShotBlue.boneRoot = SpineShotBlue.skeletonData.findBone("root");
        SpineShotBlue.slotGameShot01 = SpineShotBlue.skeletonData.findSlot("game/shot-01");
        SpineShotBlue.skinDefault = SpineShotBlue.skeletonData.findSkin("default");
        SpineShotGray.skeletonData = assetManager.get("spine/shot-gray.json");
        SpineShotGray.animationData = assetManager.get("spine/shot-gray.json-animation");
        SpineShotGray.animationAnimation = SpineShotGray.skeletonData.findAnimation("animation");
        SpineShotGray.boneRoot = SpineShotGray.skeletonData.findBone("root");
        SpineShotGray.slotGameShot01 = SpineShotGray.skeletonData.findSlot("game/shot-01");
        SpineShotGray.skinDefault = SpineShotGray.skeletonData.findSkin("default");
        SpineShotGreen.skeletonData = assetManager.get("spine/shot-green.json");
        SpineShotGreen.animationData = assetManager.get("spine/shot-green.json-animation");
        SpineShotGreen.animationAnimation = SpineShotGreen.skeletonData.findAnimation("animation");
        SpineShotGreen.boneRoot = SpineShotGreen.skeletonData.findBone("root");
        SpineShotGreen.slotGameShot01 = SpineShotGreen.skeletonData.findSlot("game/shot-01");
        SpineShotGreen.skinDefault = SpineShotGreen.skeletonData.findSkin("default");
        SpineShotRed.skeletonData = assetManager.get("spine/shot-red.json");
        SpineShotRed.animationData = assetManager.get("spine/shot-red.json-animation");
        SpineShotRed.animationAnimation = SpineShotRed.skeletonData.findAnimation("animation");
        SpineShotRed.boneRoot = SpineShotRed.skeletonData.findBone("root");
        SpineShotRed.slotGameShot01 = SpineShotRed.skeletonData.findSlot("game/shot-01");
        SpineShotRed.skinDefault = SpineShotRed.skeletonData.findSkin("default");
        SpineShotWhite.skeletonData = assetManager.get("spine/shot-white.json");
        SpineShotWhite.animationData = assetManager.get("spine/shot-white.json-animation");
        SpineShotWhite.animationAnimation = SpineShotWhite.skeletonData.findAnimation("animation");
        SpineShotWhite.boneRoot = SpineShotWhite.skeletonData.findBone("root");
        SpineShotWhite.slotGameShot01 = SpineShotWhite.skeletonData.findSlot("game/shot-01");
        SpineShotWhite.skinDefault = SpineShotWhite.skeletonData.findSkin("default");
        SpineShot.skeletonData = assetManager.get("spine/shot.json");
        SpineShot.animationData = assetManager.get("spine/shot.json-animation");
        SpineShot.animationAnimation = SpineShot.skeletonData.findAnimation("animation");
        SpineShot.boneRoot = SpineShot.skeletonData.findBone("root");
        SpineShot.slotGameShot01 = SpineShot.skeletonData.findSlot("game/shot-01");
        SpineShot.skinDefault = SpineShot.skeletonData.findSkin("default");
        SpineShotgunOrange.skeletonData = assetManager.get("spine/shotgun-orange.json");
        SpineShotgunOrange.animationData = assetManager.get("spine/shotgun-orange.json-animation");
        SpineShotgunOrange.animationAnimation = SpineShotgunOrange.skeletonData.findAnimation("animation");
        SpineShotgunOrange.boneRoot = SpineShotgunOrange.skeletonData.findBone("root");
        SpineShotgunOrange.slotGameShot01 = SpineShotgunOrange.skeletonData.findSlot("game/shot-01");
        SpineShotgunOrange.slotGameShot02 = SpineShotgunOrange.skeletonData.findSlot("game/shot-02");
        SpineShotgunOrange.slotGameShot03 = SpineShotgunOrange.skeletonData.findSlot("game/shot-03");
        SpineShotgunOrange.slotGameShot04 = SpineShotgunOrange.skeletonData.findSlot("game/shot-04");
        SpineShotgunOrange.slotGameShot05 = SpineShotgunOrange.skeletonData.findSlot("game/shot-05");
        SpineShotgunOrange.skinDefault = SpineShotgunOrange.skeletonData.findSkin("default");
        SpineShotgun.skeletonData = assetManager.get("spine/shotgun.json");
        SpineShotgun.animationData = assetManager.get("spine/shotgun.json-animation");
        SpineShotgun.animationAnimation = SpineShotgun.skeletonData.findAnimation("animation");
        SpineShotgun.boneRoot = SpineShotgun.skeletonData.findBone("root");
        SpineShotgun.slotGameShot01 = SpineShotgun.skeletonData.findSlot("game/shot-01");
        SpineShotgun.slotGameShot02 = SpineShotgun.skeletonData.findSlot("game/shot-02");
        SpineShotgun.slotGameShot03 = SpineShotgun.skeletonData.findSlot("game/shot-03");
        SpineShotgun.slotGameShot04 = SpineShotgun.skeletonData.findSlot("game/shot-04");
        SpineShotgun.slotGameShot05 = SpineShotgun.skeletonData.findSlot("game/shot-05");
        SpineShotgun.skinDefault = SpineShotgun.skeletonData.findSkin("default");
        SpineSlashBlue.skeletonData = assetManager.get("spine/slash-blue.json");
        SpineSlashBlue.animationData = assetManager.get("spine/slash-blue.json-animation");
        SpineSlashBlue.animationAnimation = SpineSlashBlue.skeletonData.findAnimation("animation");
        SpineSlashBlue.boneRoot = SpineSlashBlue.skeletonData.findBone("root");
        SpineSlashBlue.slotGameSlash01 = SpineSlashBlue.skeletonData.findSlot("game/slash-01");
        SpineSlashBlue.skinDefault = SpineSlashBlue.skeletonData.findSkin("default");
        SpineSlashWhiteUp.skeletonData = assetManager.get("spine/slash-white-up.json");
        SpineSlashWhiteUp.animationData = assetManager.get("spine/slash-white-up.json-animation");
        SpineSlashWhiteUp.animationAnimation = SpineSlashWhiteUp.skeletonData.findAnimation("animation");
        SpineSlashWhiteUp.boneRoot = SpineSlashWhiteUp.skeletonData.findBone("root");
        SpineSlashWhiteUp.slotGameSlash01 = SpineSlashWhiteUp.skeletonData.findSlot("game/slash-01");
        SpineSlashWhiteUp.skinDefault = SpineSlashWhiteUp.skeletonData.findSkin("default");
        SpineSlashWhite.skeletonData = assetManager.get("spine/slash-white.json");
        SpineSlashWhite.animationData = assetManager.get("spine/slash-white.json-animation");
        SpineSlashWhite.animationAnimation = SpineSlashWhite.skeletonData.findAnimation("animation");
        SpineSlashWhite.boneRoot = SpineSlashWhite.skeletonData.findBone("root");
        SpineSlashWhite.slotGameSlash01 = SpineSlashWhite.skeletonData.findSlot("game/slash-01");
        SpineSlashWhite.skinDefault = SpineSlashWhite.skeletonData.findSkin("default");
        SpineSlashYellow.skeletonData = assetManager.get("spine/slash-yellow.json");
        SpineSlashYellow.animationData = assetManager.get("spine/slash-yellow.json-animation");
        SpineSlashYellow.animationAnimation = SpineSlashYellow.skeletonData.findAnimation("animation");
        SpineSlashYellow.boneRoot = SpineSlashYellow.skeletonData.findBone("root");
        SpineSlashYellow.slotGameSlash01 = SpineSlashYellow.skeletonData.findSlot("game/slash-01");
        SpineSlashYellow.skinDefault = SpineSlashYellow.skeletonData.findSkin("default");
        SpineSlash.skeletonData = assetManager.get("spine/slash.json");
        SpineSlash.animationData = assetManager.get("spine/slash.json-animation");
        SpineSlash.animationAnimation = SpineSlash.skeletonData.findAnimation("animation");
        SpineSlash.boneRoot = SpineSlash.skeletonData.findBone("root");
        SpineSlash.slotGameSlash01 = SpineSlash.skeletonData.findSlot("game/slash-01");
        SpineSlash.skinDefault = SpineSlash.skeletonData.findSkin("default");
        SpineSlashx3.skeletonData = assetManager.get("spine/slashx3.json");
        SpineSlashx3.animationData = assetManager.get("spine/slashx3.json-animation");
        SpineSlashx3.animationAnimation = SpineSlashx3.skeletonData.findAnimation("animation");
        SpineSlashx3.boneRoot = SpineSlashx3.skeletonData.findBone("root");
        SpineSlashx3.slotGameSlash01 = SpineSlashx3.skeletonData.findSlot("game/slash-01");
        SpineSlashx3.slotGameSlash02 = SpineSlashx3.skeletonData.findSlot("game/slash-02");
        SpineSlashx3.slotGameSlash03 = SpineSlashx3.skeletonData.findSlot("game/slash-03");
        SpineSlashx3.skinDefault = SpineSlashx3.skeletonData.findSkin("default");
        SpineSparkWhite.skeletonData = assetManager.get("spine/spark-white.json");
        SpineSparkWhite.animationData = assetManager.get("spine/spark-white.json-animation");
        SpineSparkWhite.animationAnimation = SpineSparkWhite.skeletonData.findAnimation("animation");
        SpineSparkWhite.boneRoot = SpineSparkWhite.skeletonData.findBone("root");
        SpineSparkWhite.slotGameSpark01 = SpineSparkWhite.skeletonData.findSlot("game/spark-01");
        SpineSparkWhite.skinDefault = SpineSparkWhite.skeletonData.findSkin("default");
        SpineSpark.skeletonData = assetManager.get("spine/spark.json");
        SpineSpark.animationData = assetManager.get("spine/spark.json-animation");
        SpineSpark.animationAnimation = SpineSpark.skeletonData.findAnimation("animation");
        SpineSpark.boneRoot = SpineSpark.skeletonData.findBone("root");
        SpineSpark.slotGameSpark01 = SpineSpark.skeletonData.findSlot("game/spark-01");
        SpineSpark.skinDefault = SpineSpark.skeletonData.findSkin("default");
        SpineSplashBlue.skeletonData = assetManager.get("spine/splash-blue.json");
        SpineSplashBlue.animationData = assetManager.get("spine/splash-blue.json-animation");
        SpineSplashBlue.animationAnimation = SpineSplashBlue.skeletonData.findAnimation("animation");
        SpineSplashBlue.boneRoot = SpineSplashBlue.skeletonData.findBone("root");
        SpineSplashBlue.slotGameSplash01 = SpineSplashBlue.skeletonData.findSlot("game/splash-01");
        SpineSplashBlue.skinDefault = SpineSplashBlue.skeletonData.findSkin("default");
        SpineSplashBrown.skeletonData = assetManager.get("spine/splash-brown.json");
        SpineSplashBrown.animationData = assetManager.get("spine/splash-brown.json-animation");
        SpineSplashBrown.animationAnimation = SpineSplashBrown.skeletonData.findAnimation("animation");
        SpineSplashBrown.boneRoot = SpineSplashBrown.skeletonData.findBone("root");
        SpineSplashBrown.slotGameSplash01 = SpineSplashBrown.skeletonData.findSlot("game/splash-01");
        SpineSplashBrown.skinDefault = SpineSplashBrown.skeletonData.findSkin("default");
        SpineSplashPurple.skeletonData = assetManager.get("spine/splash-purple.json");
        SpineSplashPurple.animationData = assetManager.get("spine/splash-purple.json-animation");
        SpineSplashPurple.animationAnimation = SpineSplashPurple.skeletonData.findAnimation("animation");
        SpineSplashPurple.boneRoot = SpineSplashPurple.skeletonData.findBone("root");
        SpineSplashPurple.slotGameSplash01 = SpineSplashPurple.skeletonData.findSlot("game/splash-01");
        SpineSplashPurple.skinDefault = SpineSplashPurple.skeletonData.findSkin("default");
        SpineSplashWhite.skeletonData = assetManager.get("spine/splash-white.json");
        SpineSplashWhite.animationData = assetManager.get("spine/splash-white.json-animation");
        SpineSplashWhite.animationAnimation = SpineSplashWhite.skeletonData.findAnimation("animation");
        SpineSplashWhite.boneRoot = SpineSplashWhite.skeletonData.findBone("root");
        SpineSplashWhite.slotGameSplash01 = SpineSplashWhite.skeletonData.findSlot("game/splash-01");
        SpineSplashWhite.skinDefault = SpineSplashWhite.skeletonData.findSkin("default");
        SpineSplash.skeletonData = assetManager.get("spine/splash.json");
        SpineSplash.animationData = assetManager.get("spine/splash.json-animation");
        SpineSplash.animationAnimation = SpineSplash.skeletonData.findAnimation("animation");
        SpineSplash.boneRoot = SpineSplash.skeletonData.findBone("root");
        SpineSplash.slotGameSplash01 = SpineSplash.skeletonData.findSlot("game/splash-01");
        SpineSplash.skinDefault = SpineSplash.skeletonData.findSkin("default");
        SpineStrikeBlue.skeletonData = assetManager.get("spine/strike-blue.json");
        SpineStrikeBlue.animationData = assetManager.get("spine/strike-blue.json-animation");
        SpineStrikeBlue.animationAnimation = SpineStrikeBlue.skeletonData.findAnimation("animation");
        SpineStrikeBlue.boneRoot = SpineStrikeBlue.skeletonData.findBone("root");
        SpineStrikeBlue.boneGameStrike01 = SpineStrikeBlue.skeletonData.findBone("game/strike-01");
        SpineStrikeBlue.slotGameStrike01 = SpineStrikeBlue.skeletonData.findSlot("game/strike-01");
        SpineStrikeBlue.skinDefault = SpineStrikeBlue.skeletonData.findSkin("default");
        SpineStrikeDownWhite.skeletonData = assetManager.get("spine/strike-down-white.json");
        SpineStrikeDownWhite.animationData = assetManager.get("spine/strike-down-white.json-animation");
        SpineStrikeDownWhite.animationAnimation = SpineStrikeDownWhite.skeletonData.findAnimation("animation");
        SpineStrikeDownWhite.boneRoot = SpineStrikeDownWhite.skeletonData.findBone("root");
        SpineStrikeDownWhite.boneGameStrike01 = SpineStrikeDownWhite.skeletonData.findBone("game/strike-01");
        SpineStrikeDownWhite.slotGameStrike01 = SpineStrikeDownWhite.skeletonData.findSlot("game/strike-01");
        SpineStrikeDownWhite.skinDefault = SpineStrikeDownWhite.skeletonData.findSkin("default");
        SpineStrikeDown.skeletonData = assetManager.get("spine/strike-down.json");
        SpineStrikeDown.animationData = assetManager.get("spine/strike-down.json-animation");
        SpineStrikeDown.animationAnimation = SpineStrikeDown.skeletonData.findAnimation("animation");
        SpineStrikeDown.boneRoot = SpineStrikeDown.skeletonData.findBone("root");
        SpineStrikeDown.boneGameStrike01 = SpineStrikeDown.skeletonData.findBone("game/strike-01");
        SpineStrikeDown.slotGameStrike01 = SpineStrikeDown.skeletonData.findSlot("game/strike-01");
        SpineStrikeDown.skinDefault = SpineStrikeDown.skeletonData.findSkin("default");
        SpineStrikeGray.skeletonData = assetManager.get("spine/strike-gray.json");
        SpineStrikeGray.animationData = assetManager.get("spine/strike-gray.json-animation");
        SpineStrikeGray.animationAnimation = SpineStrikeGray.skeletonData.findAnimation("animation");
        SpineStrikeGray.boneRoot = SpineStrikeGray.skeletonData.findBone("root");
        SpineStrikeGray.boneGameStrike01 = SpineStrikeGray.skeletonData.findBone("game/strike-01");
        SpineStrikeGray.slotGameStrike01 = SpineStrikeGray.skeletonData.findSlot("game/strike-01");
        SpineStrikeGray.skinDefault = SpineStrikeGray.skeletonData.findSkin("default");
        SpineStrikeGreen.skeletonData = assetManager.get("spine/strike-green.json");
        SpineStrikeGreen.animationData = assetManager.get("spine/strike-green.json-animation");
        SpineStrikeGreen.animationAnimation = SpineStrikeGreen.skeletonData.findAnimation("animation");
        SpineStrikeGreen.boneRoot = SpineStrikeGreen.skeletonData.findBone("root");
        SpineStrikeGreen.boneGameStrike01 = SpineStrikeGreen.skeletonData.findBone("game/strike-01");
        SpineStrikeGreen.slotGameStrike01 = SpineStrikeGreen.skeletonData.findSlot("game/strike-01");
        SpineStrikeGreen.skinDefault = SpineStrikeGreen.skeletonData.findSkin("default");
        SpineStrikeLeft.skeletonData = assetManager.get("spine/strike-left.json");
        SpineStrikeLeft.animationData = assetManager.get("spine/strike-left.json-animation");
        SpineStrikeLeft.animationAnimation = SpineStrikeLeft.skeletonData.findAnimation("animation");
        SpineStrikeLeft.boneRoot = SpineStrikeLeft.skeletonData.findBone("root");
        SpineStrikeLeft.boneGameStrike01 = SpineStrikeLeft.skeletonData.findBone("game/strike-01");
        SpineStrikeLeft.slotGameStrike01 = SpineStrikeLeft.skeletonData.findSlot("game/strike-01");
        SpineStrikeLeft.skinDefault = SpineStrikeLeft.skeletonData.findSkin("default");
        SpineStrikePurple.skeletonData = assetManager.get("spine/strike-purple.json");
        SpineStrikePurple.animationData = assetManager.get("spine/strike-purple.json-animation");
        SpineStrikePurple.animationAnimation = SpineStrikePurple.skeletonData.findAnimation("animation");
        SpineStrikePurple.boneRoot = SpineStrikePurple.skeletonData.findBone("root");
        SpineStrikePurple.boneGameStrike01 = SpineStrikePurple.skeletonData.findBone("game/strike-01");
        SpineStrikePurple.slotGameStrike01 = SpineStrikePurple.skeletonData.findSlot("game/strike-01");
        SpineStrikePurple.skinDefault = SpineStrikePurple.skeletonData.findSkin("default");
        SpineStrikeRed.skeletonData = assetManager.get("spine/strike-red.json");
        SpineStrikeRed.animationData = assetManager.get("spine/strike-red.json-animation");
        SpineStrikeRed.animationAnimation = SpineStrikeRed.skeletonData.findAnimation("animation");
        SpineStrikeRed.boneRoot = SpineStrikeRed.skeletonData.findBone("root");
        SpineStrikeRed.boneGameStrike01 = SpineStrikeRed.skeletonData.findBone("game/strike-01");
        SpineStrikeRed.slotGameStrike01 = SpineStrikeRed.skeletonData.findSlot("game/strike-01");
        SpineStrikeRed.skinDefault = SpineStrikeRed.skeletonData.findSkin("default");
        SpineStrikeRight.skeletonData = assetManager.get("spine/strike-right.json");
        SpineStrikeRight.animationData = assetManager.get("spine/strike-right.json-animation");
        SpineStrikeRight.animationAnimation = SpineStrikeRight.skeletonData.findAnimation("animation");
        SpineStrikeRight.boneRoot = SpineStrikeRight.skeletonData.findBone("root");
        SpineStrikeRight.boneGameStrike01 = SpineStrikeRight.skeletonData.findBone("game/strike-01");
        SpineStrikeRight.slotGameStrike01 = SpineStrikeRight.skeletonData.findSlot("game/strike-01");
        SpineStrikeRight.skinDefault = SpineStrikeRight.skeletonData.findSkin("default");
        SpineStrikeUpGray.skeletonData = assetManager.get("spine/strike-up-gray.json");
        SpineStrikeUpGray.animationData = assetManager.get("spine/strike-up-gray.json-animation");
        SpineStrikeUpGray.animationAnimation = SpineStrikeUpGray.skeletonData.findAnimation("animation");
        SpineStrikeUpGray.boneRoot = SpineStrikeUpGray.skeletonData.findBone("root");
        SpineStrikeUpGray.boneGameStrike01 = SpineStrikeUpGray.skeletonData.findBone("game/strike-01");
        SpineStrikeUpGray.slotGameStrike01 = SpineStrikeUpGray.skeletonData.findSlot("game/strike-01");
        SpineStrikeUpGray.skinDefault = SpineStrikeUpGray.skeletonData.findSkin("default");
        SpineStrikeUpRed.skeletonData = assetManager.get("spine/strike-up-red.json");
        SpineStrikeUpRed.animationData = assetManager.get("spine/strike-up-red.json-animation");
        SpineStrikeUpRed.animationAnimation = SpineStrikeUpRed.skeletonData.findAnimation("animation");
        SpineStrikeUpRed.boneRoot = SpineStrikeUpRed.skeletonData.findBone("root");
        SpineStrikeUpRed.boneGameStrike01 = SpineStrikeUpRed.skeletonData.findBone("game/strike-01");
        SpineStrikeUpRed.slotGameStrike01 = SpineStrikeUpRed.skeletonData.findSlot("game/strike-01");
        SpineStrikeUpRed.skinDefault = SpineStrikeUpRed.skeletonData.findSkin("default");
        SpineStrikeUp.skeletonData = assetManager.get("spine/strike-up.json");
        SpineStrikeUp.animationData = assetManager.get("spine/strike-up.json-animation");
        SpineStrikeUp.animationAnimation = SpineStrikeUp.skeletonData.findAnimation("animation");
        SpineStrikeUp.boneRoot = SpineStrikeUp.skeletonData.findBone("root");
        SpineStrikeUp.boneGameStrike01 = SpineStrikeUp.skeletonData.findBone("game/strike-01");
        SpineStrikeUp.slotGameStrike01 = SpineStrikeUp.skeletonData.findSlot("game/strike-01");
        SpineStrikeUp.skinDefault = SpineStrikeUp.skeletonData.findSkin("default");
        SpineStrikeWhite.skeletonData = assetManager.get("spine/strike-white.json");
        SpineStrikeWhite.animationData = assetManager.get("spine/strike-white.json-animation");
        SpineStrikeWhite.animationAnimation = SpineStrikeWhite.skeletonData.findAnimation("animation");
        SpineStrikeWhite.boneRoot = SpineStrikeWhite.skeletonData.findBone("root");
        SpineStrikeWhite.boneGameStrike01 = SpineStrikeWhite.skeletonData.findBone("game/strike-01");
        SpineStrikeWhite.slotGameStrike01 = SpineStrikeWhite.skeletonData.findSlot("game/strike-01");
        SpineStrikeWhite.skinDefault = SpineStrikeWhite.skeletonData.findSkin("default");
        SpineStrike.skeletonData = assetManager.get("spine/strike.json");
        SpineStrike.animationData = assetManager.get("spine/strike.json-animation");
        SpineStrike.animationAnimation = SpineStrike.skeletonData.findAnimation("animation");
        SpineStrike.boneRoot = SpineStrike.skeletonData.findBone("root");
        SpineStrike.boneGameStrike01 = SpineStrike.skeletonData.findBone("game/strike-01");
        SpineStrike.slotGameStrike01 = SpineStrike.skeletonData.findSlot("game/strike-01");
        SpineStrike.skinDefault = SpineStrike.skeletonData.findSkin("default");
        SpineStrikex2.skeletonData = assetManager.get("spine/strikex2.json");
        SpineStrikex2.animationData = assetManager.get("spine/strikex2.json-animation");
        SpineStrikex2.animationAnimation = SpineStrikex2.skeletonData.findAnimation("animation");
        SpineStrikex2.boneRoot = SpineStrikex2.skeletonData.findBone("root");
        SpineStrikex2.boneGameStrike01 = SpineStrikex2.skeletonData.findBone("game/strike-01");
        SpineStrikex2.boneGameStrike02 = SpineStrikex2.skeletonData.findBone("game/strike-02");
        SpineStrikex2.slotGameStrike01 = SpineStrikex2.skeletonData.findSlot("game/strike-01");
        SpineStrikex2.slotGameStrike02 = SpineStrikex2.skeletonData.findSlot("game/strike-02");
        SpineStrikex2.skinDefault = SpineStrikex2.skeletonData.findSkin("default");
        SpineStrikex3.skeletonData = assetManager.get("spine/strikex3.json");
        SpineStrikex3.animationData = assetManager.get("spine/strikex3.json-animation");
        SpineStrikex3.animationAnimation = SpineStrikex3.skeletonData.findAnimation("animation");
        SpineStrikex3.boneRoot = SpineStrikex3.skeletonData.findBone("root");
        SpineStrikex3.boneGameStrike01 = SpineStrikex3.skeletonData.findBone("game/strike-01");
        SpineStrikex3.boneGameStrike02 = SpineStrikex3.skeletonData.findBone("game/strike-02");
        SpineStrikex3.boneGameStrike03 = SpineStrikex3.skeletonData.findBone("game/strike-03");
        SpineStrikex3.slotGameStrike01 = SpineStrikex3.skeletonData.findSlot("game/strike-01");
        SpineStrikex3.slotGameStrike02 = SpineStrikex3.skeletonData.findSlot("game/strike-02");
        SpineStrikex3.slotGameStrike03 = SpineStrikex3.skeletonData.findSlot("game/strike-03");
        SpineStrikex3.skinDefault = SpineStrikex3.skeletonData.findSkin("default");
        SpineSwipeDown.skeletonData = assetManager.get("spine/swipe-down.json");
        SpineSwipeDown.animationData = assetManager.get("spine/swipe-down.json-animation");
        SpineSwipeDown.animationAnimation = SpineSwipeDown.skeletonData.findAnimation("animation");
        SpineSwipeDown.boneRoot = SpineSwipeDown.skeletonData.findBone("root");
        SpineSwipeDown.slotGameSwipe01 = SpineSwipeDown.skeletonData.findSlot("game/swipe-01");
        SpineSwipeDown.skinDefault = SpineSwipeDown.skeletonData.findSkin("default");
        SpineSwipeLeft.skeletonData = assetManager.get("spine/swipe-left.json");
        SpineSwipeLeft.animationData = assetManager.get("spine/swipe-left.json-animation");
        SpineSwipeLeft.animationAnimation = SpineSwipeLeft.skeletonData.findAnimation("animation");
        SpineSwipeLeft.boneRoot = SpineSwipeLeft.skeletonData.findBone("root");
        SpineSwipeLeft.slotGameSwipe01 = SpineSwipeLeft.skeletonData.findSlot("game/swipe-01");
        SpineSwipeLeft.skinDefault = SpineSwipeLeft.skeletonData.findSkin("default");
        SpineSwipeRight.skeletonData = assetManager.get("spine/swipe-right.json");
        SpineSwipeRight.animationData = assetManager.get("spine/swipe-right.json-animation");
        SpineSwipeRight.animationAnimation = SpineSwipeRight.skeletonData.findAnimation("animation");
        SpineSwipeRight.boneRoot = SpineSwipeRight.skeletonData.findBone("root");
        SpineSwipeRight.slotGameSwipe01 = SpineSwipeRight.skeletonData.findSlot("game/swipe-01");
        SpineSwipeRight.skinDefault = SpineSwipeRight.skeletonData.findSkin("default");
        SpineSwipeUp.skeletonData = assetManager.get("spine/swipe-up.json");
        SpineSwipeUp.animationData = assetManager.get("spine/swipe-up.json-animation");
        SpineSwipeUp.animationAnimation = SpineSwipeUp.skeletonData.findAnimation("animation");
        SpineSwipeUp.boneRoot = SpineSwipeUp.skeletonData.findBone("root");
        SpineSwipeUp.slotGameSwipe01 = SpineSwipeUp.skeletonData.findSlot("game/swipe-01");
        SpineSwipeUp.skinDefault = SpineSwipeUp.skeletonData.findSkin("default");
        SpineTitle.skeletonData = assetManager.get("spine/title.json");
        SpineTitle.animationData = assetManager.get("spine/title.json-animation");
        SpineTitle.animationAnimation = SpineTitle.skeletonData.findAnimation("animation");
        SpineTitle.animationStand = SpineTitle.skeletonData.findAnimation("stand");
        SpineTitle.boneRoot = SpineTitle.skeletonData.findBone("root");
        SpineTitle.boneDangerous = SpineTitle.skeletonData.findBone("dangerous");
        SpineTitle.boneThe = SpineTitle.skeletonData.findBone("the");
        SpineTitle.boneIn = SpineTitle.skeletonData.findBone("in");
        SpineTitle.boneJustice = SpineTitle.skeletonData.findBone("justice");
        SpineTitle.slotBg = SpineTitle.skeletonData.findSlot("bg");
        SpineTitle.slotDangerous = SpineTitle.skeletonData.findSlot("dangerous");
        SpineTitle.slotIn = SpineTitle.skeletonData.findSlot("in");
        SpineTitle.slotJustice = SpineTitle.skeletonData.findSlot("justice");
        SpineTitle.slotThe = SpineTitle.skeletonData.findSlot("the");
        SpineTitle.skinDefault = SpineTitle.skeletonData.findSkin("default");
        skin_skin = assetManager.get("skin/skin.json");
        SkinSkinStyles.bCompassRight = skin_skin.get("compass-right", Button.ButtonStyle.class);
        SkinSkinStyles.bCharacterHighlight = skin_skin.get("character-highlight", Button.ButtonStyle.class);
        SkinSkinStyles.bDefault = skin_skin.get("default", Button.ButtonStyle.class);
        SkinSkinStyles.bMapColor = skin_skin.get("map-color", Button.ButtonStyle.class);
        SkinSkinStyles.bCompassLeft = skin_skin.get("compass-left", Button.ButtonStyle.class);
        SkinSkinStyles.bCompassUp = skin_skin.get("compass-up", Button.ButtonStyle.class);
        SkinSkinStyles.bCompassDown = skin_skin.get("compass-down", Button.ButtonStyle.class);
        SkinSkinStyles.bCharacterSelectable = skin_skin.get("character-selectable", Button.ButtonStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.lBig = skin_skin.get("big", Label.LabelStyle.class);
        SkinSkinStyles.lNamesake = skin_skin.get("namesake", Label.LabelStyle.class);
        SkinSkinStyles.lButton = skin_skin.get("button", Label.LabelStyle.class);
        SkinSkinStyles.lLog = skin_skin.get("log", Label.LabelStyle.class);
        SkinSkinStyles.pMagic = skin_skin.get("magic", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.pGear = skin_skin.get("gear", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.pDefaultVertical = skin_skin.get("default-vertical", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.pHealth = skin_skin.get("health", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.pDefaultHorizontal = skin_skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class);
        SkinSkinStyles.sDefaultHorizontal = skin_skin.get("default-horizontal", Slider.SliderStyle.class);
        SkinSkinStyles.tbToggle = skin_skin.get("toggle", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbDefault = skin_skin.get("default", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tfDefault = skin_skin.get("default", TextField.TextFieldStyle.class);
        SkinSkinStyles.ttDefault = skin_skin.get("default", TextTooltip.TextTooltipStyle.class);
        SkinSkinStyles.wPointerUp = skin_skin.get("pointer-up", Window.WindowStyle.class);
        SkinSkinStyles.wDefault = skin_skin.get("default", Window.WindowStyle.class);
        SkinSkinStyles.wPointerDown = skin_skin.get("pointer-down", Window.WindowStyle.class);
        sfx_click = assetManager.get("sfx/click.mp3");
        sfx_libgdx01 = assetManager.get("sfx/libgdx01.mp3");
        sfx_libgdx02 = assetManager.get("sfx/libgdx02.mp3");
        sfx_libgdx03 = assetManager.get("sfx/libgdx03.mp3");
        sfx_libgdx04 = assetManager.get("sfx/libgdx04.mp3");
        sfx_libgdx05 = assetManager.get("sfx/libgdx05.mp3");
        sfx_libgdx06 = assetManager.get("sfx/libgdx06.mp3");
        sfx_libgdx07 = assetManager.get("sfx/libgdx07.mp3");
        sfx_libgdx08 = assetManager.get("sfx/libgdx08.mp3");
        sfx_libgdx09 = assetManager.get("sfx/libgdx09.mp3");
        sfx_libgdx10 = assetManager.get("sfx/libgdx10.mp3");
        sfx_libgdx11 = assetManager.get("sfx/libgdx11.mp3");
        sfx_ray3k01 = assetManager.get("sfx/ray3k01.mp3");
        sfx_ray3k02 = assetManager.get("sfx/ray3k02.mp3");
        sfx_ray3k03 = assetManager.get("sfx/ray3k03.mp3");
        sfx_ray3k04 = assetManager.get("sfx/ray3k04.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_game = assetManager.get("bgm/game.mp3");
        bgm_menu = assetManager.get("bgm/menu.mp3");
    }

    public static class SpineBiteWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameBite;

        public static SlotData slotGameBite;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameBite;

        public static SlotData slotGameBite;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastGray {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastGreen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastOrange {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastPink {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlastYellow {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBloodBrown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlood01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBloodWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlood01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBlood {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlood01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBurn {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBurn01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineBurnGreen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBurn01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionBrown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionGray {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionGray2 {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionGreen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionPink {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosionYellow {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineExplosion {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineHealthBrown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameHealth2;

        public static BoneData boneGameHealth4;

        public static BoneData boneGameHealth3;

        public static BoneData boneGameHealth5;

        public static BoneData boneGameHealth6;

        public static BoneData boneGameHealth7;

        public static BoneData boneGameHealth8;

        public static BoneData boneGameHealth9;

        public static SlotData slotGameHealth2;

        public static SlotData slotGameHealth9;

        public static SlotData slotGameHealth3;

        public static SlotData slotGameHealth8;

        public static SlotData slotGameHealth4;

        public static SlotData slotGameHealth7;

        public static SlotData slotGameHealth5;

        public static SlotData slotGameHealth6;

        public static SlotData slotHealthpath;

        public static SlotData slotHealthpath2;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineHealthRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameHealth2;

        public static BoneData boneGameHealth4;

        public static BoneData boneGameHealth3;

        public static BoneData boneGameHealth5;

        public static BoneData boneGameHealth6;

        public static BoneData boneGameHealth7;

        public static BoneData boneGameHealth8;

        public static BoneData boneGameHealth9;

        public static SlotData slotGameHealth2;

        public static SlotData slotGameHealth9;

        public static SlotData slotGameHealth3;

        public static SlotData slotGameHealth8;

        public static SlotData slotGameHealth4;

        public static SlotData slotGameHealth7;

        public static SlotData slotGameHealth5;

        public static SlotData slotGameHealth6;

        public static SlotData slotHealthpath;

        public static SlotData slotHealthpath2;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineHealth {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameHealth2;

        public static BoneData boneGameHealth4;

        public static BoneData boneGameHealth3;

        public static BoneData boneGameHealth5;

        public static BoneData boneGameHealth6;

        public static BoneData boneGameHealth7;

        public static BoneData boneGameHealth8;

        public static BoneData boneGameHealth9;

        public static SlotData slotGameHealth2;

        public static SlotData slotGameHealth9;

        public static SlotData slotGameHealth3;

        public static SlotData slotGameHealth8;

        public static SlotData slotGameHealth4;

        public static SlotData slotGameHealth7;

        public static SlotData slotGameHealth5;

        public static SlotData slotGameHealth6;

        public static SlotData slotHealthpath;

        public static SlotData slotHealthpath2;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineInfinityBrown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameInfinity01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineInfinityWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameInfinity01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineInfinity {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameInfinity01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLibgdx {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static EventData eventSfxLibgdx01;

        public static EventData eventSfxLibgdx02;

        public static EventData eventSfxLibgdx03;

        public static EventData eventSfxLibgdx04;

        public static EventData eventSfxLibgdx05;

        public static EventData eventSfxLibgdx06;

        public static EventData eventSfxLibgdx07;

        public static EventData eventSfxLibgdx08;

        public static EventData eventSfxLibgdx09;

        public static EventData eventSfxLibgdx10;

        public static EventData eventSfxLibgdx11;

        public static BoneData boneRoot;

        public static BoneData boneLogoLibgdxMetalBoard;

        public static BoneData boneLogoLibgdxBall;

        public static BoneData boneLogoLibgdxFist;

        public static BoneData boneLogoLibgdxL;

        public static BoneData boneLogoLibgdxDomino;

        public static BoneData boneLogoLibgdxDomino2;

        public static BoneData boneLogoLibgdxDomino3;

        public static BoneData boneLogoLibgdxDomino4;

        public static BoneData boneLogoLibgdxDomino5;

        public static BoneData boneLogoLibgdxIBody;

        public static BoneData boneLogoLibgdxIDot;

        public static BoneData boneLogoLibgdxBoard;

        public static BoneData boneLogoLibgdxCup2;

        public static BoneData boneLogoLibgdxMarble;

        public static BoneData boneLogoLibgdxCup;

        public static BoneData boneLogoLibgdxMarble2;

        public static BoneData boneLogoLibgdxMarble3;

        public static BoneData boneLogoLibgdxAnvil;

        public static BoneData boneLogoLibgdxB;

        public static BoneData boneLogoBalloon;

        public static BoneData boneLogoLibgdxKickstand;

        public static BoneData boneLogoLibgdxCog;

        public static BoneData boneLogoLibgdxCogSmall;

        public static BoneData boneLogoLibgdxD;

        public static BoneData boneLogoLibgdxKnife;

        public static BoneData boneLogoLibgdxX;

        public static BoneData boneLogoLibgdxG;

        public static BoneData boneLogoLibgdxBlood;

        public static SlotData slotLogoLibgdxBlood;

        public static SlotData slotLogoLibgdxL;

        public static SlotData slotLogoLibgdxI;

        public static SlotData slotLogoLibgdxB;

        public static SlotData slotLogoLibgdxD;

        public static SlotData slotLogoLibgdxX;

        public static SlotData slotLogoLibgdxBall;

        public static SlotData slotLogoLibgdxBoard;

        public static SlotData slotClip;

        public static SlotData slotLogoLibgdxChain;

        public static SlotData slotLogoLibgdxChain2;

        public static SlotData slotLogoLibgdxG;

        public static SlotData slotLogoLibgdxCogSmall;

        public static SlotData slotLogoLibgdxCog;

        public static SlotData slotLogoLibgdxMarble;

        public static SlotData slotLogoLibgdxMarble2;

        public static SlotData slotLogoLibgdxMarble3;

        public static SlotData slotLogoLibgdxIDot;

        public static SlotData slotLogoLibgdxCup;

        public static SlotData slotLogoLibgdxCup2;

        public static SlotData slotLogoLibgdxFist;

        public static SlotData slotLogoLibgdxDomino;

        public static SlotData slotLogoLibgdxDomino2;

        public static SlotData slotLogoLibgdxDomino3;

        public static SlotData slotLogoLibgdxDomino4;

        public static SlotData slotLogoLibgdxDomino5;

        public static SlotData slotLogoLibgdxIBody;

        public static SlotData slotLogoLibgdxKnife;

        public static SlotData slotLogoLibgdxLighter;

        public static SlotData slotLogoLibgdxLighterLight;

        public static SlotData slotLogoLibgdxLog;

        public static SlotData slotLogoLibgdxMetalBoard;

        public static SlotData slotLogoLibgdxRope;

        public static SlotData slotLogoLibgdxAnvil;

        public static SlotData slotLogoLibgdxShoe;

        public static SlotData slotLogoBalloon;

        public static SlotData slotLogoLibgdxKickstand;

        public static SlotData slotLogoLibgdxBoard2;

        public static SlotData slotLogoLibgdxBoard3;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLightningReverseRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameLightning09;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLightningReverseWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameLightning09;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLightningWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameLightning09;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineLightning {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameLightning09;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePing {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGamePing;

        public static SlotData slotGamePing;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePortalBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGamePortal01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePortalGreen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGamePortal01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePortalRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGamePortal01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpinePortal {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGamePortal01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineRay3k {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static EventData eventSfxRay3k01;

        public static EventData eventSfxRay3k02;

        public static EventData eventSfxRay3k03;

        public static EventData eventSfxRay3k04;

        public static BoneData boneRoot;

        public static BoneData boneLogoRay3kShadow2;

        public static BoneData boneLogoRay3kShadow;

        public static BoneData boneLogoRay3kNeck;

        public static BoneData boneLogoRay3kNeck2;

        public static BoneData boneLogoRay3kA;

        public static BoneData boneLogoRay3kLamp00;

        public static SlotData slotBg;

        public static SlotData slotLogoRay3kShadow;

        public static SlotData slotLogoRay3kShadow2;

        public static SlotData slotLogoRay3kShadow3;

        public static SlotData slotLogoRay3kShadow4;

        public static SlotData slotLogoRay3kShadow5;

        public static SlotData slotLogoRay3kShadow6;

        public static SlotData slotLogoRay3kR;

        public static SlotData slotLogoRay3kA;

        public static SlotData slotLogoRay3kY;

        public static SlotData slotLogoRay3k3;

        public static SlotData slotLogoRay3kK;

        public static SlotData slotLogoRay3kBase;

        public static SlotData slotLogoRay3kNeck;

        public static SlotData slotLogoRay3kLamp00;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShieldRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameShield;

        public static SlotData slotGameShield;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShieldYellow {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameShield;

        public static SlotData slotGameShield;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShield {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameShield;

        public static SlotData slotGameShield;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShot3 {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static SlotData slotGameShot02;

        public static SlotData slotGameShot03;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotGray {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotGreen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShot {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotgunOrange {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static SlotData slotGameShot02;

        public static SlotData slotGameShot03;

        public static SlotData slotGameShot04;

        public static SlotData slotGameShot05;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineShotgun {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

        public static SlotData slotGameShot02;

        public static SlotData slotGameShot03;

        public static SlotData slotGameShot04;

        public static SlotData slotGameShot05;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlashBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlashWhiteUp {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlashWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlashYellow {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlash {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSlashx3 {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

        public static SlotData slotGameSlash02;

        public static SlotData slotGameSlash03;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSparkWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSpark01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSpark {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSpark01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSplashBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSplash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSplashBrown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSplash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSplashPurple {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSplash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSplashWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSplash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSplash {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSplash01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeBlue {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeDownWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeDown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeGray {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeGreen {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeLeft {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikePurple {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeRight {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeUpGray {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeUpRed {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeUp {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikeWhite {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrike {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static SlotData slotGameStrike01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikex2 {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static BoneData boneGameStrike02;

        public static SlotData slotGameStrike01;

        public static SlotData slotGameStrike02;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineStrikex3 {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static BoneData boneGameStrike01;

        public static BoneData boneGameStrike02;

        public static BoneData boneGameStrike03;

        public static SlotData slotGameStrike01;

        public static SlotData slotGameStrike02;

        public static SlotData slotGameStrike03;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSwipeDown {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSwipe01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSwipeLeft {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSwipe01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSwipeRight {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSwipe01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineSwipeUp {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSwipe01;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SpineTitle {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static Animation animationStand;

        public static BoneData boneRoot;

        public static BoneData boneDangerous;

        public static BoneData boneThe;

        public static BoneData boneIn;

        public static BoneData boneJustice;

        public static SlotData slotBg;

        public static SlotData slotDangerous;

        public static SlotData slotIn;

        public static SlotData slotJustice;

        public static SlotData slotThe;

        public static com.esotericsoftware.spine.Skin skinDefault;
    }

    public static class SkinSkinStyles {
        public static Button.ButtonStyle bCompassRight;

        public static Button.ButtonStyle bCharacterHighlight;

        public static Button.ButtonStyle bDefault;

        public static Button.ButtonStyle bMapColor;

        public static Button.ButtonStyle bCompassLeft;

        public static Button.ButtonStyle bCompassUp;

        public static Button.ButtonStyle bCompassDown;

        public static Button.ButtonStyle bCharacterSelectable;

        public static Label.LabelStyle lDefault;

        public static Label.LabelStyle lBig;

        public static Label.LabelStyle lNamesake;

        public static Label.LabelStyle lButton;

        public static Label.LabelStyle lLog;

        public static ProgressBar.ProgressBarStyle pMagic;

        public static ProgressBar.ProgressBarStyle pGear;

        public static ProgressBar.ProgressBarStyle pDefaultVertical;

        public static ProgressBar.ProgressBarStyle pHealth;

        public static ProgressBar.ProgressBarStyle pDefaultHorizontal;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static TextButton.TextButtonStyle tbToggle;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Window.WindowStyle wPointerUp;

        public static Window.WindowStyle wDefault;

        public static Window.WindowStyle wPointerDown;
    }

    public static class Values {
        public static float jumpVelocity = 10.0f;

        public static String name = "Raeleus";

        public static boolean godMode = true;

        public static int id = 10;
    }
}
