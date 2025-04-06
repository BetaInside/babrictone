package com.github.BetaInside.babrictone;

import com.github.BetaInside.babrictone.command.command;
import com.github.BetaInside.babrictone.command.commands.helpCommand;
import com.github.BetaInside.babrictone.event.event;
import com.github.BetaInside.babrictone.event.events.chatEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

public class babrictone implements ModInitializer{

    public static final Logger LOGGER = LoggerFactory.getLogger("babrictone");

    public static CopyOnWriteArrayList<command> commands = new CopyOnWriteArrayList<command>();

    @Override
    public void onInitialize() {

            LOGGER.info("Initializing " + nameVer);
            registerCommands();

    }

    public static void onEvent(event e) {

        if((e instanceof chatEvent)) {
            String message = chatEvent.message();
            String command = message.substring(1);

            if(message.startsWith(";")) {
                for(command c : commands) {
                    if (command.equalsIgnoreCase(c.name)) {
                        c.enable();
                    }
                }

                e.setCancelled(true);
            }

        }

    }

    public void registerCommands() {

        commands.add(new helpCommand());

    }

    public static Minecraft mc = (Minecraft) FabricLoaderImpl.INSTANCE.getGameInstance();
    public static String ver = "0.00";
    public static String nameVer = "babrictone " + ver;

}
