package Option;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import relics.PokeGophone;

import java.util.Iterator;

import static Option.get_pokego.canwork_pokego;
import static mymod.IsaacMod.config;
import static relics.PokeGophone.*;

public class duanzao1e extends AbstractGameEffect {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor;

    public duanzao1e() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }

        Iterator var1;
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade) {
            var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                ++CardCrawlGame.metricData.campfire_upgraded;
                CardCrawlGame.metricData.addCampfireChoiceData("SMITH", c.getMetricID());
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        }

        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            CardGroup cg=AbstractDungeon.player.masterDeck.getUpgradableCards();
            CardGroup cf=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var2 = cg.group.iterator();
            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if(config.getBool("zhuore"))
                {
                    cf.group.add(c);
                }
                else if(!c.upgraded) {
                  cf.group.add(c);
                };
            }

            AbstractDungeon.gridSelectScreen.open(cf, 1, TEXT[0], true, false, true, false);
            var1 = AbstractDungeon.player.relics.iterator();

            while(var1.hasNext()) {
                AbstractRelic r = (AbstractRelic)var1.next();
                r.onSmith();
            }
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
                AbstractDungeon.actionManager.addToTop((new SFXAction("VOs_CULTIST_1A")));
                PokeGowork(dz.pgt);
                dz.usable=canwork_pokego()>0;
                pt.usable=dz.usable;
                pt.updateUsability(pt.usable);

                RestRoom rest=(RestRoom )AbstractDungeon.getCurrRoom();
                CampfireUI cm=rest.campfireUI;
                cm.hidden=false;
                cm.somethingSelected=false;
                cm.update();
                sl.usable=dz.usable;
                PokeGophone.refresh();

            }
        }

    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        if (AbstractDungeon.screen == CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect");
        TEXT = uiStrings.TEXT;
    }
}
