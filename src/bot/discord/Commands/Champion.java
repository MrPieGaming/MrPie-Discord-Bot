package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import com.lvack.championggwrapper.ChampionGGAPIFactory;
import com.lvack.championggwrapper.data.base.role.RoleData;
import com.lvack.championggwrapper.data.champion.HighLevelChampionData;
import com.lvack.championggwrapper.retrofit.APIResponse;
import com.lvack.championggwrapper.retrofit.ChampionGGAPI;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class Champion implements Command {

    private static final String HELP = "```USAGE: ~champion <champion>```";

    private ChampionGGAPIFactory factory = new ChampionGGAPIFactory("10c6ddc6ea7ac9c64d844db9a15ea207", 10);
    private ChampionGGAPI api = factory.buildChampionGGAPI();

    private APIResponse<List<HighLevelChampionData>> response = api.getHighLevelChampionData();

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getId().equals(Main.leagueChannelID) || event.getTextChannel().getId().equals(Main.mrPieFarmChannelID)) {
            response.waitForResponse();

            String message = event.getMessage().getContent();
            String[] arguments = message.split(" ");
            String arg1 = "Annie";

            if (arguments.length > 1) {
                arg1 = arguments[1];
            }

            if (arguments.length == 2) {
                if (response.isSuccess()) {
                    List<HighLevelChampionData> content = response.getContent();
                    HighLevelChampionData data = content.get(0);

                    for (HighLevelChampionData cd : content) {
                        data = cd;

                        if (data.getName().equalsIgnoreCase(arg1)) break;
                    }

                    event.getTextChannel().sendMessage("-----------------------------------------").queue();
                    event.getTextChannel().sendMessage("**Champion: **" + data.getName()).queue();

                    for (RoleData roleData : data.getRoles()) {
                        event.getTextChannel().sendMessage("- _Position:_ " + roleData.getRole()).queue();
                        event.getTextChannel().sendMessage(String.format(" - Played %04.1f%% (%d games) of the time in this role", roleData.getPercentPlayed(), roleData.getGameCount())).queue();
                        event.getTextChannel().sendMessage("http://champion.gg/champion/" + data.getName() + "/" + roleData.getRole()).queue();
                    }
                    event.getTextChannel().sendMessage("-----------------------------------------").queue();
                } else {
                    if (response.isAPIError())
                        event.getTextChannel().sendMessage(response.getErrorResponse().toString()).queue();
                    if (response.isFailure()) response.getError().printStackTrace();
                    if (response.isInvalidAPIKey()) event.getTextChannel().sendMessage("Invalid API key!").queue();
                }
            } else {
                event.getTextChannel().sendMessage(help()).queue();
            }
        }
    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }
}
