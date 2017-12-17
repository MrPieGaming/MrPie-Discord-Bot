package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Usage implements Command {

    private static final String HELP = "```USAGE: ~usage <command name>```";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String message = event.getMessage().getContent();

        String[] arguments = message.split(" ");
        String commandName = arguments[1].toLowerCase();

        TextChannel channel = event.getTextChannel();

        switch (commandName) {
            case "ping": {
                channel.sendMessage(Ping.help()).queue();
                break;
            }
            case "champion": {
                channel.sendMessage(Champion.help()).queue();
                break;
            }
            case "8ball": {
                channel.sendMessage(EightBall.help()).queue();
                break;
            }
            case "help": {
                channel.sendMessage(Help.help()).queue();
                break;
            }
            default: {
                channel.sendMessage(Usage.help()).queue();
                break;
            }
        }
    }

    public static String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
