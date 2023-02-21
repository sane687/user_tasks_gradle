package com.alex.demo.model;

public enum EnumStatus {
    COMPLETED("completed"),
    IN_PROGRESS("in progress"),
    WAITING_FOR_APPROVE("waiting for approve"),
    EXPIRED("expired");

    public final String name;

    EnumStatus(String s) {
        this.name=s;
    }
    @Override
    public String toString(){
        return this.name;
    }
}

