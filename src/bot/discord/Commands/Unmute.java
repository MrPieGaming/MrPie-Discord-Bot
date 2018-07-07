package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Unmute implements Command {

    private static final String HELP = "USAGE: ~unmute <channel name/current>";

    private EmbedBuilder success = new EmbedBuilder().setColor(Color.GREEN);
    private EmbedBuilder failure = new EmbedBuilder().setColor(Color.RED);

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getId().equals(Main.leagueChannelID) || event.getTextChannel().getId().equals(Main.mrPieFarmChannelID) || !event.getGuild().getId().equals(Main.theFarmID) || !event.getGuild().getId().equals(Main.piesGamingRealmID)) {
            boolean allowed = false;

            String message = event.getMessage().getContent();
            String[] argss = message.split(" ", 2);

            List<Role> memberRoles = event.getMember().getRoles();

            for (Role r : memberRoles) {
                if (r.equals(event.getGuild().getRoleById("344944943422504962"))) {
                    allowed = true;
                    break;
                }
            }

            if (allowed) {
                if (argss.length != 2) {
                    event.getTextChannel().sendMessage(help()).queue();
                } else {
                    GuildController gc = new GuildController(event.getGuild());

                    if (argss[1].equalsIgnoreCase("current") && event.getMember().getVoiceState().inVoiceChannel()) {
                        List<Member> members = event.getMember().getVoiceState().getChannel().getMembers();

                        for (Member m : members) {
                            if (!m.getUser().equals(event.getAuthor()) && !m.getGuild().getOwner().getUser().equals(m.getUser()))
                                gc.setMute(m, false).queue();
                        }

                        Message msg = event.getTextChannel().sendMessage(success.setDescription("Unmuted \"" + event.getMember().getVoiceState().getChannel().getName() + "\"").build()).complete();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                event.getMessage().delete().queue();
                                msg.delete().queue();
                            }
                        }, 3000);
                    } else {
                        try {
                            List<VoiceChannel> specifiedVCs = event.getGuild().getVoiceChannelsByName(argss[1], true);

                            for (VoiceChannel vc : specifiedVCs) {
                                List<Member> members = vc.getMembers();

                                for (Member m : members) {
                                    if (!m.getGuild().getOwner().getUser().equals(m.getUser()) && !m.getUser().equals(event.getAuthor()))
                                        gc.setMute(m, false).queue();
                                }
                            }

                            Message msg = event.getTextChannel().sendMessage(success.setDescription("Unmuted \"" + specifiedVCs.get(0).getName() + "\"").build()).complete();

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    event.getMessage().delete().queue();
                                    msg.delete().queue();
                                }
                            }, 3000);
                        } catch (Exception e) {
                            Message msg = event.getTextChannel().sendMessage(failure.setDescription("Try entering a different channel name").build()).complete();

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
