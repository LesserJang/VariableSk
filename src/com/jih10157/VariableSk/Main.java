package com.jih10157.VariableSk;

import ch.njol.skript.Skript;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            Skript.registerAddon(this).loadClasses("com.jih10157.VariableSk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
