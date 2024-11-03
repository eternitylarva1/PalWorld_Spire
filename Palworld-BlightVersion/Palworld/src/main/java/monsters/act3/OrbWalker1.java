package monsters.act3;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.red.BurningPact;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.GenericStrengthUpPower;

public class OrbWalker1 extends AbstractMonster {
    public static final String ID = "Orb Walker";
    public static final String DOUBLE_ENCOUNTER = "Double Orb Walker";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 90;
    private static final int HP_MAX = 96;
    private static final int A_2_HP_MIN = 92;
    private static final int A_2_HP_MAX = 102;
    public static final int LASER_DMG = 10;
    public static final int CLAW_DMG = 15;
    public static final int A_2_LASER_DMG = 11;
    public static final int A_2_CLAW_DMG = 16;
    private int clawDmg;
    private int laserDmg;
    private static final byte LASER = 1;
    private static final byte CLAW = 2;

    public OrbWalker1(float x, float y) {
        super(NAME, "Orb Walker", AbstractDungeon.monsterHpRng.random(90, 96), -20.0F, -14.0F, 280.0F, 250.0F, (String)null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(92, 102);
        } else {
            this.setHp(90, 96);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.clawDmg = 16;
            this.laserDmg = 11;
        } else {
            this.clawDmg = 15;
            this.laserDmg = 10;
        }

        this.damage.add(new DamageInfo(this, this.laserDmg));
        this.damage.add(new DamageInfo(this, this.clawDmg));
        this.loadAnimation("images/monsters/theForest/orbWalker/skeleton.atlas", "images/monsters/theForest/orbWalker/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GenericStrengthUpPower(this, MOVES[0], 5)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GenericStrengthUpPower(this, MOVES[0], 3)));
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAndDeckAction(new Inflame()));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.SLASH_HEAVY));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (num < 40) {
            if (!this.lastTwoMoves((byte)2)) {
                this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
            } else {
                this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
            }
        } else if (!this.lastTwoMoves((byte)1)) {
            this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
        } else {
            this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Orb Walker");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
