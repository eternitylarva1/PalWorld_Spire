package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relics.PokeGo;
import relics.lineTwo.InfoBlight;
import utils.ScreenPartition;

import static helpers.SummonHelper.summonMinion;
import static relics.PokeGophone.p;
import static relics.PokeGophone.technology;

public class SummonMinionAction extends AbstractGameAction {
    public AbstractMonster monster1;
    private boolean place=false;

    public SummonMinionAction(AbstractMonster monster) {
        monster1 = monster;
    }
    public SummonMinionAction(AbstractMonster monster,boolean place) {
        monster1 = monster;
        this.place=place;
    }
    @Override
    public void update() {
        if (monster1 != null) {
            monster1.flipHorizontal = true;
            if(this.place)
            {
                ScreenPartition.assignSequentialPosition(monster1, null);
            }
            summonMinion(monster1);
            monster1.createIntent();
            if (technology >= 7) {
                monster1.takeTurn();
            }
            InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
                if (pokeGo.pet == monster1) {
                    if (pokeGo.Entrylist.contains("神速")) {
                        monster1.takeTurn();
                    }
                }
            });
        }
        this.isDone = true;
    }

}
