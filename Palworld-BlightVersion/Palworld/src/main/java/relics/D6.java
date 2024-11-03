package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Enchiridion;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.relics.NilrysCodex;
import relics.abstracrt.ClickableRelic;

import java.util.Arrays;
import java.util.List;

public class D6 extends ClickableRelic{
    public static final String ID = "D6";
    public static final String IMG = "images/relics/D6.png";
    public static final String DESCRIPTION = "大师球。";

    public D6() {
        super("D6", new Texture(Gdx.files.internal("images/relics/D6.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter=-1;
    }

    private static List<String> books = Arrays.asList(Necronomicon.ID, Enchiridion.ID, NilrysCodex.ID);

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new D6();
    }

    //右键开roll
    public void onRightClick() {

    }
    @Override
    public boolean canSpawn()
    {
        return false;
    }
    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void update() {
        super.update();
    }
}
