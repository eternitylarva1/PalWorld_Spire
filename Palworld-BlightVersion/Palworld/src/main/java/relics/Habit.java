package relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import evepower.FeelNoPainPower1;
import evepower.FireBreathingPower1;
import evepower.RupturePower1;
import relics.abstracrt.ClickableRelic;
import relics.lineTwo.InfoBlight;

import java.util.Iterator;

public class Habit  extends ClickableRelic {
    public static final String ID = "Habit";
    public static final String IMG = "images/relics/Habit.png";
    public static final String DESCRIPTION = "斗蛐蛐修改器";

    public Habit() {
        super("Habit", new Texture(Gdx.files.internal("images/relics/Habit.png")), RelicTier.SPECIAL, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Habit();
    }

    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            pokeGo.Entrylist.clear();
            pokeGo.Entrylist.add("");
        });

    }
    @Override
    public void onRightClick() {

        PokeGophone.technology=2;
        Iterator var1;
        AbstractMonster m;
        if(AbstractDungeon.getCurrRoom()instanceof MonsterRoom){
        var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var1.hasNext()) {
            m = (AbstractMonster)var1.next();
            if (!m.isDying && !m.isEscaping) {
               switch(m.id)
               {
                   case"Sentry":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new FeelNoPainPower1(m,4)));
                       break;
                   case"Chosen":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new FeelNoPainPower1(m,4)));
                       break;
                   case"Hexaghost":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new FireBreathingPower1(m, 3), 3));
                       break;
                   case"Nemesis":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new FireBreathingPower1(m, 5), 5));
                       break;
                   case"SlaverBoss":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RupturePower1(m, 3)));
                       break;
                   case"SlimeBoss":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RupturePower1(m, 1)));
                       break;
                   case"BookOfStabbing":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RupturePower1(m, 1)));
                       break;
                   case"AwakenedOne":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RupturePower1(m, 3)));
                       break;
                   case"Orb Walker":
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RupturePower1(m, 1))); break;
                   default:
                       break;
               }
            }
        }}
    }

    @Override
    public void onExhaust(AbstractCard card)
    {
        Iterator var1;
        AbstractMonster m;
        var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while(true) {
            if (!var1.hasNext()) {
                break ;
            }

            m = (AbstractMonster)var1.next();
            for(AbstractPower p:m.powers)
            {
                p.onExhaust(card);
            }
        }
    }
    @Override
    public boolean canSpawn()
    {
        return false;
    }
    @Override
    public void update() {
        super.update();
    }
}
