package evepower;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FireBreathingPower1 extends AbstractPower {
    public static final String POWER_ID = "Fire Breathing";
    private static final PowerStrings powerStrings;

    public FireBreathingPower1(AbstractCreature owner, int newAmount) {
        this.name = powerStrings.NAME;
        this.ID = "Fire Breathing";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("firebreathing");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void onCardDraw(AbstractCard card) {
        if (card.type == CardType.STATUS || card.type == CardType.CURSE) {
            this.flash();
            this.addToBot(new DamageAllEnemiesAction1((AbstractCreature)null, DamageInfo.createDamageMatrix(this.amount, true), DamageType.THORNS, AttackEffect.FIRE, true));
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Fire Breathing");
    }
}
