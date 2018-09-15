package Maps;

;

public class KitchenArena extends Maps {

	public KitchenArena(){
		super(2);

	}

	public void init() {
		backgroundLayers.add(new Background("/backgrounds/KitchenBackground.png",2));
		backgroundLayers.get(0).setPosition(0, 0);
		backgroundLayers.get(0).setVector(2, 0);
		//backgroundLayers.get(0).changeHeight();
		MapObjects.add(new TableTop());
		int tempspawnPointsX []= {400,1300,1300,400};
		int tempspawnPointsY []= {750,750,550,550};
		spawnPointsX = tempspawnPointsX;
		spawnPointsY = tempspawnPointsY;
	}

}
