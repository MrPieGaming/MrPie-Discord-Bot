package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

public class Level implements Command {

    private static final String HELP = "Usage: ~level <region> <summoner name>";

    private EmbedBuilder helpMsg = new EmbedBuilder().setColor(Color.RED).setDescription(help());

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        RiotApi api = new RiotApi(Main.getRiotApiConfig());
        String message = event.getMessage().getContentRaw();
        String[] mArgs = message.split(" ", 3);
        Summoner summoner;

        if (mArgs.length < 3) {
            Main.usageError(event.getChannel(), help());
        } else {
            String region = mArgs[1];
            String summonerName = mArgs[2];
            try {
                summoner = api.getSummonerByName(Platform.getPlatformByName(region.toUpperCase()), summonerName);
                event.getChannel().sendMessage("**" + summoner.getName() + " **Level: " + summoner.getSummonerLevel()).queue();
            } catch (RiotApiException | NoSuchElementException e) {
                //e.printStackTrace();
                Message msg = event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription("Try entering a different **region** and/or **summoner name**").build()).complete();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getMessage().delete().queue();
                        msg.delete().queue();
                    }
                }, 3000);
            }
        }

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public String help() {
        return HELP;
    }
}
