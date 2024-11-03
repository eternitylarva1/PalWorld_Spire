package relics;

import actions.CreateIntentAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import powers.BlankCardPower;
import relics.abstracrt.BookSuit;
import relics.abstracrt.ClickableRelic;
import utils.randommonster;

import static com.megacrit.cardcrawl.core.CardCrawlGame.music;

public class DeathBook extends ClickableRelic {
    public static final String ID = "DeathBook";
    public static final String IMG = "images/relics/Void.png" ;
    public static final String DESCRIPTION = "蜂拥铃铛";

    public DeathBook() {
        super("DeathBook", new Texture(Gdx.files.internal("images/relics/Void.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter=-1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        DeathBook a=new DeathBook();
        return a;
    }

    //右键开大
    public void onRightClick() {

    }

    @Override
    public void onEquip() {
        super.onEquip();

    }

    public void onUnequip() {
        super.onUnequip();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        super.onEnterRoom(room);

    }


    public void positionmonster(AbstractMonster am,AbstractMonster mm)
    {
        am.drawY =mm.drawY+ (float) Settings.HEIGHT /8;
        am.drawX = (float) (mm.drawX+mm.hb_w/1.5);

        am.rollMove();
        AbstractDungeon.actionManager.addToBottom(new CreateIntentAction(am));
        am.usePreBattleAction();
        if(AbstractDungeon.player.hasRelic(BattleBell.ID)&&AbstractDungeon.actNum>=3)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(am,am,BattleBell.getrandomPower(am)));
        }
        AbstractRoom room=AbstractDungeon.getCurrRoom();

    }
    @Override
    public void atBattleStart()
    {

        AbstractRoom room=AbstractDungeon.getCurrRoom();
        if(AbstractDungeon.actNum>=2)
        {System.out.println("检测到有蜂拥铃铛，费用+1");
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
            AbstractMonster cm=AbstractDungeon.getMonsters().monsters.get(0);
            if ( room instanceof MonsterRoomBoss)
            {

                if(AbstractDungeon.actNum==2)
                {
                    music.fadeAll();
                    int a=AbstractDungeon.miscRng.random(2)+1;;
                    AbstractMonster am= randommonster.randomBoss(a);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));
                    positionmonster(am,cm);

                }
                if(AbstractDungeon.actNum==3)
                {
                    music.fadeAll();
                    int a=AbstractDungeon.miscRng.random(2)+4;;
                    AbstractMonster am= randommonster.randomBoss(a);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));
                    positionmonster(am,cm);

                }




            } else if (room instanceof MonsterRoomElite)
            {
                if(AbstractDungeon.actNum==2)
                {
                    int a=AbstractDungeon.miscRng.random(2)+1;;
                    AbstractMonster am= randommonster.randomElite(a);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));

                    positionmonster(am,cm);
                }
                if(AbstractDungeon.actNum==3)
                {
                    int a=AbstractDungeon.miscRng.random(2)+4;;
                    AbstractMonster am= randommonster.randomElite(a);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));

                    positionmonster(am,cm);
                }
                if(AbstractDungeon.actNum==4)
                {
                    int a=AbstractDungeon.miscRng.random(7,9);;
                    AbstractMonster am= randommonster.randomElite(a);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));

                    positionmonster(am,cm);
                }
            }
            else{
                if(AbstractDungeon.actNum==2)
                {
                    int a=AbstractDungeon.miscRng.random(17)+1;;
                    AbstractMonster am= randommonster.randommonster(a);

                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));

                    positionmonster(am,cm);
                }
                if(AbstractDungeon.actNum==3)
                {
                    int a=AbstractDungeon.miscRng.random(12)+26;;
                    AbstractMonster am= randommonster.randommonster(a);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(am,false));

                    positionmonster(am,cm);
                }

            }



    }
    @Override
    public void atTurnStart(){

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
