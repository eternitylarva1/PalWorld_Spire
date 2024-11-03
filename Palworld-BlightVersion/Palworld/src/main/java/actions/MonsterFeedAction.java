package actions;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;





public class MonsterFeedAction extends AbstractGameAction
{
    private int increaseHpAmount;
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public MonsterFeedAction(AbstractCreature target, DamageInfo info, int maxHPAmount) {
        this.info = info;

        this.increaseHpAmount = maxHPAmount;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
        this.target = target;
        this.source = info.owner;
        this.amount = info.output;

    }


    public void update() {
        if (this.duration == 0.1F &&
                this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);

            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
               info.owner.increaseMaxHp(this.increaseHpAmount, false);


                if (this.target instanceof com.megacrit.cardcrawl.monsters.beyond.Donu) {
                    UnlockTracker.unlockAchievement("DONUT");
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }


        tickDuration();
    }
}

