package me.creepinson.tubesplus.handler;

import me.creepinson.tubesplus.capability.GravityProvider;
import me.creepinson.tubesplus.capability.IGravity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerLogsIn(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (player.hasCapability(GravityProvider.GRAVITY_CAP, null)) {
            IGravity gravity = player.getCapability(GravityProvider.GRAVITY_CAP, null);
            String message = String.format("Hello there, your gravity state is: ", gravity.isAttracted());
            player.sendMessage(new TextComponentString(message));
        }
    }

    /**
     * Copy data from dead player to the new player
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        IGravity mana = player.getCapability(GravityProvider.GRAVITY_CAP, null);
        IGravity oldMana = event.getOriginal().getCapability(GravityProvider.GRAVITY_CAP, null);

    }
}