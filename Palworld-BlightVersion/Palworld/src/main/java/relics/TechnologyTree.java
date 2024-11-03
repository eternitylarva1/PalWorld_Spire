package relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import relics.abstracrt.ClickableRelic;
import relics.lineTwo.InfoBlight;
import com.megacrit.cardcrawl.characters.Ironclad;
import utils.InstanceMaker;
import utils.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static com.megacrit.cardcrawl.core.CardCrawlGame.playerName;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

//怪物蛋
public class TechnologyTree extends ClickableRelic {
    public static final String ID = "TechnologyTree";
    public static final String IMG = "images/relics/BlankCard.png";
    public static final String DESCRIPTION = "科技树";
    public int HP = 0;
    public int fa;
    public int ma;
    String[] a = {"工作狂"};
    private boolean isValid = false;

    public TechnologyTree() {
        super("TechnologyTree", new Texture(Gdx.files.internal("images/relics/BlankCard.png")), RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 0;

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + "给你的第一个帕鲁加" + 1 + "词条";
    }

    public AbstractRelic makeCopy() {
        return new TechnologyTree();
    }

    //右键开大


    @Override
    public void onEnterRoom(AbstractRoom room) {


    }


    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void onRightClick() {
/*
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            pokeGo.Entrylist.clear();
            pokeGo.Entrylist.addAll(Arrays.asList(a));
        });*/
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            pokeGo.counter=0;
        });
        Invoker.invoke(player,"loadAnimation","images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);;
        player.state.setAnimation(0, "Idle", true);
        Invoker.setField(topPanel,"title", Defect.NAMES[0]);
        Invoker.setField(player,"energyOrb",new EnergyOrbBlue());
        commonCardPool.clear();
        uncommonCardPool.clear();
        rareCardPool.clear();
        srcCommonCardPool.clear();
        srcUncommonCardPool.clear();
        srcRareCardPool.clear();
        ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        CardLibrary.addBlueCards(tmpPool);
        Iterator var4 = tmpPool.iterator();

        AbstractCard c;
        while(var4.hasNext()) {
            c = (AbstractCard)var4.next();
            switch (c.rarity) {
                case COMMON:
                    commonCardPool.addToTop(c);
                    srcCommonCardPool.addToTop(c.makeCopy());
                    break;
                case UNCOMMON:
                    uncommonCardPool.addToTop(c);
                    srcUncommonCardPool.addToTop(c.makeCopy());
                    break;
                case RARE:
                    rareCardPool.addToTop(c);
                    srcRareCardPool.addToTop(c.makeCopy());
                    break;
                case CURSE:
                    curseCardPool.addToTop(c);
                    break;
                default:
                  System.out.println("Unspecified rarity: " + c.rarity.name() + " when creating pools! AbstractDungeon: Line 827");
            }

    }}

    @Override
    public void update() {
        super.update();

    }
}