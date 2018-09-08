package me.duncte123.menuDocs.objects;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface ICommand {

    void handle(List<String> args, GuildMessageReceivedEvent event);

    String getHelp();

    String getInvoke();

}
