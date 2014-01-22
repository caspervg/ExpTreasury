package net.caspervg.exptreasury.listener;

import net.caspervg.exptreasury.ExpTreasury;
import net.caspervg.exptreasury.i18n.Language;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.text.MessageFormat;

public class ExpLevelListener implements Listener {

    private ExpTreasury bank;

    public ExpLevelListener(ExpTreasury bank) {
        this.bank = bank;
    }

    @EventHandler
    public void onNewLevel(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getNewLevel() == 30) {
            MessageFormat formatter = new MessageFormat(Language.getBundle().getString("exp-treasury.reminder"));
            player.sendMessage(formatter.format(new Object[]{30}));
        }

        if (event.getOldLevel() < event.getNewLevel() && bank.getConfig().getBoolean("fireworks")) {
            Firework firework = player.getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
            FireworkMeta meta = firework.getFireworkMeta();
            meta.addEffects(FireworkEffect.builder().withColor(Color.BLACK, Color.YELLOW, Color.RED).withFlicker().withFade(Color.YELLOW, Color.RED, Color.BLACK).withTrail().build());
            meta.setPower(1);
            firework.setFireworkMeta(meta);
        }

    }

}
