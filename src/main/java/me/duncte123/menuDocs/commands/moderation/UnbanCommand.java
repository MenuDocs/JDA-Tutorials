package me.duncte123.menuDocs.commands.moderation;

import me.duncte123.menuDocs.Constants;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.stream.Collectors;

public class UnbanCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();

        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("You need the Ban Members permission to use this command.").queue();
            return;
        }

        if (!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("I need the Ban Members permission to unban members.").queue();
            return;
        }

        if (args.isEmpty()) {
            channel.sendMessage("Usage: `" + Constants.PREFIX + getInvoke() + " <username/user id/username#disc>`").queue();
            return;
        }

        String argsJoined = String.join(" ", args);

        event.getGuild().retrieveBanList().queue((bans) -> {

            List<User> goodUsers = bans.stream().filter((ban) -> isCorrectUser(ban, argsJoined))
                    .map(Guild.Ban::getUser).collect(Collectors.toList());

            if (goodUsers.isEmpty()) {
                channel.sendMessage("This user is not banned").queue();
                return;
            }

            User target = goodUsers.get(0);

            String mod = String.format("%#s", event.getAuthor());
            String bannedUser = String.format("%#s", target);

            event.getGuild().unban(target)
                    .reason("Unbanned By " + mod).queue();

            channel.sendMessage("User " + bannedUser + " unbanned.").queue();

        });
    }

    @Override
    public String getHelp() {
        return "Unbans a member from the server\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <username/user id/username#disc>";
    }

    @Override
    public String getInvoke() {
        return "unban";
    }

    private boolean isCorrectUser(Guild.Ban ban, String arg) {
        User bannedUser = ban.getUser();

        return bannedUser.getName().equalsIgnoreCase(arg) || bannedUser.getId().equals(arg)
                || String.format("%#s", bannedUser).equalsIgnoreCase(arg);
    }
}
