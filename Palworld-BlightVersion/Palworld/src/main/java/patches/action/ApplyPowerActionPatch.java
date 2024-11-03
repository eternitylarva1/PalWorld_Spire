package patches.action;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;

import static mymod.IsaacMod.config;

public class ApplyPowerActionPatch {
    private static boolean opened=config.getBool("Douququ");
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.actions.common.ApplyPowerAction",
            method = "update"
    )
    public static class damagePatch {

        @SpireInsertPatch(
                loc = 147
        )
        public static void Insert(ApplyPowerAction __instance,AbstractPower ___powerToApply) {

            if(opened) {
                if(___powerToApply instanceof StrengthPower) {
                    if (MinionHelper.getMinions().monsters.contains(__instance.source) && __instance.source == __instance.target) {
                        for (AbstractMonster at : AbstractDungeon.getMonsters().monsters) {
                            if (at.hasPower(CuriosityPower.POWER_ID) && !__instance.source.hasPower(CuriosityPower.POWER_ID)) {
                                AbstractPower ap = at.getPower(CuriosityPower.POWER_ID);
                                ap.flash();
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(at, at, new StrengthPower(at, ap.amount), ap.amount));
                            }
                            ;
                        }
                    } else if (AbstractDungeon.getMonsters().monsters.contains(__instance.source) && __instance.source == __instance.target) {
                        for (AbstractMonster at : MinionHelper.getMinions().monsters) {
                            if (at.hasPower(CuriosityPower.POWER_ID) && !__instance.source.hasPower(CuriosityPower.POWER_ID)) {
                                AbstractPower ap = at.getPower(CuriosityPower.POWER_ID);
                                ap.flash();
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(at, at, new StrengthPower(at, ap.amount), ap.amount));
                            }
                            ;
                        }
                    }
                }
            }

        }
    }
}
