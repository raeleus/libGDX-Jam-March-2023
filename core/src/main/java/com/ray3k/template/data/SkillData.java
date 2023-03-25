package com.ray3k.template.data;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class SkillData {
    public String name;
    public String description;
    public int level = 1;
    public int maxLevel = 10;
    
    public SkillData() {
    }
    
    public SkillData(SkillData other) {
        this.name = other.name;
        this.description = other.description;
        this.level = other.level;
        this.maxLevel = other.maxLevel;
    }
    
    public void execute(CharacterData character, Array<Table> tiles, Table target, Runnable runnable) {
        runnable.run();
    }
    
    public Array<Table> selectTiles(Array<Table> tiles, Table target) {
        var selected = new Array<Table>();
        
        selected.add(target);
        
        return selected;
    }
}
