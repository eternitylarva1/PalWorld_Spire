/*
package relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import relics.abstracrt.ClickableRelic;

import java.util.Random;

import static utils.randommonster.randommonster;

public class MonsterEgg extends ClickableRelic {
    public static final String ID = "BloodDonationBag";
    public static final String IMG = "images/relics/BloodDonationBag.png";
    public static final String DESCIPTION = "孵化出随机怪物。";
    public int fa;
    public int mo;
    public int HP=0;
    public AbstractMonster m;

    private boolean RclickStart;
    private boolean Rclick;

    public static boolean canUse = true;

    public MonsterEgg(int hp) {
        super("BloodDonationBag", new Texture(Gdx.files.internal("images/relics/BloodDonationBag.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        HP=hp;
    }

    public void onEnterRoom()
    {
        if(!this.usedUp)
        {AbstractRoom room=AbstractDungeon.getCurrRoom();
            if (room instanceof RestRoom) {
                this.usedUp();
                PokeGo pg1=new PokeGo();
                int a=AbstractDungeon.monsterRng.random(68)+1;
                AbstractMonster am=randommonster(a);
                pg1.monsterClass=am.getClass();
                pg1.counter=HP;
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), pg1);
            }
        }

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new MonsterEgg(HP);
    }

    //右键卖血
    public void onRightClick() {

    }


    @Override
    public void update() {
        super.update();
    }


}
*/