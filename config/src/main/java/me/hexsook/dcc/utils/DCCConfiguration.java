package me.hexsook.dcc.utils;

import hexsook.originext.config.file.FileConfiguration;
import hexsook.originext.config.file.adapter.YamlFileConfiguration;

import java.io.File;

public class DCCConfiguration {

    private final File pluginFolder;

    private FileConfiguration config;
    private FileConfiguration channels;
    private FileConfiguration messages;

    public DCCConfiguration(File pluginFolder) {
        this.pluginFolder = pluginFolder;
    }

    public void load() throws Exception {
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        config = new FileConfiguration(new File(pluginFolder, "config.yml"),
                DCCConfiguration.class.getResourceAsStream("/config.yml"), YamlFileConfiguration.class);
        channels = new FileConfiguration(new File(pluginFolder, "channels.yml"),
                DCCConfiguration.class.getResourceAsStream("/channels.yml"), YamlFileConfiguration.class);
        messages = new FileConfiguration(new File(pluginFolder, "messages.yml"),
                DCCConfiguration.class.getResourceAsStream("/messages.yml"), YamlFileConfiguration.class);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public FileConfiguration getChannels() {
        return channels;
    }
}

