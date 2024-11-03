package patches.monster;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;



    @SpirePatch(
            clz= AbstractMonster.class,
            method=SpirePatch.CLASS
    )
    public class AbstractMonsterAddFieldPatch {
        public AbstractMonsterAddFieldPatch() {
        }

        public static SpireField<Boolean> canbetarget = new SpireField<>(() -> true);


    }


