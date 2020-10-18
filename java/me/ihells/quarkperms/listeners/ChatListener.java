package me.ihells.quarkperms.listeners;

import me.ihells.quarkperms.QuarkPerms;
import me.ihells.quarkperms.player.PlayerData;
import me.ihells.quarkperms.utils.CC;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {

    private final YamlConfiguration messages = QuarkPerms.getInstance().getMessages().getConfiguration();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        PlayerData data = QuarkPerms.getInstance().getPlayerManager().getPlayerData(e.getPlayer());
        String prefix = data.getRanks().get(0).getPrefix();

        String format = messages.getString("chat-format.default");
        Set<String> registeredChatFormats = messages.getConfigurationSection("chat-format").getKeys(false);

        if (registeredChatFormats.contains(data.getRanks().get(0).getName())) {
            format = messages.getString("chat-format." + data.getRanks().get(0).getName());
        }

        format = format.replace("{prefix}", prefix);
        format = format.replace("{player}", e.getPlayer().getName());
        format = CC.trns(format).replace("{message}", e.getMessage());

        e.setFormat(format);

    }
}
