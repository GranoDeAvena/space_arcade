package com.mygdx.sparcade.model;

/**
 * Created by Polomin on 24.11.2014.
 */
public class Enemy extends Player {
    public String filename = new String("downres_step2.png");
    private boolean flag = true;

    public void logic() {
        if (this.bounds.y <= 416 && flag) {
            this.bounds.y++;
            if (this.bounds.y == 416)
                flag = false;
        }
        if (this.bounds.y >= 0 && !flag) {
            this.bounds.y--;
            if (this.bounds.y == 0)
                flag = true;
        }
    }
}
