package monsters.act1;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;

public class Cultist20 extends AbstractMonster {
    public static final String ID = "Cultist";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String MURDER_ENCOUNTER_KEY = "Murder of Cultists";
    private static final String INCANTATION_NAME;
    private static final int HP_MIN = 48;
    private static final int HP_MAX = 54;
    private static final int A_2_HP_MIN = 50;
    private static final int A_2_HP_MAX = 56;
    private static final float HB_X = -8.0F;
    private static final float HB_Y = 10.0F;
    private static final float HB_W = 230.0F;
    private static final float HB_H = 240.0F;
    private static final int ATTACK_DMG = 6;
    private boolean firstMove;
    private boolean saidPower;
    private static final int RITUAL_AMT = 3;
    private static final int A_2_RITUAL_AMT = 4;
    private int ritualAmount;
    private static final byte DARK_STRIKE = 1;
    private static final byte INCANTATION = 3;
    private boolean talky;

    public Cultist20(float x, float y, boolean talk) {
        super(NAME, "Cultist", 54, -8.0F, 10.0F, 115.0F, 120.0F, (String)null, x, y);
        this.firstMove = true;
        this.saidPower = false;
        this.ritualAmount = 0;
        this.talky = true;
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(50, 56);
        } else {
            this.setHp(48, 54);
        }

        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 2) {
            this.ritualAmount = 4;
        } else {
            this.ritualAmount = 3;
        }

        this.damage.add(new DamageInfo(this, 6));
        this.talky = talk;
        if (Settings.FAST_MODE) {
            this.talky = false;
        }

        this.loadAnimation("images/monsters/theBottom/cultist20/skeleton.atlas", "images/monsters/theBottom/cultist20/skeleton37.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public Cultist20(float x, float y) {
        this(x, y, true);
    }

    public void takeTurn() {
        if(this.hasPower("Strength")){
            float pf=(float) 5*6/(this.getPower("Strength").amount+25);
            if (pf<0.05f){
                pf=0.05f;
            }
            //this.loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", pf);
            // AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
            //e.setTime(e.getEndTime() * MathUtils.random());
        }

        switch (this.nextMove) {
            case 1:

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new WeakPower(this, this.ritualAmount, false)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, -1)));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(),1,true,true));
                if (MinionHelper.getMinions().monsters.contains(this)) {
                    int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY + 60.0F * Settings.scale), 1.5F));

                    for(int i = 0; i < retVal.length; ++i) {
                        DamageInfo info = new DamageInfo(this, this.damage.get(0).base);
                        info.applyPowers(this, (AbstractCreature)AbstractDungeon.getMonsters().monsters.get(i));
                        retVal[i] = info.output;
                    }

                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) this, retVal, DamageInfo.DamageType.NORMAL, AttackEffect.NONE));

                }
                else{
                    int[] retVal = new int[MinionHelper.getMinions().monsters.size()];
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY + 60.0F * Settings.scale), 1.5F));

                    for(int i = 0; i < retVal.length; ++i) {
                        DamageInfo info = new DamageInfo(this, this.damage.get(0).base);
                        info.applyPowers(this, (AbstractCreature)MinionHelper.getMinions().monsters.get(i));
                        retVal[i] = info.output;
                    }

                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction1((AbstractCreature) this, retVal, DamageInfo.DamageType.NORMAL, AttackEffect.NONE));

                    DamageInfo info = new DamageInfo(this, this.damage.get(0).base);
                    info.applyPowers(this,AbstractDungeon.player);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, info.output, DamageInfo.DamageType.THORNS)));
                }
                break;
            case 3:
                int temp = MathUtils.random(1, 10);
                if (this.talky) {
                    this.playSfx();
                    if (temp < 4) {
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
                        this.saidPower = true;
                    } else if (temp < 7) {
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 1.0F, 2.0F));
                    }
                }

                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, this.ritualAmount + 1, false)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, this.ritualAmount, false)));
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 10)));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
        }

    }

    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_CULTIST_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_CULTIST_2B");
        } else {
            CardCrawlGame.sound.play("VO_CULTIST_2C");
        }

    }

    public void die() {
        this.playDeathSfx();

        this.useShakeAnimation(5.0F);

        AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[2], false));


        super.die();
    }

    protected void getMove(int num) {
        if (this.firstMove) {
            this.firstMove = false;
            this.setMove(INCANTATION_NAME, (byte)3, Intent.BUFF);
        } else {
            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Cultist");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        INCANTATION_NAME = MOVES[2];
    }
}


