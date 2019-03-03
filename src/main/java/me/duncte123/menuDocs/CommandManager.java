package me.duncte123.menuDocs;

import me.duncte123.menuDocs.commands.*;
import me.duncte123.menuDocs.commands.admin.SetPrefixCommand;
import me.duncte123.menuDocs.commands.moderation.BanCommand;
import me.duncte123.menuDocs.commands.moderation.KickCommand;
import me.duncte123.menuDocs.commands.moderation.UnbanCommand;
import me.duncte123.menuDocs.commands.music.*;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager(Random random) {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new CatCommand());
        addCommand(new DogCommand());
        addCommand(new MemeCommand(random));
        addCommand(new UserInfoCommand());
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new UnbanCommand());
        addCommand(new SetPrefixCommand());

        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new QueueCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        final String prefix = Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
