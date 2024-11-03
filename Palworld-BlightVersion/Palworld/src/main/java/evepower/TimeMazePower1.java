package evepower;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
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
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.Iterator;

import static relics.Randommonster.changeMonster;
import static utils.ScreenPartition.*;
import static utils.randommonster.randommonster;

public class TimeMazePower1 extends AbstractPower {
    public static final String POWER_ID = "TimeMazePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;
    private int maxAmount;

    public TimeMazePower1(AbstractCreature owner, int maxAmount) {
        this.name = "血管硬化";
        this.ID = "TimeMazePower";
        this.owner = owner;
        this.amount = maxAmount;
        this.maxAmount = maxAmount;
        this.updateDescription();
        this.loadRegion("time");
        this.type = PowerType.BUFF;


    }

    public void updateDescription() {
        this.description = "你再打出"+this.amount+"张牌后，+2力量，+30血量上限";
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        this.flashWithoutSound();
        --this.amount;
        if (this.amount == 0) {
            this.amount = this.maxAmount;
            this.owner.increaseMaxHp(30,true);
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner,this.owner, new StrengthPower(this.owner,2)));
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = maxAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("TimeMazePower");
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}

