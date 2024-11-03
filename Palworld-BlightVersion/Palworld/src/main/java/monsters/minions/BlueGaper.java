package monsters.minions;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import monsters.Hush;
import monsters.Intent.Move;
import powers.chaofeng;

public class BlueGaper extends CustomMonster {

    public static final String NAME;
    public static final String MOVE_NAME;

    private int attackDmg = 4;
    private int multiple = 3;


    public int index;

    public BlueGaper(float x, float y) {
        super(NAME, "BlueGaper", 30, -8.0F, 10.0F, 110.0F, 80, (String)null, x, y);
        this.setHp(30);
        this.img = new Texture(Gdx.files.internal("images/monsters/linpian.png"));
        this.damage.add(new DamageInfo(this, attackDmg));
        this.setMove(MOVE_NAME, (byte) Move.MULATTACK.id, Intent.ATTACK, attackDmg, multiple, true);
    }

    @Override
    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
//        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        for (int i = 0; i < multiple; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) this.damage.get(0), AttackEffect.BLUNT_LIGHT));
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void usePreBattleAction() {
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MinionPower(this)));
        for(AbstractMonster am1:AbstractDungeon.getMonsters().monsters)
        {
            if(!am1.isDeadOrEscaped())
            {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(am1, am1, new ThornsPower(am1, 4), 4));


            }
        }
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new ThornsPower(this, 4), 4));
    }

    @Override
    protected void getMove(int i) {
        this.setMove(MOVE_NAME, (byte) Move.MULATTACK.id, Intent.ATTACK, attackDmg, multiple, true);
    }

    private boolean addPower = false;

    @Override
    public void update() {
        super.update();
        if (!addPower) {

            addPower = true;

        }
    }

    @Override
    public void die() {
        for(AbstractMonster am1:AbstractDungeon.getMonsters().monsters)
        {
            if(!am1.isDeadOrEscaped())
            {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(am1, am1, "Thorns", 4));
            }
        }
        super.die();
    }

    static {
        if (Settings.language == Settings.GameLanguage.ZHS) {
            NAME = "鳞片器灵";
            MOVE_NAME = "戳刺";
        } else {
            NAME = "Scaly";
            MOVE_NAME = "attack attack attack";
        }
    }
}
