package com.github.theniles.nileca;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

public class ArmourSlotsArgumentType implements ArgumentType<Integer> {

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        
        String string = reader.readString();
        if(string.equals(all)) return 4;
        else if (string.equals(head)) return 0;
        else if (string.equals(torso)) return 1;
        else if (string.equals(leggings)) return 2;
        else if (string.equals(boots)) return 3;
        else throw new SimpleCommandExceptionType(new LiteralMessage("Invalid slot selection.")).createWithContext(reader);
    }

    public static Integer getInt(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Integer.class);
    }

    public static ArmourSlotsArgumentType armourSlots(){return new ArmourSlotsArgumentType();}

    public static final String head = "head";

    public static final String torso = "torso";

    public static final String leggings = "leggings";

    public static final String boots = "boots";

    public static final String all = "all";

    private static final Collection<String> EXAMPLES = Arrays.asList(head, torso, leggings, boots, all);

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (all.startsWith(builder.getRemaining().toLowerCase())) {
            builder.suggest(all);
        }
        else if (head.startsWith(builder.getRemaining().toLowerCase())) {
            builder.suggest(head);
        }
        else if (torso.startsWith(builder.getRemaining().toLowerCase())) {
            builder.suggest(torso);
        }
        else if (leggings.startsWith(builder.getRemaining().toLowerCase())) {
            builder.suggest(leggings);

        }
        else if (boots.startsWith(builder.getRemaining().toLowerCase())) {
            builder.suggest(boots);
        }
        return builder.buildFuture();
    }
    
    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
