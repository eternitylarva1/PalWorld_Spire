package evepower;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RupturePower1 extends AbstractPower {
    public static final String POWER_ID = "Rupture";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public RupturePower1(AbstractCreature owner, int strAmt) {
        this.name = "霸凌";
        this.ID = "Rupture";
        this.owner = owner;
        this.amount = strAmt;
        this.updateDescription();
        this.isPostActionPower = true;
        this.loadRegion("rupture");
    }


    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS && !this.owner.hasPower("No Draw")) {
            this.flash();
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        }

    }

    public void updateDescription() {
        this.description ="当玩家抽到状态牌时，获得"+ this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rupture");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
