package relics;

import actions.CreateIntentAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.LiveForeverPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import evepower.FeelNoPainPower1;
import evepower.FireBreathingPower1;
import evepower.RupturePower1;
import helpers.MinionHelper;
import powers.BlankCardPower;
import powers.chaofeng;
import relics.abstracrt.BookSuit;
import utils.randommonster;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.core.CardCrawlGame.music;

public class BattleBell extends BookSuit {
    public static final String ID = "BattleBell";
    public static final String IMG = "images/relics/Void.png" ;
    public static final String DESCRIPTION = "战斗铃铛";

    public BattleBell() {
        super("BattleBell", new Texture(Gdx.files.internal("images/relics/BattleBell.png")), RelicTier.SPECIAL, LandingSound.CLINK, 0);
        this.counter=-1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new BattleBell();
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


    public static AbstractPower getrandomPower(AbstractMonster m)
    {
        ArrayList<AbstractPower> powers=new ArrayList<>();
        powers.add(new StrengthPower(m,3));
        powers.add(new ThornsPower(m,3));
        powers.add(new CuriosityPower(m,3));
        if(!(m.type==AbstractMonster.EnemyType.BOSS)){
            powers.add(new AngerPower(m, 3));
            powers.add(new AngryPower(m, 3));
            powers.add(new RupturePower1(m,3));
        }
        powers.add(new chaofeng(m,3));
        powers.add(new FeelNoPainPower1(m,3));
        if(!MinionHelper.getMinions().monsters.contains(m)) {powers.add(new FireBreathingPower1(m,3));}else
        {
            powers.add(new FireBreathingPower(m,3));
        }
        powers.add(new FlightPower(m,3));
        powers.add(new DoubleDamagePower(m,3,true));

        powers.add(new RitualPower(m,3,false));
        if(!MinionHelper.getMinions().monsters.contains(m)) {
            powers.add(new SharpHidePower(m, 3));
        }
        powers.add(new StrengthPower(m,3));
        powers.add(new RegenerateMonsterPower(m,3));
        powers.add(new LiveForeverPower(m,3));
        powers.add(new ArtifactPower(m,3));
        powers.add(new MalleablePower(m));
        powers.add(new IntangiblePower(m,3));
        powers.add(new MetallicizePower(m,3));
        powers.add(new PlatedArmorPower(m,3));
        powers.add(new HelloPower(m,3));

        int i=AbstractDungeon.cardRandomRng.random(powers.size()-1);
        return powers.get(i);

    }
    @Override
    public void atBattleStart()
    {

        AbstractRoom room=AbstractDungeon.getCurrRoom();
        if(AbstractDungeon.actNum>=3)
        {System.out.println("检测到有战斗铃铛，力量+1");
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));
        }

        if (room instanceof MonsterRoom&&AbstractDungeon.actNum>=3) {
        Iterator var1=AbstractDungeon.getMonsters().monsters.iterator();
        while(var1.hasNext())
        {
            AbstractMonster cm= (AbstractMonster) var1.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(cm,cm,getrandomPower(cm)));
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
