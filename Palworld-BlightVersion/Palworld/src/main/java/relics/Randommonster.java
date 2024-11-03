package relics;

import actions.SummonMinionAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import helpers.SummonHelper;
import monsters.act1.*;
import monsters.act2.*;
import monsters.act3.*;
import monsters.act4.CorruptHeart1;
import monsters.act4.SpireShield1;
import monsters.act4.SpireSpear1;
import patches.monster.Cultist1;
import relics.abstracrt.ClickableRelic;
import utils.ScreenPartition;

import java.util.Arrays;


import static utils.randommonster.randommonster;

//怪物蛋
public class Randommonster extends ClickableRelic {
    public static final String ID = "Randommonster";
    public static final String IMG = "images/relics/BlankCard.png";
    public static final String DESCRIPTION = "科技树";
    public int HP=0;
    public  int fa;
    public  int ma;


    private boolean isValid = false;
    String[] a={"拾荒者"};
    public Randommonster() {
        super("Randommonster", new Texture(Gdx.files.internal("images/relics/Goat_Horn.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter=15;

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Randommonster();
    }

    //右键开大


    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        this.counter=15;

    }


    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public  void atTurnStart()
    {
        if(this.counter>=1){
            int i = AbstractDungeon.aiRng.random(0, 67);
            AbstractMonster am1 = changeMyMonster(randommonster(i));

            i = AbstractDungeon.aiRng.random(0, 67);
            AbstractMonster am2 = changeMonster(randommonster(i));
            AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(am2, false));
            AbstractDungeon.actionManager.addToTop(new SummonMinionAction(am1));
            ScreenPartition.assignSequentialPosition(am1, am2);
            am1.rollMove();
            am2.rollMove();
            am2.createIntent();
            am2.usePreBattleAction();
            this.counter--;
        }
    }
    public static AbstractMonster changeMonster(AbstractMonster m)
    {
        AbstractMonster monster=m;
        switch (m.id)
        {
            case"Reptomancer":
                monster=new Reptomancer2();
                break;
            case"BronzeAutomaton":
                monster=new BronzeAutomaton2();
                break;
            case"GremlinLeader":
                monster=new GremlinLeader2();
                break;
            case"TheCollector":
                monster=new TheCollector2();
        }
        return monster;
    }
    public AbstractMonster changeMyMonster(AbstractMonster m)
    {
        AbstractMonster monster=m;
        AbstractMonster monster1;
        switch (m.id)
        {
            case"Hexaghost":monster=new Hexaghost1();
                break;
            case"Cultist":monster=new Cultist1(0,0);
                break;
            case"Sentry":monster=new Sentry1(0,0);
                break;
            case"Looter":monster=new Looter1(0,0);
                break;
            case"GremlinTsundere":monster=new GremlinTsundere1(0,0);
                break;
            case"SlimeBoss":monster=new SlimeBoss1();
                break;
            case"TheCollector":monster=new TheCollector1();

                break;
            case"BronzeAutomaton":
                monster=new BronzeAutomaton1();

                break;
            case"BronzeOrb":
                monster=new BronzeOrb1(-300.0F, 200.0F, 0);
                break;
            case"GremlinLeader":
                monster=new GremlinLeader1();
                break;
            case"Healer":
                monster=new Healer1(0,0);
                break;
            case"SlaverBoss":
                monster=new Taskmaster1(0,0);
                break;

            case"Centurion":
                monster=new Centurion1(0,0);
                break;
            case"Chosen":
                monster=new Chosen1(0,0);
                break;
            case"Snecko":
                monster=new Snecko1(0,0);
                break;
            case"Exploder":
                monster=new Exploder1(0,0);
                break;
            case"Repulsor":
                monster=new Repulsor1(0,0);
                break;
            case"WrithingMass":
                monster=new WrithingMass1();
                break;
            case"Reptomancer":

                monster=new Reptomancer1();/*
                for(int j=0;j<2;j++){
                    monster1 = new SnakeDagger1(0, 0);
                    monster1.drawY = AbstractDungeon.player.drawY + (int) site[slot].y + (float) Settings.WIDTH /10;
                    //monster1.drawX = AbstractDungeon.player.drawX - 175 + (int) site[slot].x-200;
                    monster1.drawX = 800;

                    monster1.flipHorizontal = true;
                    SummonHelper.summonMinion(monster1);
                }*/
                break;
            case"SpireGrowth":
                monster=new SpireGrowth1();
                break;

            case"Nemesis":
                //monster=new Nemesis1();
                break;
            case"Donu":
                monster=new Donu1();
                break;
            case"Deca":
                monster=new Deca1();
                break;
            case"AwakenedOne":
                monster=new AwakenedOne1(0,0);
                break;
            case"SpireShield":
                monster=new SpireShield1();
                break;
            case"SpireSpear":
                monster=new SpireSpear1();
                break;
            case"Orb Walker":
                monster=new OrbWalker1(0,0);
                break;
            case"CorruptHeart":
                monster=new CorruptHeart1();
                break;
            case"TheGuardian":monster=new TheGuardian1();
                break;

        }
        return monster;
    }
    @Override
    public void onRightClick() {


    }

    @Override
    public void update() {
        super.update();

    }
}
