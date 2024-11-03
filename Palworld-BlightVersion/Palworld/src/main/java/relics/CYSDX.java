package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CYSDX extends CustomRelic {
    public static final String ID = "CYSDX";
    public static final String IMG = "images/relics/CYSDX.png";
    public static final String DESCRIPTION = "召唤帕鲁时，玩家获得1力量";


    public CYSDX() {
        super("CYSDX", new Texture(Gdx.files.internal("images/relics/CYSDX.png")), RelicTier.RARE, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new CYSDX();
    }

    private boolean firstTurn = false;

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        firstTurn = true;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
//        if (firstTurn) {
//            firstTurn = false;
//            for (AbstractMonster m : MinionHelper.getMinionMonsters()) {
//                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m,  5), 5));
//            }
//            this.flash();
//        }
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
