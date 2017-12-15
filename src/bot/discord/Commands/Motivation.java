package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Motivation implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
