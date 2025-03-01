package ui;

import basemod.BaseMod;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import relics.PokeGo;
import relics.lineTwo.InfoBlight;
import screen.BreedScreen;

import java.util.List;

public class BreedsearchButton extends TopPanelItem {
    private static final Texture IMG = new Texture("images/ui/panel/BlackHeart.png");
    public static final String ID = "PalMod:BreedsearchButton";

    public BreedsearchButton() {
        super(IMG, ID);
    }
    List<PokeGo> pokeGoList;
    @Override
    protected void onClick() {
        if(AbstractDungeon.screen== BreedScreen.Enum.MY_SCREEN) {
            AbstractDungeon.closeCurrentScreen();
        }else {
            pokeGoList = InfoBlight.getAllRelics(PokeGo.class);
            if(pokeGoList.size()<2){return;}
            BaseMod.openCustomScreen(BreedScreen.Enum.MY_SCREEN, "foobar",pokeGoList);

        }
        // your onclick code
    }
    @Override
    public void update() {
        super.update();
        if (!Settings.hideTopBar) {
            if (this.hitbox.hovered) {
                TipHelper.renderGenericTip((float) InputHelper.mX - 140.0F * Settings.scale, Settings.HEIGHT - 120.0F * Settings.scale, "查看已有帕鲁的配种情况", "需要至少两只帕鲁才能打开这个界面");
            } else{

            }
        }

    }
}