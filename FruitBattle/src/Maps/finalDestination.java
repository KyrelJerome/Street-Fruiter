package Maps;

import java.awt.Graphics2D;

import dataStructures.CharacterObject;
import dataStructures.GameObject;

public class finalDestination extends Maps {

	public finalDestination(){
		super(2);
		backgroundLayers.add(new Background("/backgrounds/Background2.jpg",2));
		backgroundLayers.get(0).setPosition(0, 0);
		backgroundLayers.get(0).setVector(2, 0);
		//backgroundLayers.get(0).changeHeight();
		MapObjects.add(new FDTerrain());
		int tempspawnPointsX []= {400,1300,1700,900};
		int tempspawnPointsY []= {750,750,750,750};
		spawnPointsX = tempspawnPointsX;
		spawnPointsY = tempspawnPointsY;
	}

	public void init() {
		
	}

}
