package me.duncte123.menuDocs.commands.music;

import me.duncte123.menuDocs.music.PlayerManager;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), "https://youtu.be/b6QHGfphoXU");

        manager.getGuildMusicManager(event.getGuild()).player.setVolume(10);
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}
