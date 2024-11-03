package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import helpers.MinionHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class Encourage extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Encourage";
    public static final String NAME;// = "鼓舞";
    public static final String DESCRIPTION;// = "给所有帕鲁+3/5力，+15护甲。";
    public static final String imgUrl = "images/cards/Encourage_skill.png";
    int majic=5;
    int majic2=25;
    public Encourage() {
        super(ID, NAME, imgUrl, 3, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
        this.baseDamage = 0;
        this.isMultiDamage = true;
        this.exhaust=true;
        this.rawDescription = UPGRADE_DESCRIPTION;
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
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,new StrengthPower(monster,majic)));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(monster,majic2));
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
        return new Encourage();
    }

    public void upgrade() {
        this.upgradeBaseCost(2);
        this.upgradeName();
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
