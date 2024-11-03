package relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.abstracrt.ClickableRelic;

public class PalBad extends ClickableRelic {
    public static final String ID = "PalBed";
    public static final String IMG = "images/relics/PalBed.png";
    public static final String DESCRIPTION = "帕鲁床";

    public PalBad() {
        super("PalBed", new Texture(Gdx.files.internal("images/relics/PalBed.png")), RelicTier.UNCOMMON, LandingSound.CLINK);
        this.counter=-1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PalBad();
    }

    //右键开roll
    public void onRightClick() {

    }


    @Override
    public void onVictory() {
        super.onVictory();
    }


}
