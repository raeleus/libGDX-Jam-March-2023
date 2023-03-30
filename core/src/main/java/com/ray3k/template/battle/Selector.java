package com.ray3k.template.battle;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.ray3k.template.screens.*;

public class Selector {
    public static Array<Table> selectAnyEnemy(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        for (var tile : playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles()) {
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyEnemyFrontRow(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        for (int i =0; i < 3; i++) {
            var tile = tiles.get(i);
            
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyEnemyBackRow(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        for (int i =3; i < 6; i++) {
            var tile = tiles.get(i);
            
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyEnemyCorners(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        
        var tile = tiles.get(0);
        if (tile.getUserObject() != null) returnValue.add(tile);
    
        tile = tiles.get(2);
        if (tile.getUserObject() != null) returnValue.add(tile);
    
        tile = tiles.get(3);
        if (tile.getUserObject() != null) returnValue.add(tile);
    
        tile = tiles.get(5);
        if (tile.getUserObject() != null) returnValue.add(tile);
        
        return returnValue;
    }
    
    public static Array<Table> selectAnyEnemyBackRowIfFrontIsEmpty(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        for (int i =3; i < 6; i++) {
            var tile = tiles.get(i);
            
            if (tiles.get(i - 3).getUserObject() == null && tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyAlly(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        for (var tile : playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles()) {
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyAllyNotSelf(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        for (int i = 0; i < tiles.size; i++) {
            if (i != characterPosition) {
                var tile = tiles.get(i);
                if (tile.getUserObject() != null) returnValue.add(tile);
            }
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyAllyFrontRow(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        for (int i =0; i < 3; i++) {
            var tile = tiles.get(i);
            
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyAllyBackRow(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        for (int i =3; i < 6; i++) {
            var tile = tiles.get(i);
            
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectMelee(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var playerTiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        var enemyTiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        
        //if in back row and players are in front
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                var tile = playerTiles.get(i);
                if (tile.getUserObject() != null) return returnValue;
            }
        }
        
        var targetIndex = characterPosition < 3 ? characterPosition : characterPosition - 3;
        //front row
        var tile = enemyTiles.get(targetIndex);
        if (tile.getUserObject() != null) returnValue.add(tile);
        
        //check to left
        if (targetIndex - 1 >= 0) {
            tile = enemyTiles.get(targetIndex - 1);
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        
        //check to right
        if (targetIndex + 1 < 3) {
            tile = enemyTiles.get(targetIndex + 1);
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        
        //if no targets
        if (returnValue.size == 0) {
            //check far right
            if (targetIndex + 2 < 3) {
                tile = enemyTiles.get(targetIndex + 2);
                if (tile.getUserObject() != null) returnValue.add(tile);
            } else if (targetIndex - 2 >= 0) { //check far left
                tile = enemyTiles.get(targetIndex - 2);
                if (tile.getUserObject() != null) returnValue.add(tile);
            }
        }
        
        //if still no targets
        if (returnValue.size == 0) {
            targetIndex += 3;
    
            //back row
            tile = enemyTiles.get(targetIndex);
            if (tile.getUserObject() != null) returnValue.add(tile);
    
            //check to left
            if (targetIndex - 1 >= 3) {
                tile = enemyTiles.get(targetIndex - 1);
                if (tile.getUserObject() != null) returnValue.add(tile);
            }
    
            //check to right
            if (targetIndex + 1 < 6) {
                tile = enemyTiles.get(targetIndex + 1);
                if (tile.getUserObject() != null) returnValue.add(tile);
            }
    
            //if no targets
            if (returnValue.size == 0) {
                //check far right
                if (targetIndex + 2 < 6) {
                    tile = enemyTiles.get(targetIndex + 2);
                    if (tile.getUserObject() != null) returnValue.add(tile);
                } else if (targetIndex - 2 >= 3) { //check far left
                    tile = enemyTiles.get(targetIndex - 2);
                    if (tile.getUserObject() != null) returnValue.add(tile);
                }
            }
        }
        return returnValue;
    }
    
    public static Array<Table> selectMeleeDirectlyInFront(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var playerTiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        var enemyTiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                var tile = playerTiles.get(i);
                
                if (tile.getUserObject() != null) return returnValue;
            }
            characterPosition -= 3;
        }
        
        var offset = 3;
        for (int i =0; i < 3; i++) {
            var tile = enemyTiles.get(i);
            
            if (tile.getUserObject() != null) {
                offset = 0;
                break;
            }
        }
        
        if (enemyTiles.get(characterPosition + offset).getUserObject() != null) returnValue.add(enemyTiles.get(characterPosition + offset));
        
        return returnValue;
    }
    
    public static Array<Table> selectMeleeDiagonalOnly(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var playerTiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        var enemyTiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                var tile = playerTiles.get(i);
                
                if (tile.getUserObject() != null) return returnValue;
            }
            characterPosition -= 3;
        }
        
        var offset = 3;
        for (int i =0; i < 3; i++) {
            var tile = enemyTiles.get(i);
            
            if (tile.getUserObject() != null) {
                offset = 0;
                break;
            }
        }
        
        if (characterPosition - 1 >= offset && enemyTiles.get(characterPosition - 1 + offset).getUserObject() != null) returnValue.add(enemyTiles.get(characterPosition - 1 + offset));
        else if (characterPosition + 1 < 3 + offset && enemyTiles.get(characterPosition + 1 + offset).getUserObject() != null) returnValue.add(enemyTiles.get(characterPosition + 1 + offset));
        
        return returnValue;
    }
    
    public static Array<Table> selectSelf(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        returnValue.add(tiles.get(characterPosition));
        return returnValue;
    }
    
    public static Array<Table> selectEmptyAllyForward(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        
        if (characterPosition < 3) return returnValue;
        
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        var tile = tiles.get(characterPosition - 3);
        if (tile.getUserObject() == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<Table> selectEmptyAllyBackward(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        
        if (characterPosition >= 3) return returnValue;
        
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        var tile = tiles.get(characterPosition + 3);
        if (tile.getUserObject() == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<Table> selectEmptyAllySide(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        
        var rangeMin = characterPosition < 3 ? 0 : 3;
        var rangeMax = characterPosition < 3 ? 3 : 6;
        
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        var tile = tiles.get(characterPosition - 1);
        if (characterPosition >= rangeMin && tile.getUserObject() == null) returnValue.add(tile);
        
        tile = tiles.get(characterPosition + 1);
        if (characterPosition < rangeMax && tile.getUserObject() == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<Table> selectEmptyAllyForwardOrBackward(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        
        Table tile;
        if (characterPosition < 3) tile = tiles.get(characterPosition + 3);
        else tile = tiles.get(characterPosition - 3);
        
        if (tile.getUserObject() == null) returnValue.add(tile);
        return returnValue;
    }
    
    public static Array<Table> selectEmptyAllyAdjacent(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
    
        Table tile;
        if (characterPosition < 3) tile = tiles.get(characterPosition + 3);
        else tile = tiles.get(characterPosition - 3);
        
        if (tile.getUserObject() == null) returnValue.add(tile);
        
        var rangeMin = characterPosition < 3 ? 0 : 3;
        var rangeMax = characterPosition < 3 ? 3 : 6;
        
        if (characterPosition - 1 >= rangeMin) {
            tile = tiles.get(characterPosition - 1);
            if (tile.getUserObject() == null) returnValue.add(tile);
        }
        
        if (characterPosition + 1 < rangeMax && tile.getUserObject() == null) {
            tile = tiles.get(characterPosition + 1);
            if (tile.getUserObject() == null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyEmptyAlly(BattleScreen battle, boolean playerTeam) {
        var returnValue = new Array<Table>();
    
        var tiles = playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        for (var tile : tiles) {
            if (tile.getUserObject() == null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectAnyCharacter(BattleScreen battle) {
        var returnValue = new Array<Table>();
    
        for (var tile : battle.getEnemyTiles()) {
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
    
        for (var tile : battle.getPlayerTiles()) {
            if (tile.getUserObject() != null) returnValue.add(tile);
        }
        return returnValue;
    }
    
    public static Array<Table> selectEnemyColumnDirectlyInFront(BattleScreen battle, boolean playerTeam, int characterPosition) {
        var returnValue = new Array<Table>();
        var enemyTiles = playerTeam ? battle.getEnemyTiles() : battle.getPlayerTiles();
        var playerTiles =  playerTeam ? battle.getPlayerTiles() : battle.getEnemyTiles();
        
        if (characterPosition >= 3) {
            for (int i = 0; i < 3; i++) {
                if (playerTiles.get(i).getUserObject() != null) return returnValue;
            }
        }
        
        if (enemyTiles.get(characterPosition).getUserObject() != null) returnValue.add(enemyTiles.get(characterPosition));
        if (characterPosition >= 3 && enemyTiles.get(characterPosition - 3).getUserObject() != null) returnValue.add(enemyTiles.get(characterPosition - 3));
        else if (characterPosition < 3 && enemyTiles.get(characterPosition + 3).getUserObject() != null) returnValue.add(enemyTiles.get(characterPosition + 3));
        return returnValue;
    }
}