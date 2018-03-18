package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.rithms.riot.api.RiotApi;

public class Level implements Command {

    private static final String HELP = "```Usage: ~level <summoner name>```";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getId().equals("270699031443931137") || event.getTextChannel().getId().equals("417821431636951041")) {
            RiotApi api = new RiotApi(Main.getRiotApiConfig());
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public static String help() {
        return HELP;
    }
}
