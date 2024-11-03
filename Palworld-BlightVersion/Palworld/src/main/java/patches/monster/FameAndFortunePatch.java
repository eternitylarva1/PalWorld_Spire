package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;


public class FameAndFortunePatch {

//    private static final Logger logger = LogManager.getLogger(BronzeOrbPatch.class.getName());

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.cards.optionCards.FameAndFortune",
            method = SpirePatch.CONSTRUCTOR
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 20
        )
        public static SpireReturn Insert(FameAndFortune __instance) {

            __instance.exhaust=true;
//                logger.info("Error!Not Find BronzeAutomaton in PlayerMinion!");
                return SpireReturn.Continue();


        }
    }
}


