package com.myexamples;

//problem: add new sharing option via social media
public abstract class PhoneCameraAppBeforeRefactoringWithPattern {
    public void take() {
        System.out.println("Base class take");
    }
    public abstract void edit();
    public void save() {
        System.out.println("Base class save");
    }
    public void share() {
        System.out.println("Base class share");
    }
}
