package com.github.BetaInside.babrictone.command.commands;

import com.github.BetaInside.babrictone.command.command;
import com.github.BetaInside.babrictone.util.chatUtil;
import com.github.BetaInside.babrictone.util.fontColorUtil;

import static com.github.BetaInside.babrictone.babrictone.commands;

public class helpCommand extends command {

    public helpCommand() {

        super("help", false, "View all commands");

    }

    public void onEnable() {

        chatUtil.addMessage("All babrictone commands: ");

        for(command c : commands) {
            chatUtil.addMessage(c.name + fontColorUtil.DARK_GRAY+ " - " + c.description);
        }

        enabled = false;

    }
}
