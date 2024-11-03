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

public class Fangxue extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Fangxue";
    public static final String NAME;// = "放帕鲁的血";
    public static final String DESCRIPTION;// = "所有帕鲁失去3生命，获得2/3点能量";
    public static final String imgUrl = "images/cards/FangXue.png";



    public Fangxue() {
        super(ID, NAME, (String) imgUrl, 0, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=false;
        this.magicNumber=this.baseMagicNumber=5;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       if(MinionHelper.getMinions().monsters.contains(m)) {
           for (AbstractMonster m1 : MinionHelper.getMinions().monsters) {
               AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m1, m1, this.magicNumber));

           }
           AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
       }
    }

    public AbstractCard makeCopy() {
        return new Fangxue();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-2);
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
