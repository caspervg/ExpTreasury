package net.caspervg.exptreasury;

import net.caspervg.exptreasury.command.CheckCommandExecutor;
import net.caspervg.exptreasury.command.DepositCommandExecutor;
import net.caspervg.exptreasury.command.ExchangeCommandExecutor;
import net.caspervg.exptreasury.command.WithdrawCommandExecutor;
import net.caspervg.exptreasury.i18n.Language;
import net.caspervg.exptreasury.listener.ExpLevelListener;
import net.caspervg.exptreasury.persistence.Persistor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class ExpTreasury extends JavaPlugin implements Listener{

    HashMap<UUID, Integer> bankMap = null;
    private String persistPath;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getLogger().info(Language.getBundle().getString("exp-treasury.info.firsttime"));
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        persistPath = getDataFolder() + File.separator + "exp-treasury.bin";
        File file = new File(persistPath);

        if (file.exists()) {
            this.bankMap = Persistor.loadBank(persistPath);
        } else {
            this.bankMap = new HashMap<UUID, Integer>();
        }

        getServer().getPluginManager().registerEvents(new ExpLevelListener(this), this);
        getCommand("xp-deposit").setExecutor(new DepositCommandExecutor(this));
        getCommand("xp-withdraw").setExecutor(new WithdrawCommandExecutor(this));
        getCommand("xp-check").setExecutor(new CheckCommandExecutor(this));
        getCommand("xp-exchange").setExecutor(new ExchangeCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        Persistor.saveBank(persistPath, this.bankMap);
    }

    public HashMap<UUID, Integer> getBankMap() {
        return bankMap;
    }
}
