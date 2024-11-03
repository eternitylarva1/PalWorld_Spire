package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import helpers.MinionHelper;

public class Protect extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Full_Protect";
    public static final String NAME;// = "援护";
    public static final String DESCRIPTION;// = "将身上所有护甲给这个帕鲁，然后给其2层残影";
    public static final String imgUrl = "images/cards/YuanHu.png";



    public Protect() {
        super(ID, NAME, (String) imgUrl, 1, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=2;
    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getMinions().monsters.contains(m)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new BlurPower(m,this.magicNumber)));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m,m,AbstractDungeon.player.currentBlock));
           AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(AbstractDungeon.player,AbstractDungeon.player));

        }
    }

    public AbstractCard makeCopy() {
        return new Protect();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
