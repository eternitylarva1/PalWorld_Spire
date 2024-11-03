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
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


import relics.PokeGo;

import static Option.get_pokego.canwork_pokego;
import static Option.get_pokego.getfirstcanwork_pokego;
import static relics.PokeGophone.*;
import static relics.PokeGophone.PokeGowork;

public class plante extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean hasDug = false;
    private Color screenColor;

    public plante() {
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



            CardCrawlGame.sound.play("dig");


            //AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PokeGo()));

            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            //AbstractDungeon.combatRewardScreen.open();

            CardCrawlGame.sound.play("SHOVEL");
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            PokeGo pg=getfirstcanwork_pokego();
            if (pg != null) {
                PokeGowork(pg);
            }
            dz.updateUsability(dz.usable);
            p.updateUsability(p.usable);
            sl.usable= canwork_pokego()>0;
            sl.updateUsability(sl.usable);
            //AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
            CampfireUI cm=rest.campfireUI;
            cm.hidden=false;
            cm.somethingSelected=false;
            cm.update();
            if(planted) {
                planted = false;

                if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Strawberry());
                } else if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Pear());
                } else if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new HappyFlower());
                } else if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Mango());
                } else if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Turnip());
                } else if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new MagicFlower());
                } else if (AbstractDungeon.miscRng.random(100) > 75) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new DeadBranch());
                }
            }
            else{
                planted = true;
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
