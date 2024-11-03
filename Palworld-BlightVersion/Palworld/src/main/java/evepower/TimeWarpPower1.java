package evepower;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import java.util.Iterator;

public class TimeWarpPower1 extends AbstractPower {
    public static final String POWER_ID = "Time Warp";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;
    private static final int STR_AMT = 2;
    private static final int COUNTDOWN_AMT = 12;

    public TimeWarpPower1(AbstractCreature owner) {
        this.name = "什么？";
        this.ID = "Time Warp";
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        this.loadRegion("time");
        this.type = PowerType.BUFF;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
    }

    public void updateDescription() {
        this.description = DESC[0] + 12 + DESC[1] + 2 + DESC[2];
    }

    public void onSpecificTrigger() {
        this.flashWithoutSound();
        ++this.amount;
        if (this.amount == 12) {
            this.amount = 0;
            this.playApplyPowerSfx();

            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
           AbstractMonster am= (AbstractMonster) this.owner;
           this.addToBot(new ApplyPowerAction(am, am, new StrengthPower(am, 2), 2));
           am.takeTurn();

        }

        this.updateDescription();
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Time Warp");
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}

