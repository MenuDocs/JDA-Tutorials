package me.duncte123.menuDocs;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Main {

    private Main() {
        try {
            System.out.println("Booting");
            new JDABuilder(AccountType.BOT)
                    .setToken(Secrets.TOKEN)
                    .setAudioEnabled(false)
                    .setGame(Game.streaming("Subscribe to MenuDocs", "https://twitch.tv/duncte123"))
                    .addEventListener(new Listener())
                    .build().awaitReady();
            System.out.println("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
