//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package relics.lineTwo.relicToBlight.blightHook;

import com.evacipated.cardcrawl.modthespire.finders.MatchFinderExprEditor;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.patcher.Expectation;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InOrderIndexMultiFinder extends MatchFinderExprEditor {
    protected final List<Integer> indexes;
    private final List<Integer> locations;
    private final Matcher finalMatch;
    private final List<Matcher> expectedMatches;
    private boolean foundLocation;
    private int foundMatchesIndex;

    public InOrderIndexMultiFinder(List<Matcher> expectedMatches, Matcher finalMatch, int[] indexes) {
        this.expectedMatches = expectedMatches;
        this.finalMatch = finalMatch;
        this.foundMatchesIndex = 0;
        this.foundLocation = false;
        this.locations = new ArrayList<>();
        this.indexes = Arrays.stream(indexes).boxed().collect(Collectors.toList());
    }

    public static int[] findInOrder(CtBehavior ctMethodToPatch, Matcher finalMatch, int[] indexes) throws CannotCompileException, PatchingException {
        return findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatch, indexes);
    }

    public static int[] findInOrder(CtBehavior ctMethodToPatch, List<Matcher> expectedMatches, Matcher finalMatch, int[] indexes) throws CannotCompileException, PatchingException {
        MatchFinderExprEditor editor = new InOrderIndexMultiFinder(expectedMatches, finalMatch, indexes);
        ctMethodToPatch.instrument(editor);
        if (!editor.didFindLocation()) {
            throw new PatchingException(ctMethodToPatch, "Location matching given description could not be found for patch");
        }
        else {
            return editor.getFoundLocations();
        }
    }

    public int[] getFoundLocations() {
        int[] asArray = new int[this.locations.size()];

        ArrayList<Integer> returnArray = new ArrayList<>();

        for (int i = 0; i < asArray.length; ++i) {
            if (indexes.contains(i))
                returnArray.add(this.locations.get(i));
        }

        return returnArray.stream().mapToInt(integer -> integer).toArray();
    }

    private void foundFinalMatch(int lineNumber) {
        this.foundLocation = true;
        this.locations.add(lineNumber);
    }

    private boolean finalMatch() {
        return this.foundMatchesIndex >= this.expectedMatches.size();
    }

    private void foundMatch() {
        ++this.foundMatchesIndex;
    }

    private Matcher currentMatch() {
        return this.expectedMatches.get(this.foundMatchesIndex);
    }

    protected void doMatch(Expectation expectedType, Expr toMatch) {
        if (this.finalMatch()) {
            if (this.finalMatch.getExpectation() == expectedType && this.finalMatch.match(toMatch)) {
                this.foundFinalMatch(toMatch.getLineNumber());
            }
        }
        else {
            Matcher current = this.currentMatch();
            if (current.getExpectation() == expectedType && current.match(toMatch)) {
                this.foundMatch();
            }
        }

    }

    public boolean didFindLocation() {
        return this.foundLocation;
    }
}
