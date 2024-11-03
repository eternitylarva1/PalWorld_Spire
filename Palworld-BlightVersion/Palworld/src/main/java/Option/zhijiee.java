package Option;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


import relics.NineVolt;
import utils.InstanceMaker;

import java.util.Objects;

import static Option.get_pokego.*;
import static relics.PokeGophone.*;
import static relics.PokeGophone.p;

public class zhijiee extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean hasDug = false;
    private Color screenColor;

    public zhijiee() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 2.0F;
        this.screenColor.a = 0.0F;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.updateBlackScreenColor();
        if (this.duration < 1.0F && !this.hasDug) {
            this.hasDug = true;



            CardCrawlGame.sound.play("VO_CULTIST_1A");
            CardCrawlGame.sound.play("VO_CULTIST_1B");
            CardCrawlGame.sound.play("VO_CULTIST_1C");

            //AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PokeGo()));

            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            //AbstractDungeon.combatRewardScreen.open();

            CardCrawlGame.metricData.addCampfireChoiceData("DIG");
        }

        if (this.duration < 0.0F) {
            this.isDone = true;

            //AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            Objects.requireNonNull(get_pokego.relic(dingwei)).counter=0;
            Objects.requireNonNull(relic(dingwei)).monsterClass=null;
            Objects.requireNonNull(get_pokego.relic(dingwei)).updateDescription();
            dz.usable=canwork_pokego()>0;
            sl.updateUsability(sl.usable);
            dz.updateUsability(dz.usable);
            p.updateUsability(p.usable);
            RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
            CampfireUI cm=rest.campfireUI;
            cm.hidden=false;
            cm.somethingSelected=false;
            cm.update();
            if (AbstractDungeon.player.hasRelic(NineVolt.ID)) {
            AbstractDungeon.player.getRelic(NineVolt.ID).counter++;
            }
            else {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new NineVolt());
            }

            zj.usable=false;
            p.usable =canwork_pokego()>=2;
            if(p.usable)
            {
                if(get_blank_pokego(0)!=null)
                {
                    System.out.println("检测到空帕鲁球，开始选择父本母本");
                    p.pg1= get_pokego.get_pokego(0);
                    p.pg2= get_pokego.get_pokego(1);
                    p.monster1m= InstanceMaker.getInstanceByClass(p.pg1.monsterClass);
                    p.monster2m= InstanceMaker.getInstanceByClass(p.pg2.monsterClass);
                    p.monster1=p.monster1m.name;
                    p.monster2=p.monster2m.name;
                    p.updateUsability(p.usable);
                }
                else
                {
                    System.out.println("未检测到空帕鲁球");
                    p.usable =false;
                }
            }
            }

        }



    private void updateBlackScreenColor() {
        if (this.duration > 1.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
    }

    public void dispose() {
    }
}
