package bot.discord;

import bot.discord.Commands.*;
import bot.discord.Interfaces.Command;
import bot.discord.Listeners.CommandListener;
import bot.discord.Listeners.DogListener;
import bot.discord.Listeners.ReadyListener;
import bot.discord.Utils.CommandParser;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.rithms.riot.api.ApiConfig;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

import static bot.discord.APIKey.getRiotAPIKey;

public class Main {

    private static JDA jda;

    public static final CommandParser parser = new CommandParser();

    private static HashMap<String, Command> commands = new HashMap<>();

    private static ApiConfig config = new ApiConfig().setKey(getRiotAPIKey());

    public static String piesGamingRealmID = "257916519097434113";
    public static String theFarmID = "307656057830768640";
    public static String leagueChannelID = "270699031443931137";
    public static String mrPieFarmChannelID = "417821431636951041";

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(APIKey.getDiscordAPIKey()).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);

        commands.put("ping", new Ping());
        commands.put("champion", new Champion());
        commands.put("8ball", new EightBall());
        commands.put("help", new Help());
        commands.put("usage", new Usage());
        commands.put("level", new Level());
        commands.put("mute", new Mute());
        commands.put("unmute", new Unmute());
        commands.put("clear", new Clear());

        builder.addEventListener(new ReadyListener(), new CommandListener()/*, new DogListener()*/);

        try {
            jda = builder.buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
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
}
