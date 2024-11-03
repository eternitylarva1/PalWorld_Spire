package Option;



//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import relics.PokeGophone;

import static Option.get_pokego.canwork_pokego;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static relics.PokeGophone.technology;

public class keyan extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static  String[] TEXT1=
            {

                    "帕鲁球塞手牌而不是抽牌堆",
                    "解锁休憩，交配，种田和放生按钮",
                    "解锁帕鲁护盾，帕鲁休息时,最多恢复50血(本来为20)",
                    "解锁力量石像",
                    "解锁女黄蜂之杖",
                    "升级你的帕鲁球",
                    "被召唤出来立即行动一次",
                    "还没做",
                    "还没做",
                    "还没做",
            };
    public PokeGophone pgp= (PokeGophone) AbstractDungeon.player.getRelic(PokeGophone.ID);
    public int restcount=0;
    public Texture img2=loadImage("images/ui/campfire/keyan.png");
    public keyan(boolean active) {
        this.label = TEXT[0];


        this.usable = technology<=7;
        this.updateUsability(active);
        System.out.println("一共有"+canwork_pokego()+"可以工作的帕鲁");
    }

    public void updateUsability(boolean canUse) {
        if (technology>6)
        {
            this.usable=false;
        }
        this.description = "目前科研等级"+technology+",下一级解锁"+TEXT1[technology];
        this.img = img2;
    }

    public void useOption() {
        if (this.usable) {

            AbstractDungeon.effectList.add(new keyane());
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = new String[]{"科研"};

    }
}
