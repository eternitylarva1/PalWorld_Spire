package patches.monster;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;

public class Bronzeman {

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.city.BronzeAutomaton",
            method = "takeTurn"
    )
    public static class TakeTurnPatch {
        @SpireInsertPatch(
                loc = 100
        )
        public static SpireReturn Insert(BronzeAutomaton __instance,int ___blockAmt,int ___strAmt) {
            {
                switch (__instance.nextMove) {
                    case 4:
                        if (MathUtils.randomBoolean()) {
                            AbstractDungeon.actionManager.addToBottom(new SFXAction("AUTOMATON_ORB_SPAWN",
                                    MathUtils.random(-0.1F, 0.1F)));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AUTOMATON_SUMMON",
                                    MathUtils.random(-0.1F, 0.1F)));
                        }
                        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrb(-300.0F, 200.0F, 0), true));
                        if (MathUtils.randomBoolean()) {
                            AbstractDungeon.actionManager.addToBottom(new SFXAction("AUTOMATON_ORB_SPAWN",
                                    MathUtils.random(-0.1F, 0.1F)));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AUTOMATON_SUMMON",
                                    MathUtils.random(-0.1F, 0.1F)));
                        }
                        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrb(200.0F, 130.0F, 1), true));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(__instance));
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)__instance.damage
                                .get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)__instance.damage
                                .get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                        break;
                    case 5:
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(__instance, __instance, ___blockAmt));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, ___strAmt),___strAmt));
                        break;

                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(__instance.hb.cX, __instance.hb.cY + 60.0F * Settings.scale), 1.5F));

                        if (MinionHelper.getMinions().monsters.contains(__instance)) {
                            int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];

                            for(int i = 0; i < retVal.length; ++i) {
                                DamageInfo info = new DamageInfo(__instance, __instance.damage.get(1).base);
                                info.applyPowers(__instance, (AbstractCreature)AbstractDungeon.getMonsters().monsters.get(i));
                                retVal[i] = info.output;
                            }

                            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) __instance, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

                        }
                        else{
                            int[] retVal = new int[MinionHelper.getMinions().monsters.size()];

                            for(int i = 0; i < retVal.length; ++i) {
                                DamageInfo info = new DamageInfo(__instance, __instance.damage.get(1).base);
                                info.applyPowers(__instance, (AbstractCreature)MinionHelper.getMinions().monsters.get(i));
                                retVal[i] = info.output;
                            }

                            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1((AbstractCreature) __instance, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

                            DamageInfo info = new DamageInfo(__instance, __instance.damage.get(1).base);
                            info.applyPowers(__instance,AbstractDungeon.player);
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(__instance, info.output, DamageInfo.DamageType.THORNS)));
                        }

                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(__instance, TextAboveCreatureAction.TextType.STUNNED));
                        break;
                }
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            }
            return SpireReturn.Return();
        }
    }
}
