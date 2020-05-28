package bot.discord.Listeners;

import bot.discord.Main;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith("~") && !Objects.equals(event.getMessage().getAuthor().getId(), event.getJDA().getSelfUser().getId()))
            Main.handleCommand(Main.parser.parse(event.getMessage().getContentRaw().toLowerCase(), event));
    }

    public void onReady(ReadyEvent event) {
        System.out.println("Logged in as: " + event.getJDA().getSelfUser().getName());
    }

}
