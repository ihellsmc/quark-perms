package me.ihells.quarkperms;

import com.qrakn.phoenix.lang.file.type.BasicConfigurationFile;
import lombok.Getter;
import lombok.Setter;
import me.ihells.quarkperms.commands.SetRankCommand;
import me.ihells.quarkperms.listeners.ChatListener;
import me.ihells.quarkperms.listeners.JoinListener;
import me.ihells.quarkperms.listeners.QuitListener;
import me.ihells.quarkperms.permission.PermissionManager;
import me.ihells.quarkperms.player.PlayerManager;
import me.ihells.quarkperms.rank.Rank;
import me.ihells.quarkperms.rank.RankManager;
import me.ihells.quarkperms.utils.API;
import me.ihells.quarkperms.utils.framework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class QuarkPerms extends JavaPlugin {

    @Getter private static QuarkPerms instance;
    @Getter private static API api;
    @Getter private RankManager rankManager;
    @Getter private PlayerManager playerManager;
    @Getter private PermissionManager permissionManager;

    @Getter @Setter private BasicConfigurationFile ranks;
    @Getter @Setter private BasicConfigurationFile playerData;
    @Getter @Setter private BasicConfigurationFile ladders;
    @Getter @Setter private BasicConfigurationFile messages;

    private CommandFramework framework;

    @Override
    public void onEnable() {
        instance = this; api = new API().getInstance();
        framework = new CommandFramework(this);
        registerConfigs();
        registerManagers();
        registerListeners();
        registerCommands();
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
        permissionManager = new PermissionManager();
    }

    protected void registerCommands() {
        new SetRankCommand(framework);
    }

    protected void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }

    protected void saveConfigs() {
        try {
            getRanks().getConfiguration().save(getRanks().getFile());
            getPlayerData().getConfiguration().save(getPlayerData().getFile());
            getLadders().getConfiguration().save(getLadders().getFile());
            getMessages().getConfiguration().save(getMessages().getFile());
        } catch (IOException ignored) {}
    }

    protected void reloadConfigs() {
        try {
            ranks.getConfiguration().load(getRanks().getFile());
            playerData.getConfiguration().load(getPlayerData().getFile());
            ladders.getConfiguration().load(getLadders().getFile());
            messages.getConfiguration().load(getMessages().getFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerData() {
        try {
            getPlayerData().getConfiguration().save(getPlayerData().getFile());
        } catch (IOException ignored) {}
    }

    public void saveRankData() {
        try {
            getRanks().getConfiguration().save(getRanks().getFile());
        } catch (IOException ignored) {}
    }

    public void reloadAllData() {
        // remove currently active player data
        for (Player player : Bukkit.getOnlinePlayers()) { playerManager.removePlayer(player); }
        // remove currently active rank data
        rankManager.removeRanks();
        // reload rank and player data
        reloadConfigs();
        // set updated rank data
        rankManager.registerRanks();
        // set updated player data
        for (Player player : Bukkit.getServer().getOnlinePlayers()) { playerManager.registerPlayer(player); }
    }

    public void reloadRankData(Rank rank) {
        rankManager.reloadRankData();
        // reload currently active player data if rank is changed
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playerManager.getPlayerData(player).getRanks().contains(rank)) {
                playerManager.reloadPlayer(player);
            }
        }
    }

}
