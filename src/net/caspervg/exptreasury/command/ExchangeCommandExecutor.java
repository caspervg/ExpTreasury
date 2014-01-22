package net.caspervg.exptreasury.command;

import net.caspervg.exptreasury.ExpTreasury;
import net.caspervg.exptreasury.i18n.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class ExchangeCommandExecutor implements CommandExecutor {

    ExpTreasury bank;

    public ExchangeCommandExecutor(ExpTreasury bank) {
        this.bank = bank;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Language.getBundle().getString("exp-treasury.error.notplayer"));
            return true;
        }

        Player player = (Player) commandSender;
        int percentage = bank.getConfig().getInt("percentage");
        MessageFormat formatter1 = new MessageFormat(Language.getBundle().getString("exp-treasury.command.exchange.msg1"));
        player.sendMessage(formatter1.format(new Object[]{percentage}));

        MessageFormat formatter2 = new MessageFormat(Language.getBundle().getString("exp-treasury.command.exchange.msg2"));
        player.sendMessage(formatter2.format(new Object[]{Math.round((float) (percentage/100.0 * 30))}));
        return true;
    }
}
