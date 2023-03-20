package com.ray3k.template;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class GameData {
    public static int column;
    public static int row;
    public static String victoryText = "test that may include various bananas why are they screaming the voices won't stop";
    public static Array<String> tags = new Array<>();
    public static Array<String> tagDescriptions = new Array<>();
    public static Array<String> tagNameMatches = new Array<>();
    public static Array<RoomData> rooms = new Array<>();
    public static Array<Color> colors = new Array<>(new Color[]{});
    public static Array<String> colorNames = new Array<>();
    
    public static int matchTagToName(String name) {
        for (int i = 0; i < tagNameMatches.size; i++) {
            var tag = tagNameMatches.get(i);
            var keywords = tag.split(",");
            for (var keyword : keywords) {
                if (name.contains(keyword)) return i;
            }
        }
        
        int score = 0;
        for (int j = 0; j < name.length(); j++) score += name.charAt(j);
        
        return score * name.length() % (tags.size - 1) + 1;
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
        return null
    }
}
