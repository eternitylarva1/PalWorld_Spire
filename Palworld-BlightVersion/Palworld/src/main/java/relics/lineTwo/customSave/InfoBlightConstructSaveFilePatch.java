package relics.lineTwo.customSave;

import basemod.abstracts.CustomSavableRaw;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import relics.lineTwo.relicToBlight.BlightWithRelic;

import static relics.lineTwo.customSave.PalWorldModSaves.ArrayListOfJsonElement;

@SpirePatch(clz = SaveFile.class, method = "<ctor>", paramtypez = {SaveFile.SaveType.class})
public class InfoBlightConstructSaveFilePatch {
    @SpirePostfixPatch
    public static void Prefix(final SaveFile __instance, final SaveFile.SaveType saveType) {
        final ArrayListOfJsonElement blightSaves = new ArrayListOfJsonElement();
        for (AbstractBlight blight : AbstractDungeon.player.blights) {
            if (blight instanceof BlightWithRelic
                    && ((BlightWithRelic) blight).relic instanceof CustomSavableRaw)
                blightSaves.add(((CustomSavableRaw) ((BlightWithRelic) blight).relic).onSaveRaw());
            else {
                blightSaves.add(null);
            }
        }
        PalWorldModSaves.modBlightSaves.set(__instance, blightSaves);
    }
}
