package me.duncte123.menuDocs;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.duncte123.menuDocs.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
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
        EventWaiter waiter = new EventWaiter();
        CommandManager commandManager = new CommandManager(waiter);
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
                    .setActivity(Activity.streaming("Subscribe to MenuDocs", "https://twitch.tv/duncte123"))
                    .addEventListeners(waiter, listener)
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
