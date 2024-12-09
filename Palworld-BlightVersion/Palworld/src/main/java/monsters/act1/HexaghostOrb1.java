package monsters.act1;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;
import com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import helpers.MinionHelper;

import java.util.Iterator;

public class HexaghostOrb1 extends AbstractOrb {
    public static final String ID = "HexaghostOrb";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private BobEffect effect = new BobEffect(2.0F);
    private float activateTimer;
    public boolean activated = false;
    public boolean hidden = false;
    public boolean playedSfx = false;
    private Color color;
    private float x;
    private float y;
    private float particleTimer = 0.0F;
    private static final float PARTICLE_INTERVAL = 0.06F;
    private static  HexaghostDefect owner;
    float ox=0;
    float oy=0;
    public HexaghostOrb1(float x, float y, int index,HexaghostDefect owner) {
        this.x = x * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;
        this.y = y * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;
        this.activateTimer = (float)index * 0.3F;
        this.color = Color.CHARTREUSE.cpy();
        this.color.a = 0.0F;
        this.hidden = false;
        this.owner=owner;
        this.img = ImageMaster.ORB_LIGHTNING;
        this.baseEvokeAmount = 8;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        this.setSlot(index,6);

    }

    public void activate(float oX, float oY) {
        this.playedSfx = false;
        this.activated = true;
        this.hidden = false;
    }
    public void onEndOfTurn() {
        if(this.activated) {
            if (!MinionHelper.getMinions().monsters.contains(this)) {
                AbstractCreature m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
                if (m != null) {

                    float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
                    if (Settings.FAST_MODE) {
                        speedTime = 0.0F;
                    }
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(this.owner, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
                    AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));

                }
            } else {
                if (MinionHelper.getaliveMinions() > 0) {
                    AbstractCreature m = MinionHelper.getMinions().monsters.get(AbstractDungeon.cardRandomRng.random(MinionHelper.getMinions().monsters.size() - 1));
                    if (m != null) {

                        float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
                        if (Settings.FAST_MODE) {
                            speedTime = 0.0F;
                        }
                        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
                        AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
                        AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
                        AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));

                    }
                }
            }
        }
    }
    public void deactivate() {

        this.activated = false;

    }

    public void hide() {
        this.hidden = true;
    }

    @Override
    public void updateDescription() {

    }

    @Override
    public void onEvoke() {

            AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), 0.0F));

if(!MinionHelper.getMinions().monsters.contains(this)){
    AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS), false));
}else {
    if(MinionHelper.getaliveMinions()>0){}
    {
        Iterator var5 = MinionHelper.getMinions().monsters.iterator();
        while (var5.hasNext()) {
            AbstractMonster m3 = (AbstractMonster) var5.next();
            if (!m3.isDeadOrEscaped() && !m3.halfDead) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), 0.0F));
                m3.damage(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS));
            }
        }
    }
    this.deactivate();

}
    }

    @Override
    public AbstractOrb makeCopy() {
        return null;
    }


    public void render(SpriteBatch sb) {

        sb.draw(ImageMaster.ORB_SLOT_2,
                this.x + ox + this.effect.y * 2.0F - 48F,
                this.y + oy + this.effect.y * 2.0F - 48F,
                48F,
                48F,
                122.0F,
                122.0F,
                this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F,
                this.angle,
                0,
                0,
                ImageMaster.ORB_SLOT_2.getWidth(),
                ImageMaster.ORB_SLOT_2.getHeight(),
                false,
                false);
        sb.draw(ImageMaster.ORB_SLOT_1,
                this.x + ox + this.effect.y * 2.0F - 48F,
                this.y + oy + this.effect.y * 2.0F - 48F,
                48F,
                48F,
                122.0F,
                122.0F,
                this.scale*1.1F + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F*1.1F,
                this.angle,
                0,
                0,
                ImageMaster.ORB_SLOT_1.getWidth(),
                ImageMaster.ORB_SLOT_1.getHeight(),
                false,
                false);
               if(this.activated) {
            this.shineColor.a = this.c.a / 2.0F;
            sb.setColor(this.shineColor);
            sb.setBlendFunction(770, 1);
            Texture popupArrow = ImageMaster.ORB_LIGHTNING;
            sb.setColor(1, 1, 1, 1);
            this.scale = Settings.scale * 0.85F;
            this.angle+= Gdx.graphics.getDeltaTime()* 100;

            sb.draw(popupArrow,
                    this.x + ox + this.effect.y * 2.0F - 48F,
                    this.y + oy + this.effect.y * 2.0F - 48F,
                    48F,
                    48F,
                    122.0F,
                    122.0F,
                    this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F,
                    this.angle,
                    0,
                    0,
                    popupArrow.getWidth(),
                    popupArrow.getHeight(),
                    false,
                    false);

            sb.setBlendFunction(770, 771);
            sb.setColor(this.c);
                   FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.x + ox + this.effect.y * 2.0F - 48F + NUM_X_OFFSET, this.y + oy + this.effect.y * 2.0F - 48F+ this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);

                   this.hb.render(sb);
        }

    }
    private float convertX(float x) {
        return x * 235.0F * Settings.scale-2*235.0F * Settings.scale + 640.0F * Settings.scale;
    }

    private float convertY(float y) {
        return y * -235.0F * Settings.scale + 850.0F * Settings.scale;
    }
    @Override
    public void playChannelSFX() {

    }
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }
    public void update(float oX, float oY) {
        if (!this.hidden) {
            if (this.activated) {
                this.activateTimer -= Gdx.graphics.getDeltaTime();
                if (this.activateTimer < 0.0F) {
                    if (!this.playedSfx) {
                        this.playedSfx = true;
                        AbstractDungeon.effectsQueue.add(new LightningOrbPassiveEffect(this.x + oX, this.y + oY));
                        this.ox=oX;
                        this.oy=oY;
                        if (MathUtils.randomBoolean()) {
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
                        } else {
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", 0.3F);
                        }
                    }

                    this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);
                    this.effect.update();
                    this.effect.update();
                    this.particleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.particleTimer < 0.0F) {
                        AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.x + oX + this.effect.y * 2.0F, this.y + oY + this.effect.y * 2.0F));
                        this.particleTimer = 0.06F;
                    }
                }
            } else {
                this.effect.update();
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                   // AbstractDungeon.effectList.add(new GhostlyWeakFireEffect(this.x + oX + this.effect.y * 2.0F, this.y + oY + this.effect.y * 2.0F));
                    this.particleTimer = 0.06F;
                }
            }
        } else {
            this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.0F);
        }

    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("HexaghostOrb");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
