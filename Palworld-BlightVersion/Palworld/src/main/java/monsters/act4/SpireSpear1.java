package monsters.act4;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import patches.player.PlayerAddFieldsPatch;

import java.util.Iterator;

public class SpireSpear1 extends AbstractMonster {
    public static final String ID = "SpireSpear";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private int moveCount = 0;
    private static final byte BURN_STRIKE = 1;
    private static final byte PIERCER = 2;
    private static final byte SKEWER = 3;
    private static final int BURN_STRIKE_COUNT = 2;
    private int skewerCount;

    public SpireSpear1() {
        super(NAME, "SpireSpear", 160, 0.0F, -15.0F, 380.0F, 290.0F, (String)null, 70.0F, 10.0F);
        this.type = EnemyType.ELITE;
        this.loadAnimation("images/monsters/theEnding/spear/skeleton.atlas", "images/monsters/theEnding/spear/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(180);
        } else {
            this.setHp(160);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.skewerCount = 4;
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 10));
        } else {
            this.skewerCount = 3;
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 10));
        }

    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 18) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
        }

    }

    public void takeTurn() {
        int i;
        label36:
        switch (this.nextMove) {
            case 1:
                for(i = 0; i < 2; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.15F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.FIRE));
                }

                break;
            case 2:
                AbstractCreature pp=AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(pp,pp,new StrengthPower(pp,2),2));
                ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                            if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                            {

                                AbstractPower p=new StrengthPower(monster,2);
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,p,2));
                            }
                        }
                );
                break ;
            case 3:
                for(i = 0; i < this.skewerCount; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.05F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.SLASH_DIAGONAL, true));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        switch (this.moveCount % 3) {
            case 0:
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base, 2, true);
                } else {
                    this.setMove((byte)2, Intent.BUFF);
                }
                break;
            case 1:
                this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, this.skewerCount, true);
                break;
            default:
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    this.setMove((byte)2, Intent.BUFF);
                } else {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base, 2, true);
                }
        }

        ++this.moveCount;
    }

    public void changeState(String key) {
        AnimationState.TrackEntry e = null;
        switch (key) {
            case "SLOW_ATTACK":
                this.state.setAnimation(0, "Attack_1", false);
                e = this.state.addAnimation(0, "Idle", true, 0.0F);
                e.setTimeScale(0.5F);
                break;
            case "ATTACK":
                this.state.setAnimation(0, "Attack_2", false);
                e = this.state.addAnimation(0, "Idle", true, 0.0F);
                e.setTimeScale(0.7F);
        }

    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            AnimationState.TrackEntry e = this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.7F);
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
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpireSpear");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

