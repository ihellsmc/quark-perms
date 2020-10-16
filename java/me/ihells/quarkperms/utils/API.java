package me.ihells.quarkperms.utils;

import lombok.Getter;
import me.ihells.quarkperms.rank.Rank;
import org.bukkit.entity.Player;

import java.util.List;

public class API {

    @Getter private final API instance;

    public API() { instance = this; }

    public List<Rank> getRanks(Player player) {
        return null;
    }

    public void setRank(Player player, Rank rank) {

    }

    public void removeRank(Player player, Rank rank) {

    }

    public void addRank(Player player, Rank rank) {

    }



}
