package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class EightBall implements Command {

    private String[] goodWords = {"can", "am", "is", "are", "do", "will", "does", "did", "should", "was"};

    private static final String HELP = "Usage: ~8ball <YES or NO question>";

    private EmbedBuilder error = new EmbedBuilder().setColor(Color.RED);

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String[] arguments = event.getMessage().getContent().split(" ");
        String firstWord;

        if (arguments.length != 1) {
            firstWord = arguments[1];

            boolean condition = false;

            for (String s : goodWords) {
                if (s.equalsIgnoreCase(firstWord)) {
                    condition = true;
                    break;
                }
            }

            if (condition) {
                int advice = genRandomInt();

                switch (advice) {
                    case 0: {
                        channel.sendMessage("Yes").queue();
                        break;
                    }
                    case 1: {
                        channel.sendMessage("No").queue();
                        break;
                    }
                    case 2: {
                        channel.sendMessage("I'm not sure").queue();
                        break;
                    }
                    default: {
                        channel.sendMessage("Something went wrong...").queue();
                        break;
                    }
                }
            } else {
                channel.sendMessage(error.setDescription("That is not a YES or NO question. Try again :)").build()).queue();
            }
        } else Main.usageError(channel, help());
    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }

    private int genRandomInt() {
        return (int) (Math.random() * 3);
    }
}
