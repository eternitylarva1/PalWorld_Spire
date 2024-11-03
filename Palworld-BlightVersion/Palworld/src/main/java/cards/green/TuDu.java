package cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import helpers.MinionHelper;

public class TuDu extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "TuDu";
    public static final String NAME;// = "帕鲁涂毒";
    public static final String DESCRIPTION;// = "给予目标帕鲁3层涂毒和3层中毒";
    public static final String imgUrl = "images/cards/Pills.png";



    public TuDu() {
        super(ID, NAME, (String) null, 2, DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=3;

    }





    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getMinions().monsters.contains(m)) {
            addToBot(new ApplyPowerAction(m,m,new EnvenomPower(m,this.magicNumber)));
            addToBot(new ApplyPowerAction(m,m,new PoisonPower(m,p,this.magicNumber)));
        }

    }

    public AbstractCard makeCopy() {
        return new TuDu();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    private static final String UPGRADE_DESCRIPTION ;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}
