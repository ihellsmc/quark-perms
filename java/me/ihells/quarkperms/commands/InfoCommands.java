package me.ihells.quarkperms.commands;

import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.rank.Rank;
import me.ihells.quarkperms.utils.CC;
import me.ihells.quarkperms.utils.Messages;
import me.ihells.quarkperms.utils.framework.Command;
import me.ihells.quarkperms.utils.framework.CommandArgs;
import me.ihells.quarkperms.utils.framework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class InfoCommands {

    private final YamlConfiguration messages = QuarkPerms.getInstance().getMessages().getConfiguration();
    private final YamlConfiguration playerData = QuarkPerms.getInstance().getPlayerData().getConfiguration();

    public InfoCommands(CommandFramework framework) { framework.registerCommands(this); }

    /*

    info player <player> || quark-perms.admin.playerinfo
    info rank <rank> || quark-perms.admin.rankinfo

     */

    @Command(name = "info", inGameOnly = false)
    public void infoCommands(CommandArgs cmd) {
        String[] args = cmd.getArgs();

        if (args.length == 2 && args[0].equalsIgnoreCase("player")) {

            if (cmd.getSender().hasPermission("quark-perms.admin.playerinfo")) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (target.isOnline()) {

                        String name = target.getName();
                        String rank = QuarkPerms.getInstance().getPlayerManager().getPlayerData(target.getPlayer()).getRanks().get(0).getName();
                        String ranks = "";
                        for (Rank r : QuarkPerms.getInstance().getPlayerManager().getPlayerData(target.getPlayer()).getRanks()) {
                            ranks += r.getName() + ", ";
                        }
                        String permissions = "";
                        for (String perm : QuarkPerms.getInstance().getPlayerManager().getPlayerData(target.getPlayer()).getPermissions()) {
                            permissions += perm + ", ";
                        }
                        if (permissions.equals("")) {
                            permissions = "None";
                        }

                        List<String> format = messages.getStringList("player-info");
                        for (String message : format) {
                            message = message.replace("{target}", name).replace("{rank}", rank)
                                    .replace("{ranks}", ranks).replace("{permissions}", permissions);
                            cmd.getSender().sendMessage(CC.trns(message));
                        }

                    } else {

                        if (playerData.getConfigurationSection("").getKeys(false).contains(target.getUniqueId().toString())) {

                            String name = target.getName();
                            String rank = playerData.getStringList(target.getUniqueId().toString() + ".ranks").get(0);
                            String ranks = "";
                            for (String r : playerData.getStringList(target.getUniqueId().toString() + ".ranks")) {
                                if (QuarkPerms.getInstance().getRankManager().getRank(r) != null) ranks += r + ", ";
                            }
                            String permissions = "";
                            for (String perm : playerData.getStringList(target.getUniqueId().toString() + ".permissions")) {
                                permissions += perm + ", ";
                            }
                            if (permissions.equals("")) {
                                permissions = "None";
                            }

                            List<String> format = messages.getStringList("player-info");
                            for (String message : format) {
                                message = message.replace("{target}", name).replace("{rank}", rank)
                                        .replace("{ranks}", ranks).replace("{permissions}", permissions);
                                cmd.getSender().sendMessage(CC.trns(message));
                            }

                        } else {
                            cmd.getSender().sendMessage(Messages.INVALID_PLAYER.replace("{target}", args[1]));
                        }

                    }

                } else {
                    cmd.getSender().sendMessage(Messages.INVALID_PLAYER.replace("{target}", args[1]));
                }

            } else {
                cmd.getSender().sendMessage(Messages.NO_PERMISSION);
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("rank")) {

            if (cmd.getSender().hasPermission("quark-perms.admin.rankinfo")) {

                if (QuarkPerms.getInstance().getRankManager().getRank(args[1]) != null) {
                    Rank rank = QuarkPerms.getInstance().getRankManager().getRank(args[1]);

                    String name = rank.getName();
                    String prefix = rank.getPrefix();
                    String priority = String.valueOf(rank.getPriority() + 1);
                    String inheritance = "";
                    for (Rank i : rank.getInheritance()) { inheritance += i.getName() + ", "; }
                    if (inheritance.equals("")) { inheritance = "None"; }
                    String permissions = "";
                    for (String perm : rank.getPermissions()) { permissions += perm + ", "; }
                    if (permissions.equals("")) { permissions = "None"; }

                    List<String> format = messages.getStringList("rank-info");
                    for (String message : format) {
                        message = message.replace("{rank}", name).replace("{prefix}", prefix).replace("{priority}", priority)
                                .replace("{inherits}", inheritance).replace("{permissions}", permissions);
                        cmd.getSender().sendMessage(CC.trns(message));
                    }

                } else {
                    cmd.getSender().sendMessage(Messages.INVALID_RANK.replace("{rank}", args[1]));
                }

            } else {
                cmd.getSender().sendMessage(Messages.NO_PERMISSION);
            }

        } else {
            cmd.getSender().sendMessage(Messages.INVALID_USAGE.replace("{usage}","/info <player,rank> <player,rank>"));
        }

    }

}
