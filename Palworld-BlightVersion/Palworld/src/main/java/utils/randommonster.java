package utils;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.monsters.exordium.*;
import monsters.act2.BronzeOrb1;
import monsters.act2.GremlinLeader1;
import monsters.act2.GremlinLeader2;
import monsters.act3.Reptomancer2;
import monsters.act2.BronzeAutomaton2;
import monsters.act2.TheCollector2;

import java.util.HashMap;
import java.util.Map;

public class randommonster {
    public randommonster() {}

    private static final Map<Integer, String> monsterMap = new HashMap<>();

    static {
        monsterMap.put(0, "Sentry");
        monsterMap.put(1, "SlaverBlue");
        monsterMap.put(2, "Cultist");
        monsterMap.put(3, "JawWorm");
        monsterMap.put(4, "LouseDefensive");
        monsterMap.put(5, "Looter");
        monsterMap.put(6, "SlaverRed");
        monsterMap.put(7, "AcidSlime_L");
        monsterMap.put(8, "GremlinThief");
        monsterMap.put(9, "GremlinWarrior");
        monsterMap.put(10, "GremlinWizard");
        monsterMap.put(11, "GremlinTsundere");
        monsterMap.put(12, "FungiBeast");
        monsterMap.put(13, "AcidSlime_L");
        monsterMap.put(14, "SlaverBlue");
        monsterMap.put(15, "Cultist");
        monsterMap.put(16, "GremlinThief");
        monsterMap.put(17, "SpikeSlime_L");
        monsterMap.put(18, "FungiBeast");
        monsterMap.put(19, "Lagavulin");
        monsterMap.put(20, "Sentry");
        monsterMap.put(21, "Lagavulin");
        monsterMap.put(22, "GremlinNob");
        monsterMap.put(23, "TheGuardian");
        monsterMap.put(24, "Hexaghost");
        monsterMap.put(25, "SlimeBoss");
        monsterMap.put(26, "Mugger");
        monsterMap.put(27, "Chosen");
        monsterMap.put(28, "Chosen");
        monsterMap.put(29, "Shelled Parasite");
        monsterMap.put(30, "SphericGuardian");
        monsterMap.put(31, "BanditBear");
        monsterMap.put(32, "BanditLeader");
        monsterMap.put(33, "Sentry");
        monsterMap.put(34, "Sentry");
        monsterMap.put(35, "SnakePlant");
        monsterMap.put(36, "Snecko");
        monsterMap.put(37, "Centurion");
        monsterMap.put(38, "Healer");
        monsterMap.put(39, "Shelled Parasite");
        monsterMap.put(40, "BookOfStabbing");
        monsterMap.put(41, "GremlinLeader");
        monsterMap.put(42, "SlaverBoss");
        monsterMap.put(43, "BanditPointy");
        monsterMap.put(44, "SlaverBoss");
        monsterMap.put(45, "TorchHead");
        monsterMap.put(46, "BronzeAutomaton");
        monsterMap.put(47, "Champ");
        monsterMap.put(48, "TheCollector");
        monsterMap.put(49, "SphericGuardian");
        monsterMap.put(50, "Transient");
        monsterMap.put(51, "JawWorm");
        monsterMap.put(52, "Nemesis");
        monsterMap.put(53, "OrbWalker");
        monsterMap.put(54, "SpireGrowth");
        monsterMap.put(55, "Maw");
        monsterMap.put(56, "Spiker");
        monsterMap.put(57, "Repulsor");
        monsterMap.put(58, "OrbWalker");
        monsterMap.put(60, "WrithingMass");
        monsterMap.put(61, "GiantHead");
        monsterMap.put(62, "Exploder");
        monsterMap.put(63, "TimeEater");
        monsterMap.put(64, "AwakenedOne");
        monsterMap.put(65, "Deca");
        monsterMap.put(66, "Donu");
        monsterMap.put(67, "SpireSpear");
        monsterMap.put(68, "SpireShield");
        monsterMap.put(69, "CorruptHeart");
    }

    public static String getMonster(int key) {
        return monsterMap.get(key);
    }

    public static int getKey(String monsterName) {
        for (Map.Entry<Integer, String> entry : monsterMap.entrySet()) {
            if (entry.getValue().equals(monsterName)) {
                return entry.getKey();
            }
        }
        return AbstractDungeon.miscRng.random(0,70); // 如果找不到对应的序号，返回-1或者其他适当的值
    }



    public static AbstractMonster randomElite(int key) {
        switch (key)
        {
            case 1:
                return new Lagavulin(false);
            case 2:
                return new GremlinNob(0.0F, -10.0F);
            case 3:
                return new Sentry(0.0F, 25.0F);
            case 4:
                return new BookOfStabbing(
                );
            case 5:
                return new GremlinLeader2();
            case 6:
                return new Taskmaster(-270.0F, 15.0F);
            case 7:
                return new GiantHead();
            case 8:
            case 9:
                return new Reptomancer2();

        }
        return  null;
    }
    public static AbstractMonster randomBoss(int key) {
        switch (key)
        {
            case 1:
                return new TheGuardian();
            case 2:
                return new Hexaghost();
            case 3:
                return new SlimeBoss();
            case 4:
                return new BronzeAutomaton2();
            case 5:
                return new Champ();
            case 6:
                return new TheCollector2();

        }
        return  null;
    }
    public static AbstractMonster randommonster(int key) {
        switch (key) {
            case 0:
                return new Sentry(-330.0F, 25.0F);
            case 1:
                return new SlaverBlue(0.0F, 0.0F);
            case 2:
                return new Cultist(0.0F, -10.0F);
            case 3:
                return new JawWorm(0.0F, 25.0F);
            case 4:
                return new LouseDefensive(0.0F,0.0F);
            case 5:
                return new Looter(0.0F, 0.0F);
            case 6:
                return new SlaverRed(0.0F, 0.0F);
            case 7:
                if (AbstractDungeon.miscRng.randomBoolean()) {
                    return new AcidSlime_L(0.0F, 0.0F);
                }

                return new SpikeSlime_L(0.0F, 0.0F);
            case 8:

                    return new GremlinThief(0.0F, -10.0F);


            case 9:

                    return new GremlinWarrior(0.0F, -10.0F);


            case 10:
                return new GremlinWizard(0.0F, 0.0F);

            case 11:
            return new GremlinTsundere(0.0F, 0.0F);

            case 12:
                return new FungiBeast(-400.0F, 30.0F);
            case 13:
                return new AcidSlime_L(0.0F, 0.0F);
            case 14:
                return new SlaverBlue(0.0F, 0.0F);
            case 15:
                return new Cultist(0.0F, -10.0F);
            case 16:
                return new GremlinThief(0.0F, -10.0F);
            case 17:
                return new SpikeSlime_L(0.0F, 0.0F);
            case 18:
                return new FungiBeast(-450.0F, 30.0F);
            case 19:
                return new Lagavulin(true);
            case 20:
                return new Sentry(-330.0F, 25.0F);
            case 21:
                return new Lagavulin(false);
            case 22:

            return new GremlinNob(0.0F, 0.0F);
            case 23:
                return new TheGuardian();
            case 24:
                return new Hexaghost();
            case 25:
                return new SlimeBoss();

            default:
                switch (key) {
                    case 26:
                        return new Mugger(80.0F, 0.0F);
                    case 27:
                    case 28:
                        return new Chosen();
                    case 29:
                        return new ShelledParasite();
                    case 30:
                        return new SphericGuardian();
                    case 31:
                        return new BanditBear(-230.0F, 15.0F);
                    case 32:
                        return new BanditLeader(-465.0F, -20.0F);
                    case 33:
                    case 34:
                        return new Sentry(-305.0F, 30.0F);
                    case 35:
                        return new SnakePlant(-30.0F, -30.0F);
                    case 36:
                        return new Snecko();
                    case 37:
                        return new Centurion(-200.0F, 15.0F);
                    case 38:
                        return new Healer(120.0F, 0.0F);
                    case 39:

                        return new ShelledParasite(-260.0F, 15.0F);

                    case 40:
                        return new BookOfStabbing();
                    case 41:
                        return new GremlinLeader1();
                    case 42:
                        return new Taskmaster(-133.0F, 0.0F);
                    case 43:
                        return new BanditPointy(-320.0F, 0.0F);
                    case 44:
                        return new Taskmaster(-270.0F, 15.0F);
                    case 45:
                        return new TorchHead(-133.0F, 0.0F);
                    case 46:
                        return new BronzeAutomaton();
                    case 47:
                        return new Champ();
                    case 48:
                        return new TheCollector();
                    default:
                        switch (key) {

                            case 49:
                                return new SphericGuardian();
                            case 50:
                                return new Transient();
                            case 51:
                                return new JawWorm(-490.0F, -5.0F, true);
                            case 52:
                                return new Nemesis();
                            case 53:
                                return new OrbWalker(-30.0F, 20.0F);
                            case 54:
                                return new SpireGrowth();
                            case 55:
                                return new Maw(-70.0F, 20.0F);
                            case 56:
                                return new Spiker(-70.0F, 20.0F);
                            case 57:
                                return new Repulsor(-70.0F, 20.0F);
                            case 58:
                                return new OrbWalker(-250.0F, 32.0F);
                            case 60:
                                return new WrithingMass();
                            case 61:
                                return new GiantHead();
                            case 62:
                                return new Exploder(-70.0F, 20.0F);
                            case 63:
                                return new TimeEater();
                            case 64:
                                return  new AwakenedOne(100.0F, 15.0F);
                            case 65:
                                return new Deca();
                            case 66:
                                return new Donu();
                            default:
                                switch (key) {
                                    case 67:
                                        return new SpireSpear();
                                    case 68:
                                        return new SpireShield();
                                    case 69:
                                        return new CorruptHeart();
                                    default:
                                        return new Cultist(0.0F, -10.0F);
                                }
                        }
                }
        }
    }
}
