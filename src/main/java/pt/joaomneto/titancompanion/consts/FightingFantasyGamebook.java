package pt.joaomneto.titancompanion.consts;

import pt.joaomneto.titancompanion.R;

/**
 * Created by 962633 on 01-09-2017.
 */

public enum FightingFantasyGamebook {

    THE_WARLOCK_OF_FIRETOP_MOUNTAIN(R.string.twofm, 1),
    THE_CITADEL_OF_CHAOS(R.string.tcoc, 2),
    THE_FOREST_OF_DOOM(R.string.tfod, 3),
    STARSHIP_TRAVELLER(R.string.st, 4),
    CITY_OF_THIEVES(R.string.cot, 5),
    DEATHTRAP_DUNGEON(R.string.dd, 6),
    ISLAND_OF_THE_LIZARD_KING(R.string.iotlk, 7),
    SCORPION_SWAMP(R.string.ss, 8),
    CAVERNS_OF_THE_SNOW_WITCH(R.string.cotsw, 9),
    HOUSE_OF_HELL(R.string.hoh, 10),
    TALISMAN_OF_DEATH(R.string.tod, 11),
    SPACE_ASSASSIN(R.string.sa, 12),
    FREEWAY_FIGHTER(R.string.ff, 13),
    TEMPLE_OF_TERROR(R.string.tot, 14),
    THE_RINGS_OF_KETHER(R.string.trok, 15),
    SEAS_OF_BLOOD(R.string.sob, 16),
    APPOINTMENT_WITH_F_E_A_R(R.string.awf, 17),
    REBEL_PLANET(R.string.rp, 18),
    DEMONS_OF_THE_DEEP(R.string.dotd, 19),
    SWORD_OF_THE_SAMURAI(R.string.sots, 20),
    TRIAL_OF_CHAMPIONS(R.string.toc, 21),
    ROBOT_COMMANDO(R.string.rc, 22),
    MASKS_OF_MAYHEM(R.string.mom, 23),
    CREATURE_OF_HAVOC(R.string.coh, 24),
    BENEATH_NIGHTMARE_CASTLE(R.string.bnc, 25),
    CRYPT_OF_THE_SORCERER(R.string.cots, 26),
    STAR_STRIDER(R.string.strider, 27),
    PHANTOMS_OF_FEAR(R.string.pof, 28),
    MIDNIGHT_ROGUE(R.string.mr, 29),
    CHASMS_OF_MALICE(R.string.com, 30),
    BATTLEBLADE_WARRIOR(R.string.bw, 31),
    SLAVES_OF_THE_ABYSS(R.string.sota, 32),
    SKY_LORD(R.string.sl, 33),
    STEALER_OF_SOULS(R.string.sos, 34),
    DAGGERS_OF_DARKNESS(R.string.dod, 35),
    ARMIES_OF_DEATH(R.string.aod, 36),
    PORTAL_OF_EVIL(R.string.poe, 37),
    VAULT_OF_THE_VAMPIRE(R.string.votv, 38),
    FANGS_OF_FURY(R.string.fof, 39),
    DEAD_OF_NIGHT(R.string.don, 40),
    MASTER_OF_CHAOS(R.string.moc, 41),
    BLACK_VEIN_PROPHECY(R.string.bvp, 42),
    THE_KEEP_OF_THE_LICH_LORD(R.string.tkotll, 43),
    LEGEND_OF_THE_SHADOW_WARRIORS(R.string.lotsw, 44),
    SPECTRAL_STALKERS(R.string.spectral, 45),
    TOWER_OF_DESTRUCTION(R.string.tower, 46),
    THE_CRIMSON_TIDE(R.string.tct, 47),
    MOONRUNNER(R.string.moon, 48),
    SIEGE_OF_SARDATH(R.string.siege, 49),
    RETURN_TO_FIRETOP_MOUNTAIN(R.string.rtfm, 50),
    ISLAND_OF_THE_UNDEAD(R.string.iotu, 51),
    NIGHT_DRAGON(R.string.nd, 52),
    SPELLBREAKER(R.string.s, 53),
    LEGEND_OF_ZAGOR(R.string.loz, 54),
    DEATHMOOR(R.string.d, 55),
    KNIGHTS_OF_DOOM(R.string.kod, 56),
    MAGEHUNTER(R.string.m, 57),
    REVENGE_OF_THE_VAMPIRE(R.string.rotv, 58),
    CURSE_OF_THE_MUMMY(R.string.cotm, 59);



    private final int nameResourceId;
    private final int order;


    FightingFantasyGamebook(int nameResourceId, int order) {
        this.nameResourceId = nameResourceId;
        this.order = order;
    }

    public int getNameResourceId() {
        return nameResourceId;
    }

    public int getOrder() {
        return order;
    }
}
