package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import monsters.act1.Cultist7;

public class CultistEnterAction extends AbstractGameAction {
    public CultistEnterAction(Cultist7 target) {
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = 0.1F;
        this.target=target;
    }


    public void update() {
        if (this.duration == 0.0F &&
                this.target != null) {
                this.target.state.setAnimation(0, "waving2", true);
                this.isDone=true;

        }


        tickDuration();
    }
}
