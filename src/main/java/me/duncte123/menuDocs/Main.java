package me.duncte123.menuDocs;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.duncte123.menuDocs.config.Config;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class Main {

    private final Random random = new Random();

    private Main() throws IOException {
        Config config = new Config(new File("botconfig.json"));
        CommandManager commandManager = new CommandManager(random);
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        WebUtils.setUserAgent(); // Set your own user agent as string
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(getRandomColor())
                        .setFooter("{MenuDocs}", null)
                        .setTimestamp(Instant.now())
        );

        try {
            logger.info("Booting");
            new DefaultShardManagerBuilder()
                    .setToken(config.getString("token"))
                    .setShardsTotal(2)
                    .setGame(Game.streaming("Subscribe to MenuDocs", "https://twitch.tv/duncte123"))
                    .addEventListeners(listener)
                    .build();
            logger.info("Running");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

}
