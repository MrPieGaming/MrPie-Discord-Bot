package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ping implements Command {
    private final String HELP = "```USAGE: ~ping```";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }

    public String toString() {
        return "Ping";
    }
}
