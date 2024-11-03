package relics.lineTwo.relicToBlight;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import relics.lineTwo.InfoBlight;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class CustomBlightPatch {
    public static final Set<AbstractBlight> myCustomBlight = new HashSet<>();

    /**
     * 需要在初始化时就添加好，使用InfoBlight的话，只需要用它的注册函数就行了，不需要用这个
     */
    public static void Assign(AbstractBlight addedBlight) {
        if (myCustomBlight.stream().anyMatch(blight -> Objects.equals(blight.blightID, addedBlight.blightID))) return;
        myCustomBlight.add(addedBlight);
    }

    @SpirePatch2(clz = BlightHelper.class, method = "getBlight")
    public static class AddMyCustomBlight {
        @SpirePrefixPatch
        public static SpireReturn<AbstractBlight> Map(String id) {
            Optional<AbstractBlight> blightOptional = myCustomBlight.stream()
                    .filter(blight -> Objects.equals(blight.blightID, id)).findAny();
            if (blightOptional.isPresent()) {
                if (blightOptional.get() instanceof InfoBlight)
                    return SpireReturn.Return(((InfoBlight) blightOptional.get()).makeCopy());
                return SpireReturn.Return(blightOptional.get());
            }
            return SpireReturn.Continue();
        }
    }
}
