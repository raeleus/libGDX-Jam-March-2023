package com.ray3k.template.battle;

import com.badlogic.gdx.utils.Array;
import com.ray3k.template.data.CharacterData;

public class Battle {
    public Array<BattleTile> enemyTiles = new Array<>();
    public Array<BattleTile> playerTiles = new Array<>();
    public int round;
    public Array<CharacterData> order = new Array<>();
    public Array<CharacterData> nextOrder = new Array<>();
}
