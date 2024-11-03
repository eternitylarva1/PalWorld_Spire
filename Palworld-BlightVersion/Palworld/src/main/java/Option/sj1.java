package Option;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeBall_BluePrint;
import relics.PokeGophone;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class sj1 extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public sj1(boolean active) {

        this.label = TEXT[0];

        this.usable = active;
        PokeGophone pgp=(PokeGophone)AbstractDungeon.player.getRelic("BloodBag");
        this.description = "花费"+50* pgp.counter +"制造一个帕鲁球（消耗火堆）";
        if(AbstractDungeon.player.hasRelic(PokeBall_BluePrint.ID))
        {
            this.description = "花费"+50* pgp.counter/2 +"制造一个帕鲁球（消耗火堆）";
        }
        this.img = loadImage("images/ui/campfire/makeball.png");
    }

    public void useOption() {
        if (this.usable) {
            this.usable=false;
            AbstractDungeon.effectList.add(new sj1e());

        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Lift Option");
        TEXT = new String[]{"制造"};
    }
}
