package me.ihells.quarkperms.utils;

import lombok.Getter;
import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.permission.PermissionManager;
import me.ihells.quarkperms.rank.Rank;
import me.ihells.quarkperms.rank.RankManager;
import org.bukkit.entity.Player;

import java.util.List;

public class API {

    @Getter private final API instance;
    private final RankManager rankManager = QuarkPerms.getInstance().getRankManager();
    private final PermissionManager permissionManager = QuarkPerms.getInstance().getPermissionManager();

    public API() { instance = this; }

    public List<Rank> getRanks(Player player) {
        return rankManager.getRanks(player);
    }
    public Rank getRank(String name) { return rankManager.getRank(name); }
    public Rank getDefaultRank() { return rankManager.getDefaultRank(); }
    public void setDefaultRank(Rank rank) { rankManager.setDefaultRank(rank); }
    public Rank getHighestRank() { return rankManager.getHighestRank(); }

    public void setRanks(Player player, List<Rank> ranks) { rankManager.setRanks(player, ranks); }
    public void removeRank(Player player, Rank rank) { rankManager.removeRank(player, rank); }
    public void addRank(Player player, Rank rank) { rankManager.addRank(player, rank); }

    public void addPermission(Player player, String perm) { permissionManager.givePerm(perm, player); }
    public void addPermissions(Player player, List<String> perms) { permissionManager.givePerms(perms, player); }
    public void addPermissions(Player player, String[] perms) { permissionManager.givePerms(perms, player); }

    public void removePermission(Player player, String perm) { permissionManager.removePerm(perm, player); }
    public void removePermissions(Player player, List<String> perms) { permissionManager.removePerms(perms, player); }
    public void removePermissions(Player player, String[] perms) { permissionManager.removePerms(perms, player); }

    public void setPermissions(Player player, List<String> perms) { permissionManager.setPerms(perms, player); }
    public List<String> getPermissions(Player player) { return permissionManager.getPerms(player); }

    public void addPermission(Rank rank, String perm) { rankManager.givePerm(rank, perm); }
    public void addPermissions(Rank rank, List<String> perms) { rankManager.givePerms(rank, perms); }
    public void addPermissions(Rank rank, String[] perms) { rankManager.givePerms(rank, perms); }

    public void removePermission(Rank rank, String perm) { rankManager.removePerm(rank, perm); }
    public void removePermissions(Rank rank, List<String> perms) { rankManager.removePerms(rank, perms); }
    public void removePermissions(Rank rank, String[] perms) { rankManager.removePerms(rank, perms); }

    public void setPermissions(Rank rank, List<String> perms) { rankManager.setPerms(rank, perms); }
    public List<String> getPermissions(Rank rank) { return rankManager.getPerms(rank); }

    public void addInherit(Rank rank, Rank inherit) { rankManager.giveInherit(rank, inherit); }
    public void addInherits(Rank rank, List<Rank> inherits) { rankManager.giveInherits(rank, inherits); }
    public void addInherits(Rank rank, Rank[] inherits) { rankManager.giveInherits(rank, inherits); }

    public void removeInherit(Rank rank, Rank inherit) { rankManager.removeInherit(rank, inherit); }
    public void removeInherits(Rank rank, List<Rank> inherits) { rankManager.removeInherits(rank, inherits); }
    public void removeInherits(Rank rank, Rank[] inherits) { rankManager.removeInherits(rank, inherits); }

    public void setInherits(Rank rank, List<Rank> inherits) { rankManager.setInherits(rank, inherits); }
    public List<Rank> getInherits(Rank rank) { return rankManager.getInherits(rank); }

    public void setPrefix(Rank rank, String prefix) { rankManager.setPrefix(rank, prefix); }

}
