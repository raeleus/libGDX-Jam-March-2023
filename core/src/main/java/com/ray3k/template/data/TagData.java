package com.ray3k.template.data;

import com.badlogic.gdx.utils.Array;

public class TagData {
    public String name;
    public String description;
    public Array<String> keywords = new Array<>();
    public int level = 1;
    public float healthModifier;
    public float speedModifier;
    public Array<String> availableSkills = new Array<>();
}
