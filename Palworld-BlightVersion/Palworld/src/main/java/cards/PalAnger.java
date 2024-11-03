package cards;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import helpers.MinionHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class PalAnger extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "PalAnger";
    public static final String NAME;// = "帕鲁之怒";
    public static final String DESCRIPTION;// = "所有帕鲁本回合伤害翻倍。";
    public static final String imgUrl = "images/cards/PalAnger_skill.png";

    public PalAnger() {
        super(ID, NAME, imgUrl, 2, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
        this.baseDamage = 0;
        this.isMultiDamage = true;
        this.exhaust=true;
    }



    private boolean used = false;

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.currMapNode.room.monsters.monsters;
        Iterator var1 = MinionHelper.getMinionMonsters().iterator();
        while(var1.hasNext()) {
            AbstractMonster monster=(AbstractMonster )var1.next();
            if(!monster.isDying&&!monster.escaped&&!monster.isDead)
            {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,new DoubleDamagePower(monster,1,false)));
            }

        }

    }

//    @Override
//    public void triggerWhenDrawn(){
//        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
//    }

    @Override
    public void update() {
        super.update();
    }

    public AbstractCard makeCopy() {
        return new PalAnger();
    }

    public void upgrade() {

        this.upgradeBaseCost(1);
        this.upgradeName();

        this.initializeDescription();
        this.upgraded=true;
    }



    private static final String UPGRADE_DESCRIPTION ;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}

