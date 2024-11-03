package patches.player;

import cards.tempCards.PokeBall;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateJumpAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.powers.AbstractPower;
import evepower.DamageAllEnemiesAction1;
import helpers.MinionHelper;
import patches.monster.AbstractMonsterAddFieldPatch;
import patches.monster.AbstractMonsterClickField;
import relics.D6;
import relics.PokeBall_BluePrint;
import utils.DesManager;
import utils.Invoker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
import static patches.action.ChangeTargetPatch.last;

public class PlayerMethodPatches {
    public PlayerMethodPatches() {
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "updatePowers"
    )
    public static class UpdatePowersPatch {
        public UpdatePowersPatch() {
        }

        public static void Postfix(AbstractCreature _instance) {
            if (_instance instanceof AbstractPlayer) {
                (PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                    monster.updatePowers();
                });
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "updateSingleTargetInput")
    public static class UpdateSingleTargetInputPatch {
        @SpireInsertPatch(rloc = 19) //1039-1025
        public static void Insert(final AbstractPlayer player, @ByRef AbstractMonster[] ___hoveredMonster, AbstractCard ___hoveredCard) {

                for (final AbstractMonster m : MinionHelper.getMinionMonsters() ) {
                    m.hb.update();
                    if(true){
                        if (m.hb.hovered && !m.isDying && !m.isEscaping &&
                                m.currentHealth > 0 && !(___hoveredCard instanceof PokeBall)) {
                            ___hoveredMonster[0] = m;
                            break;
                        }
                    }

                }


        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "applyStartOfTurnPowers"
    )
    public static class ApplyStartOfTurnPowersPatch {
        public ApplyStartOfTurnPowersPatch() {
        }

        public static void Postfix(AbstractCreature _instance) {
            if (_instance instanceof AbstractPlayer) {
                ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                    monster.applyStartOfTurnPowers();
                    if(!monster.hasPower("Barricade")&&!monster.hasPower("Blur")){
                        monster.loseBlock();
                    }
                });
                PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player).showIntent();
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.monsters.MonsterGroup", method = "showIntent")
    public static class ShowIntentPatch {
        @SpirePostfixPatch
        public static void Postfix(final MonsterGroup group) {
            Iterator var1 = PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player).monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                m.createIntent();
            }

        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "applyStartOfTurnPostDrawPowers"
    )
    public static class ApplyStartOfTurnPostDrawPowersPatch {
        public ApplyStartOfTurnPostDrawPowersPatch() {
        }

        public static void Postfix(AbstractCreature _instance) {
            if (_instance instanceof AbstractPlayer) {
                ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                    monster.applyStartOfTurnPostDrawPowers();
                });
            }

        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "preBattlePrep"
    )
    public static class PreBattlePatch {
        public PreBattlePatch() {
        }

        public static void Postfix(AbstractPlayer _instance) {
//            BasePlayerMinionHelper.changeMaxMinionAmount(_instance, 100);//(Integer) PlayerAddFieldsPatch.f_baseMinions.get(_instance)
           MinionHelper.clearMinions(_instance);
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "update"
    )
    public static class UpdatePatch {
        public UpdatePatch() {
        }

        public static void Postfix(AbstractPlayer player) {
            if (AbstractPlayerClickField.RclickStart.get(player) && InputHelper.justReleasedClickRight) {
                if (player.hb.hovered) {
                    AbstractPlayerClickField.Rclick.set(player, true);
                }
                AbstractPlayerClickField.RclickStart.set(player, false);
            }
            if ((player.hb != null) && ((player.hb.hovered) && (InputHelper.justClickedRight))) {
                AbstractPlayerClickField.RclickStart.set(player, true);
            }
            if (AbstractPlayerClickField.Rclick.get(player)) {
                AbstractPlayerClickField.Rclick.set(player, false);
                //右击动作
                AbstractDungeon.actionManager.addToBottom(new AnimateJumpAction(player));
            }

            MonsterGroup minions = PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player);

            switch (AbstractDungeon.getCurrRoom().phase) {
                case COMBAT:
                    if (MinionHelper.hasMinions(AbstractDungeon.player)) {
                        minions.update();
                        for(AbstractMonster m:MinionHelper.getMinions().monsters)
                        {
                            if(AbstractMonsterClickField.LclickStart.get(m)&&InputHelper.isMouseDown)
                            {
                                m.drawX=InputHelper.mX;
                                m.drawY=InputHelper.mY;
                                AbstractMonsterClickField.LclickStart.set(m,false);

                            }
                        }
                    }
            }

        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "renderPlayerBattleUi"
    )
    public static class RenderPatch {
        public RenderPatch() {
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _instance, SpriteBatch sb) {
            MonsterGroup minions = PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player);
            switch (AbstractDungeon.getCurrRoom().phase) {
                case COMBAT:
                    if (MinionHelper.hasMinions(AbstractDungeon.player)) {
                        minions.render(sb);
                    }
            }
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.AbstractMonster",
            method = "renderTip"
    )
    public static class RenderTipPatch {
        @SpireInsertPatch(
                loc = 308
        )
        public static SpireReturn Insert(AbstractCreature __instance,SpriteBatch sb,ArrayList ___tips) throws NoSuchFieldException, IllegalAccessException {
            if(!MinionHelper.getMinions().monsters.contains(__instance)){
                if (last) {
                    ___tips.add(new PowerTip("攻击对象", "本回合它将攻击帕鲁(前提是有帕鲁)"));

                } else {

                    ___tips.add(new PowerTip("攻击对象", "本回合它将攻击玩家"));
                }
                AbstractMonster m= (AbstractMonster) __instance;
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
                String  rawDescription="当前被捕获概率"+chance*100+"% ";
                ___tips.add(new PowerTip("作为帕鲁时", DesManager.getDescription(__instance.id)+" NL "+rawDescription));

            }

            return SpireReturn.Continue();
        }
    }





    /*
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.monsters.AbstractMonster",
            method = "renderTip"

    )

    public static class renderTip {




        @SpireInsertPatch(loc = 318)
        public static SpireReturn Insert(AbstractMonster _instance, SpriteBatch sb) throws NoSuchFieldException, IllegalAccessException {
            if(last)
            {
                Field field = AbstractMonster.class.getDeclaredField("tips");
                field.setAccessible(true);
                ArrayList<PowerTip> powerTips= (ArrayList<PowerTip>) field.get(_instance);
                powerTips.add(new PowerTip("意图","本回合它将攻击玩家"));
            }
            else{
                Field field = AbstractMonster.class.getDeclaredField("tips");
                field.setAccessible(true);
                ArrayList<PowerTip> powerTips= (ArrayList<PowerTip>) field.get(_instance);
                powerTips.add(new PowerTip("意图","本回合它将攻击帕鲁"));
            }
            return SpireReturn.Continue();
        }*/



}
