package screen;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import relics.PokeGo;
import utils.InstanceMaker;
import utils.randommonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreedScreen extends CustomScreen implements ScrollBarListener {

    ArrayList<AbstractCard> cards = new ArrayList<>();
    ArrayList<AbstractMonster> Monsterlist = new ArrayList<>();
    Map<String, AbstractMonster> monsterMap = new HashMap<>();
    Map<String, AbstractMonster> monsterCombinations = new HashMap<>();
    List<List<AbstractMonster>> monsterGroups = new ArrayList<>();

    private ScrollBar scrollBar;
    private float grabStartY = 0.0F;
    private float currentDiffY = 0.0F;
    private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
    private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
    private boolean grabbedScreen = false;
    private boolean canScroll = false;
    private float monsterSpacing = 200.0F * Settings.scale; // 怪物之间的间隔

    @Override
    public void scrolledUsingBar(float v) {
        this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, v);
        this.updateBarPosition();
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen MY_SCREEN;
    }

    public BreedScreen() {
        this.scrollBar = new ScrollBar(this);
        this.scrollBar.move(0.0F, -30.0F * Settings.scale);
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.MY_SCREEN;
    }

    private void open(String foo, List<PokeGo> list) {
        Monsterlist.clear();
        monsterMap.clear();
        monsterCombinations.clear();
        monsterGroups.clear();

        for (int i = 0; i < list.size(); i++) {
            PokeGo pokeGo1 = list.get(i);
            if (pokeGo1.counter > 0 && pokeGo1.monsterClass != null) {
                for (int j = i + 1; j < list.size(); j++) { // 从 i+1 开始，避免重复
                    PokeGo pokeGo2 = list.get(j);
                    if (pokeGo2.counter > 0 && pokeGo2.monsterClass != null) {
                        int fanzhili = pokeGo1.fanzhili + pokeGo2.fanzhili;
                        ArrayList<AbstractMonster> monsterList = new ArrayList<>();
                        monsterList.add(InstanceMaker.getInstanceByClass(pokeGo1.monsterClass));
                        monsterList.add(InstanceMaker.getInstanceByClass(pokeGo2.monsterClass));
                        monsterList.add(randommonster.randommonster(fanzhili % 70));
                        monsterGroups.add(monsterList);
                    }
                }
            }
        }

        // 计算滚动边界
        calculateScrollBounds();

        // MonsterList转化成AbstractMonster[]
        if (AbstractDungeon.getCurrRoom().monsters == null) {
            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Cultist(0, 0));
        }

        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE) {
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        // Call reopen in this example because the basics of
        // setting the current screen are the same across both
        reopen();
    }

    private void calculateScrollBounds() {
        float totalHeight = 0.0F;
        for (int i = 0; i < monsterGroups.size(); i++) {
            List<AbstractMonster> group = monsterGroups.get(i);
            float maxHeight = Math.max(group.get(0).hb.height, Math.max(group.get(1).hb.height, group.get(2).hb.height));
            totalHeight += maxHeight + 50.0F * Settings.scale; // 50.0F * Settings.scale 是固定间距
        }

        if (totalHeight > Settings.HEIGHT* Settings.scale) {

            int scrollTmp = monsterGroups.size() / 5 - 1;
            if (monsterGroups.size() % 5 != 0) {
                ++scrollTmp;
            }
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT +totalHeight- (Settings.HEIGHT * Settings.scale);
        } else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        this.canScroll = true;
    }

    @Override
    public void reopen() {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void close() {
        genericScreenOverlayReset();
    }

    @Override
    public void update() {
        updateScrolling();
        // 其他更新逻辑...
    }

    private void updateScrolling() {
        if (!canScroll) {
            return;
        }

        int y = InputHelper.mY;
        boolean isDraggingScrollBar = this.scrollBar.update();
        if (!isDraggingScrollBar) {
            if (!this.grabbedScreen) {
                if (InputHelper.scrolledDown) {
                    this.currentDiffY += Settings.SCROLL_SPEED;
                } else if (InputHelper.scrolledUp) {
                    this.currentDiffY -= Settings.SCROLL_SPEED;
                }

                if (InputHelper.justClickedLeft) {
                    this.grabbedScreen = true;
                    this.grabStartY = (float)y - this.currentDiffY;
                }
            } else if (InputHelper.isMouseDown) {
                this.currentDiffY = (float)y - this.grabStartY;
            } else {
                this.grabbedScreen = false;
            }
        }

        this.resetScrolling();
        this.updateBarPosition();
    }

    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        } else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }
    }

    private void updateBarPosition() {
        float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
        this.scrollBar.parentScrolledToPercent(percent);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
        spriteBatch.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT - 64.0F * Settings.scale);

        float startX = convertX(-1.0F); // 起始X坐标
        float startY = Settings.HEIGHT - 200.0F * Settings.scale; // 起始Y坐标
        float currentX = startX;
        float currentY = startY + this.currentDiffY; // 考虑滚动偏移

        for (List<AbstractMonster> group : monsterGroups) {
            AbstractMonster monster1 = group.get(0);
            AbstractMonster monster2 = group.get(1);
            AbstractMonster monster3 = group.get(2);

            // 渲染怪物1
            monster1.drawX = currentX - monster1.hb.width / 2;
            monster1.drawY = currentY - monster1.hb.height / 2;
            monster1.hb.move(monster1.drawX, monster1.drawY);
            monster1.render(spriteBatch);

            float offsetX = monster1.hb.width / 2 + monsterSpacing;

            // 渲染怪物2
            monster2.drawX = currentX + offsetX - monster2.hb.width / 2;
            monster2.drawY = currentY - monster2.hb.height / 2;
            monster2.hb.move(monster2.drawX, monster2.drawY);
            monster2.render(spriteBatch);

            // 渲染 + 符号
            float plusX = (monster1.drawX + monster2.drawX) / 2;
            float plusY = currentY;
            FontHelper.renderFontCentered(spriteBatch, FontHelper.largeCardFont, "+", plusX, plusY, Settings.CREAM_COLOR);

            // 渲染怪物3
            monster3.drawX = currentX + offsetX * 3 -monster3.hb.width / 2;
            monster3.drawY = currentY - monster3.hb.height / 2;
            monster3.hb.move(monster3.drawX, monster3.drawY);
            monster3.render(spriteBatch);

            // 渲染 = 符号
            float equalsX = (monster2.drawX + monster3.drawX) / 2;
            float equalsY = currentY;
            FontHelper.renderFontCentered(spriteBatch, FontHelper.largeCardFont, "=", equalsX, equalsY, Settings.CREAM_COLOR);

            // 更新当前Y坐标
            currentY -= 50F * Settings.scale + Math.max(monster1.hb.height, Math.max(monster2.hb.height, monster3.hb.height));
        }

        // 渲染滚动条
        if (canScroll) {
            this.scrollBar.render(spriteBatch);
        }
    }

    @Override
    public void openingSettings() {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
    }

    private float convertX(float x) {
        return x * 235.0F * Settings.scale + 640.0F * Settings.scale;
    }

    private float convertY(float y) {
        return y * -235.0F * Settings.scale + 850.0F * Settings.scale - 50.0F * Settings.scale;
    }
}
