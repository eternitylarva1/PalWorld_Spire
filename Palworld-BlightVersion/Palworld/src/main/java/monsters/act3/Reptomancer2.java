package monsters.act3;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.Iterator;

public class Reptomancer2 extends AbstractMonster {
    public static final String ID = "Reptomancer";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final int HP_MIN = 180;
    private static final int HP_MAX = 190;
    private static final int A_2_HP_MIN = 190;
    private static final int A_2_HP_MAX = 200;
    private static final int BITE_DMG = 30;
    private static final int A_2_BITE_DMG = 34;
    private static final int SNAKE_STRIKE_DMG = 13;
    private static final int A_2_SNAKE_STRIKE_DMG = 16;
    private static final int DAGGERS_PER_SPAWN = 1;
    private static final int ASC_2_DAGGERS_PER_SPAWN = 2;
    private static final byte SNAKE_STRIKE = 1;
    private static final byte SPAWN_DAGGER = 2;
    private static final byte BIG_BITE = 3;
    public static final float[] POSX;
    public static final float[] POSY;
    private int daggersPerSpawn;
    private AbstractMonster[] daggers = new AbstractMonster[4];
    private boolean firstMove = true;

    public Reptomancer2() {
        super(NAME, "Reptomancer", AbstractDungeon.monsterHpRng.random(180, 190), 0.0F, -30.0F, 220.0F, 320.0F, (String)null, -20.0F, 10.0F);
        this.type = EnemyType.ELITE;
        this.loadAnimation("images/monsters/theForest/mage/skeleton.atlas", "images/monsters/theForest/mage/skeleton.json", 1.0F);
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.daggersPerSpawn = 2;
        } else {
            this.daggersPerSpawn = 1;
        }

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(190, 200);
        } else {
            this.setHp(180, 190);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.damage.add(new DamageInfo(this, 16));
            this.damage.add(new DamageInfo(this, 34));
        } else {
            this.damage.add(new DamageInfo(this, 13));
            this.damage.add(new DamageInfo(this, 30));
        }

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Idle", "Sumon", 0.1F);
        this.stateData.setMix("Sumon", "Idle", 0.1F);
        this.stateData.setMix("Hurt", "Idle", 0.1F);
        this.stateData.setMix("Idle", "Hurt", 0.1F);
        this.stateData.setMix("Attack", "Idle", 0.1F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction() {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.id.equals(this.id)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MinionPower(this)));
            }

            if (m instanceof SnakeDagger) {
                if (AbstractDungeon.getMonsters().monsters.indexOf(m) > AbstractDungeon.getMonsters().monsters.indexOf(this)) {
                    this.daggers[0] = m;
                } else {
                    this.daggers[1] = m;
                }
            }
        }

    }

    public void takeTurn() {
        label28:
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUMMON"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                int daggersSpawned = 0;
                int i = 0;

                while(true) {
                    if (daggersSpawned >= this.daggersPerSpawn || i >= this.daggers.length) {
                        break label28;
                    }

                    if (this.daggers[i] == null || this.daggers[i].isDeadOrEscaped()) {
                        SnakeDagger daggerToSpawn = new SnakeDagger(POSX[i], POSY[i]);
                        this.daggers[i] = daggerToSpawn;
                        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(daggerToSpawn, true));
                        ++daggersSpawned;
                    }

                    ++i;
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.NONE));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private boolean canSpawn() {
        int aliveCount = 0;
        Iterator var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var2.hasNext()) {
            AbstractMonster m = (AbstractMonster)var2.next();
            if (m != this && !m.isDying) {
                ++aliveCount;
            }
        }

        if (aliveCount > 3) {
            return false;
        } else {
            return true;
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hurt", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void die() {
        super.die();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();



    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)2, Intent.UNKNOWN);
        } else {
            if (num < 33) {
                if (!this.lastMove((byte)1)) {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base, 2, true);
                } else {
                    this.getMove(AbstractDungeon.aiRng.random(33, 99));
                }
            } else if (num < 66) {
                if (!this.lastTwoMoves((byte)2)) {
                    if (this.canSpawn()) {
                        this.setMove((byte)2, Intent.UNKNOWN);
                    } else {
                        this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base, 2, true);
                    }
                } else {
                    this.setMove((byte)1, Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(0)).base, 2, true);
                }
            } else if (!this.lastMove((byte)3)) {
                this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
            } else {
                this.getMove(AbstractDungeon.aiRng.random(65));
            }

        }
    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "SUMMON":
                this.state.setAnimation(0, "Sumon", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Reptomancer");
        NAME = monsterStrings.NAME;
        POSX = new float[]{210.0F, -220.0F, 180.0F, -250.0F};
        POSY = new float[]{75.0F, 115.0F, 345.0F, 335.0F};
    }
}
