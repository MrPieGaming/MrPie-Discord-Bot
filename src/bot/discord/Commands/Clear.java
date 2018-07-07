package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Clear implements Command {

    private static final String HELP = "Usage: ~clear <amount of messages>";

    private EmbedBuilder error = new EmbedBuilder().setColor(Color.RED);
    private EmbedBuilder success = new EmbedBuilder().setColor(Color.GREEN);

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        String[] mArgs = message.split(" ", 2);

        if (mArgs.length < 2) event.getChannel().sendMessage(error.setDescription(help()).build()).queue();
        else {
            try {
                int numOfMessages = Integer.parseInt(mArgs[1]);

                if (numOfMessages < 1 || numOfMessages > 50) {
                    Message msg = event.getChannel().sendMessage(error.setDescription("You can only delete up to 50 messages").build()).complete();

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            msg.delete().queue();
                        }
                    }, 2500);
                    return;
                }

                try {
                    MessageHistory history = new MessageHistory(event.getTextChannel());
                    List<Message> messageList = history.retrievePast(numOfMessages + 1).complete();

                    //event.getMessage().delete().queue();

                    event.getTextChannel().deleteMessages(messageList).queue();
                    Message msg = event.getTextChannel().sendMessage(success.setDescription(numOfMessages + " messages have been deleted!").build()).complete();

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            msg.delete().queue();
                        }
                    }, 2500);
                } catch (Exception e) {
                    Message msg = event.getTextChannel().sendMessage(error.setDescription("Mmm, something went wrong. Try deleting a different number of messages. Make sure the message(s) you're deleting aren't more than 2 weeks old.").build()).complete();

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            msg.delete().queue();
                        }
                    }, 5000);
                }
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage(error.setDescription(help()).build()).queue();
            }
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
