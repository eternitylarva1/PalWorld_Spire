package relics.lineTwo.relicToBlight;

public interface BecomeBlight {
    BlightWithRelic makeNewBlightWithRelic();
}
/*
不一定需要使用，不过可以用这个代码来方便地把遗物变为荒疫版本然后自动注册
示例代码：
    @Override
    public void receiveEditRelics() {
        new AutoAdd(MOD_NAME.toLowerCase())
                .packageFilter(SuperstitioRelic.class)
                .any(CustomRelic.class, (info, relic) -> {
                    if (relic instanceof BecomeBlight) {
                        CustomBlightPatch.Assign(((BecomeBlight) relic).makeNewBlightWithRelic());
                        return;
                    }
                    BaseMod.addRelic(relic, RelicType.SHARED);
                    if (info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }
 */
