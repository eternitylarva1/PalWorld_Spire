package monsters;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import monsters.pet.LeechPet;

public class ConstrictedPower1 extends AbstractPower {
    public static final String POWER_ID = "Constricted";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public AbstractCreature source;

    public ConstrictedPower1(AbstractCreature target, AbstractCreature source, int fadeAmt) {
        this.name = NAME;
        this.ID = "Constricted";
        this.owner = target;
        this.source = source;
        this.amount = fadeAmt;
        this.updateDescription();
        this.loadRegion("constricted");
        this.type = PowerType.DEBUFF;
        this.priority = 105;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONSTRICTED", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flashWithoutSound();
        this.playApplyPowerSfx();
        this.addToBot(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageType.THORNS)));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Constricted");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
