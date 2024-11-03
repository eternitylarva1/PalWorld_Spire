package monsters.act2;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import actions.AddPowerAction;
import actions.HealallAction;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
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
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import patches.player.PlayerAddFieldsPatch;

import java.util.Iterator;

public class Healer1 extends AbstractMonster {
    public static final String ID = "Healer";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final float IDLE_TIMESCALE = 0.8F;
    public static final String ENC_NAME = "HealerTank";
    private static final int HP_MIN = 48;
    private static final int HP_MAX = 56;
    private static final int A_2_HP_MIN = 50;
    private static final int A_2_HP_MAX = 58;
    private static final int MAGIC_DMG = 8;
    private static final int HEAL_AMT = 16;
    private static final int STR_AMOUNT = 2;
    private static final int A_2_MAGIC_DMG = 9;
    private static final int A_2_STR_AMOUNT = 3;
    private int magicDmg;
    private int strAmt;
    private int healAmt;
    private static final byte ATTACK = 1;
    private static final byte HEAL = 2;
    private static final byte BUFF = 3;

    public Healer1(float x, float y) {
        super(NAME, "Healer", 56, 0.0F, -20.0F, 230.0F, 250.0F, (String)null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(50, 58);
        } else {
            this.setHp(48, 56);
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.magicDmg = 9;
            this.strAmt = 4;
            this.healAmt = 20;
        } else if (AbstractDungeon.ascensionLevel >= 2) {
            this.magicDmg = 9;
            this.strAmt = 3;
            this.healAmt = 16;
        } else {
            this.magicDmg = 8;
            this.strAmt = 2;
            this.healAmt = 16;
        }

        this.damage.add(new DamageInfo(this, this.magicDmg));
        this.loadAnimation("images/monsters/theCity/healer/skeleton.atlas", "images/monsters/theCity/healer/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.2F);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.setTimeScale(0.8F);
    }

    public void takeTurn() {
        Iterator var1;
        AbstractMonster m;
        label35:
        switch (this.nextMove) {
            case 1:
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                break;
            case 2:
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAFF_RAISE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                AbstractDungeon.actionManager.addToBottom(new HealallAction(true,10));
                AbstractDungeon.actionManager.addToBottom( new HealAction(AbstractDungeon.player,AbstractDungeon.player,10));
                break;

            case 3:
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAFF_RAISE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                AbstractPower pp=new StrengthPower(AbstractDungeon.player,3);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,pp));
               ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                    if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                    {
                        AbstractPower p=new StrengthPower(monster,3);
                        //尝试修复死后塞牌问题
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,p));
                    }
                }
                );


        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx() {
        if (MathUtils.randomBoolean()) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_HEALER_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_HEALER_1B"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_HEALER_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_HEALER_2B");
        } else {
            CardCrawlGame.sound.play("VO_HEALER_2C");
        }

    }

    public void changeState(String key) {
        switch (key) {
            case "STAFF_RAISE":
                this.state.setAnimation(0, "Attack", false);
                this.state.setTimeScale(0.8F);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
        }
    }

    protected void getMove(int num) {
        int needToHeal = 0;
        Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster m = (AbstractMonster)var3.next();
            if (!m.isDying && !m.isEscaping) {
                needToHeal += m.maxHealth - m.currentHealth;
            }
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            if (needToHeal > 20 && !this.lastTwoMoves((byte)2)) {
                this.setMove((byte)2, Intent.BUFF);
                return;
            }
        } else if (needToHeal > 15 && !this.lastTwoMoves((byte)2)) {
            this.setMove((byte)2, Intent.BUFF);
            return;
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            if (num >= 40 && !this.lastMove((byte)1)) {
                this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                return;
            }
        } else if (num >= 40 && !this.lastTwoMoves((byte)1)) {
            this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            return;
        }

        if (!this.lastTwoMoves((byte)3)) {
            this.setMove((byte)3, Intent.BUFF);
        } else {
            this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.setTimeScale(0.8F);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void die() {
        this.playDeathSfx();
        this.state.setTimeScale(0.1F);
        this.useShakeAnimation(5.0F);
        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Healer");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
