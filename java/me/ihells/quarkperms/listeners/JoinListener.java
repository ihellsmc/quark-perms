package me.ihells.quarkperms.listeners;

import me.ihells.quarkperms.QuarkPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) { QuarkPerms.getInstance().getPlayerManager().registerPlayer(e.getPlayer()); }

}
