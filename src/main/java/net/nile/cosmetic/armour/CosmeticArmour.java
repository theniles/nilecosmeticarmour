package net.nile.cosmetic.armour;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.mojang.brigadier.arguments.BoolArgumentType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.command.CommandManager;

public class CosmeticArmour implements ModInitializer {

public static final String modid = "nilecosmeticarmour";

public static final Logger logger = LogManager.getLogger();

/*
    Extend ArmorFeatureRenderer and override render()
    Mixin to PlayerEntityRenderer cstrctr to remove the default ArmorFeatureRenderer &
    add custom armour renderer using 'features' object which is accessed by a Mixin
    or
    Mixin to ArmourFeatureRenderer renderArmour()

    2nd option is better


    Anyways, do the following:
    Track what is visible on each entity?
*/

    @Override
    public void onInitialize() {
        Path cfgFile = FabricLoader.getInstance().getConfigDir().resolve(modid);

        int permissionLevelBuffer;

        try (BufferedReader reader = Files.newBufferedReader(cfgFile)) {
            permissionLevelBuffer = Integer.parseInt(reader.readLine());
            logger.info("NCA permission level found in config: " + permissionLevelBuffer);
        } catch (Exception e) {
            permissionLevelBuffer = 4;
            logger.error("NCA permission level was not found in config. Defaulting to level 4.", e);
        }

        int permissionLevel = permissionLevelBuffer;

ArgumentTypes.register(modid + ":armourslottype", ArmourSlotsArgumentType.class, new ConstantArgumentSerializer<>(ArmourSlotsArgumentType::armourSlots));

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            // dispatcher.register(
            //     CommandManager.literal("nileca")
            //         .argument("players", EntityArgumentType.players())
            //             .requires(requirement -> requirement.hasPermissionLevel(4))
            //                 .executes(ctx -> ArmourVisibilityCommand.run(ctx)));
            dispatcher.register(
                CommandManager.literal(ArmourVisibilityCommand.base_name)
                .then(CommandManager.literal(ArmourVisibilityCommand.set_name)
                .then(CommandManager.argument(ArmourVisibilityCommand.playersArgument, EntityArgumentType.players())
                .then(CommandManager.argument(ArmourVisibilityCommand.visibilityArgument, BoolArgumentType.bool())
                .then(CommandManager.argument(ArmourVisibilityCommand.slotsArgument, ArmourSlotsArgumentType.armourSlots())
                .requires(requirement -> requirement.hasPermissionLevel(permissionLevel))
                .executes(ArmourVisibilityCommand::run_set)))))
            );

            dispatcher.register(
                CommandManager.literal(ArmourVisibilityCommand.base_name)
                .then(CommandManager.literal(ArmourVisibilityCommand.list_name)
                .then(CommandManager.argument(ArmourVisibilityCommand.playersArgument, EntityArgumentType.players())
                .requires(requirement -> requirement.hasPermissionLevel(permissionLevel))
                .executes(ArmourVisibilityCommand::run_list)))
            );
        });
        // CommandManager.literal("nileca")
        // .then(CommandManager.argument("players", EntityArgumentType.players())
        // .requires(requirement -> requirement.hasPermissionLevel(4))
        // .executes(ArmourVisibilityCommand::run));
    }

}