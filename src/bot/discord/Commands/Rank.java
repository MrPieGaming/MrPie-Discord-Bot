package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.constant.LeagueQueue;
import net.rithms.riot.api.endpoints.league.dto.LeagueEntry;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Rank implements Command {

    private static final String HELP = "Usage: ~rank <region> <summoner name>";

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
                Set<LeagueEntry> leagues = api.getLeagueEntriesBySummonerId(Platform.getPlatformByName(region.toUpperCase()), summoner.getId());

                //MatchList matchList = api.getMatchListByAccountId(Platform.getPlatformByName(region.toUpperCase()), summoner.getAccountId());

                if (leagues.isEmpty()) {
                    event.getTextChannel().sendMessage(summoner.getName() + " is currently __Unranked__").queue();
                }

                for (LeagueEntry l : leagues) {
                    if (l.getQueueType().equals(LeagueQueue.RANKED_SOLO_5x5.name())) {
                        event.getTextChannel().sendMessage("**" + summoner.getName() + " - ** Rank: " + "__" + l.getTier() + " " + l.getRank() + "__ " + l.getLeaguePoints() + " LP").queue();
                    }
                }
            } catch (RiotApiException | NoSuchElementException e) {
                Message msg = event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription("Try entering a different **region** and/or **summoner name**").build()).complete();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getMessage().delete().queue();
                        msg.delete().queue();
                    }
                }, 3000);
                //e.printStackTrace();
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
