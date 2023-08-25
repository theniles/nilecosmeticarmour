package com.github.theniles.nileca;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class MyComponents implements EntityComponentInitializer {

    public static final ComponentKey<ArmourVisibilityComponent> ARMOUR_VISIBILITY = 
        ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(NileCa.MODID, "visibility"), ArmourVisibilityComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ARMOUR_VISIBILITY, player -> new ArmourVisibilityComponent(), RespawnCopyStrategy.CHARACTER);
    }
    
}
