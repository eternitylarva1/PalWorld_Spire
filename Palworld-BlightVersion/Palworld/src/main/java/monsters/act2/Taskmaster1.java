package monsters.act2;


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.gikk.twirk.SETTINGS;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.exordium.SlaverBlue;
import com.megacrit.cardcrawl.monsters.exordium.SlaverRed;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import evepower.RupturePower1;
import helpers.SummonHelper;

public class Taskmaster1 extends AbstractMonster {
    public static final String ID = "SlaverBoss";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 54;
    private static final int HP_MAX = 60;
    private static final int A_2_HP_MIN = 57;
    private static final int A_2_HP_MAX = 64;
    private static final int WHIP_DMG = 4;
    private static final int SCOURING_WHIP_DMG = 7;
    private static final int WOUNDS = 1;
    private static final int A_2_WOUNDS = 2;
    private int woundCount;
    private static final byte SCOURING_WHIP = 2;

    public Taskmaster1(float x, float y) {
        super(NAME, "SlaverBoss", AbstractDungeon.monsterHpRng.random(54, 60), -10.0F, -8.0F, 200.0F, 280.0F, (String)null, x, y);
        this.type = EnemyType.ELITE;
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(57, 64);
        } else {
            this.setHp(54, 60);
        }

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.woundCount = 3;
        } else if (AbstractDungeon.ascensionLevel >= 3) {
            this.woundCount = 2;
        } else {
            this.woundCount = 1;
        }

        this.damage.add(new DamageInfo(this, 4));
        this.damage.add(new DamageInfo(this, 7));
        this.loadAnimation("images/monsters/theCity/slaverMaster/skeleton.atlas", "images/monsters/theCity/slaverMaster/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public void usePreBattleAction() {
        AbstractMonster m=new SlaverBlue(0,0);
        if(AbstractDungeon.aiRng.randomBoolean(0.5f))
        {
            m=new SlaverRed(0,0);
        }
        m.flipHorizontal = true;
        m.drawX=this.drawX+ Settings.WIDTH/20;
        m.drawY=this.drawY+ Settings.HEIGHT/20;
      SummonHelper.summonMinion(m);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RupturePower1(this, 1), 1));
    }
    public void takeTurn() {
        switch (this.nextMove) {
            case 2:
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.SLASH_HEAVY));
                if (AbstractDungeon.ascensionLevel >= 18) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                }
            default:
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
        }
    }

    protected void getMove(int num) {
        this.setMove((byte)2, Intent.ATTACK_DEBUFF, 7);
    }

    private void playSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERLEADER_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERLEADER_1B"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2A");
        } else {
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2B");
        }

    }

    public void die() {
        super.die();
        this.playDeathSfx();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlaverBoss");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

