package relics.lineTwo;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.abstracts.CustomSavableRaw;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeGo;
import relics.PokeGophone;
import relics.lineTwo.relicToBlight.BlightWithRelic;
import relics.lineTwo.relicToBlight.CustomBlightPatch;
import relics.lineTwo.relicToBlight.blightHook.BlightCampfire;
import relics.lineTwo.relicToBlight.blightHook.BlightForCard;
import relics.lineTwo.relicToBlight.blightHook.BlightOnEnterRoom;

import java.util.*;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class InfoBlight extends BlightWithRelic implements
        BlightOnEnterRoom, BlightForCard, BlightCampfire{

    public InfoBlight(AbstractRelic relic) {
        super(relic);
    }


    /**
     * 需要在初始化时，使用这个注册
     * 每添加一个转换为荒疫的遗物，就需要注册一下
     * @param relic 遗物
     */
    public static void initInfoBlight(AbstractRelic relic) {
        CustomBlightPatch.Assign(new InfoBlight(relic));
    }

    /**
     * 需要在想要转化为荒疫的遗物中，修改如下的一个方法，使用这个函数，并且去掉super部分：
     * obtain
     */
    public static void obtain(AbstractRelic relic) {
//        new InfoBlight<>(relic).obtain();
        instanceObtain(relic, true);
        //obtain有一点bug，所以就先用着这个吧
    }

    /**
     * 需要在想要转化为荒疫的遗物中，修改如下的两个方法，使用这个函数，并且去掉super部分：
     * 无参数的instantObtain
     * 有参数的instantObtain
     */
    public static void instanceObtain(AbstractRelic relic, boolean callOnEquip) {
        new InfoBlight(relic).instantObtain(player, player.blights.size(), callOnEquip);
    }

    /**
     * 获取所有荒疫包含遗物中，指定类型的遗物，只获取第一个匹配的
     * @param relicClass 遗物类型
     * @return 第一个匹配项
     * @param <T> 遗物类型
     */
    public static <T extends AbstractRelic> Optional<T> getOneRelic(Class<T> relicClass) {
        for (AbstractBlight blight : player.blights) {
            if (blight instanceof BlightWithRelic) {
                BlightWithRelic blightWithRelic = (BlightWithRelic) blight;
                if (relicClass.isInstance(blightWithRelic.relic)) {
                    T t = (T) blightWithRelic.relic;
                    return Optional.of(t);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 获取所有荒疫包含遗物中，指定类型的遗物，获取所有的匹配项
     * @param relicClass 遗物类型
     * @return 列表
     * @param <T> 遗物类型
     */
    public static <T extends AbstractRelic> List<T> getAllRelics(Class<T> relicClass) {
        return player.blights.stream().filter(blight -> blight instanceof BlightWithRelic)
                .map(blight -> (BlightWithRelic) blight)
                .filter(blight -> relicClass.isInstance(blight.relic))
                .map(blight -> (T) blight.relic).collect(Collectors.toList());
    }

    /**
     * 获取所有荒疫中，类型为指定类型的荒疫，感觉没啥用，不如直接一个for循环
     * @param blightClass 荒疫类型
     * @return 列表
     * @param <T> 荒疫类型
     */
    public static <T extends AbstractBlight> List<T> getAllInfoBlights(Class<T> blightClass) {
        return player.blights.stream()
                .filter(blightClass::isInstance)
                .map(blight -> (T) blight)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有荒疫包含遗物中，指定类型的遗物，获取所有的匹配项，并且还将获取这个荒疫位于荒疫列表的位置
     * （注意，不是这个荒疫是第几个匹配项，而是在总的荒疫中的位置）
     * @param relicClass 遗物类型
     * @return 列表和索引
     * @param <T> 遗物类型
     */
    public static <T extends AbstractRelic> Map<Integer, T> getAllRelicsWithBlightIndex(Class<T> relicClass) {
        Map<Integer, T> list = new HashMap<>();
        int s = 0;
        for (AbstractBlight blight : player.blights) {
            if (blight instanceof BlightWithRelic) {
                BlightWithRelic blightWithRelic = (BlightWithRelic) blight;
                if (relicClass.isInstance(blightWithRelic.relic)) {
                    T t = (T) blightWithRelic.relic;
                    list.put(s, t);
                }
            }
            s++;
        }
        return list;
    }

    /**
     * 单纯的复制一下，主要是在CustomBlightPatch中使用，可以不用管
     */
    public InfoBlight makeCopy() {
        return new InfoBlight(this.relic.makeCopy());
    }

    /*
    下面是所有该荒疫包含的遗物可使用的Hook，注意，如果需要其他Hook的话，为了简洁明了，请写一个Patch写一个接口，然后在这里注册
     */

    @Override
    public void onEquip() {
        super.onEquip();
        this.relic.onEquip();
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        this.relic.onEnterRoom(room);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.relic.justEnteredRoom(room);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.relic.onExhaust(card);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        this.relic.onCardDraw(card);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.relic.onVictory();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        this.relic.onPlayCard(card, m);
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.relic.onPlayerEndTurn();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.relic.atBattleStart();
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.relic.atTurnStart();
    }

    @Override
    public boolean canUseCampfireOption(AbstractCampfireOption option) {
        return this.relic.canUseCampfireOption(option);
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        this.relic.addCampfireOption(options);
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        return this.relic.canPlay(card);
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "getRelic", paramtypez = String.class)
    public static class GetRelicPatch {
        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic> Prefix(AbstractPlayer __instance, String targetID) {
            Optional<AbstractRelic> anyRelic = __instance.blights.stream().filter(blight -> blight instanceof BlightWithRelic)
                    .map(blight -> (BlightWithRelic) blight)
                    .filter(blight -> Objects.equals(blight.relic.relicId, targetID))
                    .map(blight -> blight.relic).findAny();
            if (anyRelic.isPresent())
                return SpireReturn.Return(anyRelic.get());
            else
                return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "hasRelic", paramtypez = String.class)
    public static class HasRelicPatch {
        @SpirePostfixPatch
        public static SpireReturn<Boolean> Prefix(AbstractPlayer __instance, String targetID) {
            Optional<AbstractRelic> anyRelic = __instance.blights.stream().filter(blight -> blight instanceof BlightWithRelic)
                    .map(blight -> (BlightWithRelic) blight)
                    .filter(blight -> Objects.equals(blight.relic.relicId, targetID))
                    .map(blight -> blight.relic).findAny();
            if (anyRelic.isPresent())
                return SpireReturn.Return(true);
            else
                return SpireReturn.Continue();
        }
    }
}
