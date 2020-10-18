package me.ihells.quarkperms.commands;

import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.rank.Rank;
import me.ihells.quarkperms.utils.CC;
import me.ihells.quarkperms.utils.Messages;
import me.ihells.quarkperms.utils.framework.Command;
import me.ihells.quarkperms.utils.framework.CommandArgs;
import me.ihells.quarkperms.utils.framework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetRankCommand {

    public SetRankCommand(CommandFramework framework) { framework.registerCommands(this); }

    @Command(name = "setrank", inGameOnly = true)
    public void setRankCommand(CommandArgs cmd) {
        String[] args = cmd.getArgs();
        Player player = (Player) cmd.getSender();
        if (player.hasPermission("quark-perms.setrank")) {
            if (Bukkit.getPlayer(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                if (QuarkPerms.getInstance().getRankManager().getRank(args[1]) != null) {
                    Rank rank = QuarkPerms.getInstance().getRankManager().getRank(args[1]);
                    QuarkPerms.getInstance().getRankManager().setRank(player, rank);
                    player.sendMessage(CC.trns(
                            Messages.ON_RANK_CHANGE.replace("{target}", target.getName()).replace("{rank}", rank.getName())
                    ));
                } else {
                    player.sendMessage(CC.trns(Messages.INVALID_RANK.replace("{rank}", args[1])));
                }
            } else {
                player.sendMessage(CC.trns(Messages.INVALID_PLAYER.replace("{target}", args[0])));
            }
        } else {
            player.sendMessage(CC.trns(Messages.NO_PERMISSION));
        }
    }

}
