package patches.mod;



import automaton.cards.Return;
import charbosses.actions.util.CharbossDoNextCardAction;
import charbosses.actions.util.DelayedActionAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.cards.green.EnDaggerSpray;
import charbosses.cards.hermit.EnGrudge;
import charbosses.cards.hermit.EnPurgatory;
import charbosses.cards.red.EnIntimidate;
import charbosses.powers.cardpowers.EnemyTheBombPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;
import hermit.patches.EnumPatch;
import hermit.vfx.ShortScreenFire;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static helpers.MinionHelper.getMinions;

public class DownfallPatch {

    @SpirePatch(
            cls = "charbosses.cards.green.EnDaggerSpray",
            method = "use",
            optional=true,
            paramtypez = { AbstractPlayer.class,AbstractMonster.class,}
    )
    public static class EnDaggerSprayPatch {

        @SpireInsertPatch(
                loc = 33
        )



        public static SpireReturn Insert(EnDaggerSpray __instance,AbstractPlayer p,AbstractMonster m) {
            System.out.println("成功触发");
         for(int j = 0; j < 2; ++j) {
             AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(true), 0.0F));
             int[] retVal = new int[getMinions().monsters.size()];

             for (int i = 0; i < retVal.length; ++i) {
                 DamageInfo info = new DamageInfo(m, __instance.damage);
                 info.applyPowers(m, (AbstractCreature) getMinions().monsters.get(i));
                 retVal[i] = info.output;
             }

             AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1(m, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

             //AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
         }
            return SpireReturn.Return();
        }
    }
 @SpirePatch(
            cls = "charbosses.cards.hermit.EnPurgatory",
            method = "use",
            optional=true,
            paramtypez = { AbstractPlayer.class,AbstractMonster.class,}
    )
    public static class EnDaggerSprayPatch1 {

        @SpireInsertPatch(
                loc = 33
        )



        public static SpireReturn Insert(EnPurgatory __instance, AbstractPlayer p, AbstractMonster m) {
            System.out.println("成功触发");
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShortScreenFire(), 0.5F));
            int[] retVal = new int[getMinions().monsters.size()];

            for (int i = 0; i < retVal.length; ++i) {
                DamageInfo info = new DamageInfo(m, __instance.damage);
                info.applyPowers(m, (AbstractCreature) getMinions().monsters.get(i));
                retVal[i] = info.output;
            }

            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1(m, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

            for (AbstractMonster m2 : (AbstractDungeon.getMonsters()).monsters) {
                if (!m2.isDead && !m2.isDying && m2 instanceof SnakeDagger) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(m2, new InflameEffect(m), 0.2F));
                    AbstractDungeon.actionManager.addToBottom(new SuicideAction(m2));
                    AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(m2));
                }
            }

            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new SnakeDagger(-400.0F, 150.0F), true));
            return SpireReturn.Return();
        }
    }
    @SpirePatch(
            cls = "charbosses.cards.hermit.EnGrudge",
            method = "use",
            optional=true,
            paramtypez = { AbstractPlayer.class,AbstractMonster.class,}
    )
    public static class EnGrudgePatch1 {

        @SpireInsertPatch(
                rloc = 0
        )



        public static SpireReturn Insert(EnGrudge __instance, AbstractPlayer p, AbstractMonster m) {
            System.out.println("成功触发");

            int[] retVal = new int[getMinions().monsters.size()];

            for (int i = 0; i < retVal.length; ++i) {
                DamageInfo info = new DamageInfo(m, __instance.damage);
                info.applyPowers(m, (AbstractCreature) getMinions().monsters.get(i));
                retVal[i] = info.output;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1(m, retVal, DamageInfo.DamageType.NORMAL,EnumPatch.HERMIT_GHOSTFIRE));



            return SpireReturn.Return();
        }
    }
    @SpirePatch(
            cls = "charbosses.cards.red.EnIntimidate",
            method = "use",
            optional=true,
            paramtypez = { AbstractPlayer.class,AbstractMonster.class,}
    )
    public static class IntimidatePatch {

        @SpireInsertPatch(
                rloc = 0
        )



        public static SpireReturn Insert(EnIntimidate __instance, AbstractPlayer p, AbstractMonster m) {
            System.out.println("成功触发");
            for(AbstractMonster m2:getMinions().monsters)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m2, m2, new WeakPower(p, 3, false), 3, true, AbstractGameAction.AttackEffect.NONE));
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            cls = "charbosses.powers.cardpowers.EnemyTheBombPower",
            method = "atEndOfTurnPreEndTurnCards",
            optional=true,
            paramtypez = { boolean.class}
    )
    public static class TheBombPatch1 {

        @SpireInsertPatch(
                rloc = 1
        )



        public static SpireReturn Insert(EnemyTheBombPower __instance, boolean isplayer) {
            System.out.println("成功触发");
            if (__instance.amount == 1) {


                int[] retVal = new int[getMinions().monsters.size()];

                for (int i = 0; i < retVal.length; ++i) {
                    DamageInfo info = new DamageInfo(player, 50);
                    retVal[i] = info.output;
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1(player, retVal, DamageInfo.DamageType.THORNS, EnumPatch.HERMIT_GHOSTFIRE));
            }


            return SpireReturn.Return();
        }
    }
}

