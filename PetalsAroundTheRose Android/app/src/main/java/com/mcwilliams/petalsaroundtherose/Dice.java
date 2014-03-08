package com.mcwilliams.petalsaroundtherose;

/**
 * Created by joshuamcwilliams on 2/24/14.
 */
public class Dice {
    private String diceName;
    private int diceValue;
    private int diceImageResource;
    private int petalsAroundTheRose;

    public Dice(String diceName, int diceValue, int diceImageResource){
        this.diceName = diceName;
        this.diceValue = diceValue;
        this.diceImageResource = diceImageResource;
    }

    public Dice(String diceName, int diceValue, int diceImageResource, int petalsAroundTheRose){
        this.diceName = diceName;
        this.diceValue = diceValue;
        this.diceImageResource = diceImageResource;
        this.petalsAroundTheRose = petalsAroundTheRose;
    }

    public int getPetalsAroundTheRose() {
        return petalsAroundTheRose;
    }

    public void setPetalsAroundTheRose(int petalsAroundTheRose) {
        this.petalsAroundTheRose = petalsAroundTheRose;
    }

    public String getDiceName() {
        return diceName;
    }

    public void setDiceName(String diceName) {
        this.diceName = diceName;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }

    public int getDiceImageResource() {
        return diceImageResource;
    }

    public void setDiceImageResource(int diceImageResource) {
        this.diceImageResource = diceImageResource;
    }
}
