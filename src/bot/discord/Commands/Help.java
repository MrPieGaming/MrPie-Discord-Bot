package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class Help implements Command {

    private static final String HELP = "Usage: ~help";

    private EmbedBuilder cmds = new EmbedBuilder().setColor(Color.GRAY);

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(cmds.setDescription(
                "====MrPie's Bot o' Fun's Commands====\n" +
                        "\n" +
                        "- Ping\n" +
                        "- Champion\n" +
                        "- 8ball\n" +
                        "- Level\n" +
                        "- Rank\n" +
                        "- Mute\n" +
                        "- Unmute\n" +
                        "- Clear\n" +
                        "- Help\n" +
                        "- Usage\n" +
                        "\n**To find the usage of a command, type ~usage <command>**\n").build()).queue();
    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
