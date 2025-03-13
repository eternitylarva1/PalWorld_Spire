package Option;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeBall_BluePrint;
import relics.PokeGophone;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class fudan extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public fudan(boolean active) {

        this.label = TEXT[0];

        this.usable = active;
        PokeGophone pgp=(PokeGophone)AbstractDungeon.player.getRelic("BloodBag");
        AbstractRelic egg;
        AbstractMonster temp = null;
        if(AbstractDungeon.player.hasRelic(ToxicEgg2.ID)) {
          egg=AbstractDungeon.player.getRelic(ToxicEgg2.ID);
          temp=new GremlinNob(0,0);
        }
        else if(AbstractDungeon.player.hasRelic(FrozenEgg2.ID))
        {
            egg=AbstractDungeon.player.getRelic(FrozenEgg2.ID);
temp=new AwakenedOne(0,0);
        }
        else if(AbstractDungeon.player.hasRelic(MoltenEgg2.ID))
        {
            egg=AbstractDungeon.player.getRelic(MoltenEgg2.ID);
temp=new Hexaghost();
        }
        else
        {
            egg=null;
            this.usable=false;
        }
        if(egg!=null){
            this.description = "失去"+egg.name+",然后获得一个装有"+ temp.name+"的帕鲁球";
        }
        this.img = loadImage("images/ui/campfire/fudan.png");
    }
    public void updateUsability(boolean canUse) {
        AbstractRelic egg;
        AbstractMonster temp = null;
        if(AbstractDungeon.player.hasRelic(ToxicEgg2.ID)) {
            egg=AbstractDungeon.player.getRelic(ToxicEgg2.ID);
            temp=new GremlinNob(0,0);
        }
        else if(AbstractDungeon.player.hasRelic(FrozenEgg2.ID))
        {
            egg=AbstractDungeon.player.getRelic(FrozenEgg2.ID);
            temp=new AwakenedOne(0,0);
        }
        else if(AbstractDungeon.player.hasRelic(MoltenEgg2.ID))
        {
            egg=AbstractDungeon.player.getRelic(MoltenEgg2.ID);
            temp=new Hexaghost();
        }
        else
        {
            egg=null;
            this.usable=false;
        }
        if(egg!=null){
            this.description = "失去"+egg.name+",然后获得一个装有"+ temp.name+"的帕鲁球";
        }

    }


        public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new fudane());

        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Lift Option");
        TEXT = new String[]{"孵蛋"};
    }
}
