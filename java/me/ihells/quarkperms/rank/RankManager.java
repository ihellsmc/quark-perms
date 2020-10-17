package me.ihells.quarkperms.rank;

import lombok.Getter;
import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.utils.CC;
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
        for (Rank rank : ranks) { if (rank.getName().equals(name)) return rank; }
        return null;
    }

    public Rank getDefaultRank() {
        for (Rank rank : ranks) { if (rank.isDefault()) { return rank; } }
        return null;
    }

    public Rank getHighestRank() { return new ArrayList<>(ranks).get(ranks.size()-1); }

    public List<Rank> getRanks(Player player) {
        return QuarkPerms.getInstance().getPlayerManager().getPlayerData(player).getRanks();
    }

    public void setRanks(Player player, List<Rank> ranks) {
        List<String> rankNames = new ArrayList<>();
        for (Rank rank : ranks) { rankNames.add(rank.getName()); }
        playerData.set(player.getUniqueId().toString()+".ranks", rankNames);
        QuarkPerms.getInstance().savePlayerData();
        QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player);
    }

    public void removeRank(Player player, Rank rank) {
        List<String> rankNames = playerData.getStringList(player.getUniqueId().toString()+".ranks");
        rankNames.remove(rank.getName());
        playerData.set(player.getUniqueId().toString()+".ranks", rankNames);
        QuarkPerms.getInstance().savePlayerData();
        QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player);
    }

    public void addRank(Player player, Rank rank) {
        List<String> rankNames = playerData.getStringList(player.getUniqueId().toString()+".ranks");
        if (!rankNames.contains(rank.getName())) {
            rankNames.add(rank.getName());
            playerData.set(player.getUniqueId().toString() + ".ranks", rankNames);
            QuarkPerms.getInstance().savePlayerData();
            QuarkPerms.getInstance().getPlayerManager().reloadPlayer(player);
        }
    }

    public void removeRanks() {
        for (Rank rank : ranks) { rank.reset(); }
        ranks.clear();
    }

    public void givePerms(Rank rank, List<String> perms) {
        List<String> current = rank.getPermissions();
        for (String perm : perms) { if (!current.contains(perm)) current.add(perm); }
        setPerms(rank, current);
    }

    public void givePerms(Rank rank, String[] perms) {
        List<String> current = rank.getPermissions();
        for (String perm : perms) { if (!current.contains(perm)) current.add(perm); }
        setPerms(rank, current);
    }

    public void givePerm(Rank rank, String perm) {
        List<String> current = rank.getPermissions();
        if (!current.contains(perm)) current.add(perm);
        setPerms(rank, current);
    }

    public void setPerms(Rank rank, List<String> perms) {
        ranksFile.set(rank.getName()+".permissions", perms);
        QuarkPerms.getInstance().saveRankData();
        QuarkPerms.getInstance().reloadRankData(rank);
    }

    public List<String> getPerms(Rank rank) { return rank.getPermissions(); }

    public void removePerms(Rank rank, List<String> perms) {
        List<String> current = rank.getPermissions();
        for (String perm : perms) { current.remove(perm); }
        setPerms(rank, current);
    }

    public void removePerms(Rank rank, String[] perms) {
        List<String> current = rank.getPermissions();
        for (String perm : perms) { current.remove(perm); }
        setPerms(rank, current);
    }

    public void removePerm(Rank rank, String perm) {
        List<String> current = rank.getPermissions();
        current.remove(perm);
        setPerms(rank, current);
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

    public void giveInherit(Rank rank, Rank inherit) {
        List<Rank> current = rank.getInheritance();
        if (!current.contains(inherit)) { current.add(inherit); }
        setInherits(rank, current);
    }

    public void giveInherits(Rank rank, List<Rank> inherits) {
        List<Rank> current = rank.getInheritance();
        for (Rank inherit : inherits) {
            if (!current.contains(inherit)) { current.add(inherit); }
        }
        setInherits(rank, current);
    }

    public void giveInherits(Rank rank, Rank[] inherits) {
        List<Rank> current = rank.getInheritance();
        for (Rank inherit : inherits) {
            if (!current.contains(inherit)) { current.add(inherit); }
        }
        setInherits(rank, current);
    }

    public void removeInherit(Rank rank, Rank inherit) {
        List<Rank> current = rank.getInheritance();
        current.remove(inherit);
        setInherits(rank, current);
    }

    public void removeInherits(Rank rank, List<Rank> inherits) {
        List<Rank> current = rank.getInheritance();
        for (Rank inherit : inherits) { current.remove(inherit); }
        setInherits(rank, current);
    }

    public void removeInherits(Rank rank, Rank[] inherits) {
        List<Rank> current = rank.getInheritance();
        for (Rank inherit : inherits) { current.remove(inherit); }
        setInherits(rank, current);
    }

    public void setInherits(Rank rank, List<Rank> inherits) {
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

}
