package cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RupturePower;
import evepower.Together;
import powers.XianXueQiYuePower;

public class XianXueQIYue extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "XianXueQIYue";
    public static final String NAME;// = "鲜血契约";
    public static final String DESCRIPTION;// = "当你失去生命时，所有帕鲁增加一点血量上限";
    public static final String imgUrl = "images/cards/XianXueQiYue.png";



    public XianXueQIYue() {
        super(ID, NAME, (String) imgUrl, 2, DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=1;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new XianXueQiYuePower(p,this.magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new XianXueQIYue();
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
