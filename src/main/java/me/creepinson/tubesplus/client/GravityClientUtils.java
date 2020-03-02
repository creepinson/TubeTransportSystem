package me.creepinson.tubesplus.client;

import me.creepinson.tubesplus.capability.GravityProvider;
import me.creepinson.tubesplus.capability.IGravity;
import me.creepinson.tubesplus.util.GravityDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

/**
 * @author Creepinson http://gitlab.com/creepinson
 * Project Tubes Plus
 **/
public class GravityClientUtils {

    public static void orientCameraByGravity(float par1) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityLivingBase entityLivingBase = mc.player;
        IGravity gravity = entityLivingBase.getCapability(GravityProvider.GRAVITY_CAP, null);
        GravityDirection dir = gravity != null ? gravity.getDirection() : GravityDirection.upTOdown_YN;
        GL11.glRotatef(180.0F * dir.rotX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F * dir.rotZ, 0.0F, 0.0F, 1.0F);
        float pitch = entityLivingBase.prevRotationPitch + (entityLivingBase.rotationPitch - entityLivingBase.prevRotationPitch) * par1;
        GL11.glRotatef(pitch * dir.pitchRotDirX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(pitch * dir.pitchRotDirY, 0.0F, 1.0F, 0.0F);
        float yaw = entityLivingBase.prevRotationYaw + (entityLivingBase.rotationYaw - entityLivingBase.prevRotationYaw) * par1 + 180.0F;
        GL11.glRotatef(yaw * dir.yawRotDirX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(yaw * dir.yawRotDirY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(yaw * dir.yawRotDirZ, 0.0F, 0.0F, 1.0F);
        float fixHeight = (float) (entityLivingBase.posY - entityLivingBase.prevPosY / 2.0F);
        GL11.glTranslatef(fixHeight * dir.shiftEyeX, fixHeight * dir.shiftEyeY, fixHeight * dir.shiftEyeZ);
        GL11.glTranslatef((float)entityLivingBase.posX * dir.shiftSneakX, (float)entityLivingBase.posY * dir.shiftSneakY, (float)entityLivingBase.posZ * dir.shiftSneakZ);

        // TESTING
        GL11.glRotatef(90.0F * (100.0F + (100.0F - 100.0F) * par1), 0.0F, 0.0F, 0.0F);

        if (gravity != null && gravity.getTurnRate() < 1.0F)
            GL11.glRotatef(90.0F * (gravity.getPreviousTurnRate() + (gravity.getTurnRate() - gravity.getPreviousTurnRate()) * par1), 0.0F, 0.0F, 0.0F);
    }
}
