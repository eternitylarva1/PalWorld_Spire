package patches.action;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import helpers.MinionHelper;
import utils.Invoker;


@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.DamageAction", method = "update")
    public class DamagePatch {

        @SpireInsertPatch(
                rloc = 1
        )
        public static void Insert(DamageAction damageAction,DamageInfo ___info) {
            if(___info.owner!=null&&MinionHelper.getMinions().monsters.contains(___info.owner)){
                System.out.println("正在攻击"+damageAction.target.name+"目前血量为"+damageAction.target.currentHealth);
                if(damageAction.target.isDeadOrEscaped()||damageAction.target.isDying||damageAction.target.currentHealth<=0){

                    damageAction.target=AbstractDungeon.getRandomMonster();
                    damageAction.target.damage(___info);}

            }else if(___info.owner!=null&&AbstractDungeon.getMonsters().monsters.contains(___info.owner)){
                if(damageAction.target.isDeadOrEscaped()||damageAction.target.isDying||damageAction.target.currentHealth<=0){

                    damageAction.target=AbstractDungeon.getRandomMonster();
                    damageAction.target.damage(___info);}
            }
        }

}


