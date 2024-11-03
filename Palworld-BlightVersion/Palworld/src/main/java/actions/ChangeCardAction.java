package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChangeCardAction extends AbstractGameAction {
    private AbstractMonster monster;
    private int i;
    private  AbstractCard c;
    private static final float DURATION = 1.0F;
    public ChangeCardAction(int i, AbstractCard c) {
        this.i = i;
        this.c=c;

    }

    public void update() {
        AbstractDungeon.player.hand.group.set(i,c);
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
        this.isDone = true;

    }

}
