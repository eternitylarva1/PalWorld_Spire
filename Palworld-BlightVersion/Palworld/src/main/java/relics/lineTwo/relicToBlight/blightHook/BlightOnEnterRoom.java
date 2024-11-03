package relics.lineTwo.relicToBlight.blightHook;

import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import javassist.CtBehavior;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getCurrRoom;

public interface BlightOnEnterRoom {
    default void onEnterRoom(AbstractRoom room) {
    }

    default void justEnteredRoom(AbstractRoom room) {
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "nextRoomTransition",
            paramtypez = {SaveFile.class}
    )
    class OnEnterRoomPatch {

        @SpireInsertPatch(
                locator = OnEnterRoomLocator.class
        )
        public static void OnEnterRoomInsert(AbstractDungeon __instance, SaveFile saveFile) {
            boolean isLoadingPostCombatSave = CardCrawlGame.loadingSave && saveFile != null && saveFile.post_combat;
            if (AbstractDungeon.nextRoom != null && !isLoadingPostCombatSave) {
                for (final AbstractBlight blight : AbstractDungeon.player.blights) {
                    if (blight instanceof BlightOnEnterRoom)
                        ((BlightOnEnterRoom) blight).onEnterRoom(AbstractDungeon.nextRoom.room);
                }
            }
        }

        @SpireInsertPatch(
                locator = OnJustEnterRoomLocator.class
        )
        public static void OnJustEnterRoomInsert(AbstractDungeon __instance, SaveFile saveFile) {
            boolean isLoadingPostCombatSave = CardCrawlGame.loadingSave && saveFile != null && saveFile.post_combat;
            if (getCurrRoom() != null && !isLoadingPostCombatSave) {
                for (final AbstractBlight blight : AbstractDungeon.player.blights) {
                    if (blight instanceof BlightOnEnterRoom)
                        ((BlightOnEnterRoom) blight).justEnteredRoom(getCurrRoom());
                }
            }
        }

        private static class OnEnterRoomLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return InOrderIndexMultiFinder.findInOrder(ctMethodToPatch, finalMatcher, new int[]{0});
            }
        }

        private static class OnJustEnterRoomLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return InOrderIndexMultiFinder.findInOrder(ctMethodToPatch, finalMatcher, new int[]{1});
            }
        }
    }
}
