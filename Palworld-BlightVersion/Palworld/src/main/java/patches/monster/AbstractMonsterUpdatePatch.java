package patches.monster;

import basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup.ScrollingTooltips;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.animations.AnimateJumpAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helpers.MinionHelper;
import monsters.pet.LeechPet;
import mymod.IsaacMod;
import mymod.config;

import static helpers.MinionHelper.getMinions;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.monsters.AbstractMonster",
        method = "update",
        paramtypez = {}
)
public class AbstractMonsterUpdatePatch {
    public AbstractMonsterUpdatePatch() {
    }

    @SpirePostfixPatch
    public static void Postfix(AbstractMonster monster,@ByRef Texture[] ___img) {
        if (AbstractMonsterClickField.RclickStart.get(monster) && InputHelper.justReleasedClickRight) {
            if (monster.hb.hovered) {
                AbstractMonsterClickField.Rclick.set(monster, true);
            }
            AbstractMonsterClickField.RclickStart.set(monster, false);
        }
        if ((monster.hb != null) && ((monster.hb.hovered) && (InputHelper.justClickedRight))) {
            AbstractMonsterClickField.RclickStart.set(monster, true);
        }
        if (AbstractMonsterClickField.Rclick.get(monster)) {
            AbstractMonsterClickField.Rclick.set(monster,false);
            //右击动作
           // AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(monster));
        // AbstractMonsterAddFieldPatch.canbetarget.set(monster,!AbstractMonsterAddFieldPatch.canbetarget.get(monster));
            if(!IsaacMod.config.getBool("youjian")){
                AbstractMonsterClickField.LclickStart.set(monster, true);
            }
            AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(monster,0.5F,0.1F));
         if(monster instanceof LeechPet)
         {if(((LeechPet) monster).touched)
             {
                 AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(monster,0.5F,0.1F));
                 ___img[0] = new Texture(Gdx.files.internal("images/monsters/Leech1.png"));
                 ((LeechPet) monster).touched = !((LeechPet) monster).touched;
             }
             else
             {
                 ___img[0] = new Texture(Gdx.files.internal("images/monsters/Hairball.png"));
                 ((LeechPet) monster).touched = !((LeechPet) monster).touched;
             }
         }
        }
    }

}
