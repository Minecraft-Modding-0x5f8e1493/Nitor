package mod.client;

import mod.blocks.entities.NitorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleEffect;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.Color;

@Environment(EnvType.CLIENT)
public class NitorRenderer {

    public static void entityTick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof NitorEntity)
            drawNitorFlames((ClientWorld) world,
                    pos.getX() + 0.5f + world.random.nextGaussian() * 0.025,
                    pos.getY() + 0.45f + world.random.nextGaussian() * 0.025,
                    pos.getZ() + 0.5f + world.random.nextGaussian() * 0.025,
                    world.random.nextGaussian() * 0.0025,
                    world.random.nextFloat() * 0.06,
                    world.random.nextGaussian() * 0.0025,
                    state.getBlock().getDefaultMapColor());
    }

    private static void drawNitorFlames(@NotNull ClientWorld world, double x, double y, double z, double x2, double y2, double z2, @NotNull MapColor mapColor) {
        Color color = new Color(mapColor.color);

        WorldParticleBuilder.create(NitorModClient.LODESTONE)
                .setSpinData(SpinParticleData.create((float) (world.random.nextGaussian() / 5f)).build())
                .setScaleData(GenericParticleData.create(0.3f, 0f).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setRandomMotion(0.0025f, 0.0f, 0.0025f)
                .setLifetime(10 + world.random.nextInt(5))
                .addActor(genericParticle -> genericParticle.setVelocity(x2, y2, z2))
                .spawn(world, x, y, z);
    }
}
