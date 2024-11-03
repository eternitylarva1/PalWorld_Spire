package relics.lineTwo.customSave;

import basemod.abstracts.CustomSavableRaw;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.lineTwo.InfoBlight;
import relics.lineTwo.relicToBlight.BlightWithRelic;

import java.util.ArrayList;

@SpirePatch(clz = CardCrawlGame.class, method = "loadPlayerSave")
public class InfoBlightSavePatch {
    public static void Postfix(final CardCrawlGame __instance, final AbstractPlayer p) {
        final PalWorldModSaves.ArrayListOfJsonElement blighSave =
                PalWorldModSaves.modBlightSaves.get(CardCrawlGame.saveFile);
        ArrayList<AbstractBlight> blights = AbstractDungeon.player.blights;
        for (int i = 0; i < blights.size(); i++) {
            AbstractBlight blight = blights.get(i);
            if (!(blight instanceof BlightWithRelic)) continue;
            AbstractRelic relic = ((BlightWithRelic) blight).relic;
            if (!(relic instanceof CustomSavableRaw)) continue;
            if (blighSave != null && i < blighSave.size())
                ((CustomSavableRaw) relic).onLoadRaw(blighSave.get(i));
            else
                ((CustomSavableRaw) relic).onLoadRaw(null);
        }

        InfoBlight.getAllRelicsWithBlightIndex(AbstractRelic.class).forEach((integer, relic) ->
        {

        });
    }

}


