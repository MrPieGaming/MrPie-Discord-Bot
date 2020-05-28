package bot.discord.Commands;

import bot.discord.Interfaces.Command;
import bot.discord.Main;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.EmbedBuilder;

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
        if (event.getTextChannel().getId().equals(Main.piesGamingRealm_channel_ID_leagueoflegends) || !event.getGuild().getId().equals(Main.theFarm_guild_ID) || !event.getGuild().getId().equals(Main.piesGamingRealm_guild_ID)) {
            boolean allowed = false;

            String message = event.getMessage().getContentDisplay();
            String[] argss = message.split(" ", 2);

            List<Role> memberRoles = event.getMember().getRoles();

            for (Role r : memberRoles) {
                if (r.equals(event.getGuild().getRoleById(Main.theFarm_role_ID_funnyguy))) {
                    allowed = true;
                    break;
                }
            }

            if (allowed) {
                if (argss.length != 2) {
                    Main.usageError(event.getChannel(), help());
                } else {
                    Guild guild = event.getGuild();

                    if (argss[1].equalsIgnoreCase("current") && event.getMember().getVoiceState().inVoiceChannel()) {
                        List<Member> members = event.getMember().getVoiceState().getChannel().getMembers();

                        for (Member m : members) {
                            if (!m.getUser().equals(event.getAuthor()) && !m.isOwner())
                                guild.mute(m, false).queue();
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
                                    if (!m.getUser().equals(event.getAuthor()) && !m.isOwner())
                                        guild.mute(m, false).queue();
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
