package me.hexsook.dcc.channel;

import java.util.List;

public class FixedChannel extends ChatChannel {

    private final String permission;
    private final List<String> commands;

    public FixedChannel(String name, String permission, String displayName, List<String> commands, int connectionLimit) {
        super(name, displayName, connectionLimit);
        this.permission = permission;
        this.commands = commands;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getCommands() {
        return commands;
    }
}
