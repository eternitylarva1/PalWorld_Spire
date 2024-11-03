package monsters.act4;



import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import patches.player.PlayerAddFieldsPatch;

import java.util.Iterator;

public class SpireShield1 extends AbstractMonster {
    public static final String ID = "SpireShield";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private int moveCount = 0;
    private static final byte BASH = 1;
    private static final byte FORTIFY = 2;
    private static final byte SMASH = 3;
    private static final int BASH_DEBUFF = -1;
    private static final int FORTIFY_BLOCK = 30;

    public SpireShield1() {
        super(NAME, "SpireShield", 110, 0.0F, -20.0F, 380.0F, 290.0F, (String)null, -1000.0F, 15.0F);
        this.type = EnemyType.ELITE;
        this.loadAnimation("images/monsters/theEnding/shield/skeleton.atlas", "images/monsters/theEnding/shield/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(125);
        } else {
            this.setHp(110);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.damage.add(new DamageInfo(this, 14));
            this.damage.add(new DamageInfo(this, 38));
        } else {
            this.damage.add(new DamageInfo(this, 12));
            this.damage.add(new DamageInfo(this, 34));
        }

    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SurroundedPower(AbstractDungeon.player)));
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
        }

    }

    public void takeTurn() {
        label27:
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.35F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                if (!AbstractDungeon.player.orbs.isEmpty() && AbstractDungeon.aiRng.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
                }
                break;
            case 2:
                AbstractCreature pp=AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(pp, pp, 30));
                ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                            if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                            {

                                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(monster, monster, 30));

                            }
                        }
                );
                break ;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OLD_ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                if (AbstractDungeon.ascensionLevel >= 18) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 99));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, ((DamageInfo)this.damage.get(1)).output));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        switch (this.moveCount % 3) {
            case 0:
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    this.setMove((byte)2, Intent.DEFEND);
                } else {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                }
                break;
            case 1:
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base);
                } else {
                    this.setMove((byte)2, Intent.DEFEND);
                }
                break;
            default:
                this.setMove((byte)3, Intent.ATTACK_DEFEND, ((DamageInfo)this.damage.get(1)).base);
        }

        ++this.moveCount;
    }

    public void changeState(String key) {
        switch (key) {
            case "OLD_ATTACK":
                this.state.setAnimation(0, "old_attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void die() {
        super.die();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDead && !m.isDying) {
                if (AbstractDungeon.player.hasPower("Surrounded")) {
                    AbstractDungeon.player.flipHorizontal = m.drawX < AbstractDungeon.player.drawX;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Surrounded"));
                }

                if (m.hasPower("BackAttack")) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, "BackAttack"));
                }
            }
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpireShield");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

