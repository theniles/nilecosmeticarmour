package net.nile.cosmetic.armour.mixin;

import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.nile.cosmetic.armour.ArmourVisibilityComponent;
import net.nile.cosmetic.armour.MyComponents;

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public class ArmourFeatureRendererMixin {
    // @Inject(cancellable = true, at=@At("HEAD"), method = "renderArmor(Lnet/minecraft/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V")
    // private void nileRenderArmour(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, LivingEntity entity, EquipmentSlot equipmentSlot, int i, BipedEntityModel model, CallbackInfo callbackInfo)
    // {
    //     LogManager.getLogger().info("The type is " + equipmentSlot);
    //     if(entity instanceof PlayerEntity)
    //     {
    //         if(! MyComponents.ARMOUR_VISIBILITY.get(entity).isVisible(equipmentSlot))
    //         {
    //             callbackInfo.cancel();
    //         }
    //     }
    // }

    @Inject(cancellable = true, at=@At("HEAD"), method = "render(Lnet/minecraft/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;IFFFFFF)V")
    private void nileRender(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo callbackInfo)
    {
        if(livingEntity instanceof PlayerEntity)
        {
            ArmorFeatureRenderer armourRenderer = (ArmorFeatureRenderer)(Object)this;
            ArmourFeatureRendererAccessor armourRendererAccessor = (ArmourFeatureRendererAccessor)armourRenderer;
            ArmourVisibilityComponent armourVisibility = MyComponents.ARMOUR_VISIBILITY.get(livingEntity);
            if(armourVisibility.isTorsoVisible()) armourRendererAccessor.nileRenderArmour(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, armourRendererAccessor.nileGetArmour(EquipmentSlot.CHEST));
            if(armourVisibility.isLeggingsVisible()) armourRendererAccessor.nileRenderArmour(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, armourRendererAccessor.nileGetArmour(EquipmentSlot.LEGS));
            if(armourVisibility.isBootsVisible()) armourRendererAccessor.nileRenderArmour(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, armourRendererAccessor.nileGetArmour(EquipmentSlot.FEET));
            if(armourVisibility.isHelmetVisible())armourRendererAccessor.nileRenderArmour(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, armourRendererAccessor.nileGetArmour(EquipmentSlot.HEAD));
            callbackInfo.cancel();
        }
    }
}
