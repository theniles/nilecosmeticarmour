package net.nile.cosmetic.armour;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class ArmourVisibilityCommand {

    public static final String slotsArgument = "slots";

    public static final String playersArgument = "players";

    public static final String visibilityArgument = "isVisible";

    public static final String base_name = "nileca";

    public static final String set_name = "set";

    public static final String list_name = "list";

    public static int run_set(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        //Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(ctx, "players");
        int slots = ArmourSlotsArgumentType.getInt(ctx, slotsArgument);
        boolean isVisible = BoolArgumentType.getBool(ctx, visibilityArgument);
        EntityArgumentType.getPlayers(ctx, playersArgument).forEach((player)->{

        ArmourVisibilityComponent component = MyComponents.ARMOUR_VISIBILITY.get(player);

            switch (slots) {
                case 0:
                component.setHelmetVisible(isVisible);
                    break;
                case 1:
                component.setTorsoVisible(isVisible);
                    
                    break;
                case 2:
                component.setLeggingsVisible(isVisible);
                    
                    break;
                case 3:
                component.setBootsVisible(isVisible);
                    
                    break;
            
                default:
                component.setHelmetVisible(isVisible);
                component.setTorsoVisible(isVisible);
                component.setLeggingsVisible(isVisible);
                component.setBootsVisible(isVisible);

                    break;
            }
            MyComponents.ARMOUR_VISIBILITY.sync(player);
            ctx.getSource().sendFeedback(new LiteralText("NCA: Changed the armour visibility of " + player.getEntityName()), true);
        });
        return 1;
    }

    public static int run_list(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        //Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(ctx, "players");
        EntityArgumentType.getPlayers(ctx, playersArgument).forEach((player)->{
        ArmourVisibilityComponent component = MyComponents.ARMOUR_VISIBILITY.get(player);
            ctx.getSource().sendFeedback(new LiteralText("NCA: Armour visibility " + component.prettyPrint()  +  " for " + player.getEntityName()), true);
        });
        return 1;
    }
}
