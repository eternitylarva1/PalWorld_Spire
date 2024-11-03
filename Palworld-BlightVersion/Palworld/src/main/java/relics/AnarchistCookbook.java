package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.TheBombPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import monsters.abstracrt.AbstractPet;
import powers.TheMonsterBombPower;
import relics.abstracrt.BookSuit;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AnarchistCookbook extends CustomRelic {
    public static final String ID = "AnarchistCookbook";
    public static final String IMG = "images/relics/AnarchistCookbook.png";
    public static final String DESCRIPTION = "帕鲁小窝";

    public AnarchistCookbook() {
        super("AnarchistCookbook", new Texture(Gdx.files.internal("images/relics/AnarchistCookbook.png")), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new AnarchistCookbook();
    }





    @Override
    public void onEquip() {
        super.onEquip();
    }

    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void update() {
        super.update();
    }
}
