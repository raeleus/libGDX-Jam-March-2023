package com.ray3k.template;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.ObjectIntMap.Entry;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;
import com.ray3k.template.AnimationStateDataLoader.*;
import com.ray3k.template.Core.*;
import com.ray3k.template.data.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.screens.*;
import com.ray3k.template.transitions.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import javax.print.attribute.standard.MediaSize.Other;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Objects;

import static com.ray3k.template.Resources.*;
import static com.ray3k.template.data.GameData.*;

public class Core extends JamGame {
    public static final String PROJECT_NAME = "dangerousroom";
    public final static IntArray keysJustPressed = new IntArray();
    public final static IntArray buttonsJustPressed = new IntArray();
    public final static IntArray buttonsPressed = new IntArray();
    public final static IntArray scrollJustPressed = new IntArray();
    public final static ObjectIntMap<Binding> keyBindings = new ObjectIntMap<>();
    public final static ObjectIntMap<Binding> buttonBindings = new ObjectIntMap<>();
    public final static ObjectIntMap<Binding> scrollBindings = new ObjectIntMap<>();
    public final static ObjectMap<Binding, ControllerValue> controllerButtonBindings = new ObjectMap<>();
    public final static ObjectMap<Binding, ControllerValue> controllerAxisBindings = new ObjectMap<>();
    public final static ObjectSet<Binding> unboundBindings = new ObjectSet<>();
    public final static Array<Binding> bindings = new Array<>();
    public final static int ANY_BUTTON = -1;
    public final static int SCROLL_UP = -1;
    public final static int SCROLL_DOWN = 1;
    public final static int ANY_SCROLL = 0;
    public final static ControllerValue ANY_CONTROLLER_BUTTON = new ControllerValue(null, -1, 0);
    public final static ControllerValue ANY_CONTROLLER_AXIS = new ControllerValue(null, -1, 0);
    public final static ObjectMap<Controller, ControllerHandler> controllerMap = new ObjectMap<>();
    final static long MS_PER_UPDATE = 10;
    static final int MAX_VERTEX_SIZE = 32767;
    public static Core core;
    public static Skin skin;
    public static SkeletonRenderer skeletonRenderer;
    public static ChangeListener sndChangeListener;
    public static EntityController entityController;
    public static World<Entity> world;
    public static CollisionFilter defaultCollisionFilter;
    public static CollisionFilter nullCollisionFilter;
    public static CrossPlatformWorker crossPlatformWorker;
    public static float mouseX;
    public static float mouseY;
    public static Viewport viewport;
    public static OrthographicCamera camera;
    public static AssetManager assetManager;
    public static TransitionEngine transitionEngine;
    public static TwoColorPolygonBatch batch;
    public static ShapeRenderer shapeRenderer;
    public static VfxManager vfxManager;
    public static ShapeDrawer shapeDrawer;
    public static Transition defaultTransition;
    public static float defaultTransitionDuration;
    
    public static boolean isKeyJustPressed(int key) {
        return key == Keys.ANY_KEY ? keysJustPressed.size > 0 : keysJustPressed.contains(key);
    }
    
    public static boolean isKeyJustPressed(int... keys) {
        for (int key : keys) {
            if (isKeyJustPressed(key)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns true if the associated mouse button has been pressed since the last step.
     *
     * @param button The button value or -1 for any button
     * @return
     */
    public static boolean isButtonJustPressed(int button) {
        return button == ANY_BUTTON ? buttonsJustPressed.size > 0 : buttonsJustPressed.contains(button);
    }
    
    public static boolean isButtonJustPressed(int... buttons) {
        for (int button : buttons) {
            if (isButtonJustPressed(button)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isScrollJustPressed(int scroll) {
        return scroll == ANY_SCROLL ? scrollJustPressed.size > 0 : scrollJustPressed.contains(scroll);
    }
    
    public static boolean isScrollJustPressed(int... scrolls) {
        for (int scroll : scrolls) {
            if (isScrollJustPressed(scroll)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
    
    public static boolean isAnyKeyPressed(int... keys) {
        for (int key : keys) {
            if (isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyKeyPressed() {
        return isKeyPressed(Keys.ANY_KEY);
    }
    
    public static boolean isAnyKeyJustPressed(int... keys) {
        for (int key : keys) {
            if (isKeyJustPressed(key)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyKeyJustPressed() {
        return keysJustPressed.size > 0;
    }
    
    public static boolean areAllKeysPressed(int... keys) {
        for (int key : keys) {
            if (!isKeyPressed(key)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isControllerButtonJustPressed(ControllerValue... buttonCodes) {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isControllerButtonJustPressed(buttonCodes)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isControllerAxisJustPressed(ControllerValue... axisCodes) {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isControllerAxisJustPressed(axisCodes)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isControllerButtonPressed(ControllerValue... buttonCodes) {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isControllerButtonPressed(buttonCodes)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isControllerAxisPressed(ControllerValue... axisCodes) {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isControllerAxisPressed(axisCodes)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyControllerButtonPressed() {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isAnyControllerButtonPressed()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyControllerButtonJustPressed() {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isAnyControllerButtonJustPressed()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyControllerAxisPressed() {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isAnyControllerAxisPressed()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyControllerAxisJustPressed() {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.isAnyControllerAxisJustPressed()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean areAllControllerButtonsPressed(ControllerValue... buttonCodes) {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.areAllControllerButtonsPressed(buttonCodes)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean areAllControllerAxisPressed(ControllerValue... axisCodes) {
        for (ControllerHandler handler : controllerMap.values()) {
            if (handler.areAllControllerAxisPressed(axisCodes)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isButtonPressed(int button) {
        if (button == ANY_BUTTON) {
            return buttonsPressed.contains(Input.Buttons.LEFT) || buttonsPressed.contains(Input.Buttons.RIGHT)
                    || buttonsPressed.contains(Input.Buttons.MIDDLE) || buttonsPressed.contains(Input.Buttons.BACK)
                    || buttonsPressed.contains(Input.Buttons.FORWARD);
        } else {
            return buttonsPressed.contains(button);
        }
    }
    
    public static boolean isAnyButtonPressed(int... buttons) {
        for (int button : buttons) {
            if (isButtonPressed(button)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyButtonPressed() {
        return buttonsPressed.size > 0;
    }
    
    public static boolean isAnyButtonJustPressed(int... buttons) {
        for (int button : buttons) {
            if (isButtonJustPressed(button)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyButtonJustPressed() {
        return buttonsJustPressed.size > 0;
    }
    
    public static boolean areAllButtonsPressed(int... buttons) {
        for (int button : buttons) {
            if (!isButtonPressed(button)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBindingPressed(Binding binding) {
        if (keyBindings.containsKey(binding)) {
            return isKeyPressed(keyBindings.get(binding, Keys.ANY_KEY));
        } else if (buttonBindings.containsKey(binding)) {
            return isButtonPressed(buttonBindings.get(binding, ANY_BUTTON));
        } else if (controllerButtonBindings.containsKey(binding)) {
            return isControllerButtonPressed(controllerButtonBindings.get(binding));
        } else if (controllerAxisBindings.containsKey(binding)) {
            return isControllerAxisPressed(controllerAxisBindings.get(binding, ANY_CONTROLLER_AXIS));
        } else {
            return false;
        }
    }
    
    public static boolean isAnyBindingJustPressed() {
        for (Binding binding : bindings) {
            if (isBindingJustPressed(binding)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyBindingJustPressed(Binding... bindings) {
        for (Binding binding : bindings) {
            if (isBindingJustPressed(binding)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyBindingPressed() {
        for (Binding binding : bindings) {
            if (isBindingPressed(binding)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAnyBindingPressed(Binding... bindings) {
        for (Binding binding : bindings) {
            if (isBindingPressed(binding)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean areAllBindingsPressed(Binding... bindings) {
        for (Binding binding : bindings) {
            if (!isBindingPressed(binding)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBindingJustPressed(Binding binding) {
        if (keyBindings.containsKey(binding)) {
            return isKeyJustPressed(keyBindings.get(binding, Keys.ANY_KEY));
        } else if (buttonBindings.containsKey(binding)) {
            return isButtonJustPressed(buttonBindings.get(binding, ANY_BUTTON));
        } else if (scrollBindings.containsKey(binding)) {
            return isScrollJustPressed(scrollBindings.get(binding, ANY_SCROLL));
        } else if (controllerButtonBindings.containsKey(binding)) {
            return isControllerButtonJustPressed(controllerButtonBindings.get(binding, ANY_CONTROLLER_BUTTON));
        } else if (controllerAxisBindings.containsKey(binding)) {
            return isControllerAxisJustPressed(controllerAxisBindings.get(binding, ANY_CONTROLLER_AXIS));
        } else {
            return false;
        }
    }
    
    public static boolean isBindingJustPressed(Binding... bindings) {
        for (Binding binding : bindings) {
            if (isBindingJustPressed(binding)) {
                return true;
            }
        }
        return false;
    }
    
    public static void clearBindings() {
        keyBindings.clear();
        buttonBindings.clear();
        scrollBindings.clear();
        controllerButtonBindings.clear();
        controllerAxisBindings.clear();
        unboundBindings.clear();
        bindings.clear();
    }
    
    public static void addKeyBinding(Binding binding, int key) {
        buttonBindings.remove(binding, ANY_BUTTON);
        scrollBindings.remove(binding, ANY_SCROLL);
        controllerButtonBindings.remove(binding);
        controllerAxisBindings.remove(binding);
        unboundBindings.remove(binding);
        keyBindings.put(binding, key);
        if (!bindings.contains(binding, true)) {
            bindings.add(binding);
        }
    }
    
    public static void addButtonBinding(Binding binding, int button) {
        keyBindings.remove(binding, Keys.ANY_KEY);
        scrollBindings.remove(binding, ANY_SCROLL);
        controllerButtonBindings.remove(binding);
        controllerAxisBindings.remove(binding);
        unboundBindings.remove(binding);
        buttonBindings.put(binding, button);
        if (!bindings.contains(binding, true)) {
            bindings.add(binding);
        }
    }
    
    public static void addScrollBinding(Binding binding, int scroll) {
        keyBindings.remove(binding, Keys.ANY_KEY);
        buttonBindings.remove(binding, ANY_BUTTON);
        controllerButtonBindings.remove(binding);
        controllerAxisBindings.remove(binding);
        unboundBindings.remove(binding);
        scrollBindings.put(binding, scroll);
        if (!bindings.contains(binding, true)) {
            bindings.add(binding);
        }
    }
    
    public static void addControllerButtonBinding(Binding binding, ControllerValue controllerValue) {
        keyBindings.remove(binding, Keys.ANY_KEY);
        buttonBindings.remove(binding, ANY_BUTTON);
        scrollBindings.remove(binding, ANY_SCROLL);
        unboundBindings.remove(binding);
        controllerAxisBindings.remove(binding);
        controllerButtonBindings.put(binding, controllerValue);
        if (!bindings.contains(binding, true)) {
            bindings.add(binding);
        }
    }
    
    public static void addControllerAxisBinding(Binding binding, ControllerValue controllerValue) {
        keyBindings.remove(binding, Keys.ANY_KEY);
        buttonBindings.remove(binding, ANY_BUTTON);
        scrollBindings.remove(binding, ANY_SCROLL);
        unboundBindings.remove(binding);
        controllerButtonBindings.remove(binding);
        controllerAxisBindings.put(binding, controllerValue);
        if (!bindings.contains(binding, true)) {
            bindings.add(binding);
        }
    }
    
    public static void addUnboundBinding(Binding binding) {
        keyBindings.remove(binding, Keys.ANY_KEY);
        buttonBindings.remove(binding, ANY_BUTTON);
        scrollBindings.remove(binding, ANY_SCROLL);
        controllerButtonBindings.remove(binding);
        controllerAxisBindings.remove(binding);
        unboundBindings.add(binding);
        if (!bindings.contains(binding, true)) {
            bindings.add(binding);
        }
    }
    
    public static void removeBinding(Binding binding) {
        keyBindings.remove(binding, Keys.ANY_KEY);
        buttonBindings.remove(binding, ANY_BUTTON);
        scrollBindings.remove(binding, ANY_SCROLL);
        controllerButtonBindings.remove(binding);
        controllerAxisBindings.remove(binding);
        bindings.removeValue(binding, true);
    }
    
    public static boolean hasBinding(Binding binding) {
        return bindings.contains(binding, true);
    }
    
    public static boolean hasKeyBinding(Binding binding) {
        return keyBindings.containsKey(binding);
    }
    
    public static boolean hasButtonBinding(Binding binding) {
        return buttonBindings.containsKey(binding);
    }
    
    public static boolean hasScrollBinding(Binding binding) {
        return scrollBindings.containsKey(binding);
    }
    
    public static boolean hasControllerButtonBinding(Binding binding) {
        return controllerButtonBindings.containsKey(binding);
    }
    
    public static boolean hasControllerAxisBinding(Binding binding) {
        return controllerAxisBindings.containsKey(binding);
    }
    
    public static boolean hasUnboundBinding(Binding binding) {
        return unboundBindings.contains(binding);
    }
    
    public static Array<Binding> getBindings() {
        return bindings;
    }
    
    public static int getKeyBinding(Binding binding) {
        return keyBindings.get(binding, Keys.ANY_KEY);
    }
    
    public static int getButtonBinding(Binding binding) {
        return buttonBindings.get(binding, ANY_BUTTON);
    }
    
    public static int getScrollBinding(Binding binding) {
        return scrollBindings.get(binding, ANY_SCROLL);
    }
    
    public static ControllerValue getControllerButtonBinding(Binding binding) {
        return controllerButtonBindings.get(binding, ANY_CONTROLLER_BUTTON);
    }
    
    public static ControllerValue getControllerAxisBinding(Binding binding) {
        return controllerAxisBindings.get(binding, ANY_CONTROLLER_AXIS);
    }
    
    public static String getBindingCodeName(Binding binding) {
        if (keyBindings.containsKey(binding)) {
            return Keys.toString(getKeyBinding(binding));
        } else if (buttonBindings.containsKey(binding)) {
            return Utils.mouseButtonToString(getButtonBinding(binding));
        } else if (controllerButtonBindings.containsKey(binding)) {
            return Utils.controllerButtonToString(getControllerButtonBinding(binding));
        } else if (controllerAxisBindings.containsKey(binding)) {
            return Utils.controllerAxisToString(getControllerAxisBinding(binding));
        } else if (scrollBindings.containsKey(binding)) {
            return Utils.scrollAmountToString(getScrollBinding(binding));
        } else {
            return "UNBOUND";
        }
    }
    
    public static void saveBindings() {
        for (Entry<Binding> binding : keyBindings) {
            preferences.putInteger("key:" + binding.key.toString(), binding.value);
            preferences.remove("button:" + binding.key.toString());
            preferences.remove("scroll:" + binding.key.toString());
            preferences.remove("controllerbutton:" + binding.key.toString());
            preferences.remove("controlleraxis:" + binding.key.toString());
            preferences.remove("unbound:" + binding.key.toString());
        }
        
        for (Entry<Binding> binding : buttonBindings) {
            preferences.putInteger("button:" + binding.key.toString(), binding.value);
            preferences.remove("key:" + binding.key.toString());
            preferences.remove("scroll:" + binding.key.toString());
            preferences.remove("controllerbutton:" + binding.key.toString());
            preferences.remove("controlleraxis:" + binding.key.toString());
            preferences.remove("unbound:" + binding.key.toString());
        }
        
        for (Entry<Binding> binding : scrollBindings) {
            preferences.putInteger("scroll:" + binding.key.toString(), binding.value);
            preferences.remove("key:" + binding.key.toString());
            preferences.remove("button:" + binding.key.toString());
            preferences.remove("controllerbutton:" + binding.key.toString());
            preferences.remove("controlleraxis:" + binding.key.toString());
            preferences.remove("unbound:" + binding.key.toString());
        }
        
        for (ObjectMap.Entry<Binding, ControllerValue> binding : controllerButtonBindings) {
            preferences.putString("controllerbutton:" + binding.key.toString(), Controllers.getControllers().indexOf(binding.value.controller, true) + " " + binding.value.axisCode + " " + binding.value.value);
            preferences.remove("key:" + binding.key.toString());
            preferences.remove("button:" + binding.key.toString());
            preferences.remove("scroll:" + binding.key.toString());
            preferences.remove("controlleraxis:" + binding.key.toString());
            preferences.remove("unbound:" + binding.key.toString());
        }
        
        for (ObjectMap.Entry<Binding, ControllerValue> binding : controllerAxisBindings) {
            preferences.putString("controlleraxis:" + binding.key.toString(), Controllers.getControllers().indexOf(binding.value.controller, true) + " " + binding.value.axisCode + " " + binding.value.value);
            preferences.remove("key:" + binding.key.toString());
            preferences.remove("button:" + binding.key.toString());
            preferences.remove("scroll:" + binding.key.toString());
            preferences.remove("controllerbutton:" + binding.key.toString());
            preferences.remove("unbound:" + binding.key.toString());
        }
        
        for (Binding binding : unboundBindings) {
            preferences.putBoolean("unbound:" + binding.toString(), true);
            preferences.remove("key:" + binding.toString());
            preferences.remove("button:" + binding.toString());
            preferences.remove("scroll:" + binding.toString());
            preferences.remove("controllerbutton:" + binding.toString());
            preferences.remove("controlleraxis:" + binding.toString());
        }
        preferences.flush();
    }
    
    public static void loadBindings() {
        for (Binding binding : bindings) {
            String key = "key:" + binding.toString();
            if (preferences.contains(key)) {
                addKeyBinding(binding, preferences.getInteger(key));
            }
            
            key = "button:" + binding.toString();
            if (preferences.contains(key)) {
                addButtonBinding(binding, preferences.getInteger(key));
            }
            
            key = "scroll:" + binding.toString();
            if (preferences.contains(key)) {
                addScrollBinding(binding, preferences.getInteger(key));
            }
            
            key = "controllerbutton:" + binding.toString();
            if (preferences.contains(key)) {
                ControllerValue controllerValue = new ControllerValue();
                String[] line = preferences.getString(key).split(" ");
                var controllerIndex = Integer.parseInt(line[0]);
                if (controllerIndex < Controllers.getControllers().size) {
                    controllerValue.controller = Controllers.getControllers().get(controllerIndex);
                }
                controllerValue.axisCode = Integer.parseInt(line[1]);
                controllerValue.value = Integer.parseInt(line[2]);
                addControllerButtonBinding(binding, controllerValue);
            }
            
            key = "controlleraxis:" + binding.toString();
            if (preferences.contains(key)) {
                ControllerValue controllerValue = new ControllerValue();
                String[] line = preferences.getString(key).split(" ");
                var controllerIndex = Integer.parseInt(line[0]);
                if (controllerIndex < Controllers.getControllers().size) {
                    controllerValue.controller = Controllers.getControllers().get(controllerIndex);
                }
                controllerValue.axisCode = Integer.parseInt(line[1]);
                controllerValue.value = Integer.parseInt(line[2]);
                addControllerAxisBinding(binding, controllerValue);
            }
            
            key = "unbound:" + binding.toString();
            if (preferences.contains(key)) {
                addUnboundBinding(binding);
            }
        }
    }
    
    public enum Binding {
        LEFT, RIGHT, UP, DOWN, SHOOT, SPECIAL, SHIELD;
    }
    public static float bgm;
    public static float sfx;
    public static Preferences preferences;
    public static MessageDigest crypt;
    
    public static String encrypt(String message) {
        crypt.reset();
        try {
            final byte[] bytes = message.getBytes("UTF-8");
            final byte[] digest = crypt.digest(bytes);
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
        
        }
        return null;
    }
    
    public static void setPixel(int column, int row, Color color) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(HttpMethods.GET);
        String gameID = Gdx.files.internal("secret/gameid").readString();
        String key = Gdx.files.internal("secret/key").readString();
        String url = "https://api.gamejolt.com/api/game/v1_2/data-store/set/";
        String content = "?game_id=" + gameID + "&key=" + column + "-" + row + "&data=" + color.toString();
        String signature = encrypt(url + content + key);
        httpRequest.setUrl(url + content + "&signature=" + signature);
        
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

            }
        
            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                System.out.println("failed to send pixel");
            }
        
            @Override
            public void cancelled() {
            
            }
        });
    }
    
    public static void fetchPixel(int column, int row, FetchPixelHandler handler) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(HttpMethods.GET);
        String gameID = Gdx.files.internal("secret/gameid").readString();
        String key = Gdx.files.internal("secret/key").readString();
        String url = "https://api.gamejolt.com/api/game/v1_2/data-store/";
        String content = "?game_id=" + gameID + "&key=" + column + "-" + row;
        String signature = encrypt(url + content + key);
        httpRequest.setUrl(url + content + "&signature=" + signature);
    
        System.out.println(column + " " + row + " Url:" + url + content);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String response = httpResponse.getResultAsString();
                System.out.println(response);
                var jsonReader = new JsonReader();
                var root = jsonReader.parse(response).get("response");
                if (root.getBoolean("success", false)) {
                    Gdx.app.postRunnable(() -> handler.handle(column, row, Color.valueOf(root.getString("data"))));
                } else {
                    Gdx.app.postRunnable(() -> handler.handle(column, row, Color.WHITE));
                }
            }
        
            @Override
            public void failed(Throwable t) {
                Gdx.app.postRunnable(() -> handler.handle(column, row, Color.WHITE));
            }
        
            @Override
            public void cancelled() {
                Gdx.app.postRunnable(() -> handler.handle(column, row, Color.WHITE));
            }
        });
    }
    
    public static void fetchPixelUnsafe(int column, int row, FetchPixelHandler handler) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(HttpMethods.GET);
        String gameID = Gdx.files.internal("secret/gameid").readString();
        String key = Gdx.files.internal("secret/key").readString();
        String url = "https://api.gamejolt.com/api/game/v1_2/data-store/";
        String content = "?game_id=" + gameID + "&key=" + column + "-" + row;
        String signature = encrypt(url + content + key);
        httpRequest.setUrl(url + content + "&signature=" + signature);
        
        System.out.println(column + " " + row + " Url:" + url + content);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String response = httpResponse.getResultAsString();
                System.out.println(response);
                var jsonReader = new JsonReader();
                var root = jsonReader.parse(response).get("response");
                if (root.getBoolean("success", false)) {
                    handler.handle(column, row, Color.valueOf(root.getString("data")));
                } else {
                    handler.handle(column, row, Color.WHITE);
                }
            }
            
            @Override
            public void failed(Throwable t) {
                handler.handle(column, row, Color.WHITE);
            }
            
            @Override
            public void cancelled() {
                handler.handle(column, row, Color.WHITE);
            }
        });
    }
    
    private static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void fetchPixelBatch(int row, int rowEnd, FetchPixelHandler handler) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(HttpMethods.GET);
        String gameID = Gdx.files.internal("secret/gameid").readString();
        String key = Gdx.files.internal("secret/key").readString();
        var url = "https://api.gamejolt.com/api/game/v1_2/batch?game_id=" + gameID + "&parallel=true";
        for (int column = 0; column < 50; column++) {
            String subUrl = "/data-store/";
            String content = "?game_id=" + gameID + "&key=" + column + "-" + row;
            String signature = encrypt(subUrl + content + key);
            subUrl = subUrl + content + "&signature=" + signature;
            subUrl = "&" + "requests[]=" + urlEncode(subUrl);
            url += subUrl;
        }
    
        String signature = encrypt(url + key);
        url += "&signature=" + signature;
        
        httpRequest.setUrl(url);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String response = httpResponse.getResultAsString();
                var jsonReader = new JsonReader();
                var root = jsonReader.parse(response).get("response");
                if (root.getBoolean("success", false)) {
                    var responseNode = root.get("responses");
                    var c = 0;
                    for (var child : responseNode.iterator()) {
                        final var cValue = c;
                        if (child.getBoolean("success", false)) {
                            Gdx.app.postRunnable(() -> handler.handle(cValue, row, Color.valueOf(child.getString("data"))));
                        } else {
                            Gdx.app.postRunnable(() -> handler.handle(cValue, row, Color.WHITE));
                        }
                        c++;
                    }
                }
                
                if (row + 1 < rowEnd) fetchPixelBatch(row + 1, rowEnd, handler);
            }
        
            @Override
            public void failed(Throwable t) {
            
            }
        
            @Override
            public void cancelled() {
            
            }
        });
    }
    
    public interface FetchPixelHandler {
        void handle(int c, int r, Color color);
    }
    
    @Override
    public void create() {
        super.create();
        core = this;
        try {
            crypt = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        var tagNames = Gdx.files.internal("text/tag-names").readString("UTF-8").split("\n");
        var tagDescriptions = Gdx.files.internal("text/tag-descriptions").readString("UTF-8").split("\\n");
        var tagKeywords = Gdx.files.internal("text/tag-keywords").readString("UTF-8").split("\\n");
        var tagSkills = Gdx.files.internal("text/tag-skills").readString("UTF-8").split("\\n");
        for (int i = 0; i < tagNames.length; i++) {
            var tag = new TagData();
            tag.name = tagNames[i];
            tag.description = tagDescriptions[i];
            tag.keywords.addAll(tagKeywords[i].split(","));
            tag.availableSkills.addAll(tagSkills[i].split(","));
            tagTemplates.add(tag);
        }
        
        var skillNames = Gdx.files.internal("text/skill-names").readString().split("\\n");
        var skillDescriptions = Gdx.files.internal("text/skill-descriptions").readString().split("\\n");
        var skillUses = Gdx.files.internal("text/skill-uses").readString().split("\\n");
        var skillRegenerateUses = Gdx.files.internal("text/skill-regenerateUses").readString().split("\\n");
        for (int i = 0; i < skillNames.length; i++) {
            var skill = new SkillData();
            skill.name = skillNames[i];
            skill.description = skillDescriptions[i];
            skill.usesMax = Integer.parseInt(skillUses[i].trim());
            skill.uses = skill.usesMax;
            skill.regenerateUses = Boolean.parseBoolean(skillRegenerateUses[i].trim());
            skillTemplates.add(skill);
        }
        
        var heroNames = Gdx.files.internal("text/hero-names").readString().split("\\n");
        var heroDescriptions = Gdx.files.internal("text/hero-descriptions").readString().split("\\n");
        var heroSkills = Gdx.files.internal("text/hero-skills").readString().split("\\n");
        var heroTags = Gdx.files.internal("text/hero-tags").readString().split("\\n");
        for (int i = 0; i < heroNames.length; i++) {
            var hero = new CharacterData();
            hero.name = heroNames[i];
            hero.description = heroDescriptions[i];
            for (var skill : heroSkills[i].split(",")) {
                hero.addSkill(skill);
            }
            for (var tag : heroTags[i].split(",")) {
                hero.addTag(tag, false);
            }
            for (var tag : hero.tags) {
                for (var skill : hero.skills) {
                    tag.availableSkills.removeValue(skill.name, false);
                }
            }
            heroTemplates.add(hero);
        }
    
        var enemyNames = Gdx.files.internal("text/enemy-names").readString().split("\\n");
        var enemySkills = Gdx.files.internal("text/enemy-skills").readString().split("\\n");
        var enemyHealth = Gdx.files.internal("text/enemy-health").readString().split("\\n");
        for (int i = 0; i < enemyNames.length; i++) {
            var enemy = new CharacterData();
            enemy.name = enemyNames[i];
            for (var skill : enemySkills[i].split(",")) {
                enemy.addSkill(skill);
            }
            enemy.healthMax = Integer.parseInt(enemyHealth[i]);
            enemy.health = enemy.healthMax;
            enemyTemplates.add(enemy);
        }
        
        preferences = Gdx.app.getPreferences(PROJECT_NAME);
        
        bgm = preferences.getFloat("bgm", 1.0f);
        sfx = preferences.getFloat("sfx", 1.0f);
        
        setDefaultBindings();
        loadBindings();
        
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        
        entityController = new EntityController();
        
        world = new World<>(100);
        defaultCollisionFilter = (item, other) -> Response.bounce;
        nullCollisionFilter = (Item, Other) -> null;
        
        sndChangeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sfx_click.play(sfx);
            }
        };
        
        setScreen(new LoadScreen(() -> {
            loadResources(assetManager);
            skin = skin_skin;
        }));
        defaultTransition = Transitions.colorFade(Color.BLACK);
        defaultTransitionDuration = .5f;
    }
    
    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void loadAssets() {
        assetManager.setLoader(SkeletonData.class, new SkeletonDataLoader(assetManager.getFileHandleResolver()));
        assetManager.setLoader(AnimationStateData.class, new AnimationStateDataLoader(assetManager.getFileHandleResolver()));
        
        String textureAtlasPath = null;
        var fileHandle = Gdx.files.internal("textures.txt");
        if (fileHandle.exists()) for (String path : fileHandle.readString("UTF-8").split("\\n")) {
            assetManager.load(path, TextureAtlas.class);
            textureAtlasPath = path;
        }
        
        fileHandle = Gdx.files.internal("skin.txt");
        if (fileHandle.exists()) for (String path : fileHandle.readString("UTF-8").split("\\n")) {
            assetManager.load(path, Skin.class, new SkinParameter(textureAtlasPath));
        }
    
        fileHandle = Gdx.files.internal("bgm.txt");
        if (fileHandle.exists()) for (String path : fileHandle.readString("UTF-8").split("\\n")) {
            assetManager.load(path, Music.class);
        }
    
        fileHandle = Gdx.files.internal("sfx.txt");
        if (fileHandle.exists()) for (String path : fileHandle.readString("UTF-8" ).split("\\n")) {
            assetManager.load(path, Sound.class);
        }
        
        fileHandle = Gdx.files.internal("spine.txt");
        if (fileHandle.exists()) for (String path2 : fileHandle.readString("UTF-8").split("\\n")) {
            assetManager.load(path2 + "-animation", AnimationStateData.class, new AnimationStateDataParameter(path2, textureAtlasPath));
        }
    }
    
    public void setDefaultBindings() {
        addKeyBinding(Binding.LEFT, Input.Keys.LEFT);
        addKeyBinding(Binding.RIGHT, Input.Keys.RIGHT);
        addKeyBinding(Binding.UP, Input.Keys.UP);
        addKeyBinding(Binding.DOWN, Input.Keys.DOWN);
        addKeyBinding(Binding.SHOOT, Input.Keys.Z);
        addKeyBinding(Binding.SHIELD, Input.Keys.X);
        addKeyBinding(Binding.SPECIAL, Input.Keys.C);
    }
    
    public static class ControllerHandler implements ControllerListener {
        public Array<ControllerValue> controllerButtonsJustPressed = new Array<>();
        public Array<ControllerValue> controllerButtonsPressed = new Array<>();
        public Array<ControllerValue> controllerAxisJustPressed = new Array<>();
        public Array<ControllerValue> controllerAxisPressed = new SnapshotArray<>();
    
        //button
    
        public boolean isControllerButtonJustPressed(ControllerValue buttonCode) {
            return buttonCode == ANY_CONTROLLER_BUTTON ? controllerButtonsJustPressed.size > 0 : controllerButtonsJustPressed.contains(
                    buttonCode, false);
        }
    
        public boolean isControllerButtonJustPressed(ControllerValue... controllerButtons) {
            for (ControllerValue controllerButton : controllerButtons) {
                if (isControllerButtonJustPressed(controllerButton)) {
                    return true;
                }
            }
            return false;
        }
    
        public boolean isAnyControllerButtonJustPressed() {
            return controllerButtonsJustPressed.size > 0;
        }
    
        public boolean isControllerButtonPressed(ControllerValue buttonCode) {
            return buttonCode == ANY_CONTROLLER_BUTTON ? controllerButtonsPressed.size > 0 : controllerButtonsPressed.contains(
                    buttonCode, false);
        }
    
        public boolean isControllerButtonPressed(ControllerValue... buttonCodes) {
            for (ControllerValue controllerButton : buttonCodes) {
                if (isControllerButtonPressed(controllerButton)) {
                    return true;
                }
            }
            return false;
        }
    
        public boolean areAllControllerButtonsPressed(ControllerValue... buttonCodes) {
            for (ControllerValue buttonCode : buttonCodes) {
                if (!isControllerButtonPressed(buttonCode)) {
                    return false;
                }
            }
            return true;
        }
    
        public boolean isAnyControllerButtonPressed() {
            return controllerButtonsPressed.size > 0;
        }
    
        //axis
    
        public boolean isControllerAxisJustPressed(ControllerValue axisCode) {
            return axisCode == ANY_CONTROLLER_AXIS ? controllerAxisJustPressed.size > 0 : controllerAxisJustPressed.contains(
                    axisCode, false);
        }
    
        public boolean isControllerAxisJustPressed(ControllerValue... axisCodes) {
            for (ControllerValue axisCode : axisCodes) {
                if (isControllerAxisJustPressed(axisCode)) {
                    return true;
                }
            }
            return false;
        }
    
        public boolean isAnyControllerAxisJustPressed() {
            return controllerButtonsJustPressed.size > 0;
        }
    
        public boolean isControllerAxisPressed(ControllerValue axisCode) {
            return axisCode == ANY_CONTROLLER_AXIS ? controllerAxisPressed.size > 0 : controllerAxisPressed.contains(
                    axisCode, false);
        }
    
        public boolean isControllerAxisPressed(ControllerValue... axisCodes) {
            for (ControllerValue axisCode : axisCodes) {
                if (isControllerAxisPressed(axisCode)) {
                    return true;
                }
            }
            return false;
        }
    
        public boolean areAllControllerAxisPressed(ControllerValue... axisCodes) {
            for (ControllerValue axisCode : axisCodes) {
                if (!isControllerAxisPressed(axisCode)) {
                    return false;
                }
            }
            return true;
        }
    
        public boolean isAnyControllerAxisPressed() {
            return controllerAxisPressed.size > 0;
        }
    
        @Override
        public void connected(Controller controller) {
        
        }
    
        @Override
        public void disconnected(Controller controller) {
    
        }
    
        @Override
        public boolean buttonDown(Controller controller, int buttonCode) {
            ControllerValue controllerValue = new ControllerValue(controller, 0, buttonCode);
            controllerButtonsJustPressed.add(controllerValue);
            controllerButtonsPressed.add(controllerValue);
            return false;
        }
    
        @Override
        public boolean buttonUp(Controller controller, int buttonCode) {
            ControllerValue controllerValue = new ControllerValue(controller, 0, buttonCode);
            controllerButtonsPressed.removeValue(controllerValue, false);
            return false;
        }
    
        @Override
        public boolean axisMoved(Controller controller, int axisCode, float value) {
            int roundedValue = MathUtils.round(value);
    
            ControllerValue controllerValue = new ControllerValue(controller, axisCode, roundedValue);
            if (roundedValue != 0) {
                controllerAxisJustPressed.add(controllerValue);
            }
    
            Iterator<ControllerValue> iterator = controllerAxisPressed.iterator();
            while (iterator.hasNext()) {
                ControllerValue next = iterator.next();
                if (next.axisCode == axisCode) iterator.remove();
            }
    
            if (roundedValue != 0) {
                controllerAxisPressed.add(controllerValue);
            }
            return false;
        }
    }
    
    public static class ControllerValue {
        public Controller controller;
        public int axisCode;
        public int value;
    
        public ControllerValue() {
        }
    
        public ControllerValue(Controller controller, int axisCode, int value) {
            this.controller = controller;
            this.axisCode = axisCode;
            this.value = value;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ControllerValue that = (ControllerValue) o;
            return axisCode == that.axisCode &&
                    value == that.value &&
                    Objects.equals(that.controller, controller);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(axisCode, value);
        }
    }
}
