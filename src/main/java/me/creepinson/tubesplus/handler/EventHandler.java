package me.creepinson.tubesplus.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

    }

    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();

    }
}