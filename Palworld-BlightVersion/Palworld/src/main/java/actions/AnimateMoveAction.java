package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import monsters.abstracrt.AbstractPet;

public class AnimateMoveAction extends AbstractGameAction {
    private boolean called = false;

    private float targetX, targetY,oX,oY;
    private AbstractMonster p;

    private Float time;

    public AnimateMoveAction(AbstractMonster owner, AbstractCreature target, float time) {
        this.setValues(null, owner, 0);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.targetX = target.drawX;
        this.targetY = target.drawY;
        this.duration = time;
        this.p=owner;
        this.oX=owner.drawX;
        this.oY=owner.drawY;
    }


    public void update() {
        if (this.duration>=0.0F) {
           this.p.drawX+=(targetX-this.oX)/duration;
           this.p.drawY+=(targetY-this.oY)/duration;
        }

        this.tickDuration();
    }


}
