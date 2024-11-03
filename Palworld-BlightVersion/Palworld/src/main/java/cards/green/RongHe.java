package cards.green;

import actions.SummonMinionAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.FadingPower;
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

public class RongHe extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "RongHe";
    public static final String NAME;// = "融合";
    public static final String DESCRIPTION;// = "击杀一只帕鲁，其他帕鲁平分其血量上限";
    public static final String imgUrl = "images/cards/Pills.png";



    public RongHe() {
        super(ID, NAME, (String) null, 2, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=3;

    }





    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getMinions().monsters.contains(m)){

       for(AbstractMonster m1:MinionHelper.getMinions().monsters)
       {
           if(!(m1 ==m)){
               if(MinionHelper.getaliveMinions()>0)
               {
                   addToBot(new AbstractGameAction() {
                       @Override
                       public void update() {
                           m1.increaseMaxHp(m.maxHealth/MinionHelper.getaliveMinions(),true);
                           isDone=true;
                       }

                   });
               }
           }
       }
       addToBot(new SuicideAction(m,false));}
    }

    public AbstractCard makeCopy() {
        return new RongHe();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
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
