package mymod;

import basemod.*;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.*;
import cards.green.*;
import cards.red.*;
import cards.tempCards.Virus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.events.city.MaskedBandits;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.monsters.exordium.GremlinTsundere;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import monsters.act1.GremlinTsundere1;
import monsters.act1.HexaghostDefect;
import monsters.act1.HexaghostEVEPLUS;
import monsters.act2.SphericGuardianEVE;
import monsters.act3.Cultist13;
import monsters.act3.Cultist21;
import patches.player.PlayerDamagePatch;
import relics.*;

import relics.PokeGo;
import relics.PokeGophone;
import relics.lineTwo.InfoBlight;
import screen.RelicSelectScreen;
import utils.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

import static basemod.BaseMod.registerModBadge;
import static patches.ui.RenderCreaturePatch.haveJudas;
import static relics.UnicornStump.logger;

import static utils.Utils.removeRelicString;



@SpireInitializer
public class IsaacMod implements EditCardsSubscriber, EditRelicsSubscriber, PostDungeonInitializeSubscriber, EditStringsSubscriber, PreUpdateSubscriber, RenderSubscriber, PostUpdateSubscriber , PostInitializeSubscriber, CustomSavable{//PostInitializeSubscriber EditCharactersSubscriber

    public static String[] relicsString = {

            PokeGo.ID,
            PokeGophone.ID,



    };
    public static String[] cardsString = {


            Bomb.ID
    };

    public static String[] inPoolRelic = {
            PokeGo.ID
    };

    public static String[] bossRelic = {PokeGo.ID};
    public static String[] devilRelic = {PokeGo.ID
    };
    public static String[] deviOnlyRelic = {PokeGo.ID
    };

    public static List<String> relics = new ArrayList<>(Arrays.asList(relicsString));
    public static List<String> inPoolRelics = new ArrayList<>(Arrays.asList(inPoolRelic));
    public static List<String> cards = new ArrayList<>(Arrays.asList(cardsString));
    public static List<String> bossRelics = new ArrayList<>(Arrays.asList(bossRelic));
    public static List<String> devilRelics = new ArrayList<>(Arrays.asList(devilRelic));
    public static List<String> devilOnlyRelics = new ArrayList<>(Arrays.asList(deviOnlyRelic));
    public static SpireConfig config;
    public IsaacMod() {
    }

    public static void initialize() throws IOException {

        BaseMod.subscribe(new IsaacMod());
        config=new SpireConfig("Palworld","Palworld");
        config.load();
    }

    private static AbstractRelic getRelic(String name) {
        Class c;
        try {
            c = Class.forName("relics." + name);
            return (CustomRelic) c.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void receiveEditRelics() {
        InfoBlight.initInfoBlight(new PokeGo());
        InfoBlight.initInfoBlight(new PokeGophone());
        for (String relic : relics) {
            BaseMod.addRelic(getRelic(relic), RelicType.SHARED);
        }
        BaseMod.addRelic(new Habit(), RelicType.SHARED);
        BaseMod.addRelic(new BFFS(), RelicType.SHARED);
        BaseMod.addRelic(new D4(), RelicType.SHARED);
        BaseMod.addRelic(new D6(), RelicType.SHARED);
        BaseMod.addRelic(new NineVolt(), RelicType.SHARED);
        BaseMod.addRelic(new PunchingBag(),RelicType.SHARED);
        BaseMod.addRelic(new TechnologyTree(),RelicType.BLUE);
        BaseMod.addRelic(new Randommonster(),RelicType.SHARED);
        BaseMod.addRelic(new PalHudun(),RelicType.SHARED);
        BaseMod.addRelic(new BattleBell(),RelicType.SHARED);
        BaseMod.addRelic(new DeathBook(),RelicType.SHARED);
        BaseMod.addRelic(new CYSDX(),RelicType.SHARED);
        BaseMod.addRelic(new yemei(),RelicType.SHARED);
        BaseMod.addRelic(new PokeBall_BluePrint(),RelicType.SHARED);
        BaseMod.addRelic(new PalBad(),RelicType.SHARED);
        BaseMod.addRelic(new Yuzaohu(),RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(PokeBall_BluePrint.ID);
        UnlockTracker.markRelicAsSeen(PalBad.ID);
        UnlockTracker.markRelicAsSeen(Yuzaohu.ID);
    }


    @Override
    public void receiveEditCards() {
        BaseMod.addCard( new TuDu());
        BaseMod.addCard( new YouLingShiYan());
        BaseMod.addCard( new RongHe());
        BaseMod.addCard( new YuXiang());
        BaseMod.addCard( new KuaiSuXIngDong());
        BaseMod.addCard( new MoFangZhe());
        BaseMod.addCard( new HuDun());
        BaseMod.addCard( new XianXueQIYue());
        BaseMod.addCard( new Fangxue());
        BaseMod.addCard( new XiSheng());
        BaseMod.addCard( new Tongbu());
        BaseMod.addCard( new TogetherCard());
        BaseMod.addCard( new Baling());
        BaseMod.addCard( new ChaoFengStrike());
        BaseMod.addCard( new Bomb());
        BaseMod.addCard( new Encourage());
        BaseMod.addCard( new PalAnger());
        BaseMod.addCard( new Cancel());
        BaseMod.addCard( new Virus(false));
        BaseMod.addCard(new Pills());
        BaseMod.addCard(new Protect());
        BaseMod.addCard(new Rocket_Bomb());
        BaseMod.addCard(new Skill_fruit());
    }

    public void receiveEditStrings() {
        String LOCALIZATION_FOLDER;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            LOCALIZATION_FOLDER = "isaacLocalization/zhs";
        } else {
            LOCALIZATION_FOLDER = "isaacLocalization/eng";
        }

        String relicStrings = Gdx.files.internal(makePath(LOCALIZATION_FOLDER, "relic_strings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(makePath(LOCALIZATION_FOLDER, "card-strings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(makePath(LOCALIZATION_FOLDER, "power-strings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String eventStrings = Gdx.files.internal(makePath(LOCALIZATION_FOLDER, "event-strings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
//        String uiStrings = Gdx.files.internal(makePath(LOCALIZATION_FOLDER, "future_UI.json")).readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
//        String characterStrings = Gdx.files.internal(makePath(LOCALIZATION_FOLDER, "future_characters.json")).readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
    }

    public static String makePath(String folder, String resource) {
        return folder + "/" + resource;
    }

    private List<AbstractCard> getRandomCards(int num) {
        List<AbstractCard> getCards = new ArrayList<>();
        try {
            String[] rnd = Utils.getRandomCards(cards.size(), num);
            for (String card : rnd) {
                Class c = Class.forName("cards." + card);
                getCards.add((AbstractCard) c.newInstance());
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("你没找到类");
            e.printStackTrace();
        }
        return getCards;
    }

    private void preSettings() {
        //各种参数初始化

        receivedCard = false;


        relics = new ArrayList<>(Arrays.asList(relicsString));
        haveJudas = false;
        PokeGo.slotNum = 0;
        PlayerDamagePatch.resurrectRelics.clear();
    }

    /**
     * todo 初始化在这里
     */
    public void receivePostDungeonInitialize() {
        tryGetRelic(new PokeGophone());
        PokeGophone.count=1;

        PokeGophone.count=1;
    }

    private static boolean receivedCard = false;

    public static List<AbstractRelic> obtainRelics = new ArrayList<>();
    public static List<String> removeRelics = new ArrayList<>();


    @Override
    public void receivePostUpdate() {
        //初始卡牌赠送

    }

    private static boolean getScreen = false;

    public static AbstractRelic returnRandomScreenlessRelic(final AbstractRelic.RelicTier tier) {
        AbstractRelic tmpRelic;
        if (!getScreen) {
            tmpRelic= AbstractDungeon.returnRandomRelic(tier);
            if (Objects.equals(tmpRelic.relicId, "Bottled Flame") || Objects.equals(tmpRelic.relicId, "Bottled Lightning") || Objects.equals(tmpRelic.relicId, "Bottled Tornado")) {
                getScreen = true;
            }
        } else {
            tmpRelic = AbstractDungeon.returnRandomScreenlessRelic(tier);
        }
        return tmpRelic;
    }

    public static boolean obtain(AbstractPlayer p, String relicId, boolean canDuplicate) {
        AbstractRelic r = getRelic(relicId);
        if (r == null) return false;
        removeRelicString(relicId);
        Utils.removeFromPool(r);
        return obtain(p, r, canDuplicate, true);
    }

    public static boolean obtain(AbstractPlayer p, String relicId, boolean canDuplicate, boolean callOnEquip) {
        AbstractRelic r = getRelic(relicId);
        if (r == null) return false;
        removeRelicString(relicId);
        Utils.removeFromPool(r);
        return obtain(p, r, canDuplicate, callOnEquip);
    }


    public static boolean obtain(AbstractPlayer p, AbstractRelic r, boolean canDuplicate) {
        return obtain(p, r, canDuplicate, true);
    }

    public static boolean obtain(AbstractPlayer p, AbstractRelic r, boolean canDuplicate, boolean callOnEquip) {
        if (r == null) {
            return false;
        } else if (p.hasRelic(r.relicId) && !canDuplicate) {
            return false;
        } else {
            int slot = p.relics.size();
            r.makeCopy().instantObtain(p, slot, callOnEquip);
            return true;
        }
    }

    @Override
    public void receivePreUpdate() {
        RelicSelectScreen.updateScreen();
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        RelicSelectScreen.updateRender(spriteBatch);
        sb = spriteBatch;
    }

    public static SpriteBatch sb;

    private static void doDaily() {
        doDaily(null);
    }

    private static void doDaily(String specificDay) {
        File date = new File(".date");
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String theDay = sdf.format(today);
        if (!theDay.equals(specificDay)) {
            return;
        }
        if (!date.exists()) {
            try {
                date.createNewFile();
                doGetRelic(new FileWriter(date), theDay);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(date));
                String thisDay = reader.readLine();
                if (!theDay.equals(thisDay)) {
                    doGetRelic(new FileWriter(date), theDay);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void doGetRelic(FileWriter writer, String theDay) {
        try {
            writer.write(theDay);
            writer.flush();
            writer.close();
            List<AbstractRelic> all = new ArrayList<>();
//            all.addAll(inPoolRelics);
//            all.addAll(bossRelics);
//            all.addAll(devilOnlyRelics);
            all.add(new PokeGo());
            int n = new Random(Integer.parseInt(theDay)).nextInt(all.size());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    public static void saveData() {
        try {
            if (bruhData == null) {
                bruhData = new SpireConfig("downfall", "TrapSaveData");
            }

            SpireConfig config = new SpireConfig("downfall", "downfallSaveData", configDefault);
            config.setBool("contentSharing_curses", contentSharing_curses);
            config.setBool("contentSharing_relics", contentSharing_relics);
            config.setBool("contentSharing_events", contentSharing_events);
            config.setBool("contentSharing_potions", contentSharing_potions);
            config.setBool("contentSharing_colorlessCards", contentSharing_colorlessCards);
            config.setBool("crossover_characters", crossoverCharacters);
            config.setBool("crossover_mod_characters", crossoverModCharacters);
            config.setBool("normalMapLayout", normalMapLayout);
            config.setBool("unlockEverything", unlockEverything);
            config.setBool("sneckoNoModCharacters", sneckoNoModCharacters);
            config.setBool("disableMusicOverride", noMusic);
            config.setBool("useIconsForAppliedProperties", useIconsForAppliedProperties);
            config.save();
            GoldenIdol_Evil.save();
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
    */



    private void receiveEditMonsters() {
        // 注册怪物组合，你可以多添加几个怪物
        BaseMod.addMonster("PalMod:GiantHead", Cultist13.NAME, () -> new Cultist13(0.0F, 0.0F));
        BaseMod.addMonster("PalMod:HexaghostEVEPLUS", HexaghostEVEPLUS.NAME, () -> new HexaghostEVEPLUS());
        BaseMod.addMonster("PalMod:HexaghostDefect", HexaghostDefect.NAME, () -> new HexaghostDefect());

        BaseMod.addMonster("PalMod:SphericGuardianEVE_and_Gremlin", SphericGuardianEVE.NAME, () -> new MonsterGroup(new AbstractMonster[] { new GremlinTsundere(-80.0F, MathUtils.random(25.0F, 70.0F)), new SphericGuardianEVE(200.0F, MathUtils.random(25.0F, 70.0F)) }));
        // 两个异鸟
        // BaseMod.addMonster("ExampleMod:2 Byrds", "", () -> new MonsterGroup(new AbstractMonster[] { new Byrd(-80.0F, MathUtils.random(25.0F, 70.0F)), new Byrd(200.0F, MathUtils.random(25.0F, 70.0F)) }));

        // 添加战斗遭遇
        // 在第二章添加精英遭遇，权重为1.0，权重越高越可能遇到

        BaseMod.addEliteEncounter("TheBeyond", new MonsterInfo("PalMod:GiantHead", 1.0F));
        BaseMod.addMonsterEncounter(TheEnding.ID, new MonsterInfo("PalMod:SphericGuardianEVE_and_Gremlin", 1.0F));
        BaseMod.addMonsterEncounter(TheBeyond.ID, new MonsterInfo("PalMod:HexaghostDefect", 1.0F));


    }
    @Override
    public void receivePostInitialize() {
        receiveEditMonsters();
        ModPanel settingsPanel = new ModPanel();
        //settingsPanel.addUIElement(buttonLabel);

        Consumer<ModToggleButton> clickAction = (button) -> {
            config.setBool("Background",button.enabled);
            System.out.println("已经将替换背景设置为！"+button.enabled);
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Consumer<ModToggleButton> clickAction1 = (button) -> {
            config.setBool("Douququ",button.enabled);
            System.out.println("已经将斗蛐蛐设置为！"+button.enabled);
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Consumer<ModToggleButton> clickAction2 = (button) -> {
            config.setBool("gengduo",button.enabled);
            System.out.println("已经将更多词条设置为！"+button.enabled);
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Consumer<ModToggleButton> clickAction3 = (button) -> {
            config.setBool("youjian",button.enabled);
            System.out.println("已经将右键位移设置为！"+!button.enabled);
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Consumer<ModToggleButton> clickAction4 = (button) -> {
            config.setBool("zhuore",button.enabled);
            System.out.println("已经将灼热设置为！"+!button.enabled);
            try {
                config.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

// 创建一个带有标签的按钮，并将其添加到 ModPanel 中
        ModLabeledToggleButton labeledButton = new   ModLabeledToggleButton("使用原版火堆背景？", 400.0f, 700.0f-16.0f, Color.WHITE, FontHelper.buttonLabelFont, config.getBool("Background"),settingsPanel, (label) -> {
        },clickAction);
        ModLabeledToggleButton Douququ = new   ModLabeledToggleButton("是否使用斗蛐蛐模式？(会取消一些我方怪物的修改，贴近原版)", 400.0f, 700.0f-64.0f, Color.WHITE, FontHelper.buttonLabelFont, config.getBool("Douququ"),settingsPanel, (label) -> {
        },clickAction1);
        ModLabeledToggleButton gengduo = new   ModLabeledToggleButton("是否使用更多词条？（与部分mod有冲突，比如废墟图书馆）", 400.0f, 700.0f-128.0F+16.0F, Color.WHITE, FontHelper.buttonLabelFont, config.getBool("gengduo"),settingsPanel, (label) -> {
        },clickAction2);
        ModLabeledToggleButton youjian = new   ModLabeledToggleButton("是否关闭右键位移功能？", 400.0f, 700.0f-128.0F+16.0F-64.0F, Color.WHITE, FontHelper.buttonLabelFont, config.getBool("gengduo"),settingsPanel, (label) -> {
        },clickAction3);
        ModLabeledToggleButton zhuore = new   ModLabeledToggleButton("是否允许帕鲁敲类似“灼热”的牌?", 400.0f, 700.0f-128.0F+16.0F-64.0F-64.0F, Color.WHITE, FontHelper.buttonLabelFont, config.getBool("gengduo"),settingsPanel, (label) -> {
        },clickAction4);
        settingsPanel.addUIElement(labeledButton);
        settingsPanel.addUIElement(Douququ);
        settingsPanel.addUIElement(gengduo);
        settingsPanel.addUIElement(youjian);
        settingsPanel.addUIElement(zhuore);
        Texture badgeTexture = new Texture(Gdx.files.internal("images/relics/PokeGo.png"));
        registerModBadge(badgeTexture, "PalMod", "Butterfly Norm", "这是一段描述", settingsPanel);
    }


    @Override
    public Object onSave() {
        return null;
    }

    @Override
    public void onLoad(Object o) {

    }
    private void tryGetRelic(PokeGophone customRelic) {
        if (!AbstractDungeon.player.hasRelic(customRelic.relicId)) {
            logger.info(">>>人物没有遗物【{}】,尝试给人物添加遗物【{}】<<<", customRelic.relicId, customRelic.relicId);
            int slot = AbstractDungeon.player.getRelicNames().size();
            PokeGophone.technology=0;
            customRelic.instantObtain(AbstractDungeon.player, slot, false);
            logger.info(">>>尝试给人物添加遗物【{}】成功<<<", customRelic.relicId);
        }
    }

}
