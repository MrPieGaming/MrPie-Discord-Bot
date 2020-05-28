package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class Usage implements Command {

    private static final String HELP = "USAGE: ~usage <command name>";

    private EmbedBuilder usageMsg = new EmbedBuilder().setColor(Color.CYAN);
    private EmbedBuilder failure = new EmbedBuilder().setColor(Color.RED);

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        String[] arguments = message.split(" ");
        if (arguments.length != 1) {
            String commandName = arguments[1].toLowerCase();

            TextChannel channel = event.getTextChannel();

            switch (commandName) {
                case "ping": {
                    channel.sendMessage(usageMsg.setDescription(new Ping().help()).build()).queue();
                    break;
                }
                case "champion": {
                    channel.sendMessage(usageMsg.setDescription(new Champion().help()).build()).queue();
                    break;
                }
                case "8ball": {
                    channel.sendMessage(usageMsg.setDescription(new EightBall().help()).build()).queue();
                    break;
                }
                case "help": {
                    channel.sendMessage(usageMsg.setDescription(new Help().help()).build()).queue();
                    break;
                }
                case "level": {
                    channel.sendMessage(usageMsg.setDescription(new Level().help()).build()).queue();
                    break;
                }
                case "mute": {
                    channel.sendMessage(usageMsg.setDescription(new Mute().help()).build()).queue();
                    break;
                }
                case "unmute": {
                    channel.sendMessage(usageMsg.setDescription(new Unmute().help()).build()).queue();
                    break;
                }
                case "clear": {
                    channel.sendMessage(usageMsg.setDescription(new Clear().help()).build()).queue();
                    break;
                }
                case "rank": {
                    channel.sendMessage(usageMsg.setDescription(new Rank().help()).build()).queue();
                    break;
                }
                default: {
                    channel.sendMessage(failure.setDescription(help()).build()).queue();
                    break;
                }
            }
        } else Main.usageError(event.getChannel(), help());
    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
