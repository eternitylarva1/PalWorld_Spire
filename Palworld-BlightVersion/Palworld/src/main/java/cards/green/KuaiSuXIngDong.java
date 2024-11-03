package cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;

public class KuaiSuXIngDong extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "KuaiSuXIngDong";
    public static final String NAME;// = "快速行动";
    public static final String DESCRIPTION;// = "你每有一只帕鲁，抽一张牌（敲了保留）";
    public static final String imgUrl = "images/cards/Pills.png";



    public KuaiSuXIngDong() {
        super(ID, NAME, (String) null, 1, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust=false;
        this.magicNumber=this.baseMagicNumber=3;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getaliveMinions()>0){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(MinionHelper.getaliveMinions()));
        }

    }

    public AbstractCard makeCopy() {
        return new KuaiSuXIngDong();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.retain=true;
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
