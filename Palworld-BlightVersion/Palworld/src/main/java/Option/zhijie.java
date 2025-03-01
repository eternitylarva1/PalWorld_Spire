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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeGo;
import relics.lineTwo.InfoBlight;
import utils.InstanceMaker;

import java.util.Iterator;
import java.util.Objects;

import static Option.get_pokego.*;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class zhijie extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public int restcount=0;
    AbstractMonster monster1m;
    AbstractMonster monster2m;
    public String monster1="";
    public String monster2="";
    PokeGo pg1;
    public Texture img1=loadImage("images/ui/campfire/outline1.png");
    public zhijie(boolean active) {
        this.label = TEXT[0];

        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            if (pokeGo.monsterClass != null && pokeGo.counter != 0) {
                restcount++;
            }
        });

        this.usable = restcount>=1;
        if(this.usable)
        {
            if(Objects.requireNonNull(get_pokego(0)).counter!=0)
            {
                System.out.println("检测到空帕鲁球，开始选择父本母本");
                PokeGo pg1= get_pokego.get_pokego(0);
                if(Objects.requireNonNull(relic(dingwei)).counter>0){
                    pg1= get_pokego.relic(dingwei);
                }
                monster1m= PokeGo.getMonsterbycache(pg1.monsterClass);
                monster1=monster1m.name;
                System.out.println(monster1);

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

        if(monster1!=""){
            if(canUse) {
                this.description = "（右键帕鲁球选择）放生"+monster1+",功德+1";
            }else{
                this.description ="放生"+monster1+",功德+1";
            }
            this.img = img1;
        }else
        {
            this.description = "未实装，请勿点击";
            this.img = img1;
        }


    }

    public void useOption() {
        if (this.usable) {

            this.usable=false;

            if(monster1!=""){

                AbstractDungeon.effectList.add(new zhijiee());
            }
        }

    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = new String[]{"放生"};
    }
}



