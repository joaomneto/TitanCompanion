package pt.joaomneto.titancompanion.consts;

import pt.joaomneto.titancompanion.R;

/**
 * Created by 962633 on 01-09-2017.
 */

public enum FightingFantasyGamebook {

    THE_WARLOCK_OF_FIRETOP_MOUNTAIN(R.string.twofm),
    THE_CITADEL_OF_CHAOS(R.string.tcoc),
    THE_FOREST_OF_DOOM(R.string.tfod),
    STARSHIP_TRAVELLER(R.string.st),
    CITY_OF_THIEVES(R.string.cot),
    DEATHTRAP_DUNGEON(R.string.dd),
    ISLAND_OF_THE_LIZARD_KING(R.string.iotlk),
    SCORPION_SWAMP(R.string.ss),
    CAVERNS_OF_THE_SNOW_WITCH(R.string.cotsw),
    HOUSE_OF_HELL(R.string.hoh),
    TALISMAN_OF_DEATH(R.string.tod),
    SPACE_ASSASSIN(R.string.sa),
    FREEWAY_FIGHTER(R.string.ff),
    TEMPLE_OF_TERROR(R.string.tot),
    THE_RINGS_OF_KETHER(R.string.trok),
    SEAS_OF_BLOOD(R.string.sob),
    APPOINTMENT_WITH_F_E_A_R(R.string.awf),
    REBEL_PLANET(R.string.rp),
    DEMONS_OF_THE_DEEP(R.string.dotd),
    SWORD_OF_THE_SAMURAI(R.string.sots),
    TRIAL_OF_CHAMPIONS(R.string.toc),
    ROBOT_COMMANDO(R.string.rc),
    MASKS_OF_MAYHEM(R.string.mom),
    CREATURE_OF_HAVOC(R.string.coh),
    BENEATH_NIGHTMARE_CASTLE(R.string.bnc),
    CRYPT_OF_THE_SORCERER(R.string.cots),
    STAR_STRIDER(R.string.strider),
    PHANTOMS_OF_FEAR(R.string.pof),
    MIDNIGHT_ROGUE(R.string.mr),
    CHASMS_OF_MALICE(R.string.com),
    BATTLEBLADE_WARRIOR(R.string.bw),
    SLAVES_OF_THE_ABYSS(R.string.sota),
    SKY_LORD(R.string.sl),
    STEALER_OF_SOULS(R.string.sos),
    DAGGERS_OF_DARKNESS(R.string.dod),
    ARMIES_OF_DEATH(R.string.aod),
    PORTAL_OF_EVIL(R.string.poe),
    VAULT_OF_THE_VAMPIRE(R.string.votv),
    FANGS_OF_FURY(R.string.fof),
    DEAD_OF_NIGHT(R.string.don),
    MASTER_OF_CHAOS(R.string.moc),
    BLACK_VEIN_PROPHECY(R.string.bvp),
    THE_KEEP_OF_THE_LICH_LORD(R.string.tkotll),
    LEGEND_OF_THE_SHADOW_WARRIORS(R.string.lotsw),
    SPECTRAL_STALKERS(R.string.spectral),
    TOWER_OF_DESTRUCTION(R.string.tower),
    THE_CRIMSON_TIDE(R.string.tct),
    MOONRUNNER(R.string.moon),
    SIEGE_OF_SARDATH(R.string.siege),
    RETURN_TO_FIRETOP_MOUNTAIN(R.string.rtfm),
    ISLAND_OF_THE_UNDEAD(R.string.iotu),
    NIGHT_DRAGON(R.string.nd),
    SPELLBREAKER(R.string.s),
    LEGEND_OF_ZAGOR(R.string.loz),
    DEATHMOOR(R.string.d),
    KNIGHTS_OF_DOOM(R.string.kod),
    MAGEHUNTER(R.string.m),
    REVENGE_OF_THE_VAMPIRE(R.string.rotv),
    CURSE_OF_THE_MUMMY(R.string.cotm);


    private final int nameResourceId;


    FightingFantasyGamebook(int nameResourceId) {
        this.nameResourceId = nameResourceId;
    }

    public int getNameResourceId() {
        return nameResourceId;
    }
}
