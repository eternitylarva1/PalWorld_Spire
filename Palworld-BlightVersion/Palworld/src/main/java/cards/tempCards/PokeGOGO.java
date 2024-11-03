package cards.tempCards;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.powers.*;
import patches.player.PlayerAddFieldsPatch;
import relics.CYSDX;
import relics.D4;
import relics.PokeGo;
import utils.InstanceMaker;

import static relics.PokeGophone.technology;

public class PokeGOGO extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "PokeGOGO";
    public static final String NAME;
    public static final String DESCRIPTION;
    public  int cost;
    private PokeGo pokeGo;

    public PokeGOGO(PokeGo pokeGo) {


        super("PokeGOGO", NAME +  "" + "!", "images/cards/PokeBall.png", 1, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
        this.isEthereal = true;
        this.pokeGo = pokeGo;
        if(pokeGo.monsterClass!=null) {
            AbstractMonster ad = InstanceMaker.getInstanceByClass(pokeGo.monsterClass);
            rawDescription = "召唤" + ad.name;

            initializeDescription();
            if (pokeGo.monsterClass == LouseDefensive.class || pokeGo.monsterClass == LouseNormal.class || pokeGo.monsterClass == AcidSlime_S.class || pokeGo.monsterClass == SpikeSlime_S.class) {
                System.out.println("检测到帕鲁为虱虫/小史莱姆，费用-1");
                this.upgradeBaseCost(0);

            }
            if (technology >= 6) {
                this.upgrade();
            }
            if (pokeGo.Entrylist.contains("大费物")) {
                this.costForTurn = 2;
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasRelic(D4.ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PlatedArmorPower(AbstractDungeon.player,2),2));
        }
        if(AbstractDungeon.player.hasRelic(CYSDX.ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1),1));
        }
        this.pokeGo.chuzhan=true;
        if(pokeGo.monsterClass!=null&&pokeGo.counter>0) {
            pokeGo.summonPokego();

            AbstractMonster am1 = InstanceMaker.getInstanceByClass(pokeGo.monsterClass);
            if (am1.id.contains("downfall")) {
                AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
            }

            if (pokeGo.Entrylist.contains("好事成双")) {
                pokeGo.summonPokego();
            }
            switch (am1.id) {
                case "Chosen":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarkEmbracePower(AbstractDungeon.player, 1)));
                    break;
                case "CorruptHeart":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThousandCutsPower(AbstractDungeon.player, 2)));
                    break;
                case "Byrd":
                    BaseMod.logger.info("----------- Minion Before Attacking --------------");
                    ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                        if (!monster.isDying && !monster.escaped && !monster.isDead) {
                            //尝试修复死后塞牌问题
                            if (monster.id == "Byrd") {
                                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new StrengthPower(monster, 1)));
                            }
                        }
                    });
                    break;
                case "SlimeBoss":
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EvolvePower(AbstractDungeon.player, 1)));
                    break;


            }
            if(am1.id.contains("Slime"))
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EvolvePower(AbstractDungeon.player,1)));
            }
        }
    }

    public AbstractCard makeCopy() {
        return new PokeGOGO(pokeGo);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeBaseCost(0);
            this.costForTurn=0;
            this.upgradeName();
        }
    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
