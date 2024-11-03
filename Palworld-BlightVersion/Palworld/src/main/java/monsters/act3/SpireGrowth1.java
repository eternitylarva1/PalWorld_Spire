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
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

public class SpireGrowth1 extends AbstractMonster {
    public static final String ID = "Serpent";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int START_HP = 170;
    private static final int A_2_START_HP = 190;
    private int tackleDmg = 16;
    private int smashDmg = 22;
    private int constrictDmg = 10;
    private int A_2_tackleDmg = 18;
    private int A_2_smashDmg = 25;
    private int tackleDmgActual;
    private int smashDmgActual;
    private static final byte QUICK_TACKLE = 1;
    private static final byte CONSTRICT = 2;
    private static final byte SMASH = 3;

    public SpireGrowth1() {
        super(NAME, "Serpent", 170, -10.0F, -35.0F, 480.0F, 430.0F, (String)null, 0.0F, 10.0F);
        this.loadAnimation("images/monsters/theForest/spireGrowth/skeleton.atlas", "images/monsters/theForest/spireGrowth/skeleton.json", 1.0F);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(190);
        } else {
            this.setHp(170);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.tackleDmgActual = this.A_2_tackleDmg;
            this.smashDmgActual = this.A_2_smashDmg;
        } else {
            this.tackleDmgActual = this.tackleDmg;
            this.smashDmgActual = this.smashDmg;
        }

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.3F);
        this.stateData.setMix("Hurt", "Idle", 0.2F);
        this.stateData.setMix("Idle", "Hurt", 0.2F);
        this.damage.add(new DamageInfo(this, this.tackleDmgActual));
        this.damage.add(new DamageInfo(this, this.smashDmgActual));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractMonster m =AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                if (AbstractDungeon.ascensionLevel >= 17) {

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(m, this, this.constrictDmg + 2)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(m, this, this.constrictDmg)));
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (AbstractDungeon.ascensionLevel >= 17 && !AbstractDungeon.player.hasPower("Constricted") && !this.lastMove((byte)2)) {
            this.setMove((byte)2, Intent.STRONG_DEBUFF);
        } else if (num < 50 && !this.lastTwoMoves((byte)1)) {
            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        } else if (!AbstractDungeon.player.hasPower("Constricted") && !this.lastMove((byte)2)) {
            this.setMove((byte)2, Intent.STRONG_DEBUFF);
        } else if (!this.lastTwoMoves((byte)3)) {
            this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
        } else {
            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hurt", false);
            this.state.setTimeScale(1.3F);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.setTimeScale(1.3F);
                this.state.addAnimation(0, "Idle", true, 0.0F);
            default:
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Serpent");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
