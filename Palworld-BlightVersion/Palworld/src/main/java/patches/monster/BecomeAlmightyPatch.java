package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;



    public class BecomeAlmightyPatch {

//    private static final Logger logger = LogManager.getLogger(BronzeOrbPatch.class.getName());

        @SpirePatch(
                cls = "com.megacrit.cardcrawl.monsters.city.BronzeAutomaton",
                method = "takeTurn"
        )
        public static class TakeTurnPatch {
            @SpireInsertPatch(
                    loc = 136
            )
            public static SpireReturn Insert(BronzeAutomaton __instance) {
                {
                    if (!MinionHelper.getMinions().monsters.contains(__instance)) {
                        int a[] = {58};
                        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1((AbstractCreature) __instance, a, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,new DamageInfo(__instance, __instance.damage.get(0).base, DamageInfo.DamageType.THORNS)));
                    }
                }
                return SpireReturn.Continue();
            }
        }
    }


