package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Evolve;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import evepower.DamageAllEnemiesAction1;
import evepower.TimeWarpPower1;
import helpers.MinionHelper;
import powers.chaofeng;

import static mymod.IsaacMod.config;

public class TimeEaterPatch {
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.TimeEater",
            method = "usePreBattleAction"
    )
    public static class TakeTurnPatch1 {
        @SpireInsertPatch(
                loc = 98
        )
        public static SpireReturn Insert(TimeEater __instance) {
            if(config.getBool("Douququ")) {
                CardCrawlGame.music.unsilenceBGM();
                AbstractDungeon.scene.fadeOutAmbiance();
                AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
                UnlockTracker.markBossAsSeen("WIZARD");
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new TimeWarpPower1(__instance)));
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.TimeEater",
            method = "takeTurn"
    )

    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 115

        )
        public static SpireReturn Insert(TimeEater __instance) {
            switch (__instance.nextMove) {
                case 2:
                    for (int j = 0; j < 3; ++j) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(__instance, new ShockWaveEffect(__instance.hb.cX, __instance.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));

                        if (MinionHelper.getMinions().monsters.contains(__instance)) {
                            int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];

                            for(int i = 0; i < retVal.length; ++i) {
                                DamageInfo info = new DamageInfo(__instance, __instance.damage.get(0).base);
                                info.applyPowers(__instance, (AbstractCreature)AbstractDungeon.getMonsters().monsters.get(i));
                                retVal[i] = info.output;
                            }

                            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) __instance, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));

                        }
                        else{
                            int[] retVal = new int[MinionHelper.getMinions().monsters.size()];

                            for(int i = 0; i < retVal.length; ++i) {
                                DamageInfo info = new DamageInfo(__instance, __instance.damage.get(0).base);
                                info.applyPowers(__instance, (AbstractCreature)MinionHelper.getMinions().monsters.get(i));
                                retVal[i] = info.output;
                            }

                            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1((AbstractCreature) __instance, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));

                                DamageInfo info = new DamageInfo(__instance, __instance.damage.get(0).base);
                                info.applyPowers(__instance,AbstractDungeon.player);
                                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(__instance, info.output, DamageInfo.DamageType.THORNS)));
                            }
                    }
                    break;
                case 3:
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, 20));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new VulnerablePower(AbstractDungeon.player, 2, true), 2));





                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new WeakPower(AbstractDungeon.player, 1, true), 1));





                    if (AbstractDungeon.ascensionLevel >= 19) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new FrailPower(AbstractDungeon.player, 1, true), 1));
                    }
                    break;





                case 4:
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(__instance, "ATTACK"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)__instance.damage
                            .get(1), AbstractGameAction.AttackEffect.POISON));
                    if(MinionHelper.getMinions().monsters.contains(__instance)&&!config.getBool("Douququ")) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new DrawPower(AbstractDungeon.player, 1)));
                    }
                    else{
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new DrawReductionPower(AbstractDungeon.player, 2)));

                    }

                    if(MinionHelper.getMinions().monsters.contains(__instance)) {

                    if (AbstractDungeon.ascensionLevel >= 19) {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Evolve(), 1));
                    }
                    }else
                    {
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
                    }
                    break;
                case 5:
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(__instance, "Foolish", 0.5F, 2.0F));
                    AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(__instance));
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(__instance, __instance, "Shackled"));

                    AbstractDungeon.actionManager.addToBottom(new HealAction(__instance, __instance, __instance.maxHealth / 2 - __instance.currentHealth));
                    if (AbstractDungeon.ascensionLevel >= 19) {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, 30));
                    }
                    break;
            }
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));

            return SpireReturn.Return();
        }
    }
}
