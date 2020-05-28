package bot.discord.Listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DogListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().contains("https://www.huffingtonpost.com/entry/pancake-dog-house-fire_us_5a79f50be4b07af4e81e6c3e")) {
            event.getMessage().delete().queue();
        }
    }
}
