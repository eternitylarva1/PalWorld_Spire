package cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import evepower.RupturePower1;
import powers.chaofeng;

public class Baling extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Baling";
    public static final String NAME;// = "霸凌";
    public static final String DESCRIPTION;// = "抽到状态牌时，获得一点力量";
    public static final String imgUrl = "images/cards/BaLing.png";



    public Baling() {
        super(ID, NAME, (String) imgUrl, 1, DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=1;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RupturePower1(p,this.magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new Baling();
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
