package github.catchaos8.levelup.commands.get;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import github.catchaos8.levelup.stats.PlayerStatsProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GetClassXPCommand {
    public static final String CLASSXP =       "levelup.stat.cxp";

    public GetClassXPCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("levelup")
                .then(Commands.literal("stats")
                .then(Commands.literal("get")
                        .then(Commands.literal("classxp")
                .executes((this::execute))))));

    }

    private int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();

        player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(getStats -> {
            player.sendSystemMessage(Component.translatable(CLASSXP).append(Component.literal("" + getStats.getStat(6))));
        });

        return 1;
    }

}
