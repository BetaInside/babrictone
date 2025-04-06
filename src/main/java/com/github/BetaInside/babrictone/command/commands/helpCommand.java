package com.github.BetaInside.babrictone.command.commands;

import com.github.BetaInside.babrictone.command.command;
import com.github.BetaInside.babrictone.util.chatUtil;
import com.github.BetaInside.babrictone.util.fontColorUtil;

import static com.github.BetaInside.babrictone.babrictone.commands;

public class helpCommand extends command {

    public helpCommand() {

        super("help", false, "View all commands", "help <command>");

    }

    public void onEnable(String[] args) {

        if(args.length == 0) {

            chatUtil.addMessage("All babrictone commands: ");

            for(command c : commands) {
                chatUtil.addMessage(c.name + fontColorUtil.DARK_GRAY+ " - " + c.description);
            }

            enabled = false;

        } else {

            for(command c : commands) {
                if (c.name.equalsIgnoreCase(args[0])) {
                    chatUtil.addMessage(c.name.substring(0, 1).toUpperCase() + c.name.substring(1) + " command: ");
                    chatUtil.addMessage("Description " + fontColorUtil.DARK_GRAY + " : " + c.description);
                    chatUtil.addMessage("Usage" + fontColorUtil.DARK_GRAY + " : " + c.usage);
                }
            }

        }

    }
}
