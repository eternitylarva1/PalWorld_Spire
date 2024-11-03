package patches.card;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


@SpirePatch(
        cls = "com.megacrit.cardcrawl.cards.AbstractCard",
        method = "hasEnoughEnergy",
        paramtypez = {}
)
public class IncubusCardPatch {
//    @SpireInsertPatch(
//            rloc = 0
//    )
    public static SpireReturn<Boolean> Prefix(Object __obj_instance) {
        //反射私有方法

        return SpireReturn.Continue();
    }

}
