package pt.joaomneto.ffgbutil.adventure.impl.util;

public class DiceRoll {

	final Integer d1;
	final Integer d2;

	public Integer getD1() {
		return d1;
	}

	public Integer getD2() {
		return d2;
	}

	public DiceRoll(Integer d1, Integer d2) {
		super();
		this.d1 = d1;
		this.d2 = d2;
	}

	public Integer getSum(){
		return d1+d2;
	}

	@Override
	public String toString() {
		return "DiceRoll{" +
				"d1=" + d1 +
				", d2=" + d2 +
				", total="+ (d1+d2) +"}";
	}
}
