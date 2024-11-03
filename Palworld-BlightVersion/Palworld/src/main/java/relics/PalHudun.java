package relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.abstracrt.ClickableRelic;

import static relics.PokeGophone.technology;

public class PalHudun extends ClickableRelic {
    public static final String ID = "PalHudun";
    public static final String IMG = "images/relics/D4.png";
    public static final String DESCRIPTION = "帕鲁护盾";

    public PalHudun() {
        super(ID, new Texture(Gdx.files.internal("images/relics/Hudun.png")), RelicTier.BOSS, LandingSound.CLINK);
        this.counter=-1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PalHudun();
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
    @Override
    public void onEquip() {
        super.onEquip();
        technology=0;
    }

}
