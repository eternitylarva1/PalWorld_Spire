package Option;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.PokeGo;
import relics.lineTwo.InfoBlight;

import java.util.ArrayList;

public class get_pokego {
    public static int dingwei = 0;
    public static PokeGo pg;
    public static ArrayList<PokeGo> list = new ArrayList<>();

    public get_pokego() {

    }

    public static PokeGo get_pokego() {
        AbstractBlight blight = AbstractDungeon.player.blights.get(dingwei);
        if (blight instanceof InfoBlight)
            pg = (PokeGo) ((InfoBlight) blight).relic;
        else
            pg = null;
        return pg;
    }

    public static int canwork_pokego() {


        return (int) InfoBlight.getAllRelics(PokeGo.class).stream()
                .filter(pokeGo -> pokeGo.monsterClass != null && pokeGo.counter != 0 && !pokeGo.hasworked).count();
    }

    public static PokeGo getfirstcanwork_pokego() {
        list.clear();
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            if (pokeGo.monsterClass != null && pokeGo.counter != 0 && !pokeGo.hasworked) {
                list.add(pokeGo);
            }
            pokeGo.updateDescription();
        });

        if (list.size() != 0) {
            pg = list.get(0);
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                PokeGo pokeGo = list.get(i);
                System.out.println("未工作的帕鲁 " + i + ": " + pokeGo.monsterClass.getName());
            }
            return pg;
        }
        else {
            return null;
        }
    }

    public static PokeGo get_pokego(int num) {
        list.clear();
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            if (pokeGo.monsterClass != null && pokeGo.counter != 0) {
                list.add(pokeGo);
            }
            pokeGo.updateDescription();
        });

        if (num <= list.size() - 1) {
            pg = list.get(num);
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                PokeGo pokeGo = list.get(i);
                System.out.println("Element " + i + ": " + pokeGo.monsterClass);
            }
            return pg;
        }
        else {
            return null;
        }
    }

    public static PokeGo relic(int num) {
        list.clear();
        pg = InfoBlight.getAllRelicsWithBlightIndex(PokeGo.class).getOrDefault(num,null);
        return pg;
    }

    public static PokeGo get_blank_pokego(int num) {
        list.clear();
        InfoBlight.getAllRelics(PokeGo.class).forEach(pokeGo -> {
            if (pokeGo.counter == 0) {
                list.add(pokeGo);
            }
            pokeGo.updateDescription();
        });

        if (!list.isEmpty()) {
            pg = list.get(num);
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                PokeGo pokeGo = list.get(i);
                System.out.println("Element " + i + ": " + pokeGo.monsterClass);
            }
            return pg;
        }
        else {
            return null;
        }
    }

}
