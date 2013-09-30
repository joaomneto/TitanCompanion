package pt.joaomneto.ffgbutil.util;

import java.util.Random;

public abstract class DiceRoller {


	private static Random random = new Random(System.currentTimeMillis()); 
	
	public static int rollD6(){
		return random.nextInt(5)+1;
	}

	public static int roll2D6(){
		return rollD6()+rollD6();
	}
}
