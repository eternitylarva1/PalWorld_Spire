package cards.green;

import actions.SummonMinionAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.MindBlast;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import evepower.AfterImagePower1;
import helpers.MinionHelper;
import monsters.act1.*;
import monsters.act2.*;
import monsters.act3.*;
import monsters.act4.CorruptHeart1;
import monsters.act4.SpireShield1;
import monsters.act4.SpireSpear1;
import utils.InstanceMaker;
import utils.Invoker;
import utils.ScreenPartition;

public class YuXiang extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "YuXiang";
    public static final String NAME;// = "帕鲁余像";
    public static final String DESCRIPTION;// = "当你打出技能牌时，所有牌获得一点格挡（敲了固有）)";
    public static final String imgUrl = "images/cards/Pills.png";



    public YuXiang() {
        super(ID, NAME, (String) null, 1, DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=1;

    }





    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster m1 : MinionHelper.getMinions().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m1, p, new AfterImagePower1(m1,1)));
        }
    }

    public AbstractCard makeCopy() {
        return new YuXiang();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
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
