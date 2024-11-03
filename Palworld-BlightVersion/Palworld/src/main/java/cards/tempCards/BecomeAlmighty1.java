package cards.tempCards;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import patches.player.PlayerAddFieldsPatch;

import java.util.Objects;

public class BecomeAlmighty1 extends AbstractCard {
    public static final String ID = "BecomeAlmighty";
    private static final CardStrings cardStrings;

    public BecomeAlmighty1() {
        super("BecomeAlmighty", cardStrings.NAME, "colorless/power/become_almighty", -2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.FIREBRICK, true)));
        this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
            if(!monster.isDying&&!monster.escaped&&!monster.isDead)
            {
                //尝试修复死后塞牌问题
               if(Objects.equals(monster.id, "CorruptHeart"))
               {
                   System.out.println("检测到心脏，开始加力量");
                   this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.FIREBRICK, true)));
                   this.addToBot(new VFXAction(monster, new InflameEffect(monster), 1.0F));
                   this.addToBot(new ApplyPowerAction(monster, monster, new StrengthPower(monster, this.magicNumber), this.magicNumber));
               }
            }
        });
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BecomeAlmighty");
    }
}
