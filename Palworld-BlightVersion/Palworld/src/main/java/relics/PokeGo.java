package relics;


import Option.*;
import actions.ApplyEntryAction;
import actions.SummonMinionAction;
import basemod.abstracts.CustomSavable;
import cards.tempCards.PokeBall;
import cards.tempCards.PokeGOGO;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Maw;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import helpers.MinionHelper;
import helpers.SummonHelper;
import monsters.act1.*;
import monsters.act2.*;
import monsters.act3.*;
import monsters.act4.CorruptHeart1;
import monsters.act4.SpireShield1;
import monsters.act4.SpireSpear1;
import patches.action.ChangeTargetPatch;
import relics.abstracrt.ClickableRelic;
import relics.lineTwo.InfoBlight;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static Option.get_pokego.get_pokego;
import static com.megacrit.cardcrawl.core.CardCrawlGame.music;
import static mymod.IsaacMod.config;
import static relics.PokeGophone.p;
import static relics.PokeGophone.technology;
import static utils.DesManager.getDescription;
import static utils.DesManager.getEntry;

public class PokeGo extends ClickableRelic implements CustomSavable<PokeGoSave> {
    public static final String ID = "PokeGo";
    public static final String IMG = "images/relics/PokeGo.png";
    public static final String DESCRIPTION = "右击获得一张精灵球。精灵球有一定概率抓住怪物，血量越低概率越高，并成为你的随从。随从最多一只。";
    public static int level = 1;
    public static int slotNum = 0;
    private static Point[] site = {
            new Point(-150, -150),
            new Point(350, -50),
            new Point(500, -50),
            new Point(0, 200),
            new Point(-175, 200),

            new Point(175, 200),
            new Point(-350, -100),
            new Point(350, 200),
            new Point(-500, 200),

    };
    private static int clicknum = 0;
    public int fanzhili = 0;
    public AbstractMonster am;
    public boolean chuzhan = false;
    public int workcount = 0;
    public Class monsterClass = null;
    public AbstractMonster pet = null;
    public AbstractMonster target = null;
    public boolean hasworked = false;
    public ArrayList<String> Entrylist = new ArrayList<>();
    public boolean newPet = false;
    public int slot;
    private int lastPetHp = -1;
    private boolean getCard = true;

    public static Map< Class,AbstractMonster> PokeGocache=new HashMap<>();
    public PokeGo() {
        this(slotNum % 9);
    }

    public PokeGo(int slot) {
        super("PokeGo", new Texture(Gdx.files.internal("images/relics/PokeGo.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.slot = slot;
        counter = 0;

    }

    @Override
    public void onVictory() {
        if (Entrylist.contains("自残倾向")) {
            if (this.chuzhan) {
                this.counter -= 6;

            }
        }
        if (Entrylist.contains("窃贼")) {
            if (this.chuzhan) {
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(20));

            }
        }
        this.chuzhan = false;

    }
    public AbstractMonster getMonsterbycache(Class monsterClass){
        if(PokeGocache.get(monsterClass)!=null){
            return PokeGocache.get(monsterClass);
        }else {
            PokeGocache.put(monsterClass,InstanceMaker.getInstanceByClass(monsterClass));
            return PokeGocache.get(monsterClass);
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        this.workcount=0;
        if (this.monsterClass != null && this.counter != 0) {
           am=getMonsterbycache(monsterClass);
            switch (am.id) {
                case "JawWorm":
                    if (this.counter >= 300) {
                        this.monsterClass = Maw.class;
                    }
                    this.updateDescription();
                    break;
                default:
                    break;

            }
        }
        this.hasworked = false;
        if (this.Entrylist.contains("消极主义")) {
            this.hasworked = AbstractDungeon.miscRng.randomBoolean();
        }

        initializeTips();
        if (room instanceof RestRoom && this.monsterClass != null) {

            if (this.counter > 0) {
                if (this.monsterClass == Maw.class) {
                    AbstractDungeon.player.gainGold(50);
                }

                InfoBlight.getAllRelicsWithBlightIndex(PokeGo.class).forEach((integer, pokeGo) -> {
                    if (pokeGo == this) {
                        pokeGo.flash();
                        get_pokego.dingwei = integer;
                        System.out.println("当前选中编号" + integer + "的遗物");
                    }
                });
            }

        }
    }

    @Override
    public String getUpdatedDescription() {

        return this.DESCRIPTIONS[0];

    }

    public AbstractRelic makeCopy() {
        return new PokeGo(slotNum % 9);
    }

    @Override
    //右键使用
    public void onRightClick() {
        //选一个怪物抓住
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (getCard) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new PokeBall(this)));
                getCard = false;
            }
        }
        else if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            clicknum++;
            //选择系统，右键时，如果捉了帕鲁，则闪烁

            InfoBlight.getAllRelicsWithBlightIndex(PokeGo.class).forEach((integer, pokeGo) -> {
                if (pokeGo == this) {
                    pokeGo.flash();
                    get_pokego.dingwei = integer;
                    System.out.println("当前选中编号" + integer + "的遗物");
                }
            });

            peizhong pp = p;
            zhijie zj = PokeGophone.zj;
            duanzao1 dd = PokeGophone.dz;
            sleeptaketurn ss = PokeGophone.sl;
            if (this.counter != 0 && this.monsterClass != null) {
                if (clicknum % 2 == 0) {
                    PokeGo pg = get_pokego();
                    AbstractMonster monster1m = InstanceMaker.getInstanceByClass(pg.monsterClass);

                    if (!Objects.equals(pp.monster2, monster1m.name)) {
                        pp.monster1 = monster1m.name;
                        pg.updateDescription();
                        pp.updateUsability(pp.usable);
                        zj.monster1 = monster1m.name;
                        pg.updateDescription();
                        zj.updateUsability(zj.usable);
                        pp.pg1 = this;
                    }

                }
                else {
                    PokeGo pg = get_pokego();
                    AbstractMonster monster1m = InstanceMaker.getInstanceByClass(pg.monsterClass);
                    if (!Objects.equals(pp.monster1, monster1m.name)) {
                        pp.monster2 = monster1m.name;
                        pg.updateDescription();
                        pp.updateUsability(pp.usable);
                        zj.monster1 = monster1m.name;
                        pg.updateDescription();
                        zj.updateUsability(zj.usable);
                        pp.pg2 = this;
                    }
                }
                dd.updateUsability(dd.usable);
                ss.updateUsability(ss.usable);
                zj.updateUsability(zj.usable);
                p.updateUsability(p.usable);
            }


        }
    }


    @Override
    public void onEquip() {
        super.onEquip();
        if (Entrylist.contains("传说")) {
            counter *= 2;
        }
        slotNum++;
        System.out.println("当前slot为" + slot);
    }

    @Override
    public void atTurnStart() {
        updateDescription();
        super.atTurnStart();

        getCard = true;
        if (pet != null && this.counter > 0 && this.chuzhan) {
            //设置目标
            target = null;
            if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                if (!pet.isDead && !pet.escaped) {
                    ChangeTargetPatch.target = this.pet;
                    if (Entrylist.contains("鲁莽")) {

                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(pet, pet, new StrengthPower(pet, 5)));
                    }
                    if (Entrylist.contains("虔诚")) {

                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, 1)));
                    }
                    //选一个怪为目标


                }
                else {
                    pet = null;

                }
            }
        }
    }

    public void updateDescription() {
        String mode = "";

        if (this.monsterClass != null) {
            am = InstanceMaker.getInstanceByClass(monsterClass);
            mode = getDescription(am.id);
            AbstractMonster m = InstanceMaker.getInstanceByClass(monsterClass);

            tips.clear();
            tips.add(new PowerTip(this.name, "当前帕鲁：" + m.name));
            tips.add(new PowerTip(m.name, "种族天赋：" + mode));
            for (String entry : Entrylist) {
                tips.add(new PowerTip(entry, getEntry(entry)));
            }
            if (this.hasworked) {
                tips.add(new PowerTip(this.name, "当前帕鲁已经行动过了"));
            }
            tips.add(new PowerTip("繁殖力", "" + this.fanzhili));
            initializeTips();
        }
        else {
            tips.clear();
            tips.add(new PowerTip(this.name, DESCRIPTIONS[0]));
            initializeTips();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        super.onPlayerEndTurn();
        if (pet != null) {
            //设置目标

            if (!pet.isDead && !pet.escaped && this.chuzhan) {
                if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                    ChangeTargetPatch.target = this.pet;
                }
                ChangeTargetPatch.source.clear();
                ChangeTargetPatch.source.addAll(AbstractDungeon.getMonsters().monsters);
                System.out.println("检测到已经出战，将目标50%转移到宠物身上" + this.pet.name);
            }
            else if (ChangeTargetPatch.target == pet) {
                System.out.println("检测到" + this.pet.name + "未出战，取消目标");
                ChangeTargetPatch.target = null;
                ChangeTargetPatch.source.clear();

            }
        }
    }

    public void summonPokego() {
        am = InstanceMaker.getInstanceByClass(monsterClass);
        newPet = false;
        if (counter > 0 && monsterClass != null) {


            AbstractMonster monster = am;
            AbstractMonster monster1;
            //一层
            switch (am.id) {

                case "Hexaghost":
                    if(!config.getBool("Douququ"))
                    monster = new Hexaghost1();
                    break;
                //case"Cultist":monster=new Cultist(0,0);
                // break;
                case "Sentry":
                    monster = new Sentry1(0, 0);
                    break;
                case "Looter":
                    monster = new Looter1(0, 0);
                    break;
                case "GremlinTsundere":
                    monster = new GremlinTsundere1(0, 0);
                    break;
                case "SlimeBoss":
                    monster = new SlimeBoss1();
                    break;
                case "TheCollector":
                    monster = new TheCollector1();
                    break;
                case "BronzeAutomaton":
                    monster = new BronzeAutomaton1();

                    break;
                case "BronzeOrb":
                    monster = new BronzeOrb1(-300.0F, 200.0F, 0);
                    break;
                case "GremlinLeader":
                    monster = new GremlinLeader1();
                    break;
                case "Healer":
                    monster = new Healer1(0, 0);
                    break;
                case "SlaverBoss":
                    monster = new Taskmaster1(0, 0);
                    break;

                case "Centurion":
                    monster = new Centurion1(0, 0);
                    break;
                case "Chosen":
                    monster = new Chosen1(0, 0);
                    break;
                case "Snecko":
                    monster = new Snecko1(0, 0);
                    break;
                case "Exploder":
                    monster = new Exploder1(0, 0);
                    break;
                case "Repulsor":
                    monster = new Repulsor1(0, 0);
                    break;
                case "WrithingMass":
                    monster = new WrithingMass1();
                    break;
                case "Reptomancer":

                    monster = new Reptomancer1();/*
                for(int j=0;j<2;j++){
                    monster1 = new SnakeDagger1(0, 0);
                    monster1.drawY = AbstractDungeon.player.drawY + (int) site[slot].y + (float) Settings.WIDTH /10;
                    //monster1.drawX = AbstractDungeon.player.drawX - 175 + (int) site[slot].x-200;
                    monster1.drawX = 800;

                    monster1.flipHorizontal = true;
                    SummonHelper.summonMinion(monster1);
                }*/
                    break;
                case "Serpent":
                    monster = new SpireGrowth1();
                    break;

                case "Nemesis":
                    monster = new Nemesis1();
                    break;
                case "Donu":
                    monster = new Donu1();
                    break;
                case "Deca":
                    monster = new Deca1();
                    break;
                case "AwakenedOne":
                    monster = new AwakenedOne1(0, 0);
                    break;
                case "SpireShield":
                    monster = new SpireShield1();
                    break;
                case "SpireSpear":
                    monster = new SpireSpear1();
                    break;
                case "Orb Walker":
                    monster = new OrbWalker1(0, 0);
                    break;
                case "CorruptHeart":
                    monster = new CorruptHeart1();
                    break;
                case "TheGuardian":
                    monster = new TheGuardian1();
                    break;

            }


            //三层

            /*if (monster.getClass()== .class){
                monster=new BronzeAutomaton1();

            }

             */
            if (monster == null) {
                monster = new Cultist(0, 0);
                Invoker.setField(monster, "talky", true);
                System.out.println("抓不到的都变成咔咔");
            }

            ///monster.drawY = AbstractDungeon.player.drawY + (int) site[slot].y;
            ///monster.drawX = AbstractDungeon.player.drawX - 175 + (int) site[slot].x;
            pet = monster;
            monster.maxHealth = monster.currentHealth = counter;
            AbstractDungeon.actionManager.addToTop(new SummonMinionAction(monster));

            AbstractDungeon.actionManager.addToBottom(new ApplyEntryAction(this, monster));
            ScreenPartition.assignSequentialPosition(monster, null);
            BgmReplay(monster);
            System.out.println("本PokeGo的slot为" + slot);
            System.out.println("已在" + +monster.drawX + "," + monster.drawY + "生成" + this.pet.name);

        }
        else {
            monsterClass = null;
            pet = null;
        }
    }

    public void BgmReplay(AbstractMonster m) {

        switch (m.id) {
            case "Lagavulin":
            case "TheCollector":
            case "Champ":
            case "Hexaghost":
                music.fadeAll();
        }

    }

    @Override
    public boolean canSpawn() {
        return false;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if (this.monsterClass != null && this.counter > 0) {
            MinionHelper.clearMinions(AbstractDungeon.player);
            AbstractCard ac = new PokeGOGO(this);
            //ac.costForTurn=0;
            if (technology < 1) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ac, 1, true, true));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(ac));
            }

        }

    }

    @Override
    public void update() {
        super.update();
        //怪的血是带到下一局的
        if (pet != null && lastPetHp != pet.currentHealth && !newPet) {
            lastPetHp = pet.currentHealth;
            counter = lastPetHp;
            pet.flipHorizontal = true;
        }
    }

    @Override
    public PokeGoSave onSave() {
        return monsterClass == null ?
                null : new PokeGoSave(monsterClass.toString(),counter, slot, Entrylist, fanzhili);
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {/*
        if(monsterClass == SlaverBlue.class||monsterClass == SlaverRed.class||monsterClass == Taskmaster.class)
        {
            options.add(new plant(true));

        }*/
    }

    @Override
    public void onLoad(PokeGoSave s) {
        if (s != null && s.slot != null && s.className != null && s.fanzhili != null && s.counter != null) {
            try {
                monsterClass = Class.forName(s.className.substring(6, s.className.length()));
                slot = s.slot;
                Entrylist = s.entry;
                fanzhili = s.fanzhili;
                counter = s.counter;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void obtain() {
        InfoBlight.obtain(this);
    }

    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        InfoBlight.instanceObtain(this, callOnEquip);
    }

    @Override
    public void instantObtain() {
        InfoBlight.instanceObtain(this, true);
    }
}
