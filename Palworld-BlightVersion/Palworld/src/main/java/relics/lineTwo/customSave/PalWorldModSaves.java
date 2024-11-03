package relics.lineTwo.customSave;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(clz = SaveFile.class, method = "<class>")
public class PalWorldModSaves
{
    @SerializedName("palworldmod:mod_blight")
    public static SpireField<ArrayListOfJsonElement> modBlightSaves =
            (SpireField<ArrayListOfJsonElement>)new SpireField(() -> null);

    public static class ArrayListOfJsonElement extends ArrayList<JsonElement>
    {
    }

    public static class ArrayListOfString extends ArrayList<String>
    {
    }

    public static class HashMapOfJsonElement extends HashMap<String, JsonElement>
    {
    }
}
