package me.duncte123.menuDocs;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

class Listener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.printf("Logged in as %#s\n", event.getJDA().getSelfUser());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            System.out.printf("(%s)[%s]<%#s>: %s\n", guild.getName(), textChannel.getName(), author, content);
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PRIV]<%#s>: %s\n", author, content);
        }
    }
}
