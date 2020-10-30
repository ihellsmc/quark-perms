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

public class SetRankCommands {

    private final YamlConfiguration messages = QuarkPerms.getInstance().getMessages().getConfiguration();

    public SetRankCommands(CommandFramework framework) { framework.registerCommands(this); }

    /*

    setrank <player> <rank> || quark-perms.admin.setrank
    addrand <player> <rank> || quark-perms.admin.addrank
    removerank <player> <rank> || quark-perms.admin.removerank

     */

    @Command(name = "rank", inGameOnly = false)
    public void setRankCommands(CommandArgs cmd) {
        String[] args = cmd.getArgs();

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            if (cmd.getSender().hasPermission("quark-perms.admin.setrank")) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (QuarkPerms.getInstance().getRankManager().getRank(args[2]) != null) {

                        Rank rank = QuarkPerms.getInstance().getRankManager().getRank(args[2]);
                        QuarkPerms.getInstance().getRankManager().setRanks(target.getUniqueId(), rank);
                        cmd.getSender().sendMessage(CC.trns(
                                Messages.ON_RANK_CHANGE.replace("{target}", target.getName()).replace("{rank}", rank.getName())
                        ));

                    } else {
                        cmd.getSender().sendMessage(CC.trns(Messages.INVALID_RANK.replace("{rank}", args[2])));
                    }

                } else {
                    cmd.getSender().sendMessage(CC.trns(Messages.INVALID_PLAYER.replace("{target}", args[1])));
                }

            } else {
                cmd.getSender().sendMessage(CC.trns(Messages.NO_PERMISSION));
            }

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            if (cmd.getSender().hasPermission("quark-perms.admin.addrank")) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (QuarkPerms.getInstance().getRankManager().getRank(args[2]) != null) {

                        Rank rank = QuarkPerms.getInstance().getRankManager().getRank(args[2]);
                        QuarkPerms.getInstance().getRankManager().addRanks(target.getUniqueId(), rank);
                        cmd.getSender().sendMessage(CC.trns(
                                Messages.ON_RANK_ADD.replace("{target}", target.getName()).replace("{rank}", rank.getName())
                        ));

                    } else {
                        cmd.getSender().sendMessage(CC.trns(Messages.INVALID_RANK.replace("{rank}", args[2])));
                    }

                } else {
                    cmd.getSender().sendMessage(CC.trns(Messages.INVALID_PLAYER.replace("{target}", args[1])));
                }

            } else {
                cmd.getSender().sendMessage(Messages.NO_PERMISSION);
            }

        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            if (cmd.getSender().hasPermission("quark-perms.admin.removerank")) {

                if (Bukkit.getOfflinePlayer(args[1]) != null) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (QuarkPerms.getInstance().getRankManager().getRank(args[2]) != null) {

                        Rank rank = QuarkPerms.getInstance().getRankManager().getRank(args[2]);
                        QuarkPerms.getInstance().getRankManager().removeRanks(target.getUniqueId(), rank);
                        cmd.getSender().sendMessage(CC.trns(
                                Messages.ON_RANK_REMOVE.replace("{target}", target.getName()).replace("{rank}", rank.getName())
                        ));

                    } else {
                        cmd.getSender().sendMessage(CC.trns(Messages.INVALID_RANK.replace("{rank}", args[2])));
                    }

                } else {
                    cmd.getSender().sendMessage(CC.trns(Messages.INVALID_PLAYER.replace("{target}", args[1])));
                }

            } else {
                cmd.getSender().sendMessage(Messages.NO_PERMISSION);
            }

        } else {
            cmd.getSender().sendMessage(Messages.INVALID_USAGE
                    .replace("{usage}", "/rank <set,add,remove> <player> <rank>"));
        }
    }

}
