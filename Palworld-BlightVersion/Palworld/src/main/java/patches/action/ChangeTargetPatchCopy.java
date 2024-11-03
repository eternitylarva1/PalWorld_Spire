package patches.action;

/*
public class ChangeTargetPatchCopy {
    public ChangeTargetPatchCopy() {
    }

    public static AbstractCreature target = null;
    public static List<AbstractCreature> source = new ArrayList<>();

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
            if (!AbstractDungeon.overlayMenu.endTurnButton.enabled) {
                //忽略反伤
                if (info.type == THORNS) {
                    return;
                }

                //怪物不打人，打宠物

                if (AbstractDungeon.player.hasRelic(PokeGo.ID)) {
                    for (AbstractRelic relic : AbstractDungeon.player.relics) {

                        if (relic instanceof PokeGo) {
                            PokeGo pokeGo = (PokeGo) relic;

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
                                            if (ChangeTargetPatchCopy.target != null && target == AbstractDungeon.player && source.contains(info.owner))
                                            {
                                            action.target = ChangeTargetPatchCopy.target;
                                            System.out.println("已将"+info.owner.name+"的伤害的目标改成"+action.target.name);
                                            }
                                        }
                                        else if(AbstractDungeon.player.hasRelic(D4.ID))
                                        {
                                            if (ChangeTargetPatchCopy.target != null && target == AbstractDungeon.player && source.contains(info.owner))
                                            {action.target = ChangeTargetPatchCopy.target;
                                            System.out.println("检测到有护盾，已将"+info.owner.name+"的伤害的目标改成"+action.target.name);
                                            }
                                        }
                                    else if(!(getMinionMonsters().contains(info.owner)||info.owner==AbstractDungeon.player)){
                                        System.out.println("概率失败，未成功转移目标");
                                        action.target=AbstractDungeon.player;

                                }
                            }
                        }
                    }
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
            if (!AbstractDungeon.overlayMenu.endTurnButton.enabled) {
                if (AbstractDungeon.player.hasRelic(PokeGo.ID)) {
                    for (AbstractRelic relic : AbstractDungeon.player.relics) {
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
                                if (action instanceof ApplyPowerAction )
                                {
                                    if (source.getClass()==GremlinLeader1.class||source.getClass()== Healer.class||source.getClass()== Deca.class||source.getClass()== Donu.class)
                                    {
                                        if(AbstractDungeon.aiRng.randomBoolean(0.5F))
                                        {
                                            action.target = source;

                                        }
                                        else{
                                            action.target = AbstractDungeon.player;
                                        }

                                    }

                                }

                                //指定特定action给自己
                                if (action instanceof GainBlockAction || action instanceof HealAction||action instanceof GainBlockRandomMonsterAction) {

                                    if(AbstractDungeon.aiRng.randomBoolean(0.5F))
                                    {
                                        action.target = source;
                                    }
                                    else{
                                        action.target = AbstractDungeon.player;
                                    }

                                }



                                //不指定就跳过
                                if (action.target == null) {
                                    try {
                                        AbstractDungeon.actionManager.actions.removeIf(a-> a instanceof MakeTempCardInDiscardAction||a instanceof MakeTempCardInDrawPileAction||a instanceof MakeTempCardAtBottomOfDeckAction);
                                        Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                        method.setAccessible(true);
                                        if(action.actionType!= AbstractGameAction.ActionType.CARD_MANIPULATION) {
                                        method.invoke(action);}

                                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            //其他怪debuff到宠物上
                            else if
                            (pokeGo.pet != null && pokeGo.pet.currentHealth > 0 && target == AbstractDungeon.player && ChangeTargetPatchCopy.source.contains(source)) {
                                if(AbstractDungeon.aiRng.randomBoolean(0.5F))
                                {
                                action.target = pokeGo.pet;}
                                else
                                {

                                }
                            }
                        }
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
            if (!AbstractDungeon.overlayMenu.endTurnButton.enabled) {
                if (source instanceof SpireShield && target == AbstractDungeon.player) {
                    return;
                }
                if (AbstractDungeon.player.hasRelic(PokeGo.ID)) {
                    for (AbstractRelic relic : AbstractDungeon.player.relics) {
                        if (relic instanceof PokeGo) {
                            PokeGo pokeGo = (PokeGo) relic;
                            AbstractDungeon.actionManager.actions.removeIf(a-> a instanceof MakeTempCardInDiscardAction||a instanceof MakeTempCardInDrawPileAction||a instanceof MakeTempCardAtBottomOfDeckAction);
                            if (source != null && source == pokeGo.pet) {
                                action.target = pokeGo.target;
                                if (source == target && !MinionHelper.hasMinion(pokeGo.target)) {
                                    action.target = source;
                                }


                                if (action.target == null) {
                                    try {
                                        AbstractDungeon.actionManager.actions.removeIf(a-> a instanceof MakeTempCardInDiscardAction||a instanceof MakeTempCardInDrawPileAction||a instanceof MakeTempCardAtBottomOfDeckAction);
                                        Method method = AbstractGameAction.class.getDeclaredMethod("tickDuration");
                                        method.setAccessible(true);
                                        method.invoke(action);
                                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (pokeGo.pet != null && pokeGo.pet.currentHealth > 0 && ChangeTargetPatchCopy.source.contains(source) && target == AbstractDungeon.player) {
                                action.target = pokeGo.pet;
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
            if (power.owner != action.target && power.owner != AbstractDungeon.player) {
                power.owner = action.target;
            }


        }
    }


}
*/