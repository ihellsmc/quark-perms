package me.ihells.quarkperms.player;

import lombok.Getter;
import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.rank.Rank;
import me.ihells.quarkperms.rank.RankManager;
import me.ihells.quarkperms.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    @Getter private final List<PlayerData> playerData = new ArrayList<>();
    private final YamlConfiguration playerDataFile = QuarkPerms.getInstance().getPlayerData().getConfiguration();

    private final RankManager rankManager = QuarkPerms.getInstance().getRankManager();

    public PlayerManager() { init(); }

    protected void init() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) { registerPlayer(player); }
    }

    public void registerPlayer(Player player) {

        List<Rank> ranks = new ArrayList<>(); List<String> permissions = new ArrayList<>();
        PlayerData data;

        // if they DO have player data in the file
        if (playerDataFile.getConfigurationSection("").getKeys(false).contains(player.getUniqueId().toString())) {

            // if they have ranks in the config
            if (playerDataFile.getStringList(player.getUniqueId().toString() + ".ranks") != null) {
                // set rank they have
                for (String rankName : playerDataFile.getStringList(player.getUniqueId().toString() + ".ranks")) {
                    Rank rank = rankManager.getRank(rankName);
                    if (rank != null) {
                        ranks.add(rank);
                        System.out.println(rank.getName());
                    }
                }
            }
            // if they have perms in the config
            if (playerDataFile.getStringList(player.getUniqueId().toString() + ".permissions") != null) {
                // set perms they have
                permissions.addAll(playerDataFile.getStringList(player.getUniqueId().toString() + ".permissions"));
            }

        } else {
            // set default rank and no new permissions
            ranks.add(rankManager.getDefaultRank());

            List<String> ranksToSet = new ArrayList<>();
            for (Rank rank : ranks) { ranksToSet.add(rank.getName()); }
            playerDataFile.set(player.getUniqueId().toString()+".ranks", ranksToSet);

            QuarkPerms.getInstance().savePlayerData();

        }

        // if they have data, get it, else create it
        if (getPlayerData(player) != null) {
            data = getPlayerData(player);
        } else {
            data = new PlayerData(player.getUniqueId());
        }

        if (ranks.isEmpty()) { ranks.add(rankManager.getDefaultRank()); }

        PermissionAttachment attachment = player.addAttachment(QuarkPerms.getInstance());
        data.setAttachment(attachment); // set perm attachment
        data.setName(player.getName()); // set name
        data.setRanks(ranks); // set ranks
        data.setPermissions(permissions); // set permissions

        playerData.add(data);

        QuarkPerms.getInstance().getPermissionManager().initialSet(data);

    }

    public void removePlayer(Player player) {
        PlayerData data = getPlayerData(player);
        playerData.remove(data);
        if (data.getAttachment() != null) { player.removeAttachment(data.getAttachment()); }
        data.reset();
    }

    public void reloadPlayer(Player player) { removePlayer(player); registerPlayer(player); }

    public PlayerData getPlayerData(Player player) {
        for (PlayerData data : playerData) { if (data.getUuid().equals(player.getUniqueId())) return data; }
        return null;
    }

}
