package me.ihells.quarkperms.rank;

import lombok.Getter;
import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class RankManager {

    private final YamlConfiguration ranksFile = QuarkPerms.getInstance().getRanks().getConfiguration();
    private final YamlConfiguration playerData = QuarkPerms.getInstance().getPlayerData().getConfiguration();
    private final Set<String> rankNames = ranksFile.getConfigurationSection("").getKeys(false);

    @Getter public final Set<Rank> ranks = new HashSet<>();

    public RankManager() { registerRanks(); }

    public void registerRanks() {
        int index = 0;

        for (String name : rankNames) {
            Rank rank = new Rank(name);

            rank.setPriority(index); // set priority

            if (ranksFile.get(name+".default") != null && ranksFile.getBoolean(name+".default")) { // set default
                rank.setDefault(true);
            }

            if (ranksFile.get(name+".prefix") != null) { // set prefix
                rank.setPrefix(CC.trns(ranksFile.getString(name+".prefix")));
            }

            if (ranksFile.get(name+".permissions") != null) { // set perms
                rank.setPermissions(ranksFile.getStringList(name+".permissions"));
            }

            ranks.add(rank);

            index++;
        }

        setInheritance();

    }

    private void setInheritance() {
        for (Rank rank : ranks) {
            List<Rank> inherits = new ArrayList<>();
            if (ranksFile.get(rank.getName()+".inheritance") != null) {
                for (String inherit : ranksFile.getStringList(rank.getName()+".inheritance")) {
                    inherits.add(getRank(inherit));
                }
            }
            rank.setInheritance(inherits);
        }
    }

    public Rank getRank(String name) {
        for (Rank rank : ranks) { if (rank.getName().equalsIgnoreCase(name)) return rank; }
        return null;
    }

    public Rank getDefaultRank() {
        for (Rank rank : ranks) { if (rank.isDefault()) { return rank; } }
        return null;
    }

    public Rank getHighestRank() { return new ArrayList<>(ranks).get(ranks.size()-1); }

    public List<Rank> getRanks(UUID uuid) {
        List<Rank> toReturn = new ArrayList<>();
        if (playerData.getStringList(uuid.toString() + ".ranks") != null) {
            for (String rank : playerData.getStringList(uuid.toString() + ".ranks")) {
                if (getRank(rank) != null) toReturn.add(getRank(rank));
            }
        }
        return toReturn;
    }

    public void setRanks(UUID uuid, Rank... ranks) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player == null) return;

        List<String> rankNames = new ArrayList<>();
        for (Rank rank : ranks) { rankNames.add(rank.getName()); }

        playerData.set(uuid.toString() + ".ranks", sortRankPriorityNames(rankNames));
        QuarkPerms.getInstance().savePlayerData();

        if (player.isOnline()) QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player.getPlayer());
    }

    public void removeRanks() {
        for (Rank rank : ranks) { rank.reset(); }
        ranks.clear();
    }

    public void removeRanks(UUID uuid, Rank... ranks) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player == null) return;

        List<String> rankNames = new ArrayList<>();
        if (playerData.getStringList(uuid.toString() + ".ranks") != null) {
            rankNames = playerData.getStringList(uuid.toString() + ".ranks");
        }

        for (Rank rank : ranks) {
            rankNames.remove(rank.getName());
        }

        playerData.set(uuid.toString() + ".ranks", sortRankPriorityNames(rankNames));
        QuarkPerms.getInstance().savePlayerData();

        if (player.isOnline()) QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player.getPlayer());

    }

    public void addRanks(UUID uuid, Rank... ranks) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player == null) return;

        List<String> rankNames = new ArrayList<>();
        if (playerData.getStringList(uuid.toString() + ".ranks") != null) {
            rankNames = playerData.getStringList(uuid.toString() + ".ranks");
        }

        for (Rank rank : ranks) {
            if (!rankNames.contains(rank.getName())) {
                rankNames.add(rank.getName());
            }
        }

        playerData.set(uuid.toString() + ".ranks", sortRankPriorityNames(rankNames));
        QuarkPerms.getInstance().savePlayerData();

        if (player.isOnline()) QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player.getPlayer());

    }

    public void givePerms(Rank rank, String... perms) {
        List<String> current = rank.getPermissions();
        for (String perm : perms) { if (!current.contains(perm)) current.add(perm); }
        setPerms(rank, current.toArray(new String[0]));
    }

    public void setPerms(Rank rank, String... perms) {
        ranksFile.set(rank.getName()+".permissions", perms);
        QuarkPerms.getInstance().saveRankData();
        QuarkPerms.getInstance().reloadRankData(rank);
    }

    public List<String> getPerms(Rank rank) { return rank.getPermissions(); }

    public void removePerms(Rank rank, String... perms) {
        List<String> current = rank.getPermissions();
        for (String perm : perms) { current.remove(perm); }
        setPerms(rank, current.toArray(new String[0]));
    }

    public void setPrefix(Rank rank, String prefix) {
        ranksFile.set(rank.getName()+".prefix", prefix);
        QuarkPerms.getInstance().saveRankData();
        QuarkPerms.getInstance().reloadRankData(rank);
    }

    public void setDefaultRank(Rank rank) {
        for (Rank r : ranks) {
            if (ranksFile.get(r.getName()+".default") != null) { ranksFile.set(r.getName()+".default", false); }
        }
        ranksFile.set(rank.getName()+".default", true);
        QuarkPerms.getInstance().saveRankData();
        QuarkPerms.getInstance().reloadRankData(rank);
    }

    public void giveInherits(Rank rank, Rank... inherits) {
        List<Rank> current = rank.getInheritance();
        for (Rank inherit : inherits) {
            if (!current.contains(inherit)) { current.add(inherit); }
        }
        setInherits(rank, current.toArray(new Rank[0]));
    }

    public void removeInherits(Rank rank, Rank... inherits) {
        List<Rank> current = rank.getInheritance();
        for (Rank inherit : inherits) { current.remove(inherit); }
        setInherits(rank, current.toArray(new Rank[0]));
    }

    public void setInherits(Rank rank, Rank... inherits) {
        ranksFile.set(rank.getName()+".inheritance", inherits);
        QuarkPerms.getInstance().saveRankData();
        QuarkPerms.getInstance().reloadRankData(rank);
    }

    public List<Rank> getInherits(Rank rank) { return rank.getInheritance(); }

    public void reloadRankData() {
        for (Rank rank : ranks) { rank.reset(); }
        ranks.clear();
        registerRanks();
    }

    public List<Rank> sortRankPriority(List<Rank> toSort) {
        List<Rank> sorted = new ArrayList<>();
        for (int i = 0; i < ranks.size(); i++) {
            for (Rank rank : toSort) { if (rank.getPriority() == i) sorted.add(rank); }
        }
        return sorted;
    }

    public List<String> sortRankPriorityNames(List<String> rankNames) {
        List<Rank> ranks = new ArrayList<>();
        List<String> sorted = new ArrayList<>();
        for (String name : rankNames) {
            if (getRank(name) != null && !ranks.contains(getRank(name))) ranks.add(getRank(name));
        }
        ranks = sortRankPriority(ranks);
        for (Rank rank : ranks) { sorted.add(rank.getName()); }
        return sorted;
    }

}
