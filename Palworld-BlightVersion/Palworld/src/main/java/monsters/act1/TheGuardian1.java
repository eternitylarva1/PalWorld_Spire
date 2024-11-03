package monsters.act1;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.FungiBeast;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import relics.Habit;

import static mymod.IsaacMod.config;

public class TheGuardian1 extends AbstractMonster {
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.monsters.exordium.TheGuardian.class.getName());
    public static final String ID = "TheGuardian";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final String DEFENSIVE_MODE = "Defensive Mode";
    private static final String OFFENSIVE_MODE = "Offensive Mode";
    private static final String RESET_THRESH = "Reset Threshold";
    public static final int HP = 240;
    public static final int A_2_HP = 250;
    private static final int DMG_THRESHOLD = 30;
    private static final int A_2_DMG_THRESHOLD = 35;
    private static final int A_19_DMG_THRESHOLD = 40;
    private int dmgThreshold;
    private int dmgThresholdIncrease = 10;
    private int dmgTaken;
    private static final int FIERCE_BASH_DMG = 32;
    private static final int A_2_FIERCE_BASH_DMG = 36;
    private static final int ROLL_DMG = 9;
    private static final int A_2_ROLL_DMG = 10;
    private int fierceBashDamage;
    private int whirlwindDamage = 5;
    private int twinSlamDamage = 8;
    private int rollDamage;
    private int whirlwindCount = 4;
    private int DEFENSIVE_BLOCK = 20;
    private int blockAmount = 9;
    private int thornsDamage = 3;
    private int VENT_DEBUFF = 2;
    private boolean isOpen = true;
    private boolean closeUpTriggered = false;
    private static final byte CLOSE_UP = 1;
    private static final byte FIERCE_BASH = 2;
    private static final byte ROLL_ATTACK = 3;
    private static final byte TWIN_SLAM = 4;
    private static final byte WHIRLWIND = 5;
    private static final byte CHARGE_UP = 6;
    private static final byte VENT_STEAM = 7;
    private static final String CLOSEUP_NAME;
    private static final String FIERCEBASH_NAME;
    private static final String TWINSLAM_NAME;
    private static final String WHIRLWIND_NAME;
    private static final String CHARGEUP_NAME;
    private static final String VENTSTEAM_NAME;

    public TheGuardian1() {
        super(NAME, "TheGuardian", 240, 0.0F, 95.0F, 440.0F, 350.0F, (String)null, -50.0F, -100.0F);
        this.type = EnemyType.BOSS;
        this.dialogX = -100.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 19) {
            this.setHp(250);
            this.dmgThreshold = 40;
        } else if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(250);
            this.dmgThreshold = 35;
        } else {
            this.setHp(240);
            this.dmgThreshold = 30;
        }

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.fierceBashDamage = 36;
            this.rollDamage = 10;
        } else {
            this.fierceBashDamage = 32;
            this.rollDamage = 9;
        }

        this.damage.add(new DamageInfo(this, this.fierceBashDamage));
        this.damage.add(new DamageInfo(this, this.rollDamage));
        this.damage.add(new DamageInfo(this, this.whirlwindDamage));
        this.damage.add(new DamageInfo(this, this.twinSlamDamage));
        this.loadAnimation("images/monsters/theBottom/boss/guardian/skeleton.atlas", "images/monsters/theBottom/boss/guardian/skeleton.json", 2.0F);
        this.state.setAnimation(0, "idle", true);
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, this.dmgThreshold)));
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
        UnlockTracker.markBossAsSeen("GUARDIAN");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                this.useCloseUp();
                break;
            case 2:
                this.useFierceBash();
                break;
            case 3:
                this.useRollAttack();
                break;
            case 4:
                this.useTwinSmash();
                break;
            case 5:
                this.useWhirlwind();
                break;
            case 6:
                this.useChargeUp();
                break;
            case 7:
                this.useVentSteam();
                break;
            default:
                logger.info("ERROR");
        }

    }

    private void useFierceBash() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
        this.setMove(VENTSTEAM_NAME, (byte)7, Intent.STRONG_DEBUFF);
    }

    private void useVentSteam() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.VENT_DEBUFF, true), this.VENT_DEBUFF));
        this.setMove(WHIRLWIND_NAME, (byte)5, Intent.ATTACK, this.whirlwindDamage, this.whirlwindCount, true);
    }

    private void useCloseUp() {
        AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, DIALOG[1]));
        if (AbstractDungeon.ascensionLevel >= 19) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, this.thornsDamage + 1)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, this.thornsDamage)));
        }

        this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
    }

    private void useTwinSmash() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Offensive Mode"));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(3), AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(3), AttackEffect.SLASH_HEAVY));

        this.setMove(WHIRLWIND_NAME, (byte)5, Intent.ATTACK, this.whirlwindDamage, this.whirlwindCount, true);
    }

    private void useRollAttack() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
        this.setMove(TWINSLAM_NAME, (byte)4, Intent.ATTACK_BUFF, this.twinSlamDamage, 2, true);
    }

    private void useWhirlwind() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
        if(AbstractDungeon.player.hasRelic(Habit.ID)||config.getBool("Douququ")){AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this, this, "Thorns",this.thornsDamage + 1));
        }
        if(MinionHelper.getMinions().monsters.contains(this))
        {
            for (int i = 0; i < this.whirlwindCount; ++i) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
                /*AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(2), AttackEffect.NONE, true));
                 */
                int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];

                for (int j = 0; j < retVal.length; ++j) {
                    DamageInfo info = new DamageInfo(this, this.damage.get(2).base);
                    info.applyPowers(AbstractDungeon.player, (AbstractCreature) AbstractDungeon.getMonsters().monsters.get(j));
                    retVal[j] = info.output;
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) this, retVal, DamageInfo.DamageType.NORMAL, AttackEffect.NONE));
            }
        }else
        {
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
            for (int i = 0; i < 4; i++) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
                int[] retVal = new int[MinionHelper.getMinions().monsters.size()];

                for (int j = 0; j < retVal.length; ++j) {
                    DamageInfo info = new DamageInfo(this, this.damage.get(2).base);
                    info.applyPowers(this, (AbstractCreature) MinionHelper.getMinions().monsters.get(j));
                    retVal[j] = info.output;
                }

                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1((AbstractCreature) this, retVal, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

            }
            }


        this.setMove(CHARGEUP_NAME, (byte)6, Intent.DEFEND);
    }

    private void useChargeUp() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.blockAmount));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2], 1.0F, 2.5F));
        this.setMove(FIERCEBASH_NAME, (byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
    }

    protected void getMove(int num) {
        if (this.isOpen) {
            this.setMove(CHARGEUP_NAME, (byte)6, Intent.DEFEND);
        } else {
            this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
        }

    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "Defensive Mode":
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Mode Shift"));
                CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.DEFENSIVE_BLOCK));
                this.stateData.setMix("idle", "transition", 0.1F);
                this.state.setTimeScale(2.0F);
                this.state.setAnimation(0, "transition", false);
                this.state.addAnimation(0, "defensive", true, 0.0F);
                this.dmgThreshold += this.dmgThresholdIncrease;
                this.setMove(CLOSEUP_NAME, (byte)1, Intent.BUFF);
                this.createIntent();
                this.isOpen = false;
                this.updateHitbox(0.0F, 95.0F, 440.0F, 250.0F);
                this.healthBarUpdatedEvent();
                break;
            case "Offensive Mode":
                if(AbstractDungeon.player.hasRelic(Habit.ID)||config.getBool("Douququ")){AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this, this, "Thorns",this.thornsDamage + 1));
                }

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, this.dmgThreshold)));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
                if (this.currentBlock != 0) {
                    AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
                }

                this.stateData.setMix("defensive", "idle", 0.2F);
                this.state.setTimeScale(1.0F);
                this.state.setAnimation(0, "idle", true);
                this.isOpen = true;
                this.closeUpTriggered = false;
                this.updateHitbox(0.0F, 95.0F, 440.0F, 350.0F);
                this.healthBarUpdatedEvent();
                break;
            case "Reset Threshold":
                this.dmgTaken = 0;
        }

    }

    public void damage(DamageInfo info) {
        int tmpHealth = this.currentHealth;
        super.damage(info);
        if (this.isOpen && !this.closeUpTriggered && tmpHealth > this.currentHealth && !this.isDying) {
            this.dmgTaken += tmpHealth - this.currentHealth;
            if (this.getPower("Mode Shift") != null) {
                AbstractPower var10000 = this.getPower("Mode Shift");
                var10000.amount -= tmpHealth - this.currentHealth;
                this.getPower("Mode Shift").updateDescription();
            }

            if (this.dmgTaken >= this.dmgThreshold) {
                this.dmgTaken = 0;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, false), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
                this.closeUpTriggered = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    public void die() {
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        this.onBossVictoryLogic();
        UnlockTracker.hardUnlockOverride("GUARDIAN");
        UnlockTracker.unlockAchievement("GUARDIAN");
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TheGuardian");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        CLOSEUP_NAME = MOVES[0];
        FIERCEBASH_NAME = MOVES[1];
        TWINSLAM_NAME = MOVES[3];
        WHIRLWIND_NAME = MOVES[4];
        CHARGEUP_NAME = MOVES[5];
        VENTSTEAM_NAME = MOVES[6];
    }
}
