package relics;

import Option.get_pokego;
import Option.zhijie;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class NineVolt extends CustomRelic {
    public static final String ID = "NineVolt";
    public static final String IMG = "images/relics/NineVolt1.png";
    public static final String DESCRIPTION = "功德";


    public NineVolt() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.SPECIAL, LandingSound.CLINK
        );

        this.counter=1;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new NineVolt();
    }

    @Override
    public void atBattleStart()
        {
            float i = 0.01f*this.counter;
            if(AbstractDungeon.aiRng.randomBoolean(i))
            {
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)
            {
                AbstractDungeon.actionManager.addToTop(new EscapeAction(m));
            }
            };
        }
    @Override
    public void update() {
        super.update();
    }


}
