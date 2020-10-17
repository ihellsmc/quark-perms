package me.ihells.quarkperms.rank;

import lombok.Data;

import java.util.List;

@Data
public class Rank {

    private final String name;

    private String prefix;
    private int priority;
    private boolean isDefault = false;

    private List<Rank> inheritance;
    private List<String> permissions;

    public void reset() {
        this.prefix = null; this.priority = -1; this.inheritance = null; this.permissions = null;
    }

}
