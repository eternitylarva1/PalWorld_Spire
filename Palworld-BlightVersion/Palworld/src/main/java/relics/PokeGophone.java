package relics;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import Option.*;
import basemod.abstracts.CustomMultiPageFtue;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Nemesis;
import com.megacrit.cardcrawl.monsters.beyond.Reptomancer;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.city.TheCollector;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import evepower.FireBreathingPower1;
import helpers.MinionHelper;
import monsters.act1.Cultist2;
import monsters.act1.Cultist3;
import monsters.act1.Cultist4;
import monsters.act2.BronzeAutomaton1;
import monsters.act2.GremlinLeader1;
import monsters.act2.TheCollector1;
import monsters.act3.Reptomancer1;
import monsters.pet.LeechPet;
import patches.monster.Cultist1;
import relics.abstracrt.ClickableRelic;
import relics.lineTwo.InfoBlight;
import utils.DesManager;
import utils.PokeGophoneSave;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static helpers.MinionHelper.getMinions;
import static patches.action.ChangeTargetPatch.last;
import static utils.ScreenPartition.currentCol;
import static utils.ScreenPartition.currentRow;
import static utils.randommonster.randommonster;


public class PokeGophone extends ClickableRelic implements CustomSavable<PokeGophoneSave> {
    public static final String ID = "PokeGophone";
    public static final String DESCRIPTION = "控制台";
    public static boolean planted = false;
    public static int count = 0;
    public static int technology = 0;
    public static peizhong p;
    public static sleeptaketurn sl;
    public static duanzao1 dz;
    public static zhijie zj;
    public static plant pt;
    public static dig1 sh;
    public static fudan fd;
    public int restcount = 0;
    AbstractMonster am;
    
    String[] a = {
            "右键帕鲁球遗物以获得帕鲁球卡牌, NL 每只帕鲁球控制一只帕鲁，可以替换，只能捕捉血量20%以下的怪物。 NL 特殊提示：右键自己的怪物，再左键任意位置可以移位置，避免遮挡buff",
            "在火堆你可以制作帕鲁球(初始50金币，造一个涨价50)，或者让你的帕鲁按顺序行动，每个帕鲁只能行动一次（配种需要两个帕鲁和一个计数为0的帕鲁球，配种不消耗行动次数，可以右键帕鲁球来选择父本母本），配种会随机继承词条 NL 回忆按钮在最下方，滚轮要多滚几下",
            "帕鲁除了参与战斗外，有的还会有额外的被动技能(注意！帕鲁是会给玩家塞牌的，不过会有一些补偿)，鼠标放到怪物身上可查看其被捕捉后的种族天赋（放帕鲁球上也会显示当前帕鲁技能）,除了种族天赋还会自带1-3个词条",
            "怪物会轮流攻击玩家和帕鲁（上一回合打玩家，下一回合就是打帕鲁），部分怪物有aoe伤害(蹲起的旋风斩，铜人的射爆，老头的连击，心脏的律动，还有六火的火焰吐息buff)",
            "帕鲁的繁殖力与对照表。计算公式：父本繁殖力+母亲本繁殖力对70取余数。如果表上没有，那么就随机roll(建议截图)"
    };

    public PokeGophone() {
        super("BloodBag", new Texture(Gdx.files.internal("images/relics/BFFS.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 1;

        tips.add(new PowerTip("帕鲁mod核心", "本遗物添加了帕鲁相关的火堆选项和最终boss增强，请勿交给恩洛斯和兰伟德"));
        tips.add(new PowerTip("特殊提示", "右键自己的怪物，再左键任意位置可以移位置，避免遮挡buff"));

        initializeTips();
    }

    public static void refresh() {

        if (p != null) {
            sl.updateUsability(sl.usable);
            dz.updateUsability(dz.usable);
            p.updateUsability(p.usable);
            pt.updateUsability(p.usable);
            sh.updateUsability(sh.usable);
        }
    }

    public static void PokeGowork(PokeGo p) {

        if (p.Entrylist.contains("工作狂")) {
            if (p.workcount == 0) {
                p.workcount++;
            }
            else {
                p.hasworked = true;
            }
        }
        else {
            p.hasworked = true;
        }
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {

        sh = new dig1(true);
        dz = new duanzao1(true);
        zj = new zhijie(get_pokego.get_pokego(0) != null);
        p = new peizhong(true);
        pt = new plant(true);
        sl = new sleeptaketurn(true);
        fd=new fudan(true);
        options.add(new sj1((AbstractDungeon.player.hasRelic(PokeBall_BluePrint.ID))?AbstractDungeon.player.gold >= 25 * this.counter:AbstractDungeon.player.gold >= 50 * this.counter));
        options.add(dz);
        options.add(fd);
        options.add(new keyan(true));
        InfoBlight.getAllRelics(PokeGo.class).forEach(
                pokeGo -> {
                    if (pokeGo.Entrylist.contains("拾荒")) {
                    }
                }
        );
        if (technology >= 2) {
            options.add(sl);
            options.add(p);
            options.add(zj);
            options.add(pt);

        }
    }

    @Override
    public boolean canSpawn() {
        return false;
    }

    @Override
    public void atTurnStart() {
        last = !last;
        if (AbstractDungeon.player.hasRelic(PalHudun.ID)) {
            last = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        if (count == 1) {
            count = 0;
            PokeGo pg = new PokeGo();
            int a = AbstractDungeon.monsterRng.random(3) + 2;
            AbstractMonster monster = randommonster(a);
            pg.counter = monster.maxHealth;
            pg.monsterClass = monster.getClass();
            pg.Entrylist.add(DesManager.getrandomEntry());
            pg.Entrylist.add(DesManager.getrandomEntry());
            RewardItem rewardItem1 = new RewardItem(pg);

            RewardItem rewardItem2 = new RewardItem(new DeathBook());
            RewardItem rewardItem3 = new RewardItem(new BattleBell());
            AbstractDungeon.getCurrRoom().rewards.add(rewardItem1);
            AbstractDungeon.getCurrRoom().rewards.add(rewardItem2);
            AbstractDungeon.getCurrRoom().rewards.add(rewardItem3);
            pg.updateDescription();
        }
        super.onVictory();
        MinionHelper.clearMinions(AbstractDungeon.player);
    }

    @Override
    public void atBattleStart() {
        last = true;
        currentRow = 1;
        currentCol = 5;
        if (AbstractDungeon.actNum==2) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.id == "Cultist") {
                    int a = AbstractDungeon.aiRng.random(5);
                    switch (a) {
                        case 1:
                            am = new Cultist2(0, 0);

                            break;
                        case 2:
                            am = new Cultist(0, 0);

                            break;
                        case 5:
                            am = new Cultist4(0, 0);
                            break;
                        case 3:
                            am = new Cultist3(0, 0);
                            break;
                        default:
                            am = new Cultist1(0, 0);

                    }
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(am, false));
                    am.drawX = m.drawX;
                    am.drawY = m.drawY;
                    AbstractDungeon.actionManager.addToTop(new SuicideAction(m, false));


                }
            }
        }
        if (AbstractDungeon.actNum >= 2) {
            if (AbstractDungeon.monsterRng.randomBoolean(0.05f)) {
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(new LeechPet(-Settings.WIDTH / 15, 0), false));
            }
        }

        //AbstractDungeon.player.drawX+= (float) Settings.WIDTH /20;
        //AbstractDungeon.player.drawY+= (float) Settings.HEIGHT /20;
        AbstractMonster m = AbstractDungeon.getMonsters().monsters.get(0);
        if (m.getClass() == CorruptHeart.class) {
            int a = AbstractDungeon.monsterRng.random(4) + 63;
            AbstractMonster monster = randommonster(a);
            AbstractMonster mm = monster;
            if (monster.getClass() == TheCollector.class) {
                mm = new TheCollector1();
            }
            if (monster.getClass() == BronzeAutomaton.class) {
                monster = new BronzeAutomaton1();
            }
            if (monster.getClass() == GremlinLeader.class) {
                monster = new GremlinLeader1();
            }
            if (monster.getClass() == Reptomancer.class) {
                monster = new Reptomancer1();
            }
            mm.drawY = m.drawY - 100;
            mm.drawX = (float) (m.drawX - 0.2 * Settings.WIDTH);
            mm.rollMove();
            mm.createIntent();
            mm.usePreBattleAction();
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(monster, false));


        }
        if (m.getClass() == Hexaghost.class || m.getClass() == Nemesis.class) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new FireBreathingPower1(m, 2)));
        }

    }

    @Override
    public void onRightClick() {

        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            AbstractDungeon.ftue = new CustomMultiPageFtue(
                    new Texture[]{
                            ImageMaster.loadImage("images/tips/t1.png"),
                            ImageMaster.loadImage("images/tips/t2.png"),
                            ImageMaster.loadImage("images/tips/t3.png"),
                            ImageMaster.loadImage("images/tips/t4.png"),
                            ImageMaster.loadImage("images/tips/t5.png"),
                    },
                    a
            );
        }


    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {

    }

    @Override
    public void onCardDraw(AbstractCard card) {
        Iterator var1;
        AbstractMonster m;
        var1 = getMinions().monsters.iterator();

        while (true) {
            if (!var1.hasNext()) {
                break;
            }

            m = (AbstractMonster) var1.next();
            for (AbstractPower p : m.powers) {
                if (!m.isDeadOrEscaped()) {
                    p.onCardDraw(card);
                }
            }
        }
        Iterator var2;
        AbstractMonster m1;
        var2 = AbstractDungeon.getMonsters().monsters.iterator();

        while (true) {
            if (!var2.hasNext()) {
                break;
            }

            m1 = (AbstractMonster) var2.next();
            for (AbstractPower p : m1.powers) {
                p.onCardDraw(card);
            }
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        AbstractMonster m;

        for (AbstractMonster monster : getMinions().monsters) {
            m = monster;
            for (AbstractPower p : m.powers) {
                if (!m.isDeadOrEscaped()) {
                    p.onExhaust(card);
                }
            }
        }
    }

    @Override
    public void initializeTips() {
        Scanner desc = new Scanner(this.description);

        while (true) {
            String s;
            boolean alreadyExists;
            do {
                if (!desc.hasNext()) {
                    desc.close();
                    return;
                }

                s = desc.next();
                if (s.charAt(0) == '#') {
                    s = s.substring(2);
                }

                s = s.replace(',', ' ');
                s = s.replace('.', ' ');
                s = s.trim();
                s = s.toLowerCase();
                alreadyExists = false;
            } while (!GameDictionary.keywords.containsKey(s));

            s = GameDictionary.parentWord.get(s);

            for (PowerTip t : this.tips) {
                if (t.header.toLowerCase().equals(s)) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                this.tips.add(new PowerTip(TipHelper.capitalize(s), GameDictionary.keywords.get(s)));
            }
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        tips.clear();
        tips.add(new PowerTip("新手教程", "战斗中右键本遗物查看新手教程"));
        tips.add(new PowerTip("特殊提示", "右键自己的怪物，再左键任意位置可以移位置，避免遮挡buff"));
        for (int i = 0; i < technology; i++) {

            tips.add(new PowerTip("科研等级" + (i + 1), keyan.TEXT1[i]));
        }
        initializeTips();
    }

    @Override
    public void update() {
        super.update();

    }


    @Override
    public PokeGophoneSave onSave() {
        return new PokeGophoneSave(planted,counter, technology);
    }

    @Override
    public void onLoad(PokeGophoneSave pokeGophoneSave) {
        if (pokeGophoneSave != null && pokeGophoneSave.planted != null && pokeGophoneSave.technology != null && pokeGophoneSave.counter != null) {
            planted = pokeGophoneSave.planted;
            counter = pokeGophoneSave.counter;
            technology = pokeGophoneSave.technology;
        }
    }


    @Override
    public void obtain() {
        InfoBlight.obtain(this);
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        InfoBlight.instanceObtain(this,callOnEquip);
    }

    @Override
    public void instantObtain() {
        InfoBlight.instanceObtain(this,true);
    }
}
      