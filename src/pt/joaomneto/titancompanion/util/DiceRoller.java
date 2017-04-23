package pt.joaomneto.titancompanion.util;

import java.util.Random;

import pt.joaomneto.titancompanion.adventure.impl.util.DiceRoll;

public abstract class DiceRoller {


	private static Random random = new Random(System.currentTimeMillis()); 
	
	public static int rollD6(){
		return random.nextInt(6)+1;
	}

	public static DiceRoll roll2D6(){
		return new DiceRoll(rollD6(),rollD6());
	}
	

	public static int roll3D6(){
		return rollD6()+rollD6()+rollD6();
	}
	
}


