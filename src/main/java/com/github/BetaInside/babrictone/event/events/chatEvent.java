package com.github.BetaInside.babrictone.event.events;

import com.github.BetaInside.babrictone.event.event;

public class chatEvent extends event {

    public static String message;

    public chatEvent(String message)
    {
        this.message = message;
    }

    public static String message()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
