package pt.joaomneto.titancompanion.adventure.impl.fragments.rc;

import pt.joaomneto.titancompanion.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Joao on 15-08-2015.
 */
public enum RobotSpecialAbility {


    SUPER_COWBOY_ROBOT_SONIC_SCREAM(9, R.string.robotSpecialAbilitySonicScream, R.string.robotSpecialAbilitySonicScreamDesc),
    DIGGER_ROBOT_SHOVEL(13, R.string.robotSpecialAbilityShovel, R.string.robotSpecialAbilityShovelDesc),
    WASP_FIGHTER_SPECIAL_ATTACK(41, R.string.robotSpecialAbilityFlyCircles, R.string.robotSpecialAbilityFlyCirclesDesc),
    DRAGONFLY_MODEL_D_EVADE(47, R.string.robotSpecialAbilityUltraFast, R.string.robotSpecialAbilityUltraFastDesc),
    TROOPER_XI_HUMAN_SHIELD(167, R.string.robotSpecialAbilityShield, R.string.robotSpecialAbilityShieldDEsc),
    SERPENT_VII_COIL(208, R.string.robotSpecialAbilitySerpentCoil, R.string.robotSpecialAbilitySerpentCoilDesc),
    ROBOTANK_SONIC_SHOT(247, R.string.robotSpecialAbilitySonicShot, R.string.robotSpecialAbilitySonicShotDesc),
    HEDGEHOG_ANTI_AIR(261, R.string.robotSpecialAbilityAirDefense, R.string.robotSpecialAbilityAirDefenseDesc),
    ENEMY_CRUSHER_DOUBLE_ATTACK(27, R.string.robotSpecialAbilityDoubleDamage, R.string.robotSpecialAbilityDoubleDamageDesc),
    ENEMY_BATTLEMAN_EXTRA_DAMAGE(108, R.string.robotSpecialAbilityCriticalHit, R.string.robotSpecialAbilityCriticalHitDesc),
    ENEMY_SUPERTANK_SMALL_WEAPONS(156, R.string.robotSpecialAbilitySmallWeapons, R.string.robotSpecialAbilitySmallWeaponsDesc),
    ENEMY_WASP_FIGHTER_SPECIAL_ATTACK(341, R.string.robotSpecialAbilityFlyInCircles, R.string.robotSpecialAbilityFlyInCirclesDesc),
    ENEMY_ANKYLOSAURUS_SPECIAL_ATTACK(400, R.string.robotSpecialAbilityAnkylosaurus, R.string.robotSpecialAbilityAnkylosaurusDesc);


    protected static Map<Integer, RobotSpecialAbility> specialAbilities;

    static {
        specialAbilities = new HashMap<Integer, RobotSpecialAbility>();
        specialAbilities.put(9, SUPER_COWBOY_ROBOT_SONIC_SCREAM);
        specialAbilities.put(13, DIGGER_ROBOT_SHOVEL);
        specialAbilities.put(41, WASP_FIGHTER_SPECIAL_ATTACK);
        specialAbilities.put(47, DRAGONFLY_MODEL_D_EVADE);
        specialAbilities.put(167, TROOPER_XI_HUMAN_SHIELD);
        specialAbilities.put(208, SERPENT_VII_COIL);
        specialAbilities.put(247, ROBOTANK_SONIC_SHOT);
        specialAbilities.put(261, HEDGEHOG_ANTI_AIR);
        specialAbilities.put(27, ENEMY_CRUSHER_DOUBLE_ATTACK);
        specialAbilities.put(108, ENEMY_BATTLEMAN_EXTRA_DAMAGE);
        specialAbilities.put(156, ENEMY_SUPERTANK_SMALL_WEAPONS);
        specialAbilities.put(341, ENEMY_WASP_FIGHTER_SPECIAL_ATTACK);
        specialAbilities.put(400, ENEMY_ANKYLOSAURUS_SPECIAL_ATTACK);
    }

    Integer reference;
    Integer name;
    Integer description;

    RobotSpecialAbility(Integer reference, Integer name, Integer description) {
        this.reference = reference;
        this.name = name;
        this.description = description;
    }

    public static RobotSpecialAbility getAbiliyByReference(Integer reference) {
        return specialAbilities.get(reference);
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }
}
