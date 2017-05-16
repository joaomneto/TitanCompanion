package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

import java.util.HashMap;
import java.util.Map;

import pt.joaomneto.titancompanion.R;

public enum RobotSpeed {

    SLOW(0, R.string.robotSpeedSlow), MEDIUM(1, R.string.robotSpeedMedium), FAST(2, R.string.robotSpeedFast), VERY_FAST(3,
            R.string.robotSpeedVeryFast);

    protected static Map<Integer, RobotSpeed> speeds;

    static {
        speeds = new HashMap<Integer, RobotSpeed>();
        speeds.put(0, SLOW);
        speeds.put(1, MEDIUM);
        speeds.put(2, FAST);
        speeds.put(3, VERY_FAST);
    }

    protected static RobotSpeed getSpeedForId(Integer id) {
        return speeds.get(id);
    }

    Integer id;
    int name;

    public int getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(int name) {
        this.name = name;
    }

    private RobotSpeed(Integer id, int name) {
        this.name = name;
        this.id = id;
    }

    public boolean gt(RobotSpeed rs) {
        return id > rs.getId();
    }

    public boolean gte(RobotSpeed rs) {
        return id >= rs.getId();
    }

    public boolean lt(RobotSpeed rs) {
        return id < rs.getId();
    }

    public boolean lte(RobotSpeed rs) {
        return id <= rs.getId();
    }
}