package cards;

import actions.AnimateMoveAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helpers.MinionHelper;
import relics.BattleBell;

public class Skill_fruit extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Skill_fruit";
    public static final String NAME;// = "火箭弹";
    public static final String DESCRIPTION;// = "将帕鲁发射出去，对所有敌人造成等同于其血量的伤害，然后其死亡";
    public static final String imgUrl = "images/cards/Pills.png";


    public Skill_fruit() {
        super(ID, NAME, (String) null, 2, DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust=true;
        this.baseDamage=0;
    }



    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getMinions().monsters.contains(m)) {
         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m, BattleBell.getrandomPower(m)));
        }

    }

    public AbstractCard makeCopy() {
        return new Skill_fruit();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
