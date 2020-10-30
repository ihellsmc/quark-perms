package me.ihells.quarkperms.utils;

import lombok.Getter;
import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.permission.PermissionManager;
import me.ihells.quarkperms.rank.Rank;
import me.ihells.quarkperms.rank.RankManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class API {

    @Getter private final API instance;
    private final RankManager rankManager = QuarkPerms.getInstance().getRankManager();
    private final PermissionManager permissionManager = QuarkPerms.getInstance().getPermissionManager();

    public API() { instance = this; }

    public List<Rank> getRanks(UUID uuid) {
        return rankManager.getRanks(uuid);
    }
    public Rank getRank(String name) { return rankManager.getRank(name); }
    public Rank getDefaultRank() { return rankManager.getDefaultRank(); }
    public void setDefaultRank(Rank rank) { rankManager.setDefaultRank(rank); }
    public Rank getHighestRank() { return rankManager.getHighestRank(); }

    public void setRank(UUID uuid, Rank rank) { rankManager.setRanks(uuid, rank); }
    public void setRanks(UUID uuid, List<Rank> ranks) { rankManager.setRanks(uuid, ranks.toArray(new Rank[0])); }
    public void removeRank(UUID uuid, Rank rank) { rankManager.removeRanks(uuid, rank); }
    public void addRank(UUID uuid, Rank rank) { rankManager.addRanks(uuid, rank); }

    public void addPermission(UUID uuid, String perm) { permissionManager.givePerms(uuid, perm); }
    public void addPermissions(UUID uuid, List<String> perms) { permissionManager.givePerms(uuid, perms.toArray(new String[0])); }
    public void addPermissions(UUID uuid, String[] perms) { permissionManager.givePerms(uuid, perms); }

    public void removePermission(UUID uuid, String perm) { permissionManager.removePerms(uuid, perm); }
    public void removePermissions(UUID uuid, List<String> perms) { permissionManager.removePerms(uuid, perms.toArray(new String[0])); }
    public void removePermissions(UUID uuid, String[] perms) { permissionManager.removePerms(uuid, perms); }

    public void setPermissions(Player player, List<String> perms) { permissionManager.setPerms(player, perms.toArray(new String[0])); }
    public List<String> getPermissions(UUID uuid) { return permissionManager.getPerms(uuid); }

    public void addPermission(Rank rank, String perm) { rankManager.givePerms(rank, perm); }
    public void addPermissions(Rank rank, List<String> perms) { rankManager.givePerms(rank, perms.toArray(new String[0])); }
    public void addPermissions(Rank rank, String[] perms) { rankManager.givePerms(rank, perms); }

    public void removePermission(Rank rank, String perm) { rankManager.removePerms(rank, perm); }
    public void removePermissions(Rank rank, List<String> perms) { rankManager.removePerms(rank, perms.toArray(new String[0])); }
    public void removePermissions(Rank rank, String[] perms) { rankManager.removePerms(rank, perms); }

    public void setPermissions(Rank rank, List<String> perms) { rankManager.setPerms(rank, perms.toArray(new String[0])); }
    public List<String> getPermissions(Rank rank) { return rankManager.getPerms(rank); }

    public void addInherit(Rank rank, Rank inherit) { rankManager.giveInherits(rank, inherit); }
    public void addInherits(Rank rank, List<Rank> inherits) { rankManager.giveInherits(rank, inherits.toArray(new Rank[0])); }
    public void addInherits(Rank rank, Rank[] inherits) { rankManager.giveInherits(rank, inherits); }

    public void removeInherit(Rank rank, Rank inherit) { rankManager.removeInherits(rank, inherit); }
    public void removeInherits(Rank rank, List<Rank> inherits) { rankManager.removeInherits(rank, inherits.toArray(new Rank[0])); }
    public void removeInherits(Rank rank, Rank[] inherits) { rankManager.removeInherits(rank, inherits); }

    public void setInherits(Rank rank, List<Rank> inherits) { rankManager.setInherits(rank, inherits.toArray(new Rank[0])); }
    public List<Rank> getInherits(Rank rank) { return rankManager.getInherits(rank); }

    public void setPrefix(Rank rank, String prefix) { rankManager.setPrefix(rank, prefix); }

}
