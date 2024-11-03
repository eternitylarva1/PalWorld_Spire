package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Feed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;
import monsters.act1.Cultist2;
import monsters.act1.Cultist3;
import monsters.act1.Cultist4;
import utils.ScreenPartition;

public class AwakenedOnePatch {

    public static boolean skip = false;

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.AwakenedOne",
            method = "usePreBattleAction"
    )
    public static class usePreBattleActionPatch {

        public static void Postfix(AwakenedOne awakenedOne) {
            if (!skip && MinionHelper.getMinions().monsters.contains(awakenedOne)) {
                AbstractDungeon.getCurrRoom().cannotLose = false;
            }
        }
        public static void Prefix(AwakenedOne awakenedOne) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                skip = true;
            } else {
                skip = false;
            }
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.AwakenedOne",
            method = "damage"
    )
    public static class damagePatch {

        @SpireInsertPatch(
                loc = 327
        )
        public static void Insert(AwakenedOne awakenedOne, DamageInfo info) {
            if (MinionHelper.getMinions().monsters.contains(awakenedOne)) {
                awakenedOne.halfDead = true;
            }
        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.AwakenedOne",
            method = "damage"
    )
    public static class AOEPatch {

        @SpireInsertPatch(
                loc = 327
        )
        public static void Insert(AwakenedOne awakenedOne, DamageInfo info) {
            if (MinionHelper.getMinions().monsters.contains(awakenedOne)) {
                awakenedOne.halfDead = true;
            }
        }
    }
    /*
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.AwakenedOne",
            method = "takeTurn"
    )
    public static class SummonPatch {

        @SpireInsertPatch(
                loc = 172
        )
        public static void Insert(AwakenedOne awakenedOne) {
            int j=0;
            for(AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if(m.id=="Cultist"&&!m.isDeadOrEscaped())
                {
                    ++j;
                }
            }
            if(j<=2){
                for (int i = 0; i < 2; i++) {
                    AbstractMonster m;
                    int a = AbstractDungeon.aiRng.random(3);
                    switch (a) {
                        case 1:
                            m = new Cultist2(0, 0);

                            break;
                        case 2:
                            m = new Cultist4(0, 0);
                            break;
                        case 3:
                            m = new Cultist3(0, 0);
                            break;
                        default:
                            m = new Cultist1(0, 0);

                    }
                    ScreenPartition.currentRow = 3;
                    ScreenPartition.assignSequentialPosition(null, m);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(m, true));
                }
            }
        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.AwakenedOne",
            method = "takeTurn"
    )
    public static class SummonPatch1 {

        @SpireInsertPatch(
                loc = 208
        )
        public static void Insert(AwakenedOne awakenedOne) {
            int j=0;
            for(AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if(m.id=="Cultist"&&!m.isDeadOrEscaped())
                {
                    ++j;
                }
            }
            if(j<=2){
                for (int i = 0; i < 2; i++) {
                    AbstractMonster m;
                    int a = AbstractDungeon.aiRng.random(3);
                    switch (a) {
                        case 1:
                            m = new Cultist2(0, 0);

                            break;
                        case 2:
                            m = new Cultist4(0, 0);
                            break;
                        case 3:
                            m = new Cultist3(0, 0);
                            break;
                        default:
                            m = new Cultist1(0, 0);

                    }
                    ScreenPartition.currentRow = 3;
                    ScreenPartition.assignSequentialPosition(null, m);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(m, true));
                }
            }

        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.AwakenedOne",
            method = "takeTurn"
    )
    public static class KillPatch {

        @SpireInsertPatch(
                loc = 199
        )
        public static void Insert(AwakenedOne awakenedOne) {
            int j=0;
            for(AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if(m.id=="Cultist"&&!m.isDeadOrEscaped())
                {
                    if(m.hasPower(StrengthPower.POWER_ID))
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(awakenedOne,awakenedOne,new StrengthPower(awakenedOne,m.getPower(StrengthPower.POWER_ID).amount)) );
                        awakenedOne.increaseMaxHp(m.currentHealth, true);
                    }
                   AbstractDungeon.actionManager.addToBottom(new SuicideAction(m));
                   break;
                }
            }
        }
    }
*/
}
