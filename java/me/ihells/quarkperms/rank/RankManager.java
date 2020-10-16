package me.ihells.quarkperms.rank;

import lombok.Getter;
import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.utils.CC;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RankManager {

    private final YamlConfiguration ranksFile = QuarkPerms.getInstance().getRanks().getConfiguration();
    private final Set<String> rankNames = ranksFile.getConfigurationSection("").getKeys(false);

    @Getter public final Set<Rank> ranks = new HashSet<>();

    public RankManager() { init(); }

    private void init() {
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

    public void setRank(Player player, Rank rank) {

    }

    public void removeRank(Player player, Rank rank) {

    }

    public void addRank(Player player, Rank rank) {

    }

}
