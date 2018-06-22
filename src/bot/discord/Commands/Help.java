package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help implements Command {

    private static final String HELP = "```USAGE: ~help```";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("```markdown\n" +
                "\n" +
                "# ====MrPie's Bot o' Fun's Commands==== #\n" +
                "\n" +
                "- Ping\n" +
                "- Champion\n" +
                "- 8ball\n" +
                "- Level\n" +
                "- Mute\n" +
                "- Unmute\n" +
                "- Help\n" +
                "- Usage\n" +
                "\n**To find the usage of a command, type ~usage <command>**\n" +
                "```").queue();
    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
