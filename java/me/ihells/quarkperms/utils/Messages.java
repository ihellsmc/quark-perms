package me.ihells.quarkperms.utils;

import me.ihells.quarkperms.QuarkPerms;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {

    private static final YamlConfiguration messages = QuarkPerms.getInstance().getMessages().getConfiguration();

    public static final String NO_PERMISSION = CC.trns(messages.getString("no-permission"));
    public static final String INVALID_USAGE = CC.trns(messages.getString("invalid-usage"));
    public static final String INVALID_PLAYER = CC.trns(messages.getString("invalid-player"));
    public static final String INVALID_RANK = CC.trns(messages.getString("invalid-rank"));

    public static final String ON_RANK_CHANGE = CC.trns(messages.getString("on-rank-change"));
    public static final String ON_RANK_ADD = CC.trns(messages.getString("on-rank-add"));
    public static final String ON_RANK_REMOVE = CC.trns(messages.getString("on-rank-remove"));

    public static final String ON_INHERIT_ADD = CC.trns(messages.getString("on-inherit-add"));
    public static final String ON_INHERIT_REMOVE = CC.trns(messages.getString("on-inherit-remove"));

    public static final String PLAYER_PERM_ADD = CC.trns(messages.getString("player-permission-add"));
    public static final String PLAYER_PERM_REMOVE = CC.trns(messages.getString("player-permission-remove"));
    public static final String RANK_PERM_ADD = CC.trns(messages.getString("rank-permission-add"));
    public static final String RANK_PERM_REMOVE = CC.trns(messages.getString("rank-permission-remove"));

    public static final String ON_SET_DEFAULT_RANK = CC.trns(messages.getString("on-set-default-rank"));
    public static final String ON_SET_RANK_PREFIX = CC.trns(messages.getString("on-set-rank-prefix"));

}
