package Option;



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeGo;
import utils.InstanceMaker;

import static Option.get_pokego.*;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class dig1 extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    PokeGo pgt= getfirstcanwork_pokego();
    public static final String[] TEXT;
    public int restcount=0;
    public Texture img2=loadImage("images/ui/campfire/dig1.png");
    public dig1(boolean active) {
        this.label = TEXT[0];


        this.usable = canwork_pokego()>0;
        this.updateUsability(active);
        System.out.println("一共有"+canwork_pokego()+"可以工作的帕鲁");
    }

    public void updateUsability(boolean canUse) {
        if(canUse&&getfirstcanwork_pokego()!=null){
            pgt=getfirstcanwork_pokego();
            AbstractMonster m = InstanceMaker.getInstanceByClass(pgt.monsterClass);
            this.description = "命令"+m.name + "拾荒,"+"还剩"+canwork_pokego()+"个没行动的帕鲁";
            this.img = img2;
        }
        else{
            this.usable=false;
            this.description = "命令一只帕鲁锻造（不视为行动）剩余" + 0+ "次";
            this.img = img2;
        }
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new dig1e());
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = new String[]{"拾荒"};
    }
}

