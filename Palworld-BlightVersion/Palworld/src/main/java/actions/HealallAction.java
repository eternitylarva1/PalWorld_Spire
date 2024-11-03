package actions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import patches.player.PlayerAddFieldsPatch;

public class HealallAction extends AbstractGameAction {
    public boolean p;
    public HealallAction( boolean includep, int amount) {
        this.setValues(target, source, amount);
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }
        p=includep;
        this.actionType = ActionType.HEAL;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            if(p){
                AbstractDungeon.player.heal(amount);
            }
            ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                {
                    //尝试修复死后塞牌问题
                    monster.heal(amount);

                }
            });
        }

        this.tickDuration();
    }
}
