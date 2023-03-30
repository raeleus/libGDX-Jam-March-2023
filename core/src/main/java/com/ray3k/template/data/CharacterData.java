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
    public boolean stunned;
    public boolean stunEnemyOnHit;
    public boolean stunEnemyOnNextHit;
    public float damageMitigation;
    
    public float extraDamage;
    public float extraDamageNextTurn;
    public float extraDamageIfNotHurt;
    
    public boolean killOnBattleCompletion;
    
    public boolean blockNextAttack;
    public float counterNextAttack;
    
    public float delayedDamage;
    
    public CharacterData() {
    }
    
    public CharacterData(CharacterData other) {
        this.name = other.name;
        this.description = other.description;
        copyProperties(other);
        skills.addAll(other.skills);
        tags.addAll(other.tags);
    }
    
    public void copyProperties(CharacterData other) {
        this.health = other.health;
        this.healthMax = other.healthMax;
        this.speed = other.speed;
        this.stunned = other.stunned;
        this.stunEnemyOnHit = other.stunEnemyOnHit;
        this.stunEnemyOnNextHit = other.stunEnemyOnNextHit;
        this.damageMitigation = other.damageMitigation;
        this.extraDamage = other.extraDamage;
        this.extraDamageNextTurn = other.extraDamageNextTurn;
        this.extraDamageIfNotHurt = other.extraDamageIfNotHurt;
        this.killOnBattleCompletion = other.killOnBattleCompletion;
        this.blockNextAttack = other.blockNextAttack;
        this.counterNextAttack = other.counterNextAttack;
        this.delayedDamage = other.delayedDamage;
    }
    
    public void addTag(String tag, boolean addFirstSkill) {
        var tagData = GameData.findTag(tag);
        tagData = new TagData(tagData);
        tags.add(tagData);
        
        healthMax += tagData.healthModifier;
        if (healthMax > 0) health += healthMax;
        
        speed += tagData.speedModifier;
        
        if (addFirstSkill && tagData.availableSkills.size > 0) {
            addSkill(tagData.availableSkills.first());
            tagData.availableSkills.removeIndex(0);
        }
    }
    
    public void addSkill(String skill) {
        for (var skillData : skills) {
            if (skill.equals(skillData.name)) return;
        }
        
        var skillData = GameData.findSkill(skill);
        skills.add(new SkillData(skillData));
    }
}
