package cards.green;

import actions.ApplyEntryAction;
import actions.SummonMinionAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.FadingPower;
import helpers.MinionHelper;
import monsters.act1.*;
import monsters.act2.*;
import monsters.act3.*;
import monsters.act4.CorruptHeart1;
import monsters.act4.SpireShield1;
import monsters.act4.SpireSpear1;
import utils.InstanceMaker;
import utils.Invoker;
import utils.ScreenPartition;

public class MoFangZhe extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "MoFangZhe";
    public static final String NAME;// = "模仿者";
    public static final String DESCRIPTION;// = "创建一个目标的我方复制品，带有2层消逝 消耗";
    public static final String imgUrl = "images/cards/Pills.png";



    public MoFangZhe() {
        super(ID, NAME, (String) null, 2, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust=true;
        this.magicNumber=this.baseMagicNumber=3;

    }

    public  AbstractMonster summonPokego(AbstractMonster m) {
         AbstractMonster   am = InstanceMaker.getInstanceByClass(m.getClass());

            AbstractMonster monster = am;

            //一层
            switch (am.id) {

                case "Hexaghost":
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
                case "SpireGrowth":
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
            AbstractDungeon.actionManager.addToTop(new SummonMinionAction(monster));
            ScreenPartition.assignSequentialPosition(monster, null);
            return monster;
        }






    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster m1=this.summonPokego(m);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m1,m1,new FadingPower(m1,2)));



    }

    public AbstractCard makeCopy() {
        return new MoFangZhe();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

            this.upgradeBaseCost(1);
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    private static final String UPGRADE_DESCRIPTION ;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}
