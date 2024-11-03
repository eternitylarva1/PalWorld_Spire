package Option;


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
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import relics.*;
import relics.PokeGophone;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static relics.PokeGophone.technology;

public class keyane extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean hasDug = false;
    private Color screenColor;

    public keyane() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 0.5F;
        this.screenColor.a = 0.0F;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.updateBlackScreenColor();
        if (this.duration < 1.0F && !this.hasDug) {
            this.hasDug = true;
            CardCrawlGame.sound.play("SHOVEL");
            PokeGophone pgp=(PokeGophone) player.getRelic("BloodBag");


            //AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PokeGo()));


            //AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            //AbstractDungeon.combatRewardScreen.open();

            CardCrawlGame.metricData.addCampfireChoiceData("DIG");
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            switch (technology)
            {
                case 3:
                    AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new BFFS()));
                    AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                    AbstractDungeon.combatRewardScreen.open();

                    break;
                case 4:
                    AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PunchingBag()));
                    AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                    AbstractDungeon.combatRewardScreen.open();
                    break;
                case 2:
                    AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new D4()));
                    AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                    AbstractDungeon.combatRewardScreen.open();

                    break;
                default:
                    break;

            }

            PokeGophone pg= (PokeGophone) player.getRelic("BloodBag");
            if(pg!=null){
                pg.tips.add((new PowerTip("科研等级" + technology, keyan.TEXT1[technology])));
                pg.initializeTips();
            }
            else
            {
                System.out.println("错误，不存在控制台");
            }
            technology++;

            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
            CampfireUI cm=rest.campfireUI;




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
