package net.caspervg.xpbank;

import net.caspervg.xpbank.command.CheckCommandExecutor;
import net.caspervg.xpbank.command.DepositCommandExecutor;
import net.caspervg.xpbank.command.WithdrawCommandExecutor;
import net.caspervg.xpbank.listener.ExpLevelListener;
import net.caspervg.xpbank.persistence.Persistor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class XPBank extends JavaPlugin implements Listener{

    HashMap<UUID, Integer> bankMap = null;
    private String persistPath;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        persistPath = getDataFolder() + File.separator + "xp-bank.bin";
        File file = new File(persistPath);

        if (file.exists()) {
            this.bankMap = Persistor.loadBank(persistPath);
        } else {
            this.bankMap = new HashMap<UUID, Integer>();
        }

        getServer().getPluginManager().registerEvents(new ExpLevelListener(), this);
        getCommand("xp-deposit").setExecutor(new DepositCommandExecutor(this));
        getCommand("xp-withdraw").setExecutor(new WithdrawCommandExecutor(this));
        getCommand("xp-check").setExecutor(new CheckCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        Persistor.saveBank(persistPath, this.bankMap);
    }

    public HashMap<UUID, Integer> getBankMap() {
        return bankMap;
    }
}
