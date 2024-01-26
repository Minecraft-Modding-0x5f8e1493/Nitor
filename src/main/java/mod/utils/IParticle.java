package mod.utils;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;

public interface IParticle {
    Particle nitor$createParticle(BlockStateParticleEffect blockStateParticleEffect,
                                  ClientWorld clientWorld,
                                  double d,
                                  double e,
                                  double f,
                                  double g,
                                  double h,
                                  double i);
}
