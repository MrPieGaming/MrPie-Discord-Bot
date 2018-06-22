package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.constant.LeagueQueue;
import net.rithms.riot.api.endpoints.league.dto.LeagueItem;
import net.rithms.riot.api.endpoints.league.dto.LeagueList;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

import java.util.List;
import java.util.NoSuchElementException;

public class Rank implements Command { // this command DOES NOT work

    private static final String HELP = "```Usage: ~rank <region> <summoner name>```";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getId().equals("270699031443931137") || event.getTextChannel().getId().equals("417821431636951041") || !event.getGuild().getId().equals("307656057830768640") || !event.getGuild().getId().equals("257916519097434113")) {
            RiotApi api = new RiotApi(Main.getRiotApiConfig());
            String message = event.getMessage().getContent();
            String[] mArgs = message.split(" ", 3);
            Summoner summoner;

            if (mArgs.length < 3) {
                event.getTextChannel().sendMessage(help()).queue();
            } else {
                String region = mArgs[1];
                String summonerName = mArgs[2];

                try {
                    summoner = api.getSummonerByName(Platform.getPlatformByName(region), summonerName);
                    List<LeagueList> leagues = api.getLeagueBySummonerId(Platform.getPlatformByName(region), summoner.getId());

                    for (LeagueList l : leagues) {
                        if (l.getQueue().equals(LeagueQueue.RANKED_SOLO_5x5.name())) {
                            LeagueItem entry = l.getEntries().get(0);
                            event.getTextChannel().sendMessage("**" + summoner.getName() + "** Rank: " + "__" + l.getTier() + " " + entry.getRank() + "__ " + entry.getLeaguePoints() + " LP").queue();
                        }
                    }
                } catch (RiotApiException | NoSuchElementException e) {
                    event.getChannel().sendMessage("Try entering a different region and/or summoner").queue();
                    e.printStackTrace();
                }
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
