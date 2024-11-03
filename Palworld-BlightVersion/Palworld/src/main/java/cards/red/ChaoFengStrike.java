package cards.red;

import basemod.abstracts.CustomCard;
import champ.cards.Strike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import helpers.MinionHelper;
import powers.chaofeng;

public class ChaoFengStrike extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "ChaoFengStrike";
    public static final String NAME;// = "嘲讽打击";
    public static final String DESCRIPTION;// = "打8，给予目标3层嘲讽";
    public static final String imgUrl = "images/cards/ChaoFeng.png";



    public ChaoFengStrike() {
        super(ID, NAME, (String) imgUrl, 1, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);

        this.baseDamage=this.damage=8;
        this.tags.add(CardTags.STRIKE);
    }




    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new chaofeng(m,3)));


    }

    public AbstractCard makeCopy() {
        return new ChaoFengStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
