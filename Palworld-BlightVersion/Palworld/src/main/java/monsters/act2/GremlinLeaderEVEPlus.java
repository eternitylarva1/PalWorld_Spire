package monsters.act2;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static utils.ScreenPartition.assignSequentialPosition;

public class GremlinLeaderEVEPlus extends AbstractMonster {
    public static final String ID = "GremlinLeader";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 140;
    private static final int HP_MAX = 148;
    private static final int A_2_HP_MIN = 145;
    private static final int A_2_HP_MAX = 155;
    public static final String ENC_NAME = "Gremlin Leader Combat";
    public ArrayList <AbstractMonster> gremlins = new ArrayList<>();
    public static final float[] POSX;
    public static final float[] POSY;
    private static final byte RALLY = 2;
    private static final String RALLY_NAME;
    private static final byte ENCOURAGE = 3;
    private static final int STR_AMT = 3;
    private static final int BLOCK_AMT = 6;
    private static final int A_2_STR_AMT = 4;
    private static final int A_18_STR_AMT = 5;
    private static final int A_18_BLOCK_AMT = 10;
    private int strAmt;
    private int blockAmt;
    private static final byte STAB = 4;
    private int STAB_DMG = 6;
    private int STAB_AMT = 3;

    public GremlinLeaderEVEPlus() {
        super("召唤地精", "GremlinLeader", 148, 0.0F, -15.0F, 200.0F, 310.0F, (String)null, 35.0F, 0.0F);
        this.type = EnemyType.ELITE;
        this.loadAnimation("images/monsters/theCity/gremlinleader/skeleton.atlas", "images/monsters/theCity/gremlinleader/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(145, 155);
        } else {
            this.setHp(140, 148);
        }

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.strAmt = 5;
            this.blockAmt = 10;
        } else if (AbstractDungeon.ascensionLevel >= 3) {
            this.strAmt = 4;
            this.blockAmt = 6;
        } else {
            this.strAmt = 3;
            this.blockAmt = 6;
        }

        this.dialogX = -80.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        this.damage.add(new DamageInfo(this, this.STAB_DMG));
    }

    public void usePreBattleAction() {

    }

    private AbstractMonster getRandomGremlin(int slot) {
        ArrayList<String> pool = new ArrayList();
        pool.add("GremlinWarrior");
        pool.add("GremlinWarrior");
        pool.add("GremlinThief");
        pool.add("GremlinThief");
        pool.add("GremlinFat");
        pool.add("GremlinFat");
        pool.add("GremlinTsundere");
        pool.add("GremlinWizard");
        float x;
        float y;
        switch (slot) {
            case 0:
                x = GremlinLeader.POSX[0];
                y = GremlinLeader.POSY[0];
                break;
            case 1:
                x = GremlinLeader.POSX[1];
                y = GremlinLeader.POSY[1];
                break;
            case 2:
                x = GremlinLeader.POSX[2];
                y = GremlinLeader.POSY[2];
                break;
            default:
                x = GremlinLeader.POSX[0];
                y = GremlinLeader.POSY[0];
        }

        return MonsterHelper.getGremlin((String)pool.get(AbstractDungeon.aiRng.random(0, pool.size() - 1)), x, y);
    }

    public void takeTurn() {
        label23:
        switch (this.nextMove) {
            case 2:

                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "CALL"));
                for(int i=0;i<2;i++) {
                    AbstractMonster gremlin = getRandomGremlin(0);
                    assignSequentialPosition(null, gremlin);
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(gremlin, true));
                    gremlin.usePreBattleAction();
                    gremlins.add(gremlin);
                }
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getEncourageQuote()));

                /*
                AbstractDungeon.actionManager.addToBottom(new SummonGremlinAction(this.gremlins));
                AbstractDungeon.actionManager.addToBottom(new SummonGremlinAction(this.gremlins));*/
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, this.getEncourageQuote()));
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while(var1.hasNext()) {
                    AbstractMonster m=(AbstractMonster )var1.next();

                    if (m == this) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, this.strAmt), this.strAmt));
                    } else if (!m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, this.strAmt), this.strAmt));
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, this.blockAmt));
                    }
                }
                break ;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HORIZONTAL, true));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_VERTICAL, true));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HEAVY));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private String getEncourageQuote() {
        ArrayList<String> list = new ArrayList();
        list.add(DIALOG[0]);
        list.add(DIALOG[1]);
        list.add(DIALOG[2]);
        list.add("尖塔属于地精！");
        list.add("出来吧！无尽的地精军团！");
        return (String)list.get(AbstractDungeon.aiRng.random(0, list.size() - 1));
    }

    protected void getMove(int num) {

            this.setMove(RALLY_NAME, (byte) 2, Intent.UNKNOWN);


        /*

        if (this.numAliveGremlins() == 0) {
            if (num < 75) {
                if (!this.lastMove((byte)2)) {
                    this.setMove(RALLY_NAME, (byte)2, Intent.UNKNOWN);
                } else {
                    this.setMove((byte)4, Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
                }
            } else if (!this.lastMove((byte)4)) {
                this.setMove((byte)4, Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
            } else {
                this.setMove(RALLY_NAME, (byte)2, Intent.UNKNOWN);
            }
        } else if (this.numAliveGremlins() < 2) {
            if (num < 50) {
                if (!this.lastMove((byte)2)) {
                    this.setMove(RALLY_NAME, (byte)2, Intent.UNKNOWN);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(50, 99));
                }
            } else if (num < 80) {
                if (!this.lastMove((byte)3)) {
                    this.setMove((byte)3, Intent.DEFEND_BUFF);
                } else {
                    this.setMove((byte)4, Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
                }
            } else if (!this.lastMove((byte)4)) {
                this.setMove((byte)4, Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
            } else {
                this.getMove(AbstractDungeon.aiRng.random(0, 80));
            }
        } else if (this.numAliveGremlins() > 1) {
            if (num < 66) {
                if (!this.lastMove((byte)3)) {
                    this.setMove((byte)3, Intent.DEFEND_BUFF);
                } else {
                    this.setMove((byte)4, Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
                }
            } else if (!this.lastMove((byte)4)) {
                this.setMove((byte)4, Intent.ATTACK, this.STAB_DMG, this.STAB_AMT, true);
            } else {
                this.setMove((byte)3, Intent.DEFEND_BUFF);
            }
        }
*/
    }

    private int numAliveGremlins() {
        int count = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var2.hasNext()) {
            AbstractMonster m = (AbstractMonster)var2.next();
            if (m != null && m != this && !m.isDying) {
                ++count;
            }
        }

        return count;
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "CALL":
                this.state.setAnimation(0, "Call", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void die() {
        super.die();
        boolean first = true;
        Iterator var2 = gremlins.iterator();

        AbstractMonster m;
        while(var2.hasNext()) {
            m = (AbstractMonster)var2.next();
            if (!m.isDying) {
                if (first) {
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, DIALOG[3], 0.5F, 1.2F));
                    first = false;
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, DIALOG[4], 0.5F, 1.2F));
                }
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinLeader");
        NAME = "无尽召唤头子";
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        POSX = new float[]{-366.0F, -170.0F, -532.0F};
        POSY = new float[]{-4.0F, 6.0F, 0.0F};
        RALLY_NAME = MOVES[0];
    }
}
