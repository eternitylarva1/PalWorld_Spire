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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeGo;
import utils.InstanceMaker;
import utils.randommonster;

import static Option.get_pokego.canwork_pokego;
import static Option.get_pokego.get_blank_pokego;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class peizhong extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public int restcount=0;
    AbstractMonster monster1m;
    AbstractMonster monster2m;
    public String monster1="";
    public String monster2="";
    public Texture img1=loadImage("images/ui/campfire/sleep1.png");
    public PokeGo pg1;
    public PokeGo pg2;
    public peizhong(boolean active) {
        this.label = TEXT[0];


        this.usable =canwork_pokego()>=2;
        if(this.usable)
        {
            if(get_blank_pokego(0)!=null)
            {
                System.out.println("检测到空帕鲁球，开始选择父本母本");
             pg1= get_pokego.get_pokego(0);
             pg2= get_pokego.get_pokego(1);
            monster1m= InstanceMaker.getInstanceByClass(pg1.monsterClass);
            monster2m= InstanceMaker.getInstanceByClass(pg2.monsterClass);
            monster1=monster1m.name;
            monster2=monster2m.name;
            System.out.println(monster1);
            System.out.println(monster2);
            }
            else
            {
                System.out.println("未检测到空帕鲁球");
                this.usable =false;
            }
        }

        this.updateUsability(usable);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

    }

    public void updateUsability(boolean canUse) {

        if(monster1!=""&&monster2!=""){

        if(canUse) {
            int yulan=pg1.fanzhili+pg2.fanzhili;
            this.description = "(不消耗火堆)右键帕鲁球以选择,配种" +monster1 + "和" +monster2+"生出"+ randommonster.randommonster(yulan%70).name;
        }else{
            this.description ="右键帕鲁球以选择帕鲁进行配种";
        }
        this.img = img1;
    }else
    {
        this.description = "(不消耗火堆)右键帕鲁球以选择,配种" +monster1 + "和" +monster2;
        this.img = img1;
    }


}

    public void useOption() {
        if (this.usable) {

            this.usable=false;

            if(monster1!=""&&monster2!=""){
            AbstractDungeon.effectList.add(new peizhonge());
            }
        }

    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = new String[]{"配种"};
    }
}


