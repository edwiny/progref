package com.myexamples;

public class Main {

    public static void main(String[] args) {
        BasicCameraApp cameraApp = new BasicCameraApp();


        cameraApp.setShareBehaviour(new ShareBehaviourImplSocialMedia());

        cameraApp.take();
        cameraApp.edit();
        cameraApp.doShare();
    }
}
