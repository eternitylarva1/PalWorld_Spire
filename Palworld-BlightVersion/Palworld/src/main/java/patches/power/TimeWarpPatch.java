package patches.power;


import champ.cards.Strike;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import helpers.MinionHelper;

import static mymod.IsaacMod.config;

public class TimeWarpPatch {
    private static final boolean opened=config.getBool("Douququ");
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.actions.GameActionManager",
            method = "getNextAction"
    )
    public static class damagePatch {

        @SpireInsertPatch(
                loc = 420
        )
        public static void Insert(GameActionManager __instance) {

            if(opened) {


                for (AbstractMonster at : MinionHelper.getMinions().monsters) {
                    if (at.hasPower(TimeWarpPower.POWER_ID)) {
                        AbstractPower ap = at.getPower(TimeWarpPower.POWER_ID);
                        ap.onSpecificTrigger();

                    }


            }

        }
    }
}}
