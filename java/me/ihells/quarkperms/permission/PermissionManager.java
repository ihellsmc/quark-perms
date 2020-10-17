package me.ihells.quarkperms.permission;

import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.player.PlayerData;
import me.ihells.quarkperms.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PermissionManager {

    private final YamlConfiguration playerData = QuarkPerms.getInstance().getPlayerData().getConfiguration();

    public void givePerm(String perm, Player player) {
        PlayerData data = QuarkPerms.getInstance().getPlayerManager().getPlayerData(player);
        List<String> current;

        if (playerData.getStringList(player.getUniqueId().toString()+".permissions") != null) {
            current = playerData.getStringList(player.getUniqueId().toString() + ".permissions");
        } else {
            current = new ArrayList<>();
        }
        if (!current.contains(perm)) { current.add(perm); }

        playerData.set(player.getUniqueId().toString()+".permissions", current);

        attachPerms(Collections.singletonList(perm), data);
    }

    public void givePerms(List<String> perms, Player player) {
        PlayerData data = QuarkPerms.getInstance().getPlayerManager().getPlayerData(player);
        List<String> current;

        if (playerData.getStringList(player.getUniqueId().toString()+".permissions") != null) {
            current = playerData.getStringList(player.getUniqueId().toString() + ".permissions");
        } else {
            current = new ArrayList<>();
        }

        for (String perm : perms) {
            if (!current.contains(perm)) { current.add(perm); }
        }

        playerData.set(player.getUniqueId().toString()+".permissions", current);

        attachPerms(perms, data);
    }

    public void givePerms(String[] perms, Player player) {
        PlayerData data = QuarkPerms.getInstance().getPlayerManager().getPlayerData(player);
        List<String> current;

        if (playerData.getStringList(player.getUniqueId().toString()+".permissions") != null) {
            current = playerData.getStringList(player.getUniqueId().toString() + ".permissions");
        } else {
            current = new ArrayList<>();
        }

        for (String perm : perms) {
            if (!current.contains(perm)) { current.add(perm); }
        }

        playerData.set(player.getUniqueId().toString()+".permissions", current);

        attachPerms(Arrays.asList(perms), data);
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

    public void removePerm(String perm, Player player) {
        if (playerData.getStringList(player.getUniqueId().toString()+".permissions") != null) {
            List<String> perms = playerData.getStringList(player.getUniqueId().toString() + ".permissions");
            perms.remove(perm);
            setPerms(perms, player);
        }
    }

    public void removePerms(List<String> perms, Player player) {
        if (playerData.getStringList(player.getUniqueId().toString()+".permissions") != null) {
            List<String> current = playerData.getStringList(player.getUniqueId().toString() + ".permissions");
            for (String perm : perms) {
                current.remove(perm);
            }
            setPerms(current, player);
        }
    }

    public void removePerms(String[] perms, Player player) {
        if (playerData.getStringList(player.getUniqueId().toString()+".permissions") != null) {
            List<String> current = playerData.getStringList(player.getUniqueId().toString() + ".permissions");
            for (String perm : perms) {
                current.remove(perm);
            }
            setPerms(current, player);
        }
    }

    public void setPerms(List<String> perms, Player player) {
        playerData.set(player.getUniqueId().toString() + ".permissions", perms);
        QuarkPerms.getInstance().savePlayerData();
        QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player);
    }

    public List<String> getPerms(Player player) {
        return QuarkPerms.getInstance().getPlayerManager().getPlayerData(player).getPermissions();
    }

}
