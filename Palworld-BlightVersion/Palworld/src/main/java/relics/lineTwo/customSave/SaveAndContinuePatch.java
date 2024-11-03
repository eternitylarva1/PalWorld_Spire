package relics.lineTwo.customSave;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import java.util.HashMap;

@SpirePatch(clz = SaveAndContinue.class, method = "save", paramtypez = { SaveFile.class })
public class SaveAndContinuePatch
{
    @SpireInsertPatch(locator = Locator.class, localvars = { "params" })
    public static void Insert(final SaveFile save, final HashMap<String, Object> params) {
        params.put("palworldmod:mod_blight", PalWorldModSaves.modBlightSaves.get(save));
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(final CtBehavior ctMethodToPatch) throws Exception {
            final Matcher finalMatcher = new Matcher.MethodCallMatcher("com.google.gson.GsonBuilder", "create");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
