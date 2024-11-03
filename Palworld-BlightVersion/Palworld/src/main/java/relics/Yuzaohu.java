package relics;


import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class Yuzaohu extends CustomRelic {
    public static final String ID = "Yuzaohu";
    public static final String IMG = "images/relics/Yuzaohu.png";
    public static final String DESCRIPTION = "玉藻狐";


    public Yuzaohu() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.RARE, LandingSound.CLINK
        );

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Yuzaohu();
    }

    @Override
    public void atBattleStart()
    {

    }
    @Override
    public void update() {
        super.update();
    }
    @Override
    public void onVictory()
    {
        if(AbstractDungeon.getCurrRoom()instanceof MonsterRoomElite||AbstractDungeon.getCurrRoom()instanceof MonsterRoomBoss)
        {
            if(AbstractDungeon.miscRng.randomBoolean()){
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(new PokeGo()));
            }
        }
    }

}

