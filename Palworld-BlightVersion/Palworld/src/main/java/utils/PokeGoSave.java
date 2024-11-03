package utils;

import java.io.Serializable;
import java.util.ArrayList;

public class PokeGoSave implements Serializable{
    public String className;
    public Integer slot;
    public  ArrayList<String> entry;
    public Integer fanzhili;
    public Integer counter;
    public PokeGoSave(String className, Integer counter, Integer slot, ArrayList<String> entry, int fanzhili) {
        this.className = className;
        this.slot = slot;
        this.entry=entry;
        this.fanzhili=fanzhili;
        this.counter = counter;
    }
}
