package me.duncte123.menuDocs.commands.moderation;

import me.duncte123.menuDocs.Constants;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if (args.isEmpty() || mentionedMembers.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        Member target = mentionedMembers.get(0);
        String reason = String.join(" ", args.subList(1, args.size()));

        if (!member.hasPermission(Permission.KICK_MEMBERS) || !member.canInteract(target)) {
            channel.sendMessage("You don't have permission to use this command").queue();
            return;
        }


        if (!selfMember.hasPermission(Permission.KICK_MEMBERS) || !selfMember.canInteract(target)) {
            channel.sendMessage("I can't kick that user or I don't have the kick members permission").queue();
            return;
        }


        event.getGuild().kick(target, String.format("Kick by: %#s, with reason: %s",
                event.getAuthor(), reason)).queue();

        channel.sendMessage("Success!").queue();

    }

    @Override
    public String getHelp() {
        return "Kicks a user off the server.\n" +
                "Usage: `"  + Constants.PREFIX + getInvoke() + " <user> <reason>`";
    }

    @Override
    public String getInvoke() {
        return "kick";
    }
}
