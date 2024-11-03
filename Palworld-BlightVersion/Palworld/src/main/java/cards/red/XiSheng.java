package cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;

public class XiSheng extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "XiSheng";
    public static final String NAME;// = "牺牲";
    public static final String DESCRIPTION;// = "失去5点生命，所有帕鲁获得等量力量";
    public static final String imgUrl = "images/cards/XiSheng.png";



    public XiSheng() {
        super(ID, NAME, (String) imgUrl, 0, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust=false;
        this.magicNumber=this.baseMagicNumber=3;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getaliveMinions()>0){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
            for (AbstractMonster m1 : MinionHelper.getMinions().monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m1, p, new StrengthPower(m1, this.magicNumber)));
            }
        }

    }

    public AbstractCard makeCopy() {
        return new XiSheng();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
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
