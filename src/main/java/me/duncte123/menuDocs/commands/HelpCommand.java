package me.duncte123.menuDocs.commands;

import me.duncte123.menuDocs.CommandManager;
import me.duncte123.menuDocs.Constants;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class HelpCommand implements ICommand {

    private final Random random = new Random();

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            generateAndSendEmbed(event);
            return;
        }

        String joined = String.join("", args);

        ICommand command = manager.getCommand(joined);

        if(command == null) {
            event.getChannel().sendMessage("The command `" + joined + "` does not exist\n" +
                    "Use `" + Constants.PREFIX + getInvoke() + "` for a list of commands").queue();
            return;
        }

        String message = "Command help for `" + command.getInvoke() + "`\n" + command.getHelp();

        event.getChannel().sendMessage(message).queue();
    }

    private void generateAndSendEmbed(GuildMessageReceivedEvent event) {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("A list of all my commands:")
                .setColor(getRandomColor());

        StringBuilder descriptionBuilder = builder.getDescriptionBuilder();

        manager.getCommands().forEach(
                (command) -> descriptionBuilder.append('`').append(command.getInvoke()).append("`\n")
        );

        //TODO: Make a permission check to see if the bot can send embeds if not, send plain text
        event.getChannel().sendMessage(builder.build()).queue();
    }

    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

    @Override
    public String getHelp() {
        return "Shows a list of all the commands.\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " [command]`";
    }

    @Override
    public String getInvoke() {
        return "help";
    }
}
