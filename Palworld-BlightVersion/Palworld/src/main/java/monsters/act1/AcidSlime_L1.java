package monsters.act1;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import actions.SummonMinionAction;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SplitPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import helpers.MinionHelper;

import java.util.Iterator;

import static mymod.IsaacMod.config;

public class AcidSlime_L1 extends AbstractMonster {
    public static final String ID = "AcidSlime_L";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    private static final String WOUND_NAME;
    private static final String SPLIT_NAME;
    private static final String WEAK_NAME;
    public static final int HP_MIN = 65;
    public static final int HP_MAX = 69;
    public static final int A_2_HP_MIN = 68;
    public static final int A_2_HP_MAX = 72;
    public static final int W_TACKLE_DMG = 11;
    public static final int N_TACKLE_DMG = 16;
    public static final int A_2_W_TACKLE_DMG = 12;
    public static final int A_2_N_TACKLE_DMG = 18;
    public static final int WEAK_TURNS = 2;
    public static final int WOUND_COUNT = 2;
    private static final byte SLIME_TACKLE = 1;
    private static final byte NORMAL_TACKLE = 2;
    private static final byte SPLIT = 3;
    private static final byte WEAK_LICK = 4;
    private float saveX;
    private float saveY;
    private boolean splitTriggered;

    public AcidSlime_L1(float x, float y) {
        this(x, y, 0, 69);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(68, 72);
        } else {
            this.setHp(65, 69);
        }

    }

    public AcidSlime_L1(float x, float y, int poisonAmount, int newHealth) {
        super(NAME, "AcidSlime_L", newHealth, 0.0F, 0.0F, 300.0F, 180.0F, (String)null, x, y, true);
        this.saveX = x;
        this.saveY = y;
        this.splitTriggered = false;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, 12));
            this.damage.add(new DamageInfo(this, 18));
        } else {
            this.damage.add(new DamageInfo(this, 11));
            this.damage.add(new DamageInfo(this, 16));
        }

        this.powers.add(new SplitPower(this));
        if (poisonAmount >= 1) {
            this.powers.add(new PoisonPower(this, this, poisonAmount));
        }

        this.loadAnimation("images/monsters/theBottom/slimeL/skeleton.atlas", "images/monsters/theBottom/slimeL/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addListener(new SlimeAnimListener());
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_SLIME_ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Slimed(), 2));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                if(MinionHelper.getMinions().monsters.contains(this))
                {
                    AbstractDungeon.actionManager.addToBottom(new SummonMinionAction(new AcidSlime_M(this.saveX - 134.0F, this.saveY + MathUtils.random(-4.0F, 4.0F), 0, this.currentHealth) ));
                    AbstractDungeon.actionManager.addToBottom(new SummonMinionAction(new AcidSlime_M(this.saveX + 134.0F, this.saveY + MathUtils.random(-4.0F, 4.0F), 0, this.currentHealth) ));

                }
                else{
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new AcidSlime_M(this.saveX - 134.0F, this.saveY + MathUtils.random(-4.0F, 4.0F), 0, this.currentHealth), false));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new AcidSlime_M(this.saveX + 134.0F, this.saveY + MathUtils.random(-4.0F, 4.0F), 0, this.currentHealth), false));
        }
                if(!config.getBool("Douququ")) {
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());}
                this.setMove(SPLIT_NAME, (byte)3, Intent.UNKNOWN);
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (!this.isDying && (float)this.currentHealth <= (float)this.maxHealth / 2.0F && this.nextMove != 3 && !this.splitTriggered) {
            this.setMove(SPLIT_NAME, (byte)3, Intent.UNKNOWN);
            this.createIntent();
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, SPLIT_NAME, (byte)3, Intent.UNKNOWN));
            this.splitTriggered = true;
        }

    }

    protected void getMove(int num) {
        if (AbstractDungeon.ascensionLevel >= 17) {
            if (num < 40) {
                if (this.lastTwoMoves((byte)1)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.6F)) {
                        this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                    } else {
                        this.setMove(WEAK_NAME, (byte)4, Intent.DEBUFF);
                    }
                } else {
                    this.setMove(WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                }
            } else if (num < 70) {
                if (this.lastTwoMoves((byte)2)) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.6F)) {
                        this.setMove(WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                    } else {
                        this.setMove(WEAK_NAME, (byte)4, Intent.DEBUFF);
                    }
                } else {
                    this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                }
            } else if (this.lastMove((byte)4)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                    this.setMove(WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                }
            } else {
                this.setMove(WEAK_NAME, (byte)4, Intent.DEBUFF);
            }
        } else if (num < 30) {
            if (this.lastTwoMoves((byte)1)) {
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
                } else {
                    this.setMove(WEAK_NAME, (byte)4, Intent.DEBUFF);
                }
            } else {
                this.setMove(WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            }
        } else if (num < 70) {
            if (this.lastMove((byte)2)) {
                if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                    this.setMove(WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.setMove(WEAK_NAME, (byte)4, Intent.DEBUFF);
                }
            } else {
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
            }
        } else if (this.lastTwoMoves((byte)4)) {
            if (AbstractDungeon.aiRng.randomBoolean(0.4F)) {
                this.setMove(WOUND_NAME, (byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            } else {
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
            }
        } else {
            this.setMove(WEAK_NAME, (byte)4, Intent.DEBUFF);
        }

    }

    public void die() {
        super.die();
        Iterator var1 = AbstractDungeon.actionManager.actions.iterator();

        AbstractGameAction a;
        do {
            if (!var1.hasNext()) {
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                    this.onBossVictoryLogic();
                    UnlockTracker.hardUnlockOverride("SLIME");
                    UnlockTracker.unlockAchievement("SLIME_BOSS");
                }

                return;
            }

            a = (AbstractGameAction)var1.next();
        } while(!(a instanceof SpawnMonsterAction));

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AcidSlime_L");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        WOUND_NAME = MOVES[0];
        SPLIT_NAME = MOVES[1];
        WEAK_NAME = MOVES[2];
    }
}

