package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(
        clz=AbstractMonster.class,
        method=SpirePatch.CLASS
)
public class AbstractMonsterClickField {
    public AbstractMonsterClickField() {
    }

    public static SpireField<Boolean> RclickStart = new SpireField<>(() -> false);
    public static SpireField<Boolean> Rclick = new SpireField<>(() -> false);
    public static SpireField<Boolean> LclickStart = new SpireField<>(() -> false);
    public static SpireField<Boolean> Lclick = new SpireField<>(() -> false);
    public static SpireField<Integer> Lickx = new SpireField<>(() -> 0);
    public static SpireField<Integer> Licky = new SpireField<>(() -> 0);
}
