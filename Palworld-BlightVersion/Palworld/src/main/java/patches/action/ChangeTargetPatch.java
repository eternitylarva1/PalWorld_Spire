package patches.action;

import actions.MonsterFeedAction;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import helpers.MinionHelper;
import powers.chaofeng;
import relics.*;
import relics.PokeGo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static helpers.MinionHelper.getMinionMonsters;
import static mymod.IsaacMod.config;


public class ChangeTargetPatch {
    public ChangeTargetPatch() {
    }

    public static AbstractCreature target = null;
    public static List<AbstractCreature> source = new ArrayList<>();
    public static boolean last=false;
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.actions.AbstractGameAction",
            method = "setValues",
            paramtypez = {AbstractCreature.class, DamageInfo.class}
    )
    public static class ChangeDamageTarget {
        public ChangeDamageTarget() {
        }

        public static void Postfix(AbstractGameAction action, AbstractCreature target, DamageInfo info) {
            //在怪物回合开始后

                //忽略反伤
                if (info.type == THORNS&&!Loader.isModLoadedOrSideloaded("downfall")) {
                    return;
                }
                if(info.owner!=null) {
                    if (info.owner.hasPower(EntanglePower.POWER_ID))
                    //怪物不打人，打宠物
                    {
                        action.target = null;
                        info.base=0;
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(info.owner, info.owner, EntanglePower.POWER_ID));
                        action.isDone=true;
                        return;
                    }
                    if (player.hasRelic(PokeGo.ID)) {

                        List<AbstractMonster> livepet1 = getMinionMonsters().stream()
                                .filter(monster -> !monster.isDeadOrEscaped())
                                .collect(Collectors.toList());


                        if (livepet1.size() > 0) {
                            if (livepet1.contains(info.owner)) {
                                AbstractMonster Mt = getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);

                                for (AbstractMonster am1 : getMonsters().monsters) {
                                    if (!am1.isDeadOrEscaped()) {
                                        for (AbstractPower p : am1.powers) {
                                            if (p instanceof chaofeng) {
                                                Mt = am1;
                                    /*if(action.target .hasPower("Vulnerable")){
                                        System.out.println("检测到易伤");
                                        info.output= (int) (1.5*info.base);
                                    }*/

                                            }
                                        }
                                    }
                                }
                                action.target = Mt;
                                if (Mt != null && !Mt.isDeadOrEscaped()&&!Mt.halfDead) {
                                    info.isModified = false;
                                    info.applyPowers(info.owner, Mt);
                                    if (Objects.equals(info.owner.id, "JawWorm") && !Mt.hasPower("Minion")) {
                                        /*if(info.owner.hasPower(VulnerablePower.POWER_ID))
                                        {
                                            info.output*=1.5;
                                        }*/

                                        AbstractDungeon.actionManager.addToBottom(new MonsterFeedAction(Mt, info, 20));
                                        if ((((AbstractMonster) action.target).isDying || action.target.currentHealth <= 0) && !action.target.halfDead) {
                                            info.owner.maxHealth += 20;
                                            info.owner.heal(20);
                                        }
                                        action.target = null;

                                    }
                                    if (Objects.equals(info.owner.id, "Mugger") && !Mt.hasPower("Minion")) {
                                        action.target.damage(info);
                                        if ((((AbstractMonster) action.target).isDying || action.target.currentHealth <= 0) && !action.target.halfDead) {
                                            player.gainGold(25);
                                        }
                                        action.target = null;

                                    }


                                    //带壳寄生怪
                                    if (action.target == null && action instanceof VampireDamageAction) {
                                        if (!Mt.isDeadOrEscaped()) {
                                            action.target = Mt;
                                        }

                                    }
                                    if (action.target == null) {
                                        try {
                                            Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                            method.setAccessible(true);

                                            method.invoke(action);

                                        } catch (NoSuchMethodException | InvocationTargetException |
                                                 IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        info.applyPowers(info.owner, action.target);
                                    }
                                } else {
                                    action.target = null;
                                }
                            }

                    /* for (AbstractRelic relic : AbstractDungeon.player.relics) {

                        if (relic instanceof PokeGo) {
                            PokeGo pokeGo = (
                                    PokeGo) relic;

                            //宠物打怪
                            if (info.owner == pokeGo.pet) {
                                action.target = Mt;
                                //带壳寄生怪
                                if (action.target == null && action instanceof VampireDamageAction) {
                                    action.target = Mt;
                                }
                                if (action.target == null) {
                                    try {
                                        Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                        method.setAccessible(true);

                                        method.invoke(action);

                                    } catch (NoSuchMethodException | InvocationTargetException |
                                             IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    info.applyPowers(pokeGo.pet, action.target);
                                }
                            }
                        }
                    }
*/


                            List<AbstractMonster> livepet = getMinionMonsters().stream()
                                    .filter(monster -> !monster.isDeadOrEscaped())
                                    .collect(Collectors.toList());
                            int m = livepet.size();
                            if (m > 0) {
                                int n = AbstractDungeon.aiRng.random(m - 1);
                                AbstractMonster monster = livepet.get(n);


                                if (info.owner instanceof AbstractMonster && !getMinionMonsters().contains(info.owner) && action.target == player) {

                                    if (player.hasRelic(PalHudun.ID)) {
                                        action.target = monster;

                                        info.isModified = false;
                                        info.applyPowers(info.owner, monster);
                                    /*if(action.target .hasPower("Vulnerable")){
                                        System.out.println("检测到易伤");
                                        info.output= (int) (1.5*info.base);
                                    }
*/

                                    } else if (last) {

                                        action.target = monster;
                                    /*if(action.target .hasPower("Vulnerable")){
                                        System.out.println("检测到易伤");
                                        info.output= (int) (1.5*info.base);
                                    }*/
                                        info.isModified = false;
                                        info.applyPowers(info.owner, monster);
                                    } else {

                                        info.isModified = false;
                                        info.applyPowers(info.owner, player);
                                        action.target = player;
                                    }
                                    for (AbstractMonster am1 : MinionHelper.getMinions().monsters) {
                                        if (!am1.isDeadOrEscaped()) {
                                            for (AbstractPower p : am1.powers) {
                                                if (p instanceof chaofeng) {
                                                    action.target = am1;
                                    /*if(action.target .hasPower("Vulnerable")){
                                        System.out.println("检测到易伤");
                                        info.output= (int) (1.5*info.base);
                                    }*/
                                                    info.isModified = false;
                                                    info.applyPowers(info.owner, am1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }





                    /*
                    for (AbstractRelic relic : AbstractDungeon.player.relics) {

                        if (relic instanceof PokeGo) {
                            PokeGo pokeGo = (
                                    PokeGo) relic;

                                //宠物打怪
                                if (info.owner == pokeGo.pet) {
                                    action.target = pokeGo.target;
                                    //带壳寄生怪
                                    if (action.target == null && action instanceof VampireDamageAction) {
                                        action.target = info.owner;
                                    }
                                    if (action.target == null) {
                                        try {
                                            Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                            method.setAccessible(true);

                                            method.invoke(action);

                                        } catch (NoSuchMethodException | InvocationTargetException |
                                                 IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        info.applyPowers(pokeGo.pet, action.target);
                                    }
                                }
                                else
                                    if(((PokeGo) relic).chuzhan) {
                                        if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                                            if (ChangeTargetPatch.target != null && target == AbstractDungeon.player && source.contains(info.owner))
                                            {
                                            action.target = ChangeTargetPatch.target;
                                            System.out.println("已将"+info.owner.name+"的伤害的目标改成"+action.target.name);
                                            }
                                        }
                                        else if(AbstractDungeon.player.hasRelic(D4.ID))
                                        {
                                            if (ChangeTargetPatch.target != null && target == AbstractDungeon.player && source.contains(info.owner))
                                            {action.target = ChangeTargetPatch.target;
                                            System.out.println("检测到有护盾，已将"+info.owner.name+"的伤害的目标改成"+action.target.name);
                                            }
                                        }
                                    else if(!(getMinionMonsters().contains(info.owner)||info.owner==AbstractDungeon.player)){
                                        System.out.println("概率失败，未成功转移目标");
                                        action.target=AbstractDungeon.player;

                                }
                            }
                        }
                    }*/
                        }
                    }
                }

        }

        @SpirePatch(
                cls = "com.megacrit.cardcrawl.actions.AbstractGameAction",
                method = "setValues",
                paramtypez = {AbstractCreature.class, AbstractCreature.class, int.class}
        )
        public static class ChangeBuffTarget {
            public ChangeBuffTarget() {
            }

            public static void Postfix(AbstractGameAction action, AbstractCreature target, AbstractCreature source, int amount) throws NoSuchFieldException {
                /*if (!AbstractDungeon.overlayMenu.endTurnButton.enabled) {*/
                    if (player.hasRelic(PokeGo.ID)) {
                        AbstractMonster Mt = getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
                        List<AbstractMonster> livepet1 = getMinionMonsters().stream()
                                .filter(monster -> !monster.isDeadOrEscaped())
                                .collect(Collectors.toList());
                        int m = livepet1.size();

                        if (livepet1.size() > 0) {
                            if (livepet1.contains(action.source)) {
                                if (action.source!=action.target){
                                    action.target = Mt;
                                }



                                //带壳寄生怪
                                if (action.target == null && action instanceof VampireDamageAction) {
                                    action.target = Mt;
                                }

                                if (action.target == null) {
                                    try {

                                        Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                        method.setAccessible(true);
                                        if (action.actionType != AbstractGameAction.ActionType.CARD_MANIPULATION) {
                                            method.invoke(action);
                                        }

                                    } catch (NoSuchMethodException | InvocationTargetException |
                                             IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else if (m>0&& target == player && (ChangeTargetPatch.source.contains(source)||action.source==null)) {
                                if (player.hasRelic(PalHudun.ID)) {
                                    int n = AbstractDungeon.aiRng.random(m - 1);
                                    AbstractMonster monster = livepet1.get(n);
                                    action.target = monster;

                                }
                                else if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {

                                    int n = AbstractDungeon.aiRng.random(m - 1);
                                    AbstractMonster monster = livepet1.get(n);
                                    action.target = monster;

                                } else {


                                }
                        /*for (AbstractRelic relic : AbstractDungeon.player.relics) {
                            if (relic instanceof PokeGo) {
                                PokeGo pokeGo = (PokeGo) relic;
                                //宠物的动作

                                if (source != null && source == pokeGo.pet) {
                                    //给怪上debuff
                                    action.target = pokeGo.target;
                                    //buff类动作不会给随从外的怪物
                                    if (source == target && !MinionHelper.hasMinion(pokeGo.target)) {
                                        action.target = source;
                                    }
                                    //指定特定action给自己
                                    //不指定就跳过
                                    if (action.target == null) {
                                        try {

                                            Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                            method.setAccessible(true);
                                            if (action.actionType != AbstractGameAction.ActionType.CARD_MANIPULATION) {
                                                method.invoke(action);
                                            }

                                        } catch (NoSuchMethodException | InvocationTargetException |
                                                 IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                //其他怪debuff到宠物上
                                else if
                                (pokeGo.pet != null && pokeGo.pet.currentHealth > 0 && target == AbstractDungeon.player && ChangeTargetPatch.source.contains(source)) {
                                    if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                                        action.target = pokeGo.pet;
                                    } else {

                                    }
                                }
                            }
                        }*/

                            }
                        }
                    }

            }
        }


        @SpirePatch(
                cls = "com.megacrit.cardcrawl.actions.AbstractGameAction",
                method = "setValues",
                paramtypez = {AbstractCreature.class, AbstractCreature.class}
        )
        public static class ChangeBuff2Target {
            public ChangeBuff2Target() {
            }

            public static void Postfix(AbstractGameAction action, AbstractCreature target, AbstractCreature source) throws NoSuchFieldException {
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                    if (player.hasRelic(PokeGo.ID)) {
                        AbstractMonster Mt = getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                        List<AbstractMonster> livepet1 = getMinionMonsters().stream()
                                .filter(monster -> !monster.isDeadOrEscaped())
                                .collect(Collectors.toList());
                        int m = livepet1.size();

                        if (livepet1.size() > 0) {
                            if (livepet1.contains(action.source)) {
                                if (action.source != action.target) {
                                    action.target = Mt;
                                }


                                //带壳寄生怪
                                if (action.target == null && action instanceof VampireDamageAction) {
                                    action.target = Mt;
                                }
                                if (action.target == null) {
                                    try {

                                        Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                        method.setAccessible(true);
                                        if (action.actionType != AbstractGameAction.ActionType.CARD_MANIPULATION) {
                                            method.invoke(action);
                                        }

                                    } catch (NoSuchMethodException | InvocationTargetException |
                                             IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (m > 0 && target == player && ChangeTargetPatch.source.contains(source)) {
                                if (player.hasRelic(PalHudun.ID)) {
                                    int n = AbstractDungeon.aiRng.random(m - 1);
                                    AbstractMonster monster = livepet1.get(n);
                                    action.target = monster;

                                } else if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {

                                    int n = AbstractDungeon.aiRng.random(m - 1);
                                    AbstractMonster monster = livepet1.get(n);
                                    action.target = monster;

                                } else {


                                }
                            }
                        }
                    }
                }
            }
        }

        @SpirePatch(
                cls = "com.megacrit.cardcrawl.actions.common.ApplyPowerAction",
                method = SpirePatch.CONSTRUCTOR,
                paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class}
        )
        public static class ChangeApplyBuffTarget {
            public ChangeApplyBuffTarget() {
            }

            public static void Postfix(ApplyPowerAction action, AbstractCreature target, AbstractCreature source, AbstractPower power, int n, boolean b, AbstractGameAction.AttackEffect e) {
                if (power.owner != action.target) {
                    power.owner = action.target;
                }


            }
        }

    }
}
