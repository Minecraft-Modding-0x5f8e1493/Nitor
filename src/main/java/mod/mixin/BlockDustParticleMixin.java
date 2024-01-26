package mod.mixin;

import mod.NitorMod;
import mod.utils.IParticle;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockDustParticle.Factory.class)
public abstract class BlockDustParticleMixin implements IParticle {

    @Override
    public Particle nitor$createParticle(@NotNull BlockStateParticleEffect effect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return isBlockStateParticleEffect(effect.getBlockState()) ? new BlockDustParticle(clientWorld, d, e, f, g, h, i, effect.getBlockState()) : null;
    }

    @Unique
    private boolean isBlockStateParticleEffect(@NotNull BlockState state) {
        return !state.isAir() && !state.isOf(Blocks.MOVING_PISTON) && !NitorMod.NITOR_BLOCKS.contains(state.getBlock());
    }
}
