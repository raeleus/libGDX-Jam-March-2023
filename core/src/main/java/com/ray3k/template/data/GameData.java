package com.ray3k.template.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class GameData {
    public static int difficulty;
    public static int column;
    public static int row;
    public static String victoryText = null;
    public static String failureText = "Oh no, you died! Sad.";
    public static Array<TagData> tagTemplates = new Array<>();
    public static Array<SkillData> skillTemplates = new Array<>();
    public static Array<CharacterData> heroTemplates = new Array<>();
    public static Array<CharacterData> enemyTemplates = new Array<>();
    public static Array<RoomData> rooms = new Array<>();
    public static Array<Color> colors = new Array<>(new Color[]{Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BROWN, Color.CYAN, Color.GRAY, Color.PURPLE, Color.ORANGE, Color.PINK, Color.GOLD});
    public static Array<String> colorNames = new Array<>(new String[]{"Off", "White", "Red", "Green", "Blue", "Yellow", "Brown", "Cyan", "Gray", "Purple", "Orange", "Pink", "Gold"});
    public static Array<CharacterData> playerTeam = new Array<>();
    public static Array<CharacterData> enemyTeam = new Array<>();
    public static Array<CharacterData> characterOrder = new Array<>();
    
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
            if (name.equals(tag.name)) return tag;
        }
        
        return tagTemplates.get(0);
    }
    
    public static SkillData findSkill(String name) {
        for (int i = 0; i < skillTemplates.size; i++) {
            var skill = skillTemplates.get(i);
            if (name.equals(skill.name)) return skill;
        }
        
        return skillTemplates.get(0);
    }
    
    public static CharacterData findHeroTemplate(String name) {
        for (int i = 0; i < heroTemplates.size; i++) {
            var hero = heroTemplates.get(i);
            if (name.equals(hero.name)) return hero;
        }
    
        return heroTemplates.get(0);
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
    
    public static void calculateOrder(int turn) {
        var order = new Array<CharacterData>();
        
        if (turn >= characterOrder.size) {
            characterOrder.addAll(nextOrder());
        }
        order.addAll(characterOrder);
    }
    
    public static Array<CharacterData> nextOrder() {
        var order = new Array<CharacterData>();
    
        order.addAll(playerTeam);
        order.addAll(enemyTeam);
        order.sort((o1, o2) -> o2.speed - o1.speed);
        return order;
    }
}
