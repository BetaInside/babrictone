package com.github.BetaInside.babrictone.command;

public class command {

    public String name;
    public boolean enabled;
    public String description;

    public command(String name, boolean enabled, String description) {

        this.name = name;
        this.enabled = enabled;
        this.description = description;

    }

    public void enable() {
        enabled = true;
        onEnable();
    }

    public void onEnable() {

    }

}
