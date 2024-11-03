package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import powers.chaofeng;

public class Cancel extends AbstractCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Cancel";
    public static final String NAME;// = "撤销";
    public static final String DESCRIPTION;// = "给予敌人一层嘲讽。";


    public Cancel() {
        super(ID, NAME, "blue/attack/lock_on", 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=true;
    }



    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new chaofeng(m,99)));
    }

    public AbstractCard makeCopy() {
        return new Cancel();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
