package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import helpers.MinionHelper;

public class CanNotLosePatch {

    public static boolean skip = false;

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.Darkling",
            method = "usePreBattleAction"
    )
    public static class usePreBattleActionPatch {

        public static void Postfix(Darkling darkling) {
            if (!skip && MinionHelper.getMinions().monsters.contains(darkling)) {
                AbstractDungeon.getCurrRoom().cannotLose = false;
            }
        }
        public static void Prefix(Darkling darkling) {
            if (AbstractDungeon.getCurrRoom().cannotLose) {
                skip = true;
            } else {
                skip = false;
            }
        }
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.beyond.Darkling",
            method = "damage"
    )
    public static class damagePatch {
        @SpireInsertPatch(
                loc = 225
        )
        public static SpireReturn  Insert(Darkling darkling) {
            if(MinionHelper.getMinions().monsters.contains(darkling))
            {
                return  SpireReturn.Return();
             
            }else{
                return  SpireReturn.Continue();
            }
        }

    }
}
