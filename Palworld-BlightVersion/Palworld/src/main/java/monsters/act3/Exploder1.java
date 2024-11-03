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
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ExplosivePower;
import com.megacrit.cardcrawl.powers.TheBombPower;

public class Exploder1 extends AbstractMonster {
    public static final String ID = "Exploder";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String ENCOUNTER_NAME = "Ancient Shapes";
    private static final int HP_MIN = 30;
    private static final int HP_MAX = 30;
    private static final int A_2_HP_MIN = 30;
    private static final int A_2_HP_MAX = 35;
    private int turnCount = 0;
    private static final float HB_X = -8.0F;
    private static final float HB_Y = -10.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final byte ATTACK = 1;
    private static final int ATTACK_DMG = 9;
    private static final int A_2_ATTACK_DMG = 11;
    private int attackDmg;
    private static final byte BLOCK = 2;
    private static final int EXPLODE_BASE = 3;

    public Exploder1(float x, float y) {
        super(NAME, "Exploder", 30, -8.0F, -10.0F, 150.0F, 150.0F, (String)null, x, y + 10.0F);
        this.loadAnimation("images/monsters/theForest/exploder/skeleton.atlas", "images/monsters/theForest/exploder/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(30, 35);
        } else {
            this.setHp(30, 30);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.attackDmg = 11;
        } else {
            this.attackDmg = 9;
        }

        this.damage.add(new DamageInfo(this, this.attackDmg));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TheBombPower(this,3,40)));
    }

    public void takeTurn() {
        ++this.turnCount;
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.FIRE));
                return;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TheBombPower(this,3,40)));
                return;
            default:
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }
    }

    protected void getMove(int num) {
        if (this.turnCount %3<2) {
            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        } else {
            this.setMove((byte)2, Intent.UNKNOWN);
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Exploder");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

