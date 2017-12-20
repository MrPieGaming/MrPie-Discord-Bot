package bot.discord;

import bot.discord.Commands.*;
import bot.discord.Interfaces.Command;
import bot.discord.Listeners.CommandListener;
import bot.discord.Listeners.ReadyListener;
import bot.discord.Utils.CommandParser;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

public class Main {

    private static JDA jda;

    public static final CommandParser parser = new CommandParser();

    private static HashMap<String, Command> commands = new HashMap<>();

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(APIKey.returnAPIKey()).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);

        commands.put("ping", new Ping());
        commands.put("champion", new Champion());
        commands.put("8ball", new EightBall());
        commands.put("help", new Help());
        commands.put("usage", new Usage());

        builder.addEventListener(new ReadyListener(), new CommandListener());

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
}
