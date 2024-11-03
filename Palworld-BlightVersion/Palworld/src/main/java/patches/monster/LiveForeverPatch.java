package patches.monster;


import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;



public class LiveForeverPatch {

//    private static final Logger logger = LogManager.getLogger(BronzeOrbPatch.class.getName());

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.cards.optionCards.LiveForever",
            method = "onChoseThisOption"
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 46
        )
        public static SpireReturn Insert(LiveForever __instance) {
            if (AbstractDungeon.getMonsters().getMonster("BronzeAutomaton") == null) {
                for (AbstractMonster m : MinionHelper.getMinions().monsters) {
                    if (m.id.equals("CorruptHeart")) {
                        System.out.println("检测到心脏");
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new PlatedArmorPower(m, __instance.magicNumber), __instance.magicNumber));
                    }
                }
//                logger.info("Error!Not Find BronzeAutomaton in PlayerMinion!");
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}



