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
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import helpers.MinionHelper;

public class YouLingShiYan extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "YouLingShiYan";
    public static final String NAME;// = "幽灵实验";
    public static final String DESCRIPTION;// = "目标帕鲁获得3层无实体，但是其5回合后死亡";
    public static final String imgUrl = "images/cards/Pills.png";



    public YouLingShiYan() {
        super(ID, NAME, (String) null, 1, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=3;

    }





    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if(MinionHelper.getMinions().monsters.contains(m)){
            addToBot(new ApplyPowerAction(m, m, new IntangiblePlayerPower(m, this.magicNumber)));
            addToBot(new ApplyPowerAction(m, m, new FadingPower(m, 5)));
        }
    }

    public AbstractCard makeCopy() {
        return new YouLingShiYan();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
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
