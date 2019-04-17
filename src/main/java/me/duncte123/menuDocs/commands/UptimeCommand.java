package me.duncte123.menuDocs.commands;

import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class UptimeCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime();
        long uptimeInSeconds = uptime / 1000;
        long numberOfHours = uptimeInSeconds / (60 * 60);
        long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
        long numberOfSeconds = uptimeInSeconds % 60;

        event.getChannel().sendMessageFormat(
                "My uptime is `%s hours, %s minutes, %s seconds`",
                numberOfHours, numberOfMinutes, numberOfSeconds
        ).queue();
    }

    @Override
    public String getHelp() {
        return "Shows the current uptime of the bot.";
    }

    @Override
    public String getInvoke() {
        return "uptime";
    }
}
