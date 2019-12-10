package com.example.collections.support;

public class Person {
    public static enum Sex { MALE, FEMALE };

    protected String name;
    protected Sex sex;

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public Person(String name, Sex sex) {
        this.name = name;
        this.sex  = sex;
    }

    public String toString() {
        return(this.name + " (" + this.sex + ")");

    }
}
