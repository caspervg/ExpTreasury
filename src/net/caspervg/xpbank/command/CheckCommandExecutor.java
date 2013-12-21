package net.caspervg.xpbank.command;

import net.caspervg.xpbank.XPBank;
import net.caspervg.xpbank.i18n.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.UUID;

public class CheckCommandExecutor implements CommandExecutor {

    private XPBank bank;

    public CheckCommandExecutor(XPBank bank) {
        this.bank = bank;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Language.getBundle().getString("xp-bank.error.notplayer"));
            return true;
        }

        Player player = (Player) commandSender;
        checkLevels(player);
        return true;
    }


    private void checkLevels(Player player) {
        UUID id = player.getUniqueId();
        HashMap<UUID, Integer> bankMap = bank.getBankMap();

        int current = 0;
        if (bankMap.containsKey(id)) {
            current = bankMap.get(id);
        }

        MessageFormat formatter = new MessageFormat(Language.getBundle().getString("xp-bank.command.check"));
        player.sendMessage(formatter.format(new Object[]{current}));
    }

}
