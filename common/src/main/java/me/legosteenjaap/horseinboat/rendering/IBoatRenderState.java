package me.legosteenjaap.horseinboat.rendering;

public interface IBoatRenderState {
    boolean horseinboat$isInBoat();
    boolean horseinboat$boatHas2Passengers();
    boolean horseinboat$boatIsChestBoat();
    void horseinboat$setBoatInfo(boolean inBoat, boolean has2Passengers, boolean isChestBoat);
}
