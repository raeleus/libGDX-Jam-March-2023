package com.ray3k.template.battle;

import com.badlogic.gdx.utils.Array;

public class Selector {
    public static Array<BattleTile> selectAnyEnemy(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        for (var tile : playerTeam ? battle.enemyTiles : battle.playerTiles) {
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyEnemyFrontRow(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        for (int i =0; i < 3; i++) {
            var tile = tiles.get(i);
            
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyEnemyBackRow(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        for (int i =3; i < 6; i++) {
            var tile = tiles.get(i);
            
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyEnemyCorners(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        
        var tile = tiles.get(0);
        if (tile.characterData != null) returnValue.add(tile);
    
        tile = tiles.get(2);
        if (tile.characterData != null) returnValue.add(tile);
    
        tile = tiles.get(3);
        if (tile.characterData != null) returnValue.add(tile);
    
        tile = tiles.get(5);
        if (tile.characterData != null) returnValue.add(tile);
        
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyEnemyBackRowIfFrontIsEmpty(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        for (int i =3; i < 6; i++) {
            var tile = tiles.get(i);
            
            if (tiles.get(i - 3).characterData == null && tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyAlly(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        for (var tile : playerTeam ? battle.playerTiles : battle.enemyTiles) {
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyAllyNotSelf(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        for (int i = 0; i < tiles.size; i++) {
            if (i != characterPosition) {
                var tile = tiles.get(i);
                if (tile.characterData != null) returnValue.add(tile);
            }
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyAllyFrontRow(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        for (int i =0; i < 3; i++) {
            var tile = tiles.get(i);
            
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyAllyBackRow(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        for (int i =3; i < 6; i++) {
            var tile = tiles.get(i);
            
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectMelee(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var playerTiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        var enemyTiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                var tile = playerTiles.get(i);
                
                if (tile.characterData != null) return returnValue;
            }
            characterPosition -= 3;
        }
        
        var offset = 3;
        for (int i =0; i < 3; i++) {
            var tile = enemyTiles.get(i);
            
            if (tile.characterData != null) {
                offset = 0;
                break;
            }
        }
        
        if (enemyTiles.get(characterPosition + offset).characterData != null) returnValue.add(enemyTiles.get(characterPosition + offset));
        else if (characterPosition - 1 >= offset && enemyTiles.get(characterPosition - 1 + offset).characterData != null) returnValue.add(enemyTiles.get(characterPosition - 1 + offset));
        else if (characterPosition + 1 < 3 + offset && enemyTiles.get(characterPosition + 1 + offset).characterData != null) returnValue.add(enemyTiles.get(characterPosition + 1 + offset));
        
        return returnValue;
    }
    
    public static Array<BattleTile> selectMeleeDirectlyInFront(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var playerTiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        var enemyTiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                var tile = playerTiles.get(i);
                
                if (tile.characterData != null) return returnValue;
            }
            characterPosition -= 3;
        }
        
        var offset = 3;
        for (int i =0; i < 3; i++) {
            var tile = enemyTiles.get(i);
            
            if (tile.characterData != null) {
                offset = 0;
                break;
            }
        }
        
        if (enemyTiles.get(characterPosition + offset).characterData != null) returnValue.add(enemyTiles.get(characterPosition + offset));
        
        return returnValue;
    }
    
    public static Array<BattleTile> selectMeleeDiagonalOnly(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var playerTiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        var enemyTiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                var tile = playerTiles.get(i);
                
                if (tile.characterData != null) return returnValue;
            }
            characterPosition -= 3;
        }
        
        var offset = 3;
        for (int i =0; i < 3; i++) {
            var tile = enemyTiles.get(i);
            
            if (tile.characterData != null) {
                offset = 0;
                break;
            }
        }
        
        if (characterPosition - 1 >= offset && enemyTiles.get(characterPosition - 1 + offset).characterData != null) returnValue.add(enemyTiles.get(characterPosition - 1 + offset));
        else if (characterPosition + 1 < 3 + offset && enemyTiles.get(characterPosition + 1 + offset).characterData != null) returnValue.add(enemyTiles.get(characterPosition + 1 + offset));
        
        return returnValue;
    }
    
    public static Array<BattleTile> selectSelf(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        returnValue.add(tiles.get(characterPosition));
        return returnValue;
    }
    
    public static Array<BattleTile> selectEmptyAllyForward(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        
        if (characterPosition < 3) return returnValue;
        
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        var tile = tiles.get(characterPosition - 3);
        if (tile.characterData == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<BattleTile> selectEmptyAllyBackward(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        
        if (characterPosition >= 3) return returnValue;
        
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        var tile = tiles.get(characterPosition + 3);
        if (tile.characterData == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<BattleTile> selectEmptyAllySide(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        
        var rangeMin = characterPosition < 3 ? 0 : 3;
        var rangeMax = characterPosition < 3 ? 3 : 6;
        
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        var tile = tiles.get(characterPosition - 1);
        if (characterPosition >= rangeMin && tile.characterData == null) returnValue.add(tile);
        
        tile = tiles.get(characterPosition + 1);
        if (characterPosition < rangeMax && tile.characterData == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<BattleTile> selectEmptyAllyForwardOrBackward(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        
        BattleTile tile;
        if (characterPosition < 3) tile = tiles.get(characterPosition + 3);
        else tile = tiles.get(characterPosition - 3);
        
        if (tile.characterData == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<BattleTile> selectEmptyAllyAdjacent(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
    
        BattleTile tile;
        if (characterPosition < 3) tile = tiles.get(characterPosition + 3);
        else tile = tiles.get(characterPosition - 3);
        
        if (tile.characterData == null) returnValue.add(tile);
        
        var rangeMin = characterPosition < 3 ? 0 : 3;
        var rangeMax = characterPosition < 3 ? 3 : 6;
        
        tile = tiles.get(characterPosition - 1);
        if (characterPosition >= rangeMin && tile.characterData == null) returnValue.add(tile);
        
        tile = tiles.get(characterPosition + 1);
        if (characterPosition < rangeMax && tile.characterData == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyEmptyAlly(Battle battle, boolean playerTeam) {
        var returnValue = new Array<BattleTile>();
    
        var tiles = playerTeam ? battle.playerTiles : battle.enemyTiles;
        for (var tile : tiles) {
            if (tile.characterData == null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectAnyCharacter(Battle battle) {
        var returnValue = new Array<BattleTile>();
    
        for (var tile : battle.enemyTiles) {
            if (tile.characterData != null) returnValue.add(tile);
        }
    
        for (var tile : battle.playerTiles) {
            if (tile.characterData != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<BattleTile> selectEnemyColumnDirectlyInFront(Battle battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<BattleTile>();
        var enemyTiles = playerTeam ? battle.enemyTiles : battle.playerTiles;
        var playerTiles =  playerTeam ? battle.playerTiles : battle.enemyTiles;
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                if (playerTiles.get(i).characterData != null) return returnValue;
            }
        }
        
        if (enemyTiles.get(characterPosition).characterData != null) returnValue.add(enemyTiles.get(characterPosition));
        if (characterPosition >= 3 && enemyTiles.get(characterPosition - 3).characterData != null) returnValue.add(enemyTiles.get(characterPosition - 3));
        else if (characterPosition < 3 && enemyTiles.get(characterPosition + 3).characterData != null) returnValue.add(enemyTiles.get(characterPosition + 3));
        return returnValue;
    }
}