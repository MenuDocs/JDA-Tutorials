package me.duncte123.menuDocs.commands.music;

import me.duncte123.menuDocs.music.GuildMusicManager;
import me.duncte123.menuDocs.music.PlayerManager;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class StopCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

        musicManager.scheduler.getQueue().clear();
        musicManager.player.stopTrack();
        musicManager.player.setPaused(false);

        event.getChannel().sendMessage("Stopping the player and clearing the queue").queue();
    }

    @Override
    public String getHelp() {
        return "Stops the music player";
    }

    @Override
    public String getInvoke() {
        return "stop";
    }
}
