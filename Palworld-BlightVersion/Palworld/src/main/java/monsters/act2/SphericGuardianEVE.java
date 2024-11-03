package monsters.act2;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.FrailPower;

public class SphericGuardianEVE extends AbstractMonster {
    public static final String ID = "SphericGuardian";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 10.0F;
    private static final float HB_W = 280.0F;
    private static final float HB_H = 280.0F;
    private static final int DMG = 10;
    private static final int A_2_DMG = 11;
    private int dmg;
    private static final int SLAM_AMT = 2;
    private static final int HARDEN_BLOCK = 15;
    private static final int FRAIL_AMT = 5;
    private static final int ACTIVATE_BLOCK = 25;
    private static final int ARTIFACT_AMT = 3;
    private static final int STARTING_BLOCK_AMT = 40;
    private static final byte BIG_ATTACK = 1;
    private static final byte INITIAL_BLOCK_GAIN = 2;
    private static final byte BLOCK_ATTACK = 3;
    private static final byte FRAIL_ATTACK = 4;
    private boolean firstMove;
    private boolean secondMove;

    public SphericGuardianEVE() {
        this(0.0F, 0.0F);
    }

    public SphericGuardianEVE(float x, float y) {
        super(NAME, "SphericGuardian", 20, 0.0F, 10.0F, 280.0F, 280.0F, (String)null, x, y);
        this.firstMove = true;
        this.secondMove = true;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.dmg = 11;
        } else {
            this.dmg = 10;
        }

        this.damage.add(new DamageInfo(this, this.dmg));
        this.loadAnimation("images/monsters/theCity/sphere/skeleton.atlas", "images/monsters/theCity/sphere/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2F);
        this.stateData.setMix("Idle", "Attack", 0.1F);
        this.state.setTimeScale(0.8F);
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 40));
    }
public void changeSize(){
    this.loadAnimation("images/monsters/theCity/sphere/skeleton.atlas", "images/monsters/theCity/sphere/skeleton.json", this.currentBlock>=100?1.5F- (float) 100/75F:1.5F- (float) SphericGuardianEVE.this.currentBlock /75);
    AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
    e.setTime(e.getEndTime() * MathUtils.random());
    this.stateData.setMix("Hit", "Idle", 0.2F);
    this.stateData.setMix("Idle", "Attack", 0.1F);
    this.state.setTimeScale(0.8F);
    this.damage.get(0).base=this.currentBlock;
    if (this.lastMove((byte)1)) {
        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);

    } else {
        this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(0)).base);
    }
}


    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY, true));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 35));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 25));
                }

                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
                if (MathUtils.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_1"));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_2"));
                }
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        SphericGuardianEVE.this.damage.get(0).base=  SphericGuardianEVE.this.currentBlock;

                        changeSize();
                        isDone=true;
                    }
                });

                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 15));
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        SphericGuardianEVE.this.damage.get(0).base=  SphericGuardianEVE.this.currentBlock;

                        AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(  SphericGuardianEVE.this));
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)  SphericGuardianEVE.this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                        changeSize();
                        isDone=true;
                    }
                });

                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 5, true), 5));
        }
this.addToBot(new AbstractGameAction() {
    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(SphericGuardianEVE.this));
        isDone=true;
    }
});

    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.setTimeScale(0.8F);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.setTimeScale(0.8F);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            if(!this.firstMove){
                changeSize();
            }

                   }

    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)2, Intent.DEFEND);

        } else if (this.secondMove) {
            this.secondMove = false;
            this.setMove((byte)4, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
        } else {
            changeSize();
            if (this.lastMove((byte)1)) {
                this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(0)).base);
            } else {
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base, 2, true);
            }

        }
    }

    public void die() {
        super.die();
        if (MathUtils.randomBoolean()) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_1"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SPHERE_DETECT_VO_2"));
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SphericGuardian");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
