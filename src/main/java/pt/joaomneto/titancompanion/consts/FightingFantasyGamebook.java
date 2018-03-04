package pt.joaomneto.titancompanion.consts;

import pt.joaomneto.titancompanion.R;
import android.support.v4.app.Fragment;
import pt.joaomneto.titancompanion.util.AdventureFragmentRunner;

/**
 * Created by 962633 on 01-09-2017.
 */

public enum FightingFantasyGamebook {

    THE_WARLOCK_OF_FIRETOP_MOUNTAIN(R.string.twofm,"twofm", 1),
    THE_CITADEL_OF_CHAOS(R.string.tcoc,"tcoc", 2),
    THE_FOREST_OF_DOOM(R.string.tfod,"tfod", 3),
    STARSHIP_TRAVELLER(R.string.st,"st", 4),
    CITY_OF_THIEVES(R.string.cot,"cot", 5),
    DEATHTRAP_DUNGEON(R.string.dd,"dd", 6),
    ISLAND_OF_THE_LIZARD_KING(R.string.iotlk,"iotlk", 7),
    SCORPION_SWAMP(R.string.ss,"ss", 8),
    CAVERNS_OF_THE_SNOW_WITCH(R.string.cotsw,"cotsw", 9),
    HOUSE_OF_HELL(R.string.hoh,"hoh", 10),
    TALISMAN_OF_DEATH(R.string.tod,"tod", 11),
    SPACE_ASSASSIN(R.string.sa,"sa", 12),
    FREEWAY_FIGHTER(R.string.ff,"ff", 13),
    TEMPLE_OF_TERROR(R.string.tot,"tot", 14),
    THE_RINGS_OF_KETHER(R.string.trok,"trok", 15),
    SEAS_OF_BLOOD(R.string.sob,"sob", 16),
    APPOINTMENT_WITH_F_E_A_R(R.string.awf,"awf", 17),
    REBEL_PLANET(R.string.rp,"rp", 18),
    DEMONS_OF_THE_DEEP(R.string.dotd,"dotd", 19),
    SWORD_OF_THE_SAMURAI(R.string.sots,"sots", 20),
    TRIAL_OF_CHAMPIONS(R.string.toc,"toc", 21),
    ROBOT_COMMANDO(R.string.rc,"rc", 22),
    MASKS_OF_MAYHEM(R.string.mom,"mom", 23),
    CREATURE_OF_HAVOC(R.string.coh,"coh", 24),
    BENEATH_NIGHTMARE_CASTLE(R.string.bnc,"bnc", 25),
    CRYPT_OF_THE_SORCERER(R.string.cots,"cots", 26),
    STAR_STRIDER(R.string.strider,"strider", 27),
    PHANTOMS_OF_FEAR(R.string.pof,"pof", 28),
    MIDNIGHT_ROGUE(R.string.mr,"mr", 29),
    CHASMS_OF_MALICE(R.string.com,"com", 30),
    BATTLEBLADE_WARRIOR(R.string.bw,"bw", 31),
    SLAVES_OF_THE_ABYSS(R.string.sota,"sota", 32),
    SKY_LORD(R.string.sl,"sl", 33),
    STEALER_OF_SOULS(R.string.sos,"sos", 34),
    DAGGERS_OF_DARKNESS(R.string.dod,"dod", 35),
    ARMIES_OF_DEATH(R.string.aod,"aod", 36),
    PORTAL_OF_EVIL(R.string.poe,"poe", 37),
    VAULT_OF_THE_VAMPIRE(R.string.votv,"votv", 38),
    FANGS_OF_FURY(R.string.fof,"fof", 39),
    DEAD_OF_NIGHT(R.string.don,"don", 40),
    MASTER_OF_CHAOS(R.string.moc,"moc", 41),
    BLACK_VEIN_PROPHECY(R.string.bvp,"bvp", 42),
    THE_KEEP_OF_THE_LICH_LORD(R.string.tkotll,"tkotll", 43),
    LEGEND_OF_THE_SHADOW_WARRIORS(R.string.lotsw,"lotsw", 44),
    SPECTRAL_STALKERS(R.string.spectral,"spectral", 45),
    TOWER_OF_DESTRUCTION(R.string.tower,"tower", 46),
    THE_CRIMSON_TIDE(R.string.tct,"tct", 47),
    MOONRUNNER(R.string.moon,"moon", 48),
    SIEGE_OF_SARDATH(R.string.siege,"siege", 49),
    RETURN_TO_FIRETOP_MOUNTAIN(R.string.rtfm,"rtfm", 50),
    ISLAND_OF_THE_UNDEAD(R.string.iotu,"iotu", 51),
    NIGHT_DRAGON(R.string.nd,"nd", 52),
    SPELLBREAKER(R.string.s,"s", 53),
    LEGEND_OF_ZAGOR(R.string.loz,"loz", 54),
    DEATHMOOR(R.string.d,"d", 55),
    KNIGHTS_OF_DOOM(R.string.kod,"kod", 56),
    MAGEHUNTER(R.string.m,"m", 57),
    REVENGE_OF_THE_VAMPIRE(R.string.rotv,"rotv", 58),
    CURSE_OF_THE_MUMMY(R.string.cotm, "cotm", 59),
    EYE_OF_THE_DRAGON(R.string.eotd, "eotd", 60),
    BLOODBONES(R.string.bb, "bb", 61),
    HOWL_OF_THE_WEREWOLF(R.string.hotw, "hotw", 62),
    THE_PORT_OF_PERIL(R.string.tpop, "tpop", 63);

    private final int nameResourceId;
    private final int order;
    private final String initials;


    FightingFantasyGamebook(int nameResourceId, String initials, int order) {
        this.nameResourceId = nameResourceId;
        this.order = order;
        this.initials = initials;
    }

    public int getNameResourceId() {
        return nameResourceId;
    }

    public int getOrder() {
        return order;
    }

    public String getInitials() {
        return initials;
    }
}
