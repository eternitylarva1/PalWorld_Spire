package cards.tempCards;

import actions.ChangeCardAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.TheBombPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Virus extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "Virus";
    public static final String NAME;//= "快爆炸弹";
    public static final String DESCRIPTION;//= "两回合后爆炸，对所有敌人造成40点伤害。虚无。消耗。";

    private boolean upgrade;
    int i=0;

    public Virus(boolean upgrade) {
        super("Virus", NAME, "images/cards/Virus_skill.png", 3, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.baseMagicNumber = 30;
        this.isEthereal = false;
        this.upgrade = upgrade;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            if(i>0) {
                this.exhaust = false;
                if(player.hand.group.size()>=i-1&& !player.hand.group.isEmpty()) {
                    addToBot(new ChangeCardAction(0, this.makeCopy()));
                }
            }
        }
    }

    public AbstractCard makeCopy() {
        return new Virus(upgrade);
    }
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        int j=0;
        for(AbstractCard c:player.hand.group)
        {

            if(c==this)
            {
                i=j;
            }
            ++j;
        }
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.cost = 1;
            this.upgradeName();
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
