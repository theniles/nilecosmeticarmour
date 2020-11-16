package net.nile.cosmetic.armour;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class MyComponents implements EntityComponentInitializer {

    public static final ComponentKey<ArmourVisibilityComponent> ARMOUR_VISIBILITY = 
        ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("nilecosmeticarmour:armourvisibility"), ArmourVisibilityComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ARMOUR_VISIBILITY, player -> new ArmourVisibilityComponent(), RespawnCopyStrategy.NEVER_COPY);
    }
    
}
