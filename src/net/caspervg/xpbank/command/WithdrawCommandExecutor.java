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

public class WithdrawCommandExecutor implements CommandExecutor {

    private XPBank bank;

    public WithdrawCommandExecutor(XPBank bank) {
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
        int withdrawn = 0;

        try {
            withdrawn = Integer.parseInt(strings[0]);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (withdrawn < 0) {
            commandSender.sendMessage(Language.getBundle().getString("xp-bank.command.withdraw.negative"));
            return true;
        }

        withdrawLevels(player, withdrawn);
        return true;
    }

    private void withdrawLevels(Player player, int withdrawn) {
        UUID id = player.getUniqueId();
        HashMap<UUID, Integer> bankMap = bank.getBankMap();

        int current = 0;
        if (bankMap.containsKey(id)) {
            current = bankMap.get(id);
        }

        if (current >= withdrawn) {
            current -= withdrawn;
            bankMap.put(id, current);
            player.setLevel(player.getLevel() + withdrawn);

            MessageFormat formatter = new MessageFormat(Language.getBundle().getString("xp-bank.command.withdraw.success"));
            player.sendMessage(formatter.format(new Object[]{current}));
        } else {
            player.sendMessage(Language.getBundle().getString("xp-bank.command.withdraw.failure"));
        }
    }
}
