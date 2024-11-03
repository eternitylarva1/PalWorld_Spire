package evepower;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FeelNoPainPower1 extends AbstractPower {
    public static final String POWER_ID = "Feel No Pain";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public FeelNoPainPower1(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Feel No Pain";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("noPain");
    }
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS && !this.owner.hasPower("No Draw")) {
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));
        }

    }
    public void updateDescription() {
        this.description = "当玩家抽到状态牌时，获得" + this.amount + DESCRIPTIONS[1];
    }



    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Feel No Pain");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

