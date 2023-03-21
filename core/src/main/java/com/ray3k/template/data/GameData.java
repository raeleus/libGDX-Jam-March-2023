package com.ray3k.template.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.ray3k.template.*;

public class GameData {
    public static int column;
    public static int row;
    public static String victoryText = null;
    public static Array<TagData> tagTemplates = new Array<>();
    public static Array<SkillData> skillTemplates = new Array<>();
    public static Array<CharacterData> heroTemplates = new Array<>();
    public static Array<RoomData> rooms = new Array<>();
    public static Array<Color> colors = new Array<>(new Color[]{Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BROWN, Color.CYAN, Color.GRAY, Color.PURPLE, Color.ORANGE, Color.PINK, Color.GOLD});
    public static Array<String> colorNames = new Array<>(new String[]{"Off", "White", "Red", "Green", "Blue", "Yellow", "Brown", "Cyan", "Gray", "Purple", "Orange", "Pink", "Gold"});
    
    public static TagData matchTag(String name) {
        for (int i = 0; i < tagTemplates.size; i++) {
            var tag = tagTemplates.get(i);
            for (var keyword : tag.keywords) {
                if (name.contains(keyword)) return tag;
            }
        }

        int score = 0;
        for (int j = 0; j < name.length(); j++) score += name.charAt(j);

        return tagTemplates.get(score * name.length() % (tagTemplates.size - 1) + 1);
    }
    
    public static TagData findTag(String name) {
        for (int i = 0; i < tagTemplates.size; i++) {
            var tag = tagTemplates.get(i);
            if (name.equals(name)) return tag;
        }
    
        return tagTemplates.get(0);
    }
    
    public static SkillData findSkill(String name) {
        for (int i = 0; i < skillTemplates.size; i++) {
            var skill = skillTemplates.get(i);
            if (name.equals(name)) return skill;
        }
        
        return skillTemplates.get(0);
    }
    
    public static int getRoomIndex(int column, int row) {
        return row * 10 + column;
    }
    
    public static int getRoomIndex() {
        return getRoomIndex(column, row);
    }
    
    public static RoomData getRoom(int column, int row) {
        int index = getRoomIndex(column, row);
        return rooms.get(index % rooms.size);
    }
    
    public static RoomData getRoom() {
        return getRoom(column, row);
    }
    
    public static String colorToName(Color color) {
        int colorInt = color.toIntBits();
        for (int i = 0; i < colors.size; i++) {
            var arrayColor = colors.get(i);
            if (arrayColor.toIntBits() == colorInt) return colorNames.get(i);
        }
        return "error";
    }
}