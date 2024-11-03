package utils;

import java.io.Serializable;
import java.util.ArrayList;

public class PokeGophoneSave implements Serializable{
    public Boolean planted;
    public Integer technology;
    public Integer counter;

    public PokeGophoneSave(boolean planted,Integer counter, Integer technology) {
        this.planted=planted;
        this.counter = counter;
        this.technology = technology;

    }
}
