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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import relics.PokeGo;
import utils.InstanceMaker;
import utils.randommonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreedScreen extends CustomScreen {

    ArrayList<AbstractCard> cards = new ArrayList<>();
    ArrayList<AbstractMonster> Monsterlist = new ArrayList<>();
    Map<String, AbstractMonster> monsterMap = new HashMap<>();
    Map<String, AbstractMonster> monsterCombinations = new HashMap<>();
    List<List<AbstractMonster>> monsterGroups = new ArrayList<>();

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen MY_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.MY_SCREEN;
    }

    // Note that this can be private and take any parameters you want.
    // When you call openCustomScreen it finds the first method named "open"
    // and calls it with whatever arguments were passed to it.
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



        // MonsterList转化成AbstractMonster[]
        if (AbstractDungeon.getCurrRoom().monsters == null) {
            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new Cultist(0,0));
        }

        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE) {
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }



        // Call reopen in this example because the basics of
        // setting the current screen are the same across both
        reopen();
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
    }

    private float convertX(float x) {
        return x * 235.0F * Settings.scale + 640.0F * Settings.scale;
    }

    private float convertY(float y) {
        return y * -235.0F * Settings.scale + 850.0F * Settings.scale - 50.0F * Settings.scale;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
        spriteBatch.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT - 64.0F * Settings.scale);

        float startX = convertX(-1.0F); // 起始X坐标
        float startY = Settings.HEIGHT - 100.0F * Settings.scale; // 起始Y坐标
        float currentX = startX;
        float currentY = startY;
        float padding = 200.0F * Settings.scale; // 怪物之间的间距
        float monsterSpacing = 150.0F * Settings.scale; // 怪物之间的间隔

        for (List<AbstractMonster> group : monsterGroups) {
            AbstractMonster monster1 = group.get(0);
            AbstractMonster monster2 = group.get(1);
            AbstractMonster monster3 = group.get(2);

            // 渲染怪物1
            monster1.drawX = currentX + monster1.hb.width / 2;
            monster1.drawY = currentY - monster1.hb.height / 2;
            monster1.hb.move(monster1.drawX, monster1.drawY);
            monster1.render(spriteBatch);

            float offsetX = monster1.hb.width / 2 + monsterSpacing;

            // 渲染怪物2
            monster2.drawX = currentX + offsetX + monster2.hb.width / 2;
            monster2.drawY = currentY - monster2.hb.height / 2;
            monster2.hb.move(monster2.drawX, monster2.drawY);
            monster2.render(spriteBatch);

            // 渲染 + 符号
            float plusX = (monster1.drawX + monster2.drawX)/2;
            float plusY = currentY ;
            FontHelper.renderFontCentered(spriteBatch, FontHelper.cardDescFont_N, "+", plusX, plusY, Settings.CREAM_COLOR);

            // 渲染怪物3
            monster3.drawX = currentX + offsetX * 2 + monster3.hb.width / 2;
            monster3.drawY = currentY - monster3.hb.height / 2;
            monster3.hb.move(monster3.drawX, monster3.drawY);
            monster3.render(spriteBatch);


            // 渲染 = 符号
            float equalsX = (monster2.drawX + monster3.drawX)/2;
            float equalsY = currentY;
            FontHelper.renderFontCentered(spriteBatch, FontHelper.cardDescFont_N, "=", equalsX, equalsY, Settings.CREAM_COLOR);
//padding=三种怪物中hb_w中的最大值
            padding=Math.max(monster1.hb.height, Math.max(monster2.hb.height, monster3.hb.height));

            // 更新当前Y坐标
            currentY -= 50F * Settings.scale+padding;
        }
    }

    @Override
    public void openingSettings() {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
    }
}
