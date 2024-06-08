package com.example.mancala.entity;

public class Pit {

    private Integer amount;
    private Boolean isEmpty;

    public Pit(Integer amount) {
        this.amount = amount;
        isEmpty = false;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void add() {
        amount++;
        isEmpty = false;
    }

    public Integer take(){
        Integer preValue = amount;
        amount = 0;
        isEmpty = true;
        return preValue;
    }

    @Override
    public String toString() {
        return "{" +
                amount +
                '}';
    }
}
