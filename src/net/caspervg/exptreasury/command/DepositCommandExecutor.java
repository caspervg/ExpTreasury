package net.caspervg.exptreasury.command;

import net.caspervg.exptreasury.ExpTreasury;
import net.caspervg.exptreasury.i18n.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.UUID;

public class DepositCommandExecutor implements CommandExecutor {

    private ExpTreasury bank;

    public DepositCommandExecutor(ExpTreasury bank) {
        this.bank = bank;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 1) {
            return false;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Language.getBundle().getString("exp-treasury.error.notplayer"));
            return true;
        }

        Player player = (Player) commandSender;
        int deposited;

        try {
            deposited = Integer.parseInt(strings[0]);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (deposited < 0) {
            commandSender.sendMessage(Language.getBundle().getString("exp-treasury.command.deposit.negative"));
            return true;
        }

        depositLevels(player, deposited);
        return true;
    }

    private void depositLevels(Player player, int deposited) {
        UUID id = player.getUniqueId();
        HashMap<UUID, Integer> bankMap = bank.getBankMap();

        int current = 0;
        int maximum = bank.getConfig().getInt("maximum");
        if (bankMap.containsKey(id)) {
            current = bankMap.get(id);
        }

        int after = Math.round((float) (current + bank.getConfig().getDouble("percentage")/100.0 * deposited));


        if (after > maximum && maximum >= 0) {
            MessageFormat formatter = new MessageFormat(Language.getBundle().getString("exp-treasury.command.deposit.toomuch"));
            player.sendMessage(formatter.format(new Object[]{maximum}));
        } else if (deposited <= player.getLevel()) {
            current = after;
            bankMap.put(id, current);
            player.setLevel(player.getLevel() - deposited);

            MessageFormat formatter = new MessageFormat(Language.getBundle().getString("exp-treasury.command.deposit.success"));
            player.sendMessage(formatter.format(new Object[]{current}));
        } else {
            player.sendMessage(Language.getBundle().getString("exp-treasury.command.deposit.failure"));
        }
    }
}
