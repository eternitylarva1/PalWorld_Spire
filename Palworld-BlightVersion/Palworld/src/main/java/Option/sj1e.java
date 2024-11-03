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
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import relics.PokeBall_BluePrint;
import relics.PokeGo;
import relics.PokeGophone;

import static Option.get_pokego.get_blank_pokego;

public class
sj1e extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean hasDug = false;
    private Color screenColor;

    public sj1e() {
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
            CardCrawlGame.sound.play("SHOVEL");
            PokeGophone pgp=(PokeGophone)AbstractDungeon.player.getRelic("BloodBag");

            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new PokeGo());
            //AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PokeGo()));
            if(AbstractDungeon.player.hasRelic(PokeBall_BluePrint.ID))
            {
                AbstractDungeon.player.gold -= 50*pgp.counter/2;
            }
            else {
                AbstractDungeon.player.gold -= 50 * pgp.counter;
            };
            pgp.counter++;
            //AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            //AbstractDungeon.combatRewardScreen.open();

            CardCrawlGame.metricData.addCampfireChoiceData("DIG");
        }

        if (this.duration < 0.0F) {
            this.isDone = true;

            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
            CampfireUI cm=rest.campfireUI;

            if(get_blank_pokego(0)!=null)
            {

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
