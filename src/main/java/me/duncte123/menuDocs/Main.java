package me.duncte123.menuDocs;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {

    private Main() {

        CommandManager commandManager = new CommandManager();
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        try {
            logger.info("Booting");
            new JDABuilder(AccountType.BOT)
                    .setToken(Secrets.TOKEN)
                    .setAudioEnabled(false)
                    .setGame(Game.streaming("Subscribe to MenuDocs", "https://twitch.tv/duncte123"))
                    .addEventListener(listener)
                    .build().awaitReady();
            logger.info("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
