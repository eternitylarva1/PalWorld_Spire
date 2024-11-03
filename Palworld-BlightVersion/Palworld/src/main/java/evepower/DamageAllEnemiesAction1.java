package evepower;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import helpers.MinionHelper;
import patches.player.PlayerAddFieldsPatch;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static helpers.MinionHelper.getMinionMonsters;
import static helpers.MinionHelper.getaliveMinions;

public class DamageAllEnemiesAction1 extends AbstractGameAction {
    public int[] damage;
    private int baseDamage;
    private boolean firstFrame;
    private boolean utilizeBaseDamage;

    public DamageAllEnemiesAction1(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect, boolean isFast) {
        this.firstFrame = true;
        this.utilizeBaseDamage = false;
        this.source = source;
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;


    }

    public DamageAllEnemiesAction1(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this(source, amount, type, effect, false);
    }

    public DamageAllEnemiesAction1(AbstractPlayer player, int baseDamage, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this(player, (int[])null, type, effect, false);
        this.baseDamage = baseDamage;
        this.utilizeBaseDamage = true;
    }

    public void update() {
        int i;
        if (this.firstFrame) {
            boolean playedMusic = false;
            i = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            if (this.utilizeBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            }
            /*if(getaliveMinions()==0)
            {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.attackEffect, true));
            }*/
            ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                if(!monster.isDying&&!monster.escaped&&!monster.isDead)
                {
                    //尝试修复死后塞牌问题
                    if (true) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)monster).hb.cX, ((AbstractMonster)monster).hb.cY, this.attackEffect, true));
                    } else {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)monster).hb.cX, ((AbstractMonster)monster).hb.cY, this.attackEffect));
                    }

                }
            });


            this.firstFrame = false;
        }

        this.tickDuration();
        if (this.isDone) {
            Iterator var4 = AbstractDungeon.player.powers.iterator();

            while(var4.hasNext()) {
                AbstractPower p = (AbstractPower)var4.next();
                p.onDamageAllEnemies(this.damage);
            }

            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            List<AbstractMonster> livepet = getMinionMonsters().stream()
                    .filter(monster -> !monster.isDeadOrEscaped())
                    .collect(Collectors.toList());
            int m = livepet.size();
            /*if(m==0)
            {
               AbstractDungeon.player.damage(new DamageInfo(this.source, this.damage[0], this.damageType));
            }*/
            ((MonsterGroup) PlayerAddFieldsPatch.f_minions.get(AbstractDungeon.player)).monsters.forEach((monster) -> {
                if(!monster.isDying&&!monster.escaped&&!monster.isDead&&!monster.halfDead)
                {
                    //尝试修复死后塞牌问题
                    if (this.attackEffect == AttackEffect.POISON) {
                        ((AbstractMonster)monster).tint.color.set(Color.CHARTREUSE);
                        ((AbstractMonster)monster).tint.changeColor(Color.WHITE.cpy());
                    } else if (this.attackEffect == AttackEffect.FIRE) {
                        ((AbstractMonster)monster).tint.color.set(Color.RED);
                        ((AbstractMonster)monster).tint.changeColor(Color.WHITE.cpy());
                    }
                    ((AbstractMonster)monster).damage(new DamageInfo(this.source, this.damage[0], this.damageType));
                }
            });





            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }

    }
}
