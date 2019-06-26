package bot.discord.Commands;

import bot.discord.APIKey;
import bot.discord.Interfaces.Command;
import bot.discord.Main;
import com.lvack.championggwrapper.ChampionGGAPIFactory;
import com.lvack.championggwrapper.data.base.role.RoleData;
import com.lvack.championggwrapper.data.champion.HighLevelChampionData;
import com.lvack.championggwrapper.retrofit.APIResponse;
import com.lvack.championggwrapper.retrofit.ChampionGGAPI;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class Champion implements Command {

    private static final String HELP = "USAGE: ~champion <champion>";

    private ChampionGGAPIFactory factory = new ChampionGGAPIFactory(APIKey.getChampionGGAPIKey(), 10);
    private ChampionGGAPI api = factory.buildChampionGGAPI();

    private APIResponse<List<HighLevelChampionData>> response = api.getHighLevelChampionData();

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        response.waitForResponse();

        String message = event.getMessage().getContent();
        String[] arguments = message.split(" ", 2);
        String arg1 = "Annie";

        MessageChannel mc = event.getTextChannel();

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

                //tc.sendMessage("-----------------------------------------").queue();
                //tc.sendMessage("**Champion: **" + data.getName()).queue();
                mc.sendMessage("-----------------------------------------\n**Champion: **" + data.getName()).queue();

                for (RoleData roleData : data.getRoles()) {
                    //tc.sendMessage("- _Position:_ " + roleData.getRole()).queue();
                    //tc.sendMessage(String.format(" - Played %04.1f%% (%d games) of the time in this role", roleData.getPercentPlayed(), roleData.getGameCount())).queue();
                    //tc.sendMessage("http://champion.gg/champion/" + data.getName() + "/" + roleData.getRole()).queue();
                    mc.sendMessage("- _Position:_ " + roleData.getRole() + "\n" +
                            String.format(" - Played %04.1f%% (%d games) of the time in this role", roleData.getPercentPlayed(), roleData.getGameCount()) + "\n" +
                            "http://champion.gg/champion/" + data.getName() + "/" + roleData.getRole()).queue();
                }
                mc.sendMessage("-----------------------------------------").queue();
            } else {
                if (response.isAPIError())
                    mc.sendMessage(response.getErrorResponse().toString()).queue();
                if (response.isFailure()) response.getError().printStackTrace();
                if (response.isInvalidAPIKey()) mc.sendMessage("Invalid API key!").queue();
            }
        } else {
            Main.usageError(mc, help());
        }

    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }
}
