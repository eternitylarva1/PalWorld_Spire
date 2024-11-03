package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PokeBall_BluePrint extends CustomRelic {
    public static final String ID = "PokeBall_BluePrint";
    public static final String IMG = "images/relics/PokeBall_BluePrint.png";
    public static final String DESCRIPTION = "帕鲁球蓝图";


    public PokeBall_BluePrint() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.RARE, LandingSound.CLINK
        );

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PokeBall_BluePrint();
    }

    @Override
    public void atBattleStart()
        {

        }
    @Override
    public void update() {
        super.update();
    }


}
