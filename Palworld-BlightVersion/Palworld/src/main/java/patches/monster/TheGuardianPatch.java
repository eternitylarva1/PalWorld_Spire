package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;

public class TheGuardianPatch {

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.exordium.TheGuardian",
            method = "useWhirlwind"
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 205
        )
        public static SpireReturn Insert(TheGuardian __instance) {
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(__instance));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
            for (int i = 0; i < 4; i++) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(__instance, new CleaveEffect(true), 0.15F));
                int[] retVal = new int[MinionHelper.getMinions().monsters.size()];

                for(int j = 0; j < retVal.length; ++j) {
                    DamageInfo info = new DamageInfo(__instance, __instance.damage.get(2).base);
                    info.applyPowers(__instance, (AbstractCreature)MinionHelper.getMinions().monsters.get(j));
                    retVal[j] = info.output;
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1((AbstractCreature) __instance, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

                    DamageInfo info = new DamageInfo(__instance, __instance.damage.get(2).base);
                    info.applyPowers(__instance,AbstractDungeon.player);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(__instance, info.output, DamageInfo.DamageType.THORNS)));

            }

            __instance.setMove("UNKOWN", (byte)6, AbstractMonster.Intent.DEFEND);

            return SpireReturn.Return();
        }
    }
}
/* if(MinionHelper.getaliveMinions()>=0) {
         DamageInfo info = new DamageInfo(__instance, __instance.damage.get(0).base);
         info.applyPowers(__instance,AbstractDungeon.player);
         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(__instance, info.output, DamageInfo.DamageType.THORNS)));
         } }*/