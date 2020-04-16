package me.creepinson.tubesplus.handler;

import me.creepinson.tubesplus.TubesPlus;
import me.creepinson.tubesplus.capability.GravityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(bus)
public class CapabilityHandler {
    public static final ResourceLocation GRAVITY_CAP = new ResourceLocation(TubesPlus.getInstance().modId, "gravity");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer)
            TubesPlus.debug("Added gravity capability for player.");
        event.addCapability(GRAVITY_CAP, new GravityProvider());
    }

}
