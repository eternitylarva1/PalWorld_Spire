package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helpers.MinionHelper;
import powers.chaofeng;

public class Pills extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Pills";
    public static final String NAME;// = "高级药";
    public static final String DESCRIPTION;// = "清除帕鲁身上所有debuff。";
    public static final String imgUrl = "images/cards/Pills.png";


    public Pills() {
        super(ID, NAME, imgUrl, 1, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=true;
    }



    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getMinions().monsters.contains(m)){
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(m));
        }
    }

    public AbstractCard makeCopy() {
        return new Pills();
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
