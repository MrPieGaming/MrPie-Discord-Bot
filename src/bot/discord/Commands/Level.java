package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

import java.util.NoSuchElementException;

public class Level implements Command {

    private static final String HELP = "```Usage: ~level <region> <summoner name>```";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getId().equals(Main.leagueChannelID) || event.getTextChannel().getId().equals(Main.mrPieFarmChannelID) || !event.getGuild().getId().equals(Main.theFarmID) || !event.getGuild().getId().equals(Main.piesGamingRealmID)) {
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
                    summoner = api.getSummonerByName(Platform.getPlatformByName(region.toUpperCase()), summonerName);
                    event.getChannel().sendMessage("**" + summoner.getName() + " **Level: " + summoner.getSummonerLevel()).queue();
                } catch (RiotApiException | NoSuchElementException e) {
                    event.getChannel().sendMessage("Try entering a different region and/or summoner").queue();
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
