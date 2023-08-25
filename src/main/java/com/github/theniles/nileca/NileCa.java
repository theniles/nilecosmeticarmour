package com.github.theniles.nileca;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.theniles.nileca.mixin.ArgumentTypesAccessor;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class NileCa implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "nileca";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		Path cfgFile = FabricLoader.getInstance().getConfigDir().resolve(MODID);

        int permissionLevelBuffer;

        try (BufferedReader reader = Files.newBufferedReader(cfgFile)) {
            permissionLevelBuffer = Integer.parseInt(reader.readLine());
            LOGGER.info("NCA permission level found in config: " + permissionLevelBuffer);
        } catch (Exception e) {
            permissionLevelBuffer = 4;
            LOGGER.error("NCA permission level was not found in config. Defaulting to level 4.", e);
        }

        int permissionLevel = permissionLevelBuffer;
        
ArgumentTypesAccessor.nileRegister(Registries.COMMAND_ARGUMENT_TYPE, MODID + ":armourslottype", ArmourSlotsArgumentType.class, ConstantArgumentSerializer.of(ArmourSlotsArgumentType::armourSlots));

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, env) -> {
            // dispatcher.register(
            //     CommandManager.literal("nileca")
            //         .argument("players", EntityArgumentType.players())
            //             .requires(requirement -> requirement.hasPermissionLevel(4))
            //                 .executes(ctx -> ArmourVisibilityCommand.run(ctx)));
            dispatcher.register(
                CommandManager.literal(ArmourVisibilityCommand.base_name)
                .then(CommandManager.literal(ArmourVisibilityCommand.set_name)
                .then(CommandManager.argument(ArmourVisibilityCommand.playersArgument, EntityArgumentType.players())
                .then(CommandManager.argument(ArmourVisibilityCommand.slotsArgument, ArmourSlotsArgumentType.armourSlots())
                .then(CommandManager.argument(ArmourVisibilityCommand.visibilityArgument, BoolArgumentType.bool())
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