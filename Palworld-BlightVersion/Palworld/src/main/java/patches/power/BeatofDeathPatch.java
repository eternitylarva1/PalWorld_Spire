package patches.power;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.BeatOfDeathPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;
import patches.damage.AbstractMonsterPatch;

import java.util.Iterator;

public class BeatofDeathPatch {
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.powers.BeatOfDeathPower",
            method = "onAfterUseCard"
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 33
        )
        public static SpireReturn Insert(BeatOfDeathPower __instance) {
            {
                if (!MinionHelper.getMinions().monsters.isEmpty()) {
                    Iterator var1 = MinionHelper.getMinionMonsters().iterator();
                    while(var1.hasNext()) {
                        AbstractMonster m= (AbstractMonster) var1.next();
                        if(!m.isDeadOrEscaped())
                        {
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(__instance.owner, __instance.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                        }
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

}
