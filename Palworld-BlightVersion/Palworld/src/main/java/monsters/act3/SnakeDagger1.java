package monsters.act3;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL;

public class SnakeDagger1 extends AbstractMonster {
    public static final String ID = "Dagger";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 20;
    private static final int HP_MAX = 25;
    private static final int STAB_DMG = 9;
    private static final int SACRIFICE_DMG = 25;
    private static final byte WOUND = 1;
    private static final byte EXPLODE = 2;
    public boolean firstMove = true;

    public SnakeDagger1(float x, float y) {
        super(NAME, "Dagger", AbstractDungeon.monsterHpRng.random(20, 25), 0.0F, -50.0F, 140.0F, 130.0F, (String)null, x, y);
        this.initializeAnimation();
        this.damage.add(new DamageInfo(this, 9));
        this.damage.add(new DamageInfo(this, 25));
        this.damage.add(new DamageInfo(this, 25, DamageType.HP_LOSS));
    }

    public void initializeAnimation() {
        this.loadAnimation("images/monsters/theForest/mage_dagger/skeleton.atlas", "images/monsters/theForest/mage_dagger/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUICIDE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction((DamageInfo)this.damage.get(1),AttackEffect.SLASH_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this, this, this.currentHealth));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUICIDE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction((DamageInfo)this.damage.get(1),AttackEffect.SLASH_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this, this, this.currentHealth));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hurt", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            this.stateData.setMix("Hurt", "Idle", 0.1F);
            this.stateData.setMix("Idle", "Hurt", 0.1F);
        }

    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove((byte)1, Intent.ATTACK_DEBUFF, 25);
        } else {
            this.setMove((byte)2, Intent.ATTACK, 25);
        }
    }

    public void changeState(String key) {
        switch (key) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "SUICIDE":
                this.state.setAnimation(0, "Attack2", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Dagger");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
