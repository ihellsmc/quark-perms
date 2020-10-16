package me.ihells.quarkperms;

import com.qrakn.phoenix.lang.file.type.BasicConfigurationFile;
import lombok.Getter;
import lombok.Setter;
import me.ihells.quarkperms.player.PlayerManager;
import me.ihells.quarkperms.rank.RankManager;
import me.ihells.quarkperms.utils.API;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class QuarkPerms extends JavaPlugin {

    @Getter private static QuarkPerms instance;
    @Getter private API api;
    @Getter private RankManager rankManager;
    @Getter private PlayerManager playerManager;

    @Getter @Setter private BasicConfigurationFile ranks;
    @Getter @Setter private BasicConfigurationFile playerData;
    @Getter @Setter private BasicConfigurationFile ladders;
    @Getter @Setter private BasicConfigurationFile messages;

    @Override
    public void onEnable() {
        instance = this; api = new API().getInstance();
        registerConfigs();
        registerManagers();
    }

    @Override
    public void onDisable() {

    }

    protected void registerConfigs() {
        ranks = new BasicConfigurationFile(this, "ranks");
        playerData = new BasicConfigurationFile(this, "player-data");
        ladders = new BasicConfigurationFile(this, "ladders");
        messages = new BasicConfigurationFile(this, "messages");
    }

    protected void registerManagers() {
        rankManager = new RankManager();
        playerManager = new PlayerManager();
    }

    private void saveConfigs() {
        try {
            getRanks().getConfiguration().save(getRanks().getFile());
            getPlayerData().getConfiguration().save(getPlayerData().getFile());
            getLadders().getConfiguration().save(getLadders().getFile());
            getMessages().getConfiguration().save(getMessages().getFile());
        } catch (IOException ignored) {}
    }

    private void reloadConfigs() {
        try {
            ranks.getConfiguration().load(getRanks().getFile());
            playerData.getConfiguration().load(getPlayerData().getFile());
            ladders.getConfiguration().load(getLadders().getFile());
            messages.getConfiguration().load(getMessages().getFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
