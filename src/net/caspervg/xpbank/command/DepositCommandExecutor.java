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

public class DepositCommandExecutor implements CommandExecutor {

    private XPBank bank;

    public DepositCommandExecutor(XPBank bank) {
        this.bank = bank;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 1) {
            return false;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Language.getBundle().getString("xp-bank.error.notplayer"));
            return true;
        }

        Player player = (Player) commandSender;
        int deposited = 0;

        try {
            deposited = Integer.parseInt(strings[0]);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (deposited < 0) {
            commandSender.sendMessage(Language.getBundle().getString("xp-bank.command.deposit.negative"));
            return true;
        }

        depositLevels(player, deposited);
        return true;
    }

    private void depositLevels(Player player, int deposited) {
        UUID id = player.getUniqueId();
        HashMap<UUID, Integer> bankMap = bank.getBankMap();

        int current = 0;
        if (bankMap.containsKey(id)) {
            current = bankMap.get(id);
        }

        if (deposited <= player.getLevel()) {
            current += deposited;
            bankMap.put(id, current);
            player.setLevel(player.getLevel() - deposited);

            MessageFormat formatter = new MessageFormat(Language.getBundle().getString("xp-bank.command.deposit.success"));
            player.sendMessage(formatter.format(new Object[]{current}));
        } else {
            player.sendMessage(Language.getBundle().getString("xp-bank.command.deposit.failure"));
        }
    }
}
