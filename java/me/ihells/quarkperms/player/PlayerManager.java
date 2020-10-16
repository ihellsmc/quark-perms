package me.ihells.quarkperms.player;

import me.ihells.quarkperms.QuarkPerms;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private final YamlConfiguration playerDataFile = QuarkPerms.getInstance().getPlayerData().getConfiguration();
    private final List<PlayerData> playerData = new ArrayList<>();

}
