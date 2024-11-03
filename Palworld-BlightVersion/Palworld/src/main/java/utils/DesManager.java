package utils;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.UndoAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Equilibrium;
import com.megacrit.cardcrawl.cards.blue.MachineLearning;
import com.megacrit.cardcrawl.cards.colorless.MasterOfStrategy;
import com.megacrit.cardcrawl.cards.green.Outmaneuver;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.cards.purple.Fasting;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SlaverRed;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import com.megacrit.cardcrawl.powers.watcher.StudyPower;
import com.megacrit.cardcrawl.powers.watcher.WaveOfTheHandPower;
import com.megacrit.cardcrawl.vfx.combat.FastingEffect;
import helpers.MinionHelper;
import powers.chaofeng;

import java.util.*;

import static com.megacrit.cardcrawl.core.CardCrawlGame.music;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static mymod.IsaacMod.config;

public class DesManager {
        public  static void ApplyEntry(AbstractMonster m,String Entry)
    {
        switch (Entry) {
            case "笨手笨脚":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new WeakPower(m,99,true)));
                System.out.println("笨手笨脚的讨论");
                break;

            case "弱不禁风":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new VulnerablePower(m,99,true)));
                System.out.println("弱不禁风的讨论");
                break;

            case "消极主义":
                System.out.println("消极主义的讨论");
                break;

            case "自残倾向":
                System.out.println("自残倾向的讨论");
                break;

            case "骨质疏松":
                System.out.println("骨质疏松的讨论");
                break;

            case "偷懒成瘾":

                System.out.println("偷懒成瘾的讨论");
                break;

            case "胆小怕事":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new StrengthPower(m,-3)));
                System.out.println("胆小怕事的讨论");
                break;

            case "自我毁灭":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new FadingPower(m,6)));
                System.out.println("自我毁灭的讨论");
                break;

            case "害群之马":
                Iterator var1 = MinionHelper.getMinionMonsters().iterator();
                while(var1.hasNext()) {
                    AbstractMonster monster=(AbstractMonster )var1.next();
                    if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,new StrengthPower(monster,-1)));
                    }
                }
                System.out.println("害群之马的讨论");
                break;

            case "大费物":
                System.out.println("大费物的讨论");
                break;

            case "精神扭曲":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new FocusPower(player,-1)));
                System.out.println("精神扭曲的讨论");
                break;

            case "拥挤":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new DexterityPower(player,-2)));
                System.out.println("拥挤的讨论");
                break;

            case "租赁":
                player.loseGold(15);
                System.out.println("租赁的讨论");
                break;

            case "反复无常":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new ShiftingPower(m)));
                System.out.println("反复无常的讨论");
                break;

            case "素食主义者":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new EnergyDownPower(player, 1, true)));
                System.out.println("素食主义者的讨论");
                break;

            case "被诅咒":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new HexPower(player, 1)));
                System.out.println("被诅咒的讨论");
                break;

            case "啦啦队":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new StrengthPower(player, 2)));
                System.out.println("啦啦队的讨论");
                break;

            case "运动健将":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new DexterityPower(player, 2)));
                System.out.println("运动健将的讨论");
                break;

            case "科学家":
                System.out.println("科学家的讨论");
                break;

            case "突袭指挥官":
                Iterator var2 = MinionHelper.getMinionMonsters().iterator();
                while(var2.hasNext()) {
                    AbstractMonster monster=(AbstractMonster )var2.next();
                    if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,new StrengthPower(monster,1)));
                    }
                }
                System.out.println("突袭指挥官的讨论");
                break;

            case "鲁莽":
                System.out.println("鲁莽的讨论");
                break;

            case "铁壁军师":
                Iterator var3 = MinionHelper.getMinionMonsters().iterator();
                while(var3.hasNext()) {
                    AbstractMonster monster=(AbstractMonster )var3.next();
                    if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                    {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(monster,monster,5));
                    }
                }
                System.out.println("铁壁军师的讨论");
                break;

            case "神速":
                System.out.println("神速的讨论");
                break;

            case "传说":
                System.out.println("传说的讨论");
                break;

            case "勇敢":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new StrengthPower(m,3)));
                System.out.println("勇敢的讨论");
                break;

            case "嘲讽":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new chaofeng(m,10)));
                System.out.println("嘲讽的讨论");
                break;

            case "虔诚":
                System.out.println("虔诚的讨论");
                break;

            case "创造":
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
                c.setCostForTurn(0);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
                System.out.println("创造的讨论");
                break;

            case "威猛":
                System.out.println(AbstractDungeon.getMonsters().monsters.toString());
                for(AbstractMonster m1:AbstractDungeon.getMonsters().monsters)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m1,m,new VulnerablePower(m1,3,true)));
                }

                System.out.println("威猛的讨论");
                break;

            case "受虐狂":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new AngryPower(m,3)));

                System.out.println("受虐狂的讨论");
                break;

            case "精神集中":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new FocusPower(player,1)));
                System.out.println("精神集中的讨论");
                break;

            case "带刺":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new ThornsPower(m,1)));
                System.out.println("带刺的讨论");
                break;

            case "人工制品":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new ArtifactPower(m,1)));
                System.out.println("人工制品的讨论");
                break;

            case "漂浮":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new FlightPower(m,2)));
                System.out.println("漂浮的讨论");
                break;

            case "铜墙铁壁":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new BarricadePower(m)));
                System.out.println("铜墙铁壁的讨论");
                break;

            case "生长":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new RegenerateMonsterPower(m,5)));
                System.out.println("生长的讨论");
                break;
            case "多汗体质":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new HeatsinkPower(player,1)));
                System.out.println("多汗体质的讨论");
                break;

            case "邪教":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new RitualPower(m,1,true)));
                System.out.println("邪教的讨论");
                break;

            case "柔软":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new MalleablePower(m,3)));
                System.out.println("柔软的讨论");
                break;

            case "好奇宝宝":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new CuriosityPower(m,1)));
                System.out.println("好奇宝宝的讨论");
                break;

            case "堕落":
                System.out.println("堕落的讨论");
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new CorruptionPower(m)));
                break;

            case "文化人":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new PenNibPower(player,1)));
                System.out.println("文化人的讨论");
                break;

            case "友好":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new HelloPower(player,1)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new WaveOfTheHandPower(player,1)));
                System.out.println("友好的讨论");
                break;

            case "考古学家":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Exhume(),1));
                System.out.println("考古学家的讨论");
                break;

            case "虐待狂":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new SadisticPower(player,1)));
                System.out.println("虐待狂的讨论");
                break;

            case "运筹帷幄":
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new MasterOfStrategy()));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Outmaneuver()));
                System.out.println("运筹帷幄的讨论");
                break;

            case "自爆步兵":
                System.out.println("自爆步兵的讨论");
                break;

            case "拾荒者":
                System.out.println("拾荒者的讨论");
                break;

            case "窃贼":
                System.out.println("窃贼的讨论");
                break;

            case "嗑药":
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                System.out.println("嗑药的讨论");
                break;

            case "平衡大师":

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new EquilibriumPower(player,1)));
                System.out.println("平衡大师的讨论");
                break;

            case "洁癖":
                AbstractDungeon.actionManager.addToBottom(new ExhaustAction(3, false, true, true));
                System.out.println("洁癖的讨论");
                break;

            case "心理学家":
                InputHelper.moveCursorToNeutralPosition();
                ArrayList<AbstractCard> stanceChoices = new ArrayList();
                stanceChoices.add(new ChooseWrath());
                stanceChoices.add(new ChooseCalm());
                AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(stanceChoices));
                System.out.println("心理学家的讨论");
                break;

            case "铁匠":
                AbstractDungeon.actionManager.addToBottom(new UpgradeRandomCardAction());
                System.out.println("铁匠的讨论");
                break;

            case "学霸":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction( player,player,new DrawPower(player,1),1));
                System.out.println("学霸的讨论");
                break;

            case "好事成双":

                System.out.println("好事成双的讨论");
                break;

            case "疑虑":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction( player,player,new WeakPower(player,2,false),1));
                System.out.println("疑虑的讨论");
                break;

            case "傲慢":

                System.out.println("傲慢的讨论");
                break;

            case "哲理化":
                for(AbstractMonster m1:AbstractDungeon.getCurrRoom().monsters.monsters)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m1,m1,new StrengthPower(m1,1)));
                }

                System.out.println("哲理化的讨论");
                break;

            case "盲人":
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player,new WeakPower(player,1,false)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new WeakPower(m,1,false)));
                Iterator var4 = MinionHelper.getMinionMonsters().iterator();
                while(var4.hasNext()) {
                    AbstractMonster monster=(AbstractMonster )var4.next();
                    if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,monster,new WeakPower(monster,1,true)));
                    }
                }
                System.out.println("盲人的讨论");
                break;
            default:
                System.out.println("未知情况");
                break;
        }
    }
    public static String getrandomEntry()
    {
        String entry="";
        String[] strings = {
                "笨手笨脚", "弱不禁风", "消极主义", "自残倾向", "骨质疏松",
                 "胆小怕事", "自我毁灭", "害群之马", "大费物",
                "精神扭曲", "拥挤", "租赁", "反复无常", "素食主义者",
                "被诅咒", "啦啦队", "运动健将", "科学家",
                "突袭指挥官",  "铁壁军师",  "传说",
                "勇敢", "嘲讽", "虔诚", "创造",
                "受虐狂", "精神集中", "带刺", "人工制品", "漂浮",
                "铜墙铁壁", "生长","多汗体质", "邪教", "柔软", "好奇宝宝", "堕落", "文化人", "友好", "考古学家",
                "虐待狂", "运筹帷幄", "窃贼", "嗑药", "平衡大师",
                "洁癖", "心理学家", "铁匠", "学霸", "好事成双", "疑虑",  "盲人","工作狂"
        };
        if(config.getBool("gengduo"))
        {
            String[] additionalStrings = {"哲理化", "威猛", "神速", "鲁莽"};
            strings = Arrays.copyOf(strings, strings.length + additionalStrings.length);
            System.arraycopy(additionalStrings, 0, strings, strings.length - additionalStrings.length, additionalStrings.length);

        }
       // "工作狂","偷懒成瘾","神速","鲁莽","自爆步兵","拾荒者",  暂时没时装
        // 从数组中随机选择一个字符串
        int index = AbstractDungeon.miscRng.random(strings.length-1);
       entry=strings[index];

        return entry;
    }
    public static String getEntry(String  key)
    {
        HashMap<String, String> stringToAttributeMap  = new HashMap<>();
        stringToAttributeMap.put("笨手笨脚", "开局获得99层虚弱");
        stringToAttributeMap.put("弱不禁风", "开局获得99层易伤");
        stringToAttributeMap.put("消极主义", "进入火堆时，有50%概率视为已经行动过");
        stringToAttributeMap.put("自残倾向", "战斗结束时，-6血");
        stringToAttributeMap.put("骨质疏松", "被捉到时，血量减少30%");
        stringToAttributeMap.put("偷懒成瘾", "在火堆，默认睡觉");
        stringToAttributeMap.put("胆小怕事", "开局-3力");
        stringToAttributeMap.put("自我毁灭", "消逝6");
        stringToAttributeMap.put("害群之马", "被召唤时，所有帕鲁-1力量");
        stringToAttributeMap.put("大费物", "需要两费才能召唤");
        stringToAttributeMap.put("精神扭曲", "被召唤时，玩家-1集中");
        stringToAttributeMap.put("拥挤", "被召唤时，玩家-2敏捷");
        stringToAttributeMap.put("租赁", "被召唤时，-15块钱");
        stringToAttributeMap.put("反复无常", "获得变化（收到伤害减少力量）");
        stringToAttributeMap.put("素食主义者", "玩家获得一层斋戒");
        stringToAttributeMap.put("被诅咒", "自带一层邪咒");
        stringToAttributeMap.put("工作狂", "可以在火堆工作两次");
        stringToAttributeMap.put("啦啦队", "被召唤时，玩家+2力量");
        stringToAttributeMap.put("运动健将", "被召唤时，玩家+2敏捷");
        stringToAttributeMap.put("科学家", "被捕捉时，增加一级科研等级");
        stringToAttributeMap.put("突袭指挥官", "被召唤时，群体+1力量");
        stringToAttributeMap.put("鲁莽", "每次行动时失去5血，+10力量(分两次获得)");
        stringToAttributeMap.put("铁壁军师", "被召唤时，群体+5护甲");
        stringToAttributeMap.put("神速", "被召唤时，立即行动一次");
        stringToAttributeMap.put("传说", "被捕获时，+100%最大生命");
        stringToAttributeMap.put("勇敢", "战斗开始时，+3力");
        stringToAttributeMap.put("嘲讽", "战斗开始时，获得10层嘲讽");
        stringToAttributeMap.put("虔诚", "每回合，玩家获得一层真言");
        stringToAttributeMap.put("创造", "被召唤时，玩家获得一张本角色随机能力牌");
        stringToAttributeMap.put("威猛", "被召唤时，所有敌人获得3层易伤");
        stringToAttributeMap.put("受虐狂", "获得3层生气");
        stringToAttributeMap.put("精神集中", "被召唤时，玩家获得一层集中");
        stringToAttributeMap.put("带刺", "获得一层荆棘");
        stringToAttributeMap.put("人工制品", "获得一层人工制品");
        stringToAttributeMap.put("漂浮", "自带2层飞行");
        stringToAttributeMap.put("铜墙铁壁", "被召唤时，给予玩家一层壁垒");
        stringToAttributeMap.put("生长", "获得5层再生");
        stringToAttributeMap.put("多汗体质", "被召唤时，玩家获得一层散热");
        stringToAttributeMap.put("邪教", "被召唤时，获得一层仪式");
        stringToAttributeMap.put("柔软", "被召唤时，获得3层柔韧");
        stringToAttributeMap.put("好奇宝宝", "被召唤时，获得一层好奇");
        stringToAttributeMap.put("堕落", "被召唤时，玩家获得一层腐化");
        stringToAttributeMap.put("文化人", "被召唤时，玩家获得一层钢笔尖");
        stringToAttributeMap.put("友好", "被召唤时，玩家获得一层摆手，一层你好世界");
        stringToAttributeMap.put("考古学家", "被召唤时，将一张发掘加入弃牌堆");
        stringToAttributeMap.put("虐待狂", "被召唤时，获得一层残虐天性");
        stringToAttributeMap.put("运筹帷幄", "被召唤时，将一张战略大师，抢占先机加入你的手牌");
        stringToAttributeMap.put("自爆步兵", "3回合后自爆，对所有敌人造成自己血量上限的伤害");
        stringToAttributeMap.put("拾荒者", "玩家可以在火堆拾荒（不消耗火堆，获得一个随机普通遗物）");
        stringToAttributeMap.put("窃贼", "战斗结束时，额外奖励20块");
        stringToAttributeMap.put("嗑药", "被召唤时，玩家获得1费，抽2");
        stringToAttributeMap.put("平衡大师", "被召唤时，玩家获得一层均衡");
        stringToAttributeMap.put("洁癖", "被召唤时，消耗至多3张手牌");
        stringToAttributeMap.put("心理学家", "被召唤时，选择：进入平静/进入愤怒");
        stringToAttributeMap.put("铁匠", "回合开始时，随机升级一张手牌");
        stringToAttributeMap.put("学霸", "回合开始时，玩家额外抽一");
        stringToAttributeMap.put("好事成双", "被召唤时，额外召唤一只分身(被捕获时，血量减半)");
        stringToAttributeMap.put("疑虑", "被召唤时玩家获得2层虚弱");
        stringToAttributeMap.put("傲慢", "回合结束时，将一张本牌的复制品塞到牌堆顶");
        stringToAttributeMap.put("哲理化", "被召唤时，所有敌人+1力量");
        stringToAttributeMap.put("盲人", "被召唤时，所有友军获得1层虚弱");
        return stringToAttributeMap.get(key);
    }
    public static String getDescription(String  key)
    {
        String mode="";
        switch (key)
        {
            case"SlaverRed":
                mode="丢网能让敌人一次攻击打空";
                break;
            case"Champ":
                mode="自带9层嘲讽";
                break;
            case"TimeEater":
                mode="多段伤害为aoe,重击减少抽牌改为额外抽牌，塞粘液改为塞进化";
                break;
            case"Orb Walker":
                mode="被捕获时，战斗奖励加一个稀有遗物,塞灼烧改为塞燃烧";
                break;
            case"TheGuardian":mode="每次蹲下，获得4荆棘，旋风斩为aoe伤害";
                break;
            case"Mugger":mode="这个帕鲁击杀时，获得25金币";
                break;
            case"Hexaghost":mode="出场时，获得2/3层火焰吐息";
                break;
            case"JawWorm":mode="击杀时，获得20血量上限，血量上限达到300时，进化成巨口";
                break;
            case"Loose":mode="召唤不需要费用";
                break;
            case"Sentry":mode="出场时，玩家获得2层不惧疼痛";
                break;
            case"Looter":mode="每次攻击，战斗奖励增加10金币（）";
                break;
            case"TheCollector":mode="出场时，召唤一个火炬头，火炬头为aoe伤害";
                break;
            case"GremlinTsundere":mode="出场时，所有帕鲁获得护甲，只会给玩家护甲";
                break;
            case"BronzeAutomaton":mode="出场时，召唤一个铜球,铜球会凝滞稀有度最低的牌";
                break;
            case"GremlinLeader":
                mode="不会召唤";
                break;
            case"SlaverBoss":
                mode="出场时，召唤一只奴隶，获得一层霸凌；每次抽到状态牌时，+1力";
                break;
            case"Chosen":
                mode="召唤时，获得一层黑拥";
                break;
            case"Lagavulin":
                mode="不会睡觉，开局直接吸";

                break;
            case"Byrd":
                mode="出场时，所有异鸟获得1力量";
                break;
            case"Reptomancer":
                mode="出场时，召唤一个匕首";
                break;
            case"Exploder":
                mode="自爆后不会死";
                break;
            case"Repulsor":
                mode="塞牌时，给玩家两层进化";
                break;
            case"Maw":
                mode="进入火堆时，获得50金币";
                break;
            case"Darkling":
                mode="交配时，获得一个帕鲁球";
                break;
            case"SpireGrowth":
                mode="两回合上一次缠绕";
                break;
            case"FuzzyLouseDefensive":mode="召唤不需要费用";
                break;
            case"FuzzyLouseNormal":mode="召唤不需要费用";
                break;
            case"WrithingMass":
                mode="塞寄生改为给玩家加血量上限";
                break;
            case"AwakenedOne":
                mode="半血觉醒，觉醒后塞奇迹";
                break;
            case"Nemesis":
                mode="塞牌改为给予敌方所有怪物10层自燃";
                break;
            case"AcidSlime_L":
                mode="召唤时，获得1层进化";
                break;
            case"AcidSlime_M":
                mode="召唤时，获得1层进化";
                break;
            case"SpikeSlime_L":
                mode="召唤时，获得1层进化";
                break;
            case"SpikeSlime_M":
                mode="召唤时，获得1层进化";
                break;
            case"SpikeSlime_S":
                mode="召唤时，获得1层进化";
                break;
            case"SlimeBoss":
                mode="召唤时，获得两层进化，第一次攻击之后会一直攻击";
                break;
            case"CorruptHeart":
                mode="获得两层凌迟,第二回合塞的牌改为：永生不死，力大无穷，名利双收，奇迹，洞见";
                break;
            case"Leech":
                mode="攻击不受虚弱，力量，易伤影响。连击为aoe伤害。第4回合会使用龙陨星（陨石打击）";
                break;

            default:
                mode="无特殊效果";
        }
        if(key.contains("downfall"))
        {
            mode="被召唤时，立即结束回合。每回合只会打第一张牌";
        }
        return mode;
    }
}
