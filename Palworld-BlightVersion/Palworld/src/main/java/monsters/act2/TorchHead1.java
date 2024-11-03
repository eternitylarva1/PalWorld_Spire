package monsters.act2;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.vfx.TorchHeadFireEffect;

public class TorchHead1 extends AbstractMonster {
    public static final String ID = "TorchHead";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP_MIN = 38;
    public static final int HP_MAX = 40;
    public static final int A_2_HP_MIN = 40;
    public static final int A_2_HP_MAX = 45;
    public static final int ATTACK_DMG = 7;
    private static final byte TACKLE = 1;
    private float fireTimer = 0.0F;
    private static final float FIRE_TIME = 0.04F;

    public TorchHead1(float x, float y) {
        super(NAME, "TorchHead", AbstractDungeon.monsterHpRng.random(38, 40), -5.0F, -20.0F, 145.0F, 240.0F, (String)null, x, y);
        this.setMove((byte)1, Intent.ATTACK, 7);
        this.damage.add(new DamageInfo(this, 7));
        this.loadAnimation("images/monsters/theCity/torchHead/skeleton.atlas", "images/monsters/theCity/torchHead/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(40, 45);
        } else {
            this.setHp(38, 40);
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,7, DamageInfo.DamageType.NORMAL, AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK, 7));
            default:
        }
    }

    public void update() {
        super.update();
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.04F;
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 10.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 110.0F * Settings.scale));
            }
        }

    }

    protected void getMove(int num) {
        this.setMove((byte)1, Intent.ATTACK, 7);
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TorchHead");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

