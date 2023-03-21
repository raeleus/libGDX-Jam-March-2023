package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    public static Music bgm_menu;

    public static void loadResources(AssetManager assetManager) {
        textures_textures = assetManager.get("textures/textures.atlas");
        SpineBite.skeletonData = assetManager.get("spine/bite.json");
        SpineBite.animationData = assetManager.get("spine/bite.json-animation");
        SpineBite.animationAnimation = SpineBite.skeletonData.findAnimation("animation");
        SpineBite.boneRoot = SpineBite.skeletonData.findBone("root");
        SpineBite.boneGameBite = SpineBite.skeletonData.findBone("game/bite");
        SpineBite.slotGameBite = SpineBite.skeletonData.findSlot("game/bite");
        SpineBite.skinDefault = SpineBite.skeletonData.findSkin("default");
        SpineBlastPink.skeletonData = assetManager.get("spine/blast-pink.json");
        SpineBlastPink.animationData = assetManager.get("spine/blast-pink.json-animation");
        SpineBlastPink.animationAnimation = SpineBlastPink.skeletonData.findAnimation("animation");
        SpineBlastPink.boneRoot = SpineBlastPink.skeletonData.findBone("root");
        SpineBlastPink.slotGameBlast01 = SpineBlastPink.skeletonData.findSlot("game/blast-01");
        SpineBlastPink.skinDefault = SpineBlastPink.skeletonData.findSkin("default");
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
        SpineExplosion.skeletonData = assetManager.get("spine/explosion.json");
        SpineExplosion.animationData = assetManager.get("spine/explosion.json-animation");
        SpineExplosion.animationAnimation = SpineExplosion.skeletonData.findAnimation("animation");
        SpineExplosion.boneRoot = SpineExplosion.skeletonData.findBone("root");
        SpineExplosion.slotGameExplosion01 = SpineExplosion.skeletonData.findSlot("game/explosion-01");
        SpineExplosion.skinDefault = SpineExplosion.skeletonData.findSkin("default");
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
        SpineShot.skeletonData = assetManager.get("spine/shot.json");
        SpineShot.animationData = assetManager.get("spine/shot.json-animation");
        SpineShot.animationAnimation = SpineShot.skeletonData.findAnimation("animation");
        SpineShot.boneRoot = SpineShot.skeletonData.findBone("root");
        SpineShot.slotGameShot01 = SpineShot.skeletonData.findSlot("game/shot-01");
        SpineShot.skinDefault = SpineShot.skeletonData.findSkin("default");
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
        SpineSlash.skeletonData = assetManager.get("spine/slash.json");
        SpineSlash.animationData = assetManager.get("spine/slash.json-animation");
        SpineSlash.animationAnimation = SpineSlash.skeletonData.findAnimation("animation");
        SpineSlash.boneRoot = SpineSlash.skeletonData.findBone("root");
        SpineSlash.slotGameSlash01 = SpineSlash.skeletonData.findSlot("game/slash-01");
        SpineSlash.skinDefault = SpineSlash.skeletonData.findSkin("default");
        SpineSpark.skeletonData = assetManager.get("spine/spark.json");
        SpineSpark.animationData = assetManager.get("spine/spark.json-animation");
        SpineSpark.animationAnimation = SpineSpark.skeletonData.findAnimation("animation");
        SpineSpark.boneRoot = SpineSpark.skeletonData.findBone("root");
        SpineSpark.slotGameSpark01 = SpineSpark.skeletonData.findSlot("game/spark-01");
        SpineSpark.skinDefault = SpineSpark.skeletonData.findSkin("default");
        SpineSplash.skeletonData = assetManager.get("spine/splash.json");
        SpineSplash.animationData = assetManager.get("spine/splash.json-animation");
        SpineSplash.animationAnimation = SpineSplash.skeletonData.findAnimation("animation");
        SpineSplash.boneRoot = SpineSplash.skeletonData.findBone("root");
        SpineSplash.slotGameSplash01 = SpineSplash.skeletonData.findSlot("game/splash-01");
        SpineSplash.skinDefault = SpineSplash.skeletonData.findSkin("default");
        SpineStrikeDown.skeletonData = assetManager.get("spine/strike-down.json");
        SpineStrikeDown.animationData = assetManager.get("spine/strike-down.json-animation");
        SpineStrikeDown.animationAnimation = SpineStrikeDown.skeletonData.findAnimation("animation");
        SpineStrikeDown.boneRoot = SpineStrikeDown.skeletonData.findBone("root");
        SpineStrikeDown.boneGameStrike01 = SpineStrikeDown.skeletonData.findBone("game/strike-01");
        SpineStrikeDown.slotGameStrike01 = SpineStrikeDown.skeletonData.findSlot("game/strike-01");
        SpineStrikeDown.skinDefault = SpineStrikeDown.skeletonData.findSkin("default");
        SpineStrikeLeft.skeletonData = assetManager.get("spine/strike-left.json");
        SpineStrikeLeft.animationData = assetManager.get("spine/strike-left.json-animation");
        SpineStrikeLeft.animationAnimation = SpineStrikeLeft.skeletonData.findAnimation("animation");
        SpineStrikeLeft.boneRoot = SpineStrikeLeft.skeletonData.findBone("root");
        SpineStrikeLeft.boneGameStrike01 = SpineStrikeLeft.skeletonData.findBone("game/strike-01");
        SpineStrikeLeft.slotGameStrike01 = SpineStrikeLeft.skeletonData.findSlot("game/strike-01");
        SpineStrikeLeft.skinDefault = SpineStrikeLeft.skeletonData.findSkin("default");
        SpineStrikeRight.skeletonData = assetManager.get("spine/strike-right.json");
        SpineStrikeRight.animationData = assetManager.get("spine/strike-right.json-animation");
        SpineStrikeRight.animationAnimation = SpineStrikeRight.skeletonData.findAnimation("animation");
        SpineStrikeRight.boneRoot = SpineStrikeRight.skeletonData.findBone("root");
        SpineStrikeRight.boneGameStrike01 = SpineStrikeRight.skeletonData.findBone("game/strike-01");
        SpineStrikeRight.slotGameStrike01 = SpineStrikeRight.skeletonData.findSlot("game/strike-01");
        SpineStrikeRight.skinDefault = SpineStrikeRight.skeletonData.findSkin("default");
        SpineStrikeUp.skeletonData = assetManager.get("spine/strike-up.json");
        SpineStrikeUp.animationData = assetManager.get("spine/strike-up.json-animation");
        SpineStrikeUp.animationAnimation = SpineStrikeUp.skeletonData.findAnimation("animation");
        SpineStrikeUp.boneRoot = SpineStrikeUp.skeletonData.findBone("root");
        SpineStrikeUp.boneGameStrike01 = SpineStrikeUp.skeletonData.findBone("game/strike-01");
        SpineStrikeUp.slotGameStrike01 = SpineStrikeUp.skeletonData.findSlot("game/strike-01");
        SpineStrikeUp.skinDefault = SpineStrikeUp.skeletonData.findSkin("default");
        SpineStrike.skeletonData = assetManager.get("spine/strike.json");
        SpineStrike.animationData = assetManager.get("spine/strike.json-animation");
        SpineStrike.animationAnimation = SpineStrike.skeletonData.findAnimation("animation");
        SpineStrike.boneRoot = SpineStrike.skeletonData.findBone("root");
        SpineStrike.boneGameStrike01 = SpineStrike.skeletonData.findBone("game/strike-01");
        SpineStrike.slotGameStrike01 = SpineStrike.skeletonData.findSlot("game/strike-01");
        SpineStrike.skinDefault = SpineStrike.skeletonData.findSkin("default");
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
        SkinSkinStyles.bDefault = skin_skin.get("default", Button.ButtonStyle.class);
        SkinSkinStyles.bMapColor = skin_skin.get("map-color", Button.ButtonStyle.class);
        SkinSkinStyles.bCompassLeft = skin_skin.get("compass-left", Button.ButtonStyle.class);
        SkinSkinStyles.bCompassUp = skin_skin.get("compass-up", Button.ButtonStyle.class);
        SkinSkinStyles.bCompassDown = skin_skin.get("compass-down", Button.ButtonStyle.class);
        SkinSkinStyles.lDefault = skin_skin.get("default", Label.LabelStyle.class);
        SkinSkinStyles.lBig = skin_skin.get("big", Label.LabelStyle.class);
        SkinSkinStyles.lNamesake = skin_skin.get("namesake", Label.LabelStyle.class);
        SkinSkinStyles.lButton = skin_skin.get("button", Label.LabelStyle.class);
        SkinSkinStyles.lLog = skin_skin.get("log", Label.LabelStyle.class);
        SkinSkinStyles.sDefaultHorizontal = skin_skin.get("default-horizontal", Slider.SliderStyle.class);
        SkinSkinStyles.tbToggle = skin_skin.get("toggle", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tbDefault = skin_skin.get("default", TextButton.TextButtonStyle.class);
        SkinSkinStyles.tfDefault = skin_skin.get("default", TextField.TextFieldStyle.class);
        SkinSkinStyles.ttDefault = skin_skin.get("default", TextTooltip.TextTooltipStyle.class);
        SkinSkinStyles.wDefault = skin_skin.get("default", Window.WindowStyle.class);
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
        bgm_menu = assetManager.get("bgm/menu.mp3");
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

    public static class SpineBlastPink {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameBlast01;

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

    public static class SpineExplosion {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameExplosion01;

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

    public static class SpineShot {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameShot01;

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

    public static class SpineSlash {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSlash01;

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

    public static class SpineSplash {
        public static SkeletonData skeletonData;

        public static AnimationStateData animationData;

        public static Animation animationAnimation;

        public static BoneData boneRoot;

        public static SlotData slotGameSplash01;

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

    public static class SpineStrikeLeft {
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

    public static class SpineStrikeUp {
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

        public static Button.ButtonStyle bDefault;

        public static Button.ButtonStyle bMapColor;

        public static Button.ButtonStyle bCompassLeft;

        public static Button.ButtonStyle bCompassUp;

        public static Button.ButtonStyle bCompassDown;

        public static Label.LabelStyle lDefault;

        public static Label.LabelStyle lBig;

        public static Label.LabelStyle lNamesake;

        public static Label.LabelStyle lButton;

        public static Label.LabelStyle lLog;

        public static Slider.SliderStyle sDefaultHorizontal;

        public static TextButton.TextButtonStyle tbToggle;

        public static TextButton.TextButtonStyle tbDefault;

        public static TextField.TextFieldStyle tfDefault;

        public static TextTooltip.TextTooltipStyle ttDefault;

        public static Window.WindowStyle wDefault;
    }

    public static class Values {
        public static float jumpVelocity = 10.0f;

        public static String name = "Raeleus";

        public static boolean godMode = true;

        public static int id = 10;
    }
}
