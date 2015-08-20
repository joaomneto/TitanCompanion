package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

public enum RobotSpeed {

	SLOW(0, "Slow"), MEDIUM(1, "Medium"), FAST(2, "Fast"), VERY_FAST(3,
			"Very Fast");

	Integer id;
	String name;

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	private RobotSpeed(Integer id, String name) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}

}