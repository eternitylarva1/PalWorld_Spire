package Option;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeGo;
import relics.PokeGophone;
import utils.InstanceMaker;

import static Option.get_pokego.getfirstcanwork_pokego;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static relics.PokeGophone.planted;

public class plant extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public boolean hasplant=false;

    public plant(boolean active) {
        if(!planted) {
            this.label = TEXT[0];
            this.description = "种植随机作物，下个火堆成熟";
        }
        else{
            this.label=TEXT[1];
            this.description = "收获作物";
        }
        this.usable = active;
        this.updateUsability(this.usable);
        PokeGophone pgp=(PokeGophone)AbstractDungeon.player.getRelic("BloodBag");

        this.img =loadImage("images/ui/campfire/plant.png");
    }

    public void updateUsability(boolean canUse) {
        if(!hasplant&&getfirstcanwork_pokego()!=null&&!planted){

            PokeGo pgt=getfirstcanwork_pokego();
            AbstractMonster m = PokeGo.getMonsterbycache(pgt.monsterClass);
            this.description = "命令"+m.name + "种田,下个火堆成熟";
            this.usable=true;
        }
        else if(!hasplant&&getfirstcanwork_pokego()!=null&&planted){
            PokeGo pgt=getfirstcanwork_pokego();
            AbstractMonster m = PokeGo.getMonsterbycache(pgt.monsterClass);
            this.description = "命令"+m.name + "收获";
            this.usable=true;
        }
        else{
            this.usable=false;
        }
    }

    public void useOption() {

            if (this.usable) {
                this.usable = false;
                AbstractDungeon.effectList.add(new plante());
                hasplant=true;

        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Lift Option");
        TEXT = new String[]{"种植","收获"};
    }
}

