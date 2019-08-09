package me.duncte123.menuDocs.commands;

import com.fasterxml.jackson.databind.JsonNode;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class MemeCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        WebUtils.ins.getJSONObject("https://apis.duncte123.me/meme").async((json) -> {
            JsonNode data = json.get("data");
            String url = data.get("image").asText();
            MessageEmbed embed = EmbedUtils.embedImage(url)
                    .setTitle(data.get("title").asText(), data.get("url").asText())
                    .build();
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
