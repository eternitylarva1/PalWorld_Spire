package screen;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import relics.PokeGo;
import utils.InstanceMaker;

import java.util.ArrayList;
import java.util.List;

public class BreedScreen extends CustomScreen

{
    ArrayList<AbstractCard> cards = new ArrayList<>();
    ArrayList<AbstractMonster> Monsterlist =new ArrayList<>();
    public static class Enum
    {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen MY_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen()
    {
        return Enum.MY_SCREEN;
    }

    // Note that this can be private and take any parameters you want.
    // When you call openCustomScreen it finds the first method named "open"
    // and calls it with whatever arguments were passed to it.
    private void open(String foo, List<PokeGo> list)
    {
        Monsterlist.clear();
        for (PokeGo pokeGo : list){
            if(pokeGo.counter>0&&pokeGo.monsterClass!=null){
                if (!Monsterlist.contains(PokeGo.getMonsterbycache(pokeGo.monsterClass))) {
                    Monsterlist.add(PokeGo.getMonsterbycache(pokeGo.monsterClass));
                } else {
                    Monsterlist.add(InstanceMaker.getInstanceByClass(pokeGo.monsterClass));
                }
            }
        }
        //MonsterList转化成AbstractMonster[]
        AbstractMonster[] monsterArray = new AbstractMonster[Monsterlist.size()];

        // 使用 toArray 方法将 ArrayList 转换为数组
        monsterArray = Monsterlist.toArray(monsterArray);

        AbstractDungeon.getCurrRoom().monsters=new MonsterGroup(monsterArray);
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        // Call reopen in this example because the basics of
        // setting the current screen are the same across both
        reopen();
    }

    @Override
    public void reopen()
    {
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
        return y * -235.0F * Settings.scale + 850.0F * Settings.scale-50.0F*Settings.scale;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {

        spriteBatch.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
        spriteBatch.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT - 64.0F * Settings.scale);
        float startX = 50.0F * Settings.scale; // 起始X坐标
        float startY = Settings.HEIGHT - 100.0F * Settings.scale; // 起始Y坐标
        float currentX = startX;
        float currentY = startY;
        float padding = 20.0F * Settings.scale; // 怪物之间的间距

        for (AbstractMonster monster : Monsterlist) {
            // 获取怪物的宽度和高度
            float monsterWidth = monster.hb.width;
            float monsterHeight = monster.hb.height;

            // 检查是否需要换行
            if (currentX + monsterWidth + padding > Settings.WIDTH) {
                currentX = startX;
                currentY -= monsterHeight + padding;
            }

            // 设置怪物的位置
            monster.drawX = currentX + monsterWidth / 2;
            monster.drawY = currentY + monsterHeight / 2;

            // 更新怪物的Hitbox
            monster.hb.move(monster.drawX, monster.drawY);

            // 渲染怪物
            monster.render(spriteBatch);

            // 更新当前X坐标
            currentX += monsterWidth + padding;
        }
    }

    @Override
    public void openingSettings()
    {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
    }

    // ...
}