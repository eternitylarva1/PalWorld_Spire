package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.Champ;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;
import powers.chaofeng;

public class ChampPatch {
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.city.Champ",
            method = "usePreBattleAction"
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 128
        )
        public static SpireReturn Insert(Champ  __instance) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance,__instance,new chaofeng(__instance,9)));
            return SpireReturn.Continue();
        }
    }
}
