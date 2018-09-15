package Main;

public class Keylogger{
	public final int BUFFERED_FRAME = 0;
	public final int UNPRESSED = -1;
	private int validKeys[];
	private int heldKeys[];
	private boolean pressedKeys[];
	public Keylogger(int validKeys[]){
		this.validKeys = validKeys;
		pressedKeys = new boolean [validKeys.length];
		heldKeys = new int [validKeys.length];
		for(int i = 0; i < heldKeys.length; i ++){
			pressedKeys[i] = false;
			heldKeys[i] = -1 ;
		}
	}
	public boolean isPressed(int keySearched){
		for(int i = 0; i < validKeys.length;i ++)
		{
			if(keySearched == validKeys[i]){
				return pressedKeys[i];
			}
		}
		return false;
	}
	public boolean[] isFirstFrame(){
		boolean[] tempArray = new boolean [validKeys.length];
		for(int i = 0; i < validKeys.length;i ++)
		{
			if(heldKeys[i] == 1){
				tempArray[i] = true;

			}
			else
			{
				tempArray[i] = false;
			}
		}
		return tempArray;
	}
	public int[] getInputs(){
		return heldKeys;
	}
	public void update(){

		for(int i = 0; i < heldKeys.length; i ++){
			if(pressedKeys[i])
			{
				//System.out.println("Key: " + validKeys[i]+ " frames:"+ heldKeys[i]);
				heldKeys[i] ++;
			}
		}
	}
	public boolean addKey(int keyPressed){
		for(int i = 0; i < validKeys.length;i ++)
		{
			if(keyPressed == validKeys[i]){
				//System.out.println(pressedKeys[i]);
				if(!pressedKeys[i]){
					pressedKeys[i] = true;
					heldKeys[i] = 0;
					return true;

				}
			}
		}
		return false;
	}
	public boolean removeKey(int keyPressed){
		for(int i = 0; i < validKeys.length;i ++)
		{
			if(keyPressed == validKeys[i] ){
				pressedKeys[i] = false;
				heldKeys[i] = -1 ;
				return true ;
			}
		}
		return false;
	}
}
