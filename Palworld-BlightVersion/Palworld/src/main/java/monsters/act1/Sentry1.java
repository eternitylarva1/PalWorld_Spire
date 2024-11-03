package monsters.act1;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import actions.AddPowerAction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FeelNoPainPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

public class Sentry1 extends AbstractMonster {
    public static final String ID = "Sentry";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String ENC_NAME = "Sentries";
    private static final int HP_MIN = 38;
    private static final int HP_MAX = 42;
    private static final int A_2_HP_MIN = 39;
    private static final int A_2_HP_MAX = 45;
    private static final byte BOLT = 3;
    private static final byte BEAM = 4;
    private int beamDmg;
    private int dazedAmt;
    private static final int DAZED_AMT = 2;
    private static final int A_18_DAZED_AMT = 3;
    private boolean firstMove = true;

    public Sentry1(float x, float y) {
        super(NAME, "Sentry", 42, 0.0F, -5.0F, 180.0F, 310.0F, (String)null, x, y);
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(39, 45);
        } else {
            this.setHp(38, 42);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.beamDmg = 10;
        } else {
            this.beamDmg = 9;
        }

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.dazedAmt = 3;
        } else {
            this.dazedAmt = 2;
        }

        this.damage.add(new DamageInfo(this, this.beamDmg));
        this.loadAnimation("images/monsters/theBottom/sentry/skeleton.atlas", "images/monsters/theBottom/sentry/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTimeScale(2.0F);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("idle", "attack", 0.1F);
        this.stateData.setMix("idle", "spaz1", 0.1F);
        this.stateData.setMix("idle", "hit", 0.1F);
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
        AbstractPower ap=new FeelNoPainPower(AbstractDungeon.player,2);
        AbstractDungeon.actionManager.addToBottom(new AddPowerAction(AbstractDungeon.player,ap));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 3:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP"));
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveType.ADDITIVE), 0.5F));
                    AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.2F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.ROYAL, ShockWaveType.ADDITIVE), 0.1F));
                    AbstractDungeon.actionManager.addToBottom(new FastShakeAction(AbstractDungeon.player, 0.6F, 0.15F));
                }

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.dazedAmt));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SKY)));
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.1F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY), 0.3F));
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.NONE, Settings.FAST_MODE));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "hit", false);
            this.state.addAnimation(0, "idle", true, 0.0F);
        }

    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "ATTACK":
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
            default:
        }
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            if (AbstractDungeon.getMonsters().monsters.lastIndexOf(this) % 2 == 0) {
                this.setMove((byte)3, Intent.DEBUFF);
            } else {
                this.setMove((byte)4, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            }

            this.firstMove = false;
        } else {
            if (this.lastMove((byte)4)) {
                this.setMove((byte)3, Intent.DEBUFF);
            } else {
                this.setMove((byte)4, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            }

        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Sentry");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
