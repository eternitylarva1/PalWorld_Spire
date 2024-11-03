package monsters.act1;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import actions.AddPowerAction;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FeelNoPainPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import helpers.MinionHelper;
import patches.player.PlayerAddFieldsPatch;

import java.util.Iterator;

import static mymod.IsaacMod.config;

public class GremlinTsundere1 extends AbstractMonster {
    public static final String ID = "GremlinTsundere";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 12;
    private static final int HP_MAX = 15;
    private static final int A_2_HP_MIN = 13;
    private static final int A_2_HP_MAX = 17;
    private static final int BLOCK_AMOUNT = 7;
    private static final int A_2_BLOCK_AMOUNT = 8;
    private static final int A_17_BLOCK_AMOUNT = 11;
    private static final int BASH_DAMAGE = 6;
    private static final int A_2_BASH_DAMAGE = 8;
    private int blockAmt;
    private int bashDmg;
    private static final byte PROTECT = 1;
    private static final byte BASH = 2;

    public GremlinTsundere1(float x, float y) {
        super(NAME, "GremlinTsundere", 15, 0.0F, 0.0F, 120.0F, 200.0F, (String)null, x, y);
        this.dialogY = 60.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.setHp(13, 17);
            this.blockAmt = 11;
        } else if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(13, 17);
            this.blockAmt = 8;
        } else {
            this.setHp(12, 15);
            this.blockAmt = 7;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.bashDmg = 8;
        } else {
            this.bashDmg = 6;
        }

        this.damage.add(new DamageInfo(this, this.bashDmg));
        this.loadAnimation("images/monsters/theBottom/femaleGremlin/skeleton.atlas", "images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    @Override
    public void usePreBattleAction() {
        if(!config.getBool("Douququ")) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.blockAmt));
            ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                        if (!monster.isDying && !monster.escaped && !monster.isDead && !(monster == this)) {

                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(monster, monster, this.blockAmt));

                        }
                    }
            );
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player, this.blockAmt));
                ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                            if(!monster.isDying&&!monster.escaped&&!monster.isDead&& !(monster ==this))
                            {

                                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(monster, monster, this.blockAmt));

                            }
                        }
                );

                int aliveCount = 0;
                Iterator var2 = MinionHelper.getMinions().monsters.iterator();

                while(var2.hasNext()) {
                    AbstractMonster m = (AbstractMonster)var2.next();
                    if (!m.isDying && !m.isEscaping) {
                        ++aliveCount;
                    }
                }

                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                } else if (aliveCount > 1) {
                    this.setMove(MOVES[0], (byte)1, Intent.DEFEND);
                } else {
                    this.setMove(MOVES[1], (byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                }
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                if (this.escapeNext) {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base));
                }
                break;
            case 99:
                AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[1], false));
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)99, Intent.ESCAPE));
        }

    }

    public void die() {
        super.die();
    }

    public void escapeNext() {
        if (!this.cannotEscape && !this.escapeNext) {
            this.escapeNext = true;
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0F, DIALOG[2], false));
        }

    }

    protected void getMove(int num) {
        this.setMove(MOVES[0], (byte)1, Intent.DEFEND);
    }

    public void deathReact() {
        if (this.intent != Intent.ESCAPE && !this.isDying) {
            AbstractDungeon.effectList.add(new SpeechBubble(this.dialogX, this.dialogY, 3.0F, DIALOG[2], false));
            this.setMove((byte)99, Intent.ESCAPE);
            this.createIntent();
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinTsundere");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
