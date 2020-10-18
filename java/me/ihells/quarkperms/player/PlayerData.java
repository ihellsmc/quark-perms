package me.ihells.quarkperms.player;

import lombok.Data;
import me.ihells.quarkperms.rank.Rank;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;
import java.util.UUID;

@Data
public class PlayerData {

    private final UUID uuid;

    private PermissionAttachment attachment;
    private String name;

    private List<Rank> ranks;
    private List<String> permissions;

    public void reset() {
        this.name = null; this.ranks = null; this.permissions = null; this.attachment = null;
    }

}
