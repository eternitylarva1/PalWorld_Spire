package powers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;

public class XianXueQiYuePower extends AbstractPower {
    public static final String POWER_ID = "XianXueQiYue";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public XianXueQiYuePower(AbstractCreature owner, int strAmt) {
        this.name = NAME;
        this.ID = "XianXueQiYue";
        this.owner = owner;
        this.amount = strAmt;
        this.updateDescription();
        this.isPostActionPower = true;
        this.loadRegion("rupture");
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == this.owner) {
            this.flash();
            for(AbstractMonster m: MinionHelper.getMinions().monsters) {
              m.increaseMaxHp(this.amount, true);
            } }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rupture");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

