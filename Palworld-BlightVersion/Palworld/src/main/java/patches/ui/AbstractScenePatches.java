package patches.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import com.megacrit.cardcrawl.vfx.campfire.CampfireBurningEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireEndingBurningEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static mymod.IsaacMod.config;


public class AbstractScenePatches
{
    public static final float scale = 1.0F;
    public static final float offSet_x = 0.0F;
    public static final float offSet_y = 0.0F;

static Texture a=loadImage("images/monsters/Campfire_bottom.png");
    static Texture b=loadImage("images/monsters/Campfire_City.png");

    @SpirePatch(clz = TheBottomScene.class, method = "renderCampfireRoom")
    public static class TheBottomScenePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheBottomScene scene, SpriteBatch sb) {




            if(!config.getBool("Background")) {
                AbstractScenePatches.renderCampfire(sb, b);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();



        }
    }



    @SpirePatch(clz = TheCityScene.class, method = "renderCampfireRoom")
    public static class TheCityScenePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheCityScene scene, SpriteBatch sb) {


            if(!config.getBool("Background")) {
                AbstractScenePatches.renderCampfire(sb, b);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();



        }
    }



    @SpirePatch(clz = TheBeyondScene.class, method = "renderCampfireRoom")
    public static class TheBeyondScenePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheBeyondScene scene, SpriteBatch sb) {

            if(!config.getBool("Background")) {

                AbstractScenePatches.renderCampfire(sb, b);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }



    @SpirePatch(clz = TheEndingScene.class, method = "renderCampfireRoom")
    public static class TheEndingScenePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheEndingScene scene, SpriteBatch sb) {


            if(!config.getBool("Background")) {
                AbstractScenePatches.renderCampfire(sb,b);
                return SpireReturn.Return();}
            return SpireReturn.Continue();



        }
    }


    private static void renderCampfire(SpriteBatch sb, Texture campfire) {
        if (campfire != null) {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);
            sb.draw(campfire, Settings.WIDTH / 2.0F - campfire.getWidth() / 2.0F + 0.0F * Settings.scale, Settings.HEIGHT / 2.0F - campfire
                    .getHeight() / 2.0F + 0.0F * Settings.scale, campfire
                    .getWidth() / 2.0F, campfire.getHeight() / 2.0F, campfire.getWidth(), campfire.getHeight(), 1.0F * Settings.scale, 1.0F * Settings.scale, 0.0F, 0, 0, campfire
                    .getWidth(), campfire.getHeight(), false, false);
        }
    }




    @SpirePatch(clz = CampfireBurningEffect.class, method = "update")
    public static class CampfireBurningEffectPatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CampfireBurningEffect effect) {

                effect.isDone = true;


            return SpireReturn.Return();
        }
    }



    @SpirePatch(clz = CampfireEndingBurningEffect.class, method = "update")
    public static class CampfireEndingBurningEffectPatch
    {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CampfireEndingBurningEffect effect) {

                effect.isDone = true;


            return SpireReturn.Return();
        }
    }
}
