package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import helpers.MinionHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class Bomb extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Bomb";
    public static final String NAME;// = "全军出击";
    public static final String DESCRIPTION;// = "命令所有帕鲁行动一次。";
    public static final String imgUrl = "images/cards/Bomb_skill.png";

    public Bomb() {
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
                //尝试修复死后塞牌问题
                monster.takeTurn();
                monster.applyTurnPowers();
                monster.applyEndOfTurnTriggers();
                monster.powers.forEach(AbstractPower::atEndOfRound);

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
        return new Bomb();
    }

    public void upgrade() {

        this.upgradeBaseCost(1);
        this.upgradeName();
        this.rawDescription = UPGRADE_DESCRIPTION;
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
