package me.duncte123.menuDocs.commands;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Random;

public class MemeCommand implements ICommand {

    private final Random random;

    public MemeCommand(Random random) {
        this.random = random;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        WebUtils.ins.getJSONObject("https://api-to.get-a.life/meme").async((json) -> {

            if (random.nextInt(2) > 0) {
                String url = json.getString("url");
                MessageEmbed embed = EmbedUtils.embedImage(url);
                //TODO: Make a permission check to see if the bot can send embeds if not, send plain text
                event.getChannel().sendMessage(embed).queue();
                return;
            }

            String text = json.getString("text");
            MessageEmbed embed = EmbedUtils.embedMessage(text);
            //TODO: Make a permission check to see if the bot can send embeds if not, send plain text
            event.getChannel().sendMessage(embed).queue();
        });
    }

    @Override
    public String getHelp() {
        return "Shows you a random meme.";
    }

    @Override
    public String getInvoke() {
        return "meme";
    }
}
