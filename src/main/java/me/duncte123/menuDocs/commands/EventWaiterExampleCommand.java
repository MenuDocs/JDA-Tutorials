package me.duncte123.menuDocs.commands;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventWaiterExampleCommand implements ICommand {

    private static final String EMOJI = "\uD83C\uDD95";
    private EventWaiter waiter;

    public EventWaiterExampleCommand(EventWaiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        long channelId = channel.getIdLong();

        channel.sendMessage("Please react with " + EMOJI).queue((message) -> {
            message.addReaction(EMOJI).queue();
            initWaiter(message.getIdLong(), channelId, event.getJDA().getShardManager());
        });
    }

    private void initWaiter(long messageId, long channelId, ShardManager shardManager) {
        waiter.waitForEvent(
                GuildMessageReactionAddEvent.class,
                (event) -> {
                    ReactionEmote emote = event.getReactionEmote();
                    User user = event.getUser();

                    return !user.isBot() && event.getMessageIdLong() == messageId && !emote.isEmote() && EMOJI.equals(emote.getName());
                },
                (event) -> {
                    TextChannel channel = shardManager.getTextChannelById(channelId);
                    User user = event.getUser();

                    channel.sendMessageFormat("%#s was the first to react with `" + EMOJI + "` to the message", user).queue();
                },
                10, TimeUnit.SECONDS,
                () -> {
                    TextChannel channel = shardManager.getTextChannelById(channelId);

                    channel.sendMessage("I stopped listening for reactions").queue();
                }
        );
    }

    @Override
    public String getHelp() {
        return "Shows an example with the event waiter";
    }

    @Override
    public String getInvoke() {
        return "wait";
    }
}
