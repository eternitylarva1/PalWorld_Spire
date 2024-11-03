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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


import relics.PokeGo;
import relics.PokeGophone;

import static relics.PokeGophone.*;
import static relics.PokeGophone.p;
import static utils.DesManager.getrandomEntry;
import static utils.randommonster.getKey;
import static utils.randommonster.randommonster;

public class peizhonge extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean hasDug = false;
    private Color screenColor;

    public peizhonge() {
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

            PokeGo pg1=get_pokego.get_blank_pokego(0);
            pg1.Entrylist.clear();

            AbstractMonster am=randommonster((p.pg1.fanzhili+p.pg2.fanzhili)%70);
            for (String entry:p.pg1.Entrylist)
            {
                if (AbstractDungeon.miscRng.randomBoolean()&&!pg1.Entrylist.contains(entry)) {
                    pg1.Entrylist.add(entry);
                }}
            for (String entry:p.pg2.Entrylist)
            {
                if (AbstractDungeon.miscRng.randomBoolean()&&!pg1.Entrylist.contains(entry)) {
                    pg1.Entrylist.add(entry);
                }
            }
            pg1.Entrylist.add(getrandomEntry());
            pg1.monsterClass=am.getClass();
            System.out.println("抽取怪物完毕，为"+am.name);
            float ff=(p.pg1.counter+PokeGophone.p.pg2.counter)*0.75f;
            int intValue = Math.round(ff);
            pg1.counter= intValue;
            if(PokeGophone.p.monster1m instanceof Darkling||PokeGophone.p.monster2m instanceof Darkling)
            {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new PokeGo());
            }

            CardCrawlGame.sound.play("VO_CULTIST_1A");
            CardCrawlGame.sound.play("VO_CULTIST_1B");
            CardCrawlGame.sound.play("VO_CULTIST_1C");
            pg1.fanzhili=getKey(am.id);
            pg1.updateDescription();
            //AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PokeGo()));

            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            //AbstractDungeon.combatRewardScreen.open();

            CardCrawlGame.metricData.addCampfireChoiceData("DIG");
        }

        if (this.duration < 0.0F) {
            this.isDone = true;

            //AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
            CampfireUI cm=rest.campfireUI;
            cm.hidden=false;
            cm.somethingSelected=false;
            cm.update();

            dz.updateUsability(dz.usable);
            p.updateUsability(p.usable);


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
