package me.ihells.quarkperms.player;

import lombok.Data;
import me.ihells.quarkperms.rank.Rank;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerData {

    private final UUID uuid;

    private PermissionAttachment attachment;
    private String name;

    private List<Rank> ranks = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

}
