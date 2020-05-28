package bot.discord;

import bot.discord.Commands.*;
import bot.discord.Interfaces.Command;
import bot.discord.Listeners.CommandListener;
import bot.discord.Listeners.ReadyListener;
import bot.discord.Utils.CommandParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.rithms.riot.api.ApiConfig;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.HashMap;

import static bot.discord.APIKey.getRiotAPIKey;

public class Main {

    private static JDA api;

    public static final CommandParser parser = new CommandParser();

    private static HashMap<String, Command> commands = new HashMap<>();

    private static ApiConfig config = new ApiConfig().setKey(getRiotAPIKey());

    public static String piesGamingRealm_guild_ID = "257916519097434113";
    public static String theFarm_guild_ID = "307656057830768640";
    public static String piesGamingRealm_channel_ID_leagueoflegends = "270699031443931137";

    public static String theFarm_role_ID_funnyguy = "344944943422504962";

    public static void main(String[] args) {
        //JDABuilder builder = new JDABuilder(AccountType.BOT);
        //builder.setToken(APIKey.getDiscordAPIKey()).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);

        try {
            api = JDABuilder.createDefault(APIKey.getDiscordAPIKey()).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE).build().awaitReady();
        } catch (InterruptedException | IllegalStateException | LoginException e) {
            e.printStackTrace();
        }

        commands.put("ping", new Ping());
        commands.put("champion", new Champion());
        commands.put("8ball", new EightBall());
        commands.put("help", new Help());
        commands.put("usage", new Usage());
        commands.put("level", new Level());
        commands.put("mute", new Mute());
        commands.put("unmute", new Unmute());
        commands.put("clear", new Clear());
        commands.put("rank", new Rank());

        api.addEventListener(new ReadyListener(), new CommandListener()/*, new DogListener()*/);
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }
    }

    public static ApiConfig getRiotApiConfig() {
        return config;
    }

    public static void usageError(MessageChannel channel, String helpMsg) {
        EmbedBuilder error = new EmbedBuilder().setColor(Color.RED).setDescription(helpMsg);
        channel.sendMessage(error.build()).queue();
    }
}
