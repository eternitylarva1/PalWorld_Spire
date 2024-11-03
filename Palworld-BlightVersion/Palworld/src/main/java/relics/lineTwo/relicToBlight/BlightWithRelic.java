package relics.lineTwo.relicToBlight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static com.megacrit.cardcrawl.relics.AbstractRelic.MAX_RELICS_PER_PAGE;
import static com.megacrit.cardcrawl.relics.AbstractRelic.relicPage;
/*
    以下是原版荒疫可以调用的接口名单，其他的可以看看我的BlightHook
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
    }

    public boolean canPlay(AbstractCard card) {
        return true;
    }

    public void onVictory() {
    }

    public void atBattleStart() {
    }

    public void atTurnStart() {
    }

    public void onPlayerEndTurn() {
    }

    public void onBossDefeat() {
    }

    public void onCreateEnemy(AbstractMonster m) {
    }

    public void effect() {
    //不知道这个effect是干嘛的，总之也放在这里
    }

    public void onEquip() {
    }
 */


/**
 * 可以用这个荒疫来携带一个遗物，这个遗物可以正常被查看和点击
 * 荒疫需要手动添加哦
 */
public abstract class BlightWithRelic extends AbstractBlight {

    public AbstractRelic relic;
    private boolean isInit = false;

    public BlightWithRelic(AbstractRelic relic) {
        super(relic.relicId, "BlightWithRelic", "Only a Relic carrier, do not spawn.", "maze.png", true);
        this.relic = relic;
    }

//    protected static String getIdWithRelic(AbstractRelic relic) {
//        return "blight_" + relic.relicId;
//    }

    @Override
    public void update() {
        if (!isInit) {
            initRelic();
            //给紫音写的patch
            isInit = !Loader.isModLoadedOrSideloaded("VUPShionMod");
        }


        //翻页时不显示，不更新碰撞箱，Relic本来就有这个功能所以不写
        if (AbstractDungeon.player != null && AbstractDungeon.player.blights.indexOf(this) / MAX_RELICS_PER_PAGE == relicPage) {
            this.hb.update();
        }
        else {
            this.hb.hovered = false;
        }

        super.update();
        this.relic.update();
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        this.relic.renderTip(sb);
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        this.relic.renderInTopPanel(sb);
    }

    private void initRelic() {
        this.relic.isDone = this.isDone;
        this.relic.isObtained = this.isObtained;
        this.relic.currentX = this.currentX;
        this.relic.currentY = this.currentY;
        this.relic.targetX = this.targetX;
        this.relic.targetY = this.targetY;
        this.relic.hb.move(this.currentX, this.currentY);
    }

    @Override
    public void render(SpriteBatch sb) {
        this.relic.render(sb);
    }


}
