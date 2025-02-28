package ui;

import basemod.BaseMod;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
            BaseMod.openCustomScreen(BreedScreen.Enum.MY_SCREEN, "foobar",pokeGoList);

        }
        // your onclick code
    }
}