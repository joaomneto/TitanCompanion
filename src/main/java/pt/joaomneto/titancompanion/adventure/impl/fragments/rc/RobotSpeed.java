package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.util.TranslatableEnum;

import java.util.HashMap;
import java.util.Map;

public enum RobotSpeed implements TranslatableEnum {

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

    Integer id;
    int labelId;

    RobotSpeed(Integer id, int labelId) {
        this.labelId = labelId;
        this.id = id;
    }

    protected static RobotSpeed getSpeedForId(Integer id) {
        return speeds.get(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public int getLabelId() {
        return labelId;
    }
}