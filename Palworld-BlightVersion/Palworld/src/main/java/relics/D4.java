package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import relics.abstracrt.ChargeableRelic;
import relics.abstracrt.ClickableRelic;
import relics.abstracrt.DevilInterface;

import java.lang.reflect.Field;

public class D4 extends ClickableRelic {
    public static final String ID = "D4";
    public static final String IMG = "images/relics/D4.png";
    public static final String DESCRIPTION = "帕鲁护盾";

    public D4() {
        super("D4", new Texture(Gdx.files.internal("images/relics/D4.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter=-1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new D4();
    }

    public boolean roll = false;

    private boolean doRoll = false;

    public int devilOnlyRelic = 0;
    public int rare = 0;
    public int uncommon = 0;
    public int common = 0;
    public int special = 0;
    public int boss = 0;
    public int shop = 0;
    public int starter = 0;
    public int deprecated = 0;

    //右键开roll
    public void onRightClick() {

    }


    public void init() {
        devilOnlyRelic = 0;
        rare = 0;
        uncommon = 0;
        common = 0;
        special = 0;
        boss = 0;
        shop = 0;
        starter = 0;
        deprecated = 0;
    }

    @Override
    public void onVictory() {
        super.onVictory();
    }


}
