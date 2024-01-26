package mod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.rendering.particle.type.LodestoneParticleType;

@Environment(EnvType.CLIENT)
public class NitorModClient implements ClientModInitializer {
    public static LodestoneParticleType LODESTONE = new LodestoneParticleType();

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(LODESTONE, LodestoneParticleType.Factory::new);
        LODESTONE = Registry.register(Registries.PARTICLE_TYPE, new Identifier("nitormod", "nitor"), LODESTONE);
    }
}
