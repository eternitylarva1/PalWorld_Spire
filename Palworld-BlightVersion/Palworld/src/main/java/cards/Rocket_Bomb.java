package cards;

import actions.AnimateMoveAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import helpers.MinionHelper;

public class Rocket_Bomb extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Rocket_Bomb";
    public static final String NAME;// = "火箭弹";
    public static final String DESCRIPTION;// = "将帕鲁发射出去，对所有敌人造成等同于其血量的伤害，然后其死亡";
    public static final String imgUrl = "images/cards/Pills.png";


    public Rocket_Bomb() {
        super(ID, NAME, (String) null, 3, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust=true;
        this.baseDamage=0;
    }



    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(MinionHelper.getMinions().monsters.contains(m)) {

            int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];

            for (int i = 0; i < retVal.length; ++i) {
                DamageInfo info = new DamageInfo(AbstractDungeon.player, m.currentHealth);
                info.applyPowers(AbstractDungeon.player, (AbstractCreature) AbstractDungeon.getMonsters().monsters.get(i));
                retVal[i] = info.output;
            }
            AbstractDungeon.actionManager.addToBottom(new AnimateMoveAction(m,AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true),1.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) AbstractDungeon.player, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new SuicideAction(m));
        }

    }

    public AbstractCard makeCopy() {
        return new Rocket_Bomb();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
