package patches.action;

import basemod.BaseMod;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import helpers.MinionHelper;
import relics.PokeGo;
import relics.lineTwo.InfoBlight;

import java.util.Iterator;

import static mymod.IsaacMod.config;


@SpirePatch(
        cls = "com.megacrit.cardcrawl.actions.GameActionManager",
        method = "callEndOfTurnActions"
)
public class EndOfTurnActionPatch {
    public EndOfTurnActionPatch() {
    }

    public static void Postfix(GameActionManager callEndOfTurnActions) {
        BaseMod.logger.info("----------- Minion Before Attacking --------------");

        Iterator var1 = MinionHelper.getMinionMonsters().iterator();
        while(var1.hasNext()) {
            AbstractMonster monster=(AbstractMonster )var1.next();
            if(!monster.isDying&&!monster.escaped&&!monster.isDead)
            {
                //尝试修复死后塞牌问题
                monster.takeTurn();
                InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
                    if(pokeGo.pet==monster)
                    {
                        if (pokeGo.Entrylist.contains("鲁莽"))
                        {
                            monster.damage(new DamageInfo(monster,5));
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster,monster,new StrengthPower(monster,5)));
                        }
                    }
                });

                monster.applyStartOfTurnPowers();
                AbstractPower p = monster.getPower("Poison");
                if(p!=null) {
                    p.flash();
                    monster.damage(new DamageInfo(null, p.amount, DamageInfo.DamageType.THORNS));
                    --p.amount;

                }
                monster.applyTurnPowers();
                monster.applyEndOfTurnTriggers();
                if(AbstractDungeon.player.hasPower("Together"))
                {
                    AbstractPower ap=AbstractDungeon.player.getPower("Together");
                    ap.flash();
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(ap.amount));
                }
                monster.powers.forEach(
                        AbstractPower::atEndOfRound


                );

                if(config.getBool("Douququ")) {

                    for (AbstractMonster at : AbstractDungeon.getMonsters().monsters) {
                        if (at.hasPower(TimeWarpPower.POWER_ID)) {
                            AbstractPower ap = at.getPower(TimeWarpPower.POWER_ID);
                            ap.onSpecificTrigger();

                        }
                        ;

                    }

                }

            }

        }
        if(MinionHelper.getaliveMinions()>0) {
            if (AbstractDungeon.player.hasPower("Together")) {
                AbstractPower ap = AbstractDungeon.player.getPower("Together");
                ap.flash();
                AbstractDungeon.actionManager.addToBottom(new RetainCardsAction(AbstractDungeon.player, MinionHelper.getaliveMinions()));
            }
        }
    }

}
