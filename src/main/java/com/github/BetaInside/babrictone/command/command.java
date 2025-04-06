package com.github.BetaInside.babrictone.command;

public class command {

    public String name;
    public boolean enabled;
    public String description;
    public String usage;

    public command(String name, boolean enabled, String description, String usage) {

        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.usage = usage;

    }

    public void enable(String[] args) {
        enabled = true;
        onEnable(args);
    }

    public void onEnable(String[] args) {

    }

}
