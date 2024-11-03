package relics.lineTwo.relicToBlight.blightHook;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import javassist.CtBehavior;

import java.util.ArrayList;

public interface BlightCampfire {
    default void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
    }

    default boolean canUseCampfireOption(AbstractCampfireOption option) {
        return true;
    }


    @SpirePatch2(clz = CampfireUI.class, method = "initializeButtons")
    class AddCampFireOptPath {
        @SpireInsertPatch(
                locator = AddLocator.class
        )
        public static void AddButtonsInsert(CampfireUI __instance) {
            ArrayList<AbstractCampfireOption> buttons = ReflectionHacks.getPrivate(__instance, CampfireUI.class, "buttons");
            for (AbstractBlight blight : AbstractDungeon.player.blights) {
                if (blight instanceof BlightCampfire)
                    ((BlightCampfire) blight).addCampfireOption(buttons);
            }
        }

        @SpireInsertPatch(
                locator = CanUseLocator.class
        )
        public static void CanUseButtonsInsert(CampfireUI __instance) {
            ArrayList<AbstractCampfireOption> buttons = ReflectionHacks.getPrivate(__instance, CampfireUI.class, "buttons");
            for (AbstractCampfireOption co : buttons) {
                for (AbstractBlight blight : AbstractDungeon.player.blights) {
                    if (blight instanceof BlightCampfire)
                        if (!((BlightCampfire) blight).canUseCampfireOption(co)) {
                            co.usable = false;
                            break;
                        }
                }
            }
        }

        private static class AddLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        private static class CanUseLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CampfireUI.class, "buttons");
                return InOrderIndexMultiFinder.findInOrder(ctMethodToPatch, finalMatcher, new int[]{3});
            }
        }

    }


}
