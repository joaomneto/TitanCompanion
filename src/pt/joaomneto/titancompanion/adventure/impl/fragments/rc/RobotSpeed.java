package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

import java.util.HashMap;
import java.util.Map;

public enum RobotSpeed {

    SLOW(0, "Slow"), MEDIUM(1, "Medium"), FAST(2, "Fast"), VERY_FAST(3,
            "Very Fast");

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