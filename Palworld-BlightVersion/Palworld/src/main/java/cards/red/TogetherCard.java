package cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import evepower.RupturePower1;
import evepower.Together;

public class TogetherCard extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "TogetherCard";
    public static final String NAME;// = "集合";
    public static final String DESCRIPTION;// = "帕鲁行动时，抽一张牌";
    public static final String imgUrl = "images/cards/TogetherCard.png";



    public TogetherCard() {
        super(ID, NAME, (String) imgUrl, 1, DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=1;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new Together(p,this.magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new TogetherCard();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
