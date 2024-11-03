package monsters.pet;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.MeteorStrike;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;
import monsters.Intent.Move;
import monsters.abstracrt.AbstractPet;
import patches.player.PlayerAddFieldsPatch;
import powers.BlankCardPower;

public class LeechPet extends CustomMonster {

    public static final String NAME;
    public static final String MOVE_NAME;
    public  int turncount=0;
    private int attackDmg = 15;
    private int mutiattackDmg = 2;
    public boolean touched=false;
    public LeechPet(float x, float y) {
        super(NAME, "Leech", 75, -10F, 10.0F, 150.0F, 300.0F, (String) null, x, y);

        this.img = new Texture(Gdx.files.internal("images/monsters/Hairball.png"));
        this.damage.add(new DamageInfo(this, attackDmg));
        this.damage.add(new DamageInfo(this, mutiattackDmg));
        this.setMove(MOVE_NAME, (byte) Move.ATTACK.id, Intent.ATTACK, attackDmg);
        this.maxHealth=75;

    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new BlankCardPower(this,-1)));

    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:

                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this,new WeakPower(AbstractDungeon.player,2,true),2));
                if(!MinionHelper.getMinions().monsters.contains(this)) {
                ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                if (!monster.isDying && !monster.escaped && !monster.isDead) {

                    AbstractPower p = new WeakPower(monster, 3,true);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, p, 3));

                }
            }
    );
}

                break;
            case 1:
                int a[]={((DamageInfo)this.damage.get(1)).base,((DamageInfo)this.damage.get(1)).base,((DamageInfo)this.damage.get(1)).base,((DamageInfo)this.damage.get(1)).base,((DamageInfo)this.damage.get(1)).base};
                boolean i1=false;

                for (AbstractMonster m:MinionHelper.getMinions().monsters)
                {
                    if (!m.isDeadOrEscaped())
                    {
                        i1=true;
                    }

                }

                for(int i=0;i<5;i++) {
                    if (!MinionHelper.getMinions().monsters.contains(this)) {
                        if(i1) {
                            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1(this, a, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                        }else {
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                        }
                    ;}else{
                        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this, a, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    }


                   ;
                }
                break;
            case 2:
                if(!MinionHelper.getMinions().monsters.contains(this)) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new WeightyImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                    AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player,25
                            , DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                }
                else{
                    AbstractCard c=new MeteorStrike();
                    c.cost=0;
                    c.exhaust=true;
                    c.rawDescription.concat("Exhaust");
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(c, true, EnergyPanel.getCurrentEnergy(), true, true), true);
                }

        }
        turncount++;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void update() {
        super.update();
    }

    protected void getMove(int num) {

        if (num<=30) {
            this.setMove((byte)0, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base, 1, false);
        } else {
            this.setMove((byte) 1, Intent.ATTACK, ((DamageInfo) this.damage.get(1)).base, 5, true);
        }

        if(turncount==3) {
            this.setMove((byte) 2, Intent.UNKNOWN);
        }


    }
    static {
        if (Settings.language == Settings.GameLanguage.ZHS) {
            NAME = "疾旋鼬";
            MOVE_NAME = "龙息";
        } else {
            NAME = "Leech";
            MOVE_NAME = "Blood Suck!";
        }
    }
}