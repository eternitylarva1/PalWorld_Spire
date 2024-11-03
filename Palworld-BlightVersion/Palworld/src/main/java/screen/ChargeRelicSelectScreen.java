package screen;

import basemod.abstracts.CustomRelic;
import cards.Battery;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import mymod.IsaacMod;
import relics.*;

import relics.abstracrt.ChargeableRelic;
import relics.abstracrt.ClickableRelic;
import utils.Utils;

import java.util.Collection;

public class ChargeRelicSelectScreen extends RelicSelectScreen {

    public Battery battery = null;





    public ChargeRelicSelectScreen() {
        super();
    }

    public ChargeRelicSelectScreen(boolean canSkip) {
        super(canSkip);
    }

    public ChargeRelicSelectScreen(Collection<? extends AbstractRelic> c) {
        super(c);
    }

    public ChargeRelicSelectScreen(String bDesc, String title, String desc) {
        super(bDesc, title, desc);
    }

    public ChargeRelicSelectScreen(boolean canSkip, String bDesc, String title, String desc, Battery battery) {
        super(canSkip, bDesc, title, desc);
        this.battery = battery;
    }



    public ChargeRelicSelectScreen(boolean canSkip, String bDesc, String title, String desc) {
        super(canSkip, bDesc, title, desc);
    }






    public ChargeRelicSelectScreen(Collection<? extends AbstractRelic> c, boolean canSkip) {
        super(c, canSkip);
    }

    public ChargeRelicSelectScreen(Collection<? extends AbstractRelic> c, boolean canSkip, String bDesc, String title, String desc) {
        super(c, canSkip, bDesc, title, desc);
    }



    private int count;

    @Override
    protected void addRelics() {
        if (battery != null) {
            for (AbstractRelic r : AbstractDungeon.player.relics)
                if (r instanceof ChargeableRelic)
                    this.relics.add(r);
        }
    }

    @Override
    protected void afterSelected() {
        if (battery != null) {
            ChargeableRelic relic = (ChargeableRelic) (this.selectedRelic);
            relic.counter += relic.maxCharge;

            AbstractDungeon.player.reorganizeRelics();
        }
        }


    private void obtainRandomRelics(int num) {
        try {
            String[] rnd = Utils.getRandomRelics(IsaacMod.relics.size(), num);
            for (String relic : rnd) {
                Class c = Class.forName("relics." + relic);
                CustomRelic rndRelic = (CustomRelic) c.newInstance();
                this.relics.add(rndRelic.makeCopy());
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("你没找到类");
            e.printStackTrace();
        }
    }

    @Override
    protected void afterCanceled() {
    }

}