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
import relics.PalBad;
import relics.PokeGo;
import relics.PokeGophone;
import utils.InstanceMaker;

import static Option.get_pokego.*;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static relics.PokeGophone.*;

public class sleeptaketurn extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public int restcount=0;
    AbstractMonster monster1m;
    AbstractMonster monster2m;
    public String monster1="";
    public String monster2="";
    public Texture img1=loadImage("images/ui/campfire/palslee1.png");
    PokeGo pgt=getfirstcanwork_pokego();
    PokeGo pg2;
    public sleeptaketurn(boolean active) {
        this.label = TEXT[0];


        this.usable =canwork_pokego()>=0;
        if(this.usable)
        {
            if(get_pokego.get_pokego(0)!=null)
            {
                this.usable=true;
            };
        }

        this.updateUsability(usable);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

    }
    public void updateusable()
    {
        pgt=getfirstcanwork_pokego();
        if(pgt.hasworked)
        {
            this.usable=false;
        }
        else
        {
            this.usable=true;
        }
    }

    public void updateUsability(boolean canUse) {



                pgt=getfirstcanwork_pokego();
                if(pgt!=null){

                    monster1m = PokeGo.getMonsterbycache(pgt.monsterClass);
                    monster1 = monster1m.name;
                    int a= (int) (pgt.counter*0.2);
                    this.description = "让" + monster1+"休息，回复"+a+"生命";
                    if(technology>=3)
                    {
                        if(a>50) {
                            a = 50;
                        }
                        this.description = "让" + monster1+"休息，回复"+a+"生命(20%)";
                    }
                    else
                    {
                        if(a>20) {
                            a = 20;
                        }
                        this.description = "让" + monster1+"休息，回复"+a+"生命(20%)";
                    }
                    if(AbstractDungeon.player.hasRelic(PalBad.ID))
                    {
                        a+=15;
                        this.description = "让" + monster1+"休息，回复"+a+"生命(20%+15(高级帕鲁床))";
                    }
                }
                else
                {
                    this.usable=false;
                    this.description = "当前帕鲁已经行动过了或者不存在";
                }

            this.img = img1;

    }

    public void useOption() {
        if (this.usable) {


            if(pgt.monsterClass!=null&&pgt.counter>=0){
                AbstractDungeon.effectList.add(new sleeptaketurne());

                pgt=getfirstcanwork_pokego();
                if(pgt==null)
                {
                    this.usable=false;
                }


            }
            PokeGophone.refresh();
        }

    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = new String[]{"帕鲁休憩"};
    }
}



