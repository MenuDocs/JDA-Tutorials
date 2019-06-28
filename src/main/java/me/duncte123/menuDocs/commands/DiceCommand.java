package me.duncte123.menuDocs.commands;

import me.duncte123.menuDocs.Constants;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiceCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        int sides = 6;
        int dices = 1;
        TextChannel channel = event.getChannel();

        if (!args.isEmpty()) {
            sides = Integer.parseInt(args.get(0));

            if (args.size() > 1) {
                dices = Integer.parseInt(args.get(1));
            }
        }

        if (sides > 100) {
            channel.sendMessage("The maximum sides is 100").queue();

            return;
        }

        if (dices > 20) {
            channel.sendMessage("The maximum dices is 20").queue();

            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder builder = new StringBuilder()
                .append("Results:\n");

        for (int d = 0; d < dices; d++) {
            builder.append("\uD83C\uDFB2 #")
                    .append(d)
                    .append(": **")
                    .append(random.nextInt(1, sides))
                    .append("**\n");
        }

        channel.sendMessage(builder.toString()).queue();
    }

    @Override
    public String getHelp() {
        return "Rolls a dice.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [sides] [dices]`";
    }

    @Override
    public String getInvoke() {
        return "dice";
    }
}
