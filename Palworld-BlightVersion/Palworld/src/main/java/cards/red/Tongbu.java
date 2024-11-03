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
import com.megacrit.cardcrawl.powers.StrengthPower;
import powers.chaofeng;

public class Tongbu extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Tongbu";
    public static final String NAME;// = "同步";
    public static final String DESCRIPTION;// = "获得等同于目标力量的力量";
    public static final String imgUrl = "images/cards/TongBu.png";



    public Tongbu() {
        super(ID, NAME, imgUrl, 1, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust=false;
        this.baseDamage=this.damage=8;

    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m,3)));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, (!m.hasPower(StrengthPower.POWER_ID))?3:m.getPower(StrengthPower.POWER_ID).amount + 3)));

        }
        else {
            if (m.hasPower(StrengthPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, m.getPower(StrengthPower.POWER_ID).amount + 3)));
            }
        }

    }

    public AbstractCard makeCopy() {
        return new Tongbu();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
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
