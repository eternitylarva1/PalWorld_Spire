package monsters.act3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.unique.FullHealthAdditionalDamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.ReactivePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class WrithingMass1 extends AbstractMonster {
    public static final String ID = "WrithingMass";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final int HP = 160;
    private static final int A_2_HP = 175;
    private boolean firstMove = true;
    private boolean usedMegaDebuff = false;
    private static final int HIT_COUNT = 3;
    private int normalDebuffAmt;
    private static final byte BIG_HIT = 0;
    private static final byte MULTI_HIT = 1;
    private static final byte ATTACK_BLOCK = 2;
    private static final byte ATTACK_DEBUFF = 3;
    private static final byte MEGA_DEBUFF = 4;

    public WrithingMass1() {
        super(NAME, "WrithingMass", 160, 5.0F, -26.0F, 450.0F, 310.0F, (String)null, 0.0F, 15.0F);
        this.loadAnimation("images/monsters/theForest/spaghetti/skeleton.atlas", "images/monsters/theForest/spaghetti/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(175);
        } else {
            this.setHp(160);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 38));
            this.damage.add(new DamageInfo(this, 9));
            this.damage.add(new DamageInfo(this, 16));
            this.damage.add(new DamageInfo(this, 12));
            this.normalDebuffAmt = 2;
        } else {
            this.damage.add(new DamageInfo(this, 32));
            this.damage.add(new DamageInfo(this, 7));
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 10));
            this.normalDebuffAmt = 2;
        }

    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ReactivePower(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MalleablePower(this)));
    }

    public void takeTurn() {
        label19:
        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_HEAVY));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                int i = 0;

                while(true) {
                    if (i >= 3) {
                        break label19;
                    }

                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_LIGHT));
                    ++i;
                }
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, ((DamageInfo)this.damage.get(2)).base));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(3), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.normalDebuffAmt, true), this.normalDebuffAmt));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.normalDebuffAmt, true), this.normalDebuffAmt));
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                break;
            case 4:
                this.usedMegaDebuff = true;
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(this, 0.5F, 0.2F));
                AbstractDungeon.player.maxHealth+=3;
                AbstractDungeon.player.heal(3);
                this.currentHealth-=5;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

        super.damage(info);
    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
        }
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            if (num < 33) {
                this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, 3, true);
            } else if (num < 66) {
                this.setMove((byte)2, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(2)).base);
            } else {
                this.setMove((byte)3, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(3)).base);
            }

        } else {
            if (num < 10) {
                if (!this.lastMove((byte)0)) {
                    this.setMove((byte)0, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(10, 99));
                }
            } else if (num < 20) {
                if (!this.usedMegaDebuff && !this.lastMove((byte)4)) {
                    this.setMove((byte)4, Intent.STRONG_DEBUFF);
                } else if (AbstractDungeon.aiRng.randomBoolean(0.1F)) {
                    this.setMove((byte)0, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(20, 99));
                }
            } else if (num < 40) {
                if (!this.lastMove((byte)3)) {
                    this.setMove((byte)3, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(3)).base);
                } else if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                    this.getMove(AbstractDungeon.aiRng.random(19));
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(40, 99));
                }
            } else if (num < 70) {
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, 3, true);
                } else if (AbstractDungeon.aiRng.randomBoolean(0.3F)) {
                    this.setMove((byte)2, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(2)).base);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(39));
                }
            } else if (!this.lastMove((byte)2)) {
                this.setMove((byte)2, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(2)).base);
            } else {
                this.getMove(AbstractDungeon.aiRng.random(69));
            }

            this.createIntent();
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("WrithingMass");
        NAME = monsterStrings.NAME;
    }
}

