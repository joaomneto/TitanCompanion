package pt.joaomneto.ffgbutil.adventure.impl.fragments.rc;

/**
 * Created by Joao on 15-08-2015.
 */
public enum RobotSpecialAbility {

    SUPER_COWBOY_ROBOT_SONIC_SCREAM(9, "Sonic Scream", "Reduces dinosaur scream skill by 1."),
    DIGGER_ROBOT_SHOVEL(13, "Shovel", "If using shovel, 2 points gets subtracted to combat roll. When hitting the robot deals 6 damage."),
    WASP_FIGHTER_SPECIAL_ATTACK(41, "Fly in circles", "If the combat roll is won by 4 or more points, Wasp Fighter deals double damage."),
    TROOPER_XI_HUMAN_SHIELD(167, "Shield", "If combat roll is 18 or more, the Tropper XI suffers no damage."),
    SERPENT_VII_COIL(208, "Serpent Coil", "After a combat roll bigger than 16, the Serpent VII deals 1 automatic damage point each turn afterwards."),
    ROBOTANK_SONIC_SHOT(247, "Sonic Shot", "3 optional sonic shots. Deals 1d6 damage for robots and 2d6 damage for dinosaurs."),
    ENEMY_CRUSHER_DOUBLE_ATTACK(27, "Double Damage", "The Crusher deals double damage in each successfull attack."),
    ENEMY_BATTLEMAN_EXTRA_DAMAGE(108, "Critical Hit", "When battleman wins the round with a roll difference of 4 or more points it deals an additional damange point."),
    ENEMY_SUPERTANK_SMALL_WEAPONS(156, "Small Weapons", "Reduces dinosaur scream skill by 1"),
    ENEMY_WASP_FIGHTER_SPECIAL_ATTACK(341, "Fly in circles", "If the combat roll is won by 4 or more points, Wasp Fighter deals double damage."),
    ENEMY_ANKYLOSAURUS_SPECIAL_ATTACK(400, "Ankylosaurus stun", "Player can only parry in next round after losing a combat round.");


    int reference;
    String name;
    String description;

    RobotSpecialAbility(int reference, String name, String description) {
        this.reference = reference;
        this.name = name;
        this.description = description;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
