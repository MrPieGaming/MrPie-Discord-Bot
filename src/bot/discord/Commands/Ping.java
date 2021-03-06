package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping implements Command {
    private static final String HELP = "USAGE: ~ping";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Pong!").queue();
    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }
}
