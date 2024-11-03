package powers;

import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;

import static utils.DesManager.getDescription;

public class BlankCardPower extends AbstractPower {
    private static final PowerStrings powerString;
    public static final String POWER_ID = "BlankCardPower";
    public static final String NAME;// = "白卡";
    public static final String IMG = "images/powers/BlankCardPower.png";
    public static final String[] DESCRIPTIONS ;//= new String[]{"每回合开始打出一张[", "]。", "未指定卡牌"};

    public AbstractCard card;

    public BlankCardPower(AbstractCreature owner, int amount) {
        super();
        if(this.owner!=null){ this.name = owner.name;}
        else {this.name="";}
        this.ID = "BlankCardPower";
        this.owner = owner;
        this.amount = amount;
        this.img = ImageMaster.loadImage("images/powers/BlankCardPower.png");
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.owner = owner;

    }

    public void atStartOfTurn() {

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        String mode="";
        this.description ="作为帕鲁时："+getDescription(owner.id);

    }

    public static AbstractCard playAgain(AbstractCard card, AbstractMonster m) {

        return card;
    }

    static {
        powerString = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerString.NAME;
        DESCRIPTIONS = powerString.DESCRIPTIONS;
    }

}
