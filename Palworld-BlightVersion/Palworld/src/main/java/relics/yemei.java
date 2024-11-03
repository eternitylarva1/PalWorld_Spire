package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.lineTwo.InfoBlight;

public class yemei extends CustomRelic {
    public static final String ID = "YEMEI";
    public static final String IMG = "images/relics/CYSDX.png";
    public static final String DESCRIPTION = "获得时，所有帕鲁获得15点血量上限";


    public yemei() {
        super("YEMEI", new Texture(Gdx.files.internal("images/relics/YEMEI.png")), RelicTier.COMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new yemei();
    }

    private boolean firstTurn = false;

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        firstTurn = true;
    }
    @Override
    public void onEquip()
    {
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo ->
        {
            System.out.println("检测到帕鲁球");
            if(pokeGo.monsterClass!=null&&pokeGo.counter>0)
            {
                System.out.println("帕鲁球为合法目标");
                pokeGo.counter+=15;
            }
        });
    };

    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void update() {
        super.update();
    }
}
