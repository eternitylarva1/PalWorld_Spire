package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relics.PokeGo;
import utils.DesManager;

public class ApplyEntryAction extends AbstractGameAction {

    private AbstractMonster m;
    private  PokeGo p;
    private static final float DURATION = 1.0F;
    public ApplyEntryAction(PokeGo p, AbstractMonster  m) {
        this.p = p;
        this.m=m;

    }

    public void update() {
        if(!AbstractDungeon.getMonsters().monsters.contains(m)){
            for(String entry:p.Entrylist)
        {DesManager.ApplyEntry(m,entry);}

        }else{
            AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.player,"词条系统与你开的其他Mod冲突，词条系统已被禁用"));
        }
        this.isDone = true;

    }

}
