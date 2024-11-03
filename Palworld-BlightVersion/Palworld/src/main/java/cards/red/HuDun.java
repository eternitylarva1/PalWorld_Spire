package cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HuDun extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "HuDun";
    public static final String NAME;// = "护盾";
    public static final String DESCRIPTION;// = "给予目标9/13点格挡";
    public static final String imgUrl = "images/cards/HuDun.png";



    public HuDun() {
        super(ID, NAME, (String) imgUrl, 1, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.exhaust=false;
        this.baseBlock=this.block=9;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, m,this.block));



    }

    public AbstractCard makeCopy() {
        return new HuDun();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
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
