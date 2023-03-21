package com.ray3k.template.data;

import com.badlogic.gdx.utils.Array;

public class CharacterData {
    public String name;
    public String description;
    public float health;
    public float healthMax = 100f;
    public Array<String> skills = new Array<>();
    public Array<String> tags = new Array<>();
}
