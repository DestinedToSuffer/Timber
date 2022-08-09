package co.vangar.timber;

import co.vangar.timber.leaves.leaf_decay;
import co.vangar.timber.timber.listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Timber extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new listener(), this);
        getServer().getPluginManager().registerEvents(new leaf_decay(), this);
    }
}
