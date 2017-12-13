package bot.discord.Listeners;

import bot.discord.Main;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContent().startsWith("~") && !Objects.equals(event.getMessage().getAuthor().getId(), event.getJDA().getSelfUser().getId()))
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
    }

    public void onReady(ReadyEvent event) {
        // Main.log("Status", "Logged in as: " + event.getJDA().getSelfUser().getName());
        System.out.println("Logged in as: " + event.getJDA().getSelfUser().getName());
    }

}
