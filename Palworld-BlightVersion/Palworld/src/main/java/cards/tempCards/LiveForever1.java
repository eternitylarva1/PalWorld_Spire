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
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import patches.player.PlayerAddFieldsPatch;

import java.util.Objects;

public class LiveForever1 extends AbstractCard {
    public static final String ID = "LiveForever";
    private static final CardStrings cardStrings;

    public LiveForever1() {
        super("LiveForever", cardStrings.NAME, "colorless/power/live_forever", -2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        System.out.println("开始检测");
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new VFXAction(new BorderFlashEffect(Color.CHARTREUSE, true)));
        this.addToBot(new VFXAction(p, new MiracleEffect(Color.CHARTREUSE, Color.LIME, "BLOCK_GAIN_1"), 1.0F));
        this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
        ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
            if(!monster.isDying&&!monster.escaped&&!monster.isDead)
            {
                System.out.println("开始检测");

                if(Objects.equals(monster.id, "CorruptHeart"))
                {

                    System.out.println("检测到心脏，开始加多重护甲");
                    this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.FIREBRICK, true)));
                    this.addToBot(new VFXAction(monster, new InflameEffect(monster), 1.0F));
                    this.addToBot(new ApplyPowerAction(monster, monster, new PlatedArmorPower(monster, this.magicNumber), this.magicNumber));
                }
            }
        });
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    public AbstractCard makeCopy() {
        return new com.megacrit.cardcrawl.cards.optionCards.LiveForever();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("LiveForever");
    }
}

