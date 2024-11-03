package cards.tempCards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import helpers.MinionHelper;
import relics.D6;
import relics.PokeGo;
import utils.DesManager;
import utils.randommonster;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters;
import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
import static relics.PokeGophone.technology;


public class PokeBall extends CustomCard {
    private static final CardStrings cardStrings;
    public static final String ID = "PokeBall";
    public static final String NAME;
    public static final String DESCRIPTION;
    private  int s=0;

    private PokeGo pokeGo;


    public PokeBall(PokeGo pokeGo) {
        super("PokeBall", NAME, "images/cards/PokeBall.png", 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust = true;
        this.isEthereal = true;
        this.pokeGo = pokeGo;
         int j;int k = 0;
        j = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        for(int i = 0; i < j; ++i)
        {
            if(!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying && ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).currentHealth > 0 && !((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping)
            {
                ++this.s;
                k=i;
            }
        }
        if (this.s==1)
        {
            AbstractMonster m= getMonsters().monsters.get(k);
            if(m!=null)

            {

                Float chance= 1-(float)m.currentHealth/(float)m.maxHealth;
                if ((float)m.currentHealth/(float)m.maxHealth>0.2F)
                {
                    chance=0.0f;
                }

                if (m.type == BOSS) {
                    chance*=0.25f;

                }
                if (m.type == ELITE) {
                    chance*=0.5f;

                }
                if (AbstractDungeon.player.hasRelic(D6.ID)) {
                    chance=1.0f;

                }
                rawDescription="当前捕获概率"+chance*100+"% NL 此概率为获得球时的概率";
                initializeDescription();

            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        Float chance= 1-(float)m.currentHealth/(float)m.maxHealth;
        AbstractDungeon.actionManager.addToTop(new WaitAction(1.0F));
        Float chance2=chance*1.0f;
        if ((float)m.currentHealth/(float)m.maxHealth>0.2F)
        {
            chance=0.0f;
        }
        if (m == null || MinionHelper.hasMinion(m)) {
            return;
        }

        if (m.type == BOSS) {
            System.out.println("检测到Boss，概率减3/4");

            chance*=0.25f;

        }
        if (m.type == ELITE) {
            System.out.println("检测到精英，概率减半");
            if (!AbstractDungeon.player.hasRelic(D6.ID))
            {
            chance*=0.5f;
        }}
if(AbstractDungeon.player.hasRelic(D6.ID)){
    chance=1.0f;
}

        if (AbstractDungeon.aiRng.randomBoolean(chance)) {
            if (m instanceof Darkling) {
                for (AbstractMonster monster : getMonsters().monsters) {
                    if (monster instanceof Darkling && monster != m && !monster.halfDead) {
                        return;
                    }
                }
            }
            pokeGo.counter = m.maxHealth;

            pokeGo.newPet = true;
            m.currentHealth = 0;
            if (m instanceof Darkling) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));
            }
            if (m instanceof AwakenedOne) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));

            }
            m.die();
            m.hideHealthBar();
            if (m instanceof AwakenedOne) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));
            m.showHealthBar();
            }
            System.out.println("捕捉成功");

            pokeGo.monsterClass = m.getClass();
            pokeGo.Entrylist.clear();
            for (int i=0;i<AbstractDungeon.miscRng.random(1,3);i++) {
                String a=DesManager.getrandomEntry();
                if(!pokeGo.Entrylist.contains(a)) {
                    pokeGo.Entrylist.add(DesManager.getrandomEntry());
                }
            }

            if(pokeGo.Entrylist.contains("骨质疏松"))
            {
                pokeGo.counter-=0.3*pokeGo.counter;
            }
            if(pokeGo.Entrylist.contains("传说"))
            {
                pokeGo.counter+=pokeGo.counter;
            }
            if(pokeGo.Entrylist.contains("科学家"))
            {
                if(technology<=3)
                {
                    technology++;
                }
            }
            if(pokeGo.Entrylist.contains("好事成双"))
            {
                pokeGo.counter/=2;
            }
            this.pokeGo.updateDescription();
            switch(m.id)
                {
                    case"Orb Walker":
                        AbstractDungeon.getCurrRoom().addNoncampRelicToRewards(AbstractRelic.RelicTier.RARE);
                }
                pokeGo.fanzhili=randommonster.getKey(m.id);
                pokeGo.updateDescription();

        }

    }

    public AbstractCard makeCopy() {
        return new PokeBall(pokeGo);
    }

    @Override
    public void upgrade() {

        if(!this.upgraded){
            this.upgradeBaseCost(0);

        }

    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
