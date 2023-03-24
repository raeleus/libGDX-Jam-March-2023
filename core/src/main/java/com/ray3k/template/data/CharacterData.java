package com.ray3k.template.data;

import com.badlogic.gdx.utils.Array;

public class CharacterData {
    public String name;
    public String description;
    public float health;
    public float healthMax = 100f;
    public int speed;
    public Array<SkillData> skills = new Array<>();
    public Array<TagData> tags = new Array<>();
    public int position;
    
    public CharacterData() {
    }
    
    public CharacterData(CharacterData other) {
        this.name = other.name;
        this.description = other.description;
        this.health = other.health;
        this.healthMax = other.healthMax;
        this.speed = other.speed;
        skills.addAll(other.skills);
        tags.addAll(other.tags);
    }
    
    public void addTag(String tag) {
        var tagData = GameData.findTag(tag);
        tags.add(tagData);
        
        healthMax += tagData.healthModifier;
        if (healthMax > 0) health += healthMax;
        
        speed += tagData.speedModifier;
        
        if (tagData.availableSkills.size > 0) {
            addSkill(tagData.availableSkills.first());
            tagData.availableSkills.removeIndex(0);
        }
    }
    
    public void addSkill(String skill) {
        for (var skillData : skills) {
            if (skill.equals(skillData.name)) return;
        }
        
        var skillData = GameData.findSkill(skill);
        skills.add(skillData);
    }
}
