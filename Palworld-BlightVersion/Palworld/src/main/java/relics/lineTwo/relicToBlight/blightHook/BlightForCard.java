package relics.lineTwo.relicToBlight.blightHook;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public interface BlightForCard {

    default void onExhaust(AbstractCard card) {
    }

    default void onCardDraw(AbstractCard card) {
    }


    @SpirePatch(
            clz = CardGroup.class,
            method = "moveToExhaustPile",
            paramtypez = {AbstractCard.class}
    )
    class OnExhaustPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard c) {
            for (AbstractBlight b : AbstractDungeon.player.blights) {
                if (b instanceof BlightForCard)
                    ((BlightForCard) b).onExhaust(c);
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = {int.class}
    )
    class DrawCardPatch {
        @SpireInsertPatch(
                locator = DrawCardLocator.class,
                localvars = {"c"}
        )
        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
            for (AbstractBlight b : __instance.blights) {
                if (b instanceof BlightForCard)
                    ((BlightForCard) b).onCardDraw(c);
            }
        }

        private static class DrawCardLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
