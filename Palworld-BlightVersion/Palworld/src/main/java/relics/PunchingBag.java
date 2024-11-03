package relics;

import cards.Cancel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import patches.action.ChangeTargetPatch;
import relics.abstracrt.ClickableRelic;
import utils.Point;
import utils.Utils;

public class PunchingBag extends ClickableRelic {
    public static final String ID = "PunchingBag";
    public static final String IMG = "images/relics/PunchingBag.png";
    public static final String DESCRIPTION = "女黄蜂之杖";

    public PunchingBag() {
        super("PunchingBag", new Texture(Gdx.files.internal("images/relics/PunchingBag.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        counter = -1;
    }

public boolean click=false;

    public static AbstractMonster source = null;

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PunchingBag();
    }

    private int cardCount = 0;

    //右键使用
    public void onRightClick() {
        if (!click&&AbstractDungeon.getCurrRoom()instanceof MonsterRoom) {
            click=true;
            AbstractCard c=new Cancel();
            c.upgrade();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
        }
    }

    @Override
    public void onEquip() {
        super.onEquip();

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        click=false;


    }


    @Override
    public void update() {
        super.update();
    }
}
