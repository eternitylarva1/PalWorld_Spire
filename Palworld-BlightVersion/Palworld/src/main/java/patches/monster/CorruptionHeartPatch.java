package patches.monster;

import cards.tempCards.Virus;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.TimeMazePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import evepower.FeelNoPainPower1;
import evepower.FireBreathingPower1;
import evepower.RupturePower1;
import evepower.TimeMazePower1;
import helpers.MinionHelper;

import static mymod.IsaacMod.config;

public class CorruptionHeartPatch {

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.ending.CorruptHeart",
            method = "takeTurn"
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 105
        )
        public static SpireReturn Insert(CorruptHeart __instance) {
            if(!config.getBool("Douququ")) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(false), 1, true, true));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new FeelNoPainPower1(__instance, 3), 3));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RupturePower1(__instance, 1), 1));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new TimeMazePower1(__instance, 12), 12));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new FireBreathingPower1(__instance, 3), 3));
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.ending.CorruptHeart",
            method = "takeTurn"
    )
    public static class ApplyPowerPatch {
        @SpireInsertPatch(
                loc = 178
        )
        public static SpireReturn Insert(CorruptHeart __instance) {
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RupturePower1(__instance, 1), 1));
            if(!config.getBool("Douququ")) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(false), 1, true, true));
            }
           // AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new TimeMazePower1(__instance, 15), 15));
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.ending.CorruptHeart",
            method = "takeTurn"
    )
    public static class ApplyPowerPatch1 {
        @SpireInsertPatch(
                loc = 190
        )
        public static SpireReturn Insert(CorruptHeart __instance) {
            if(!config.getBool("Douququ")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RupturePower1(__instance, 1), 1));}
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new FireBreathingPower1(__instance, 3), 3));
            // AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new TimeMazePower1(__instance, 15), 15));
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.ending.CorruptHeart",
            method = "die"
    )
    public static class DiePatch {
        @SpireInsertPatch(
                loc = 282
        )
        public static SpireReturn Insert(CorruptHeart __instance) {
            if(!config.getBool("Douququ")) {
            AbstractDungeon.actionManager.addToTop(new CanLoseAction());
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)
            {
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }}
            return SpireReturn.Continue();
        }
    }
}
