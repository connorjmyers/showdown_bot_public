package Showdown.Players.Stats;

public class Stat {

    private int value;
    private String name;

    public Stat(String name) {
        this.value = 0;
        this.name = name;
    }

    public Stat(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increaseValue(int add) {
        this.value = this.value + add;
    }

    public String getName() {
        return name;
    }
}
