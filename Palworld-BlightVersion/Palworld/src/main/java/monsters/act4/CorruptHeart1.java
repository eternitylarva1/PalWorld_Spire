package monsters.act4;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import cards.tempCards.BecomeAlmighty1;
import cards.tempCards.LiveForever1;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BeatOfDeathPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.PainfulStabsPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;

public class CorruptHeart1 extends AbstractMonster {
    public static final String ID = "CorruptHeart";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private HeartAnimListener animListener = new HeartAnimListener();
    private static final byte BLOOD_SHOTS = 1;
    private static final byte ECHO_ATTACK = 2;
    private static final byte DEBILITATE = 3;
    private static final byte GAIN_ONE_STRENGTH = 4;
    public static final int DEBUFF_AMT = -1;
    private int bloodHitCount;
    private boolean isFirstMove = true;
    private int moveCount = 0;
    private int buffCount = 0;

    public CorruptHeart1() {
        super(NAME, "CorruptHeart", 750, 30.0F, -30.0F, 476.0F, 410.0F, (String)null, -50.0F, 30.0F);
        this.loadAnimation("images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTimeScale(1.5F);
        this.state.addListener(this.animListener);
        this.type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(800);
        } else {
            this.setHp(750);
        }

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 45));
            this.damage.add(new DamageInfo(this, 2));
            this.bloodHitCount = 15;
        } else {
            this.damage.add(new DamageInfo(this, 40));
            this.damage.add(new DamageInfo(this, 2));
            this.bloodHitCount = 12;
        }

    }

    public void usePreBattleAction() {
        int invincibleAmt = 300;
        if (AbstractDungeon.ascensionLevel >= 19) {
            invincibleAmt -= 100;
        }

        int beatAmount = 1;
        if (AbstractDungeon.ascensionLevel >= 19) {
            ++beatAmount;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new InvinciblePower(this, invincibleAmt), invincibleAmt));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, beatAmount), beatAmount));
    }

    public void takeTurn() {
        label37:
        switch (this.nextMove) {

            case 1:
                AbstractMonster m=AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            if (m!=null){
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, m.hb.cX, m.hb.cY, this.bloodHitCount), 0.25F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(this.hb.cX, this.hb.cY, m.hb.cX, m.hb.cY, this.bloodHitCount), 0.6F));
                }
            }

                int i = 0;

                while(true) {
                    if (i >= this.bloodHitCount) {
                        break label37;
                    }

                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY, true));
                    ++i;
                }
            case 2:
                AbstractMonster m1=AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(m1.hb.cX, m1.hb.cY), 0.5F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_HEAVY));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartMegaDebuffEffect()));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new BecomeAlmighty(), 1, true, false, false, (float)Settings.WIDTH * 0.2F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Miracle(), 1, true, false, false, (float)Settings.WIDTH * 0.35F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new FameAndFortune(), 1, true, false, false, (float)Settings.WIDTH * 0.5F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new LiveForever(), 1, true, false, false, (float)Settings.WIDTH * 0.65F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Insight(), 1, true, false, false, (float)Settings.WIDTH * 0.8F, (float)Settings.HEIGHT / 2.0F));

                break;


            case 4:
                int additionalAmount = 0;
                if (this.hasPower("Strength") && this.getPower("Strength").amount < 0) {
                    additionalAmount = -this.getPower("Strength").amount;
                }

                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(new Color(0.8F, 0.5F, 1.0F, 1.0F))));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(this.hb.cX, this.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, additionalAmount + 2), additionalAmount + 2));
                switch (this.buffCount) {
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2), 2));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BeatOfDeathPower(this, 1), 1));
                        break;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PainfulStabsPower(this)));
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 10), 10));
                        break;
                    default:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 50), 50));
                }

                ++this.buffCount;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.isFirstMove) {
            this.setMove((byte)3, Intent.STRONG_DEBUFF);
            this.isFirstMove = false;
        } else {
            switch (this.moveCount % 3) {
                case 0:
                    if (AbstractDungeon.aiRng.randomBoolean()) {
                        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, this.bloodHitCount, true);
                    } else {
                        this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                    }
                    break;
                case 1:
                    if (!this.lastMove((byte)2)) {
                        this.setMove((byte)2, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                    } else {
                        this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, this.bloodHitCount, true);
                    }
                    break;
                default:
                    this.setMove((byte)4, Intent.BUFF);
            }

            ++this.moveCount;
        }
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            this.state.removeListener(this.animListener);
            this.onBossVictoryLogic();
            this.onFinalBossVictoryLogic();
            CardCrawlGame.stopClock = true;
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("CorruptHeart");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

