package me.duncte123.menuDocs.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.duncte123.menuDocs.music.GuildMusicManager;
import me.duncte123.menuDocs.music.PlayerManager;
import me.duncte123.menuDocs.music.TrackScheduler;
import me.duncte123.menuDocs.objects.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class SkipCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player isn't playing aything").queue();

            return;
        }

        scheduler.nextTrack();

        channel.sendMessage("Skipping the current track").queue();
    }

    @Override
    public String getHelp() {
        return "Skips the current song";
    }

    @Override
    public String getInvoke() {
        return "skip";
    }
}
