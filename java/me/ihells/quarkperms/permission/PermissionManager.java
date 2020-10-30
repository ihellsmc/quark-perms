package me.ihells.quarkperms.permission;

import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.player.PlayerData;
import me.ihells.quarkperms.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PermissionManager {

    private final YamlConfiguration playerData = QuarkPerms.getInstance().getPlayerData().getConfiguration();

    public void givePerms(UUID uuid, String... perms) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player == null) return;

        List<String> current = new ArrayList<>();
        if (playerData.getStringList(uuid.toString() + ".permissions") != null) {
            current = playerData.getStringList(uuid.toString() + ".permissions");
        }

        for (String perm : perms) {
            if (!current.contains(perm)) current.add(perm);
        }

        setPerms(player, current.toArray(new String[0]));

    }

    private void attachPerms(List<String> perms, PlayerData player) {
        for (String perm : perms) {
            if (perm.equals("*")) { // set all perms in the server
                for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                    for (Permission permission : plugin.getDescription().getPermissions()) {
                        player.getAttachment().setPermission(permission.getName(), true);
                    }
                }
            } else player.getAttachment().setPermission(perm, !perm.startsWith("-"));
        }
    }

    public void initialSet(PlayerData player) {
        for (Rank rank : player.getRanks()) {
            addRankPerms(rank, player);
        }
        attachPerms(player.getPermissions(), player);
    }

    public void addRankPerms(Rank rank, PlayerData player) {
        attachPerms(rank.getPermissions(), player);
        for (Rank inherit : rank.getInheritance()) { addRankPerms(inherit, player); }
    }

    public void removePerms(UUID uuid, String... perms) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player == null) return;

        List<String> current = new ArrayList<>();
        if (playerData.getStringList(uuid.toString() + ".permissions") != null) {
            current = playerData.getStringList(uuid.toString() + ".permissions");
        }

        for (String perm : perms) { current.remove(perm); }

        setPerms(player.getPlayer(), current.toArray(new String[0]));

    }

    public void setPerms(OfflinePlayer player, String... perms) {
        playerData.set(player.getUniqueId().toString() + ".permissions", perms);
        QuarkPerms.getInstance().savePlayerData();
        if (player.isOnline()) { QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player.getPlayer()); }
    }

    public List<String> getPerms(UUID uuid) {
        return playerData.getStringList(uuid.toString() + ".permissions");
    }

}
