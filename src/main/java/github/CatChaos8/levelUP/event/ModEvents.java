package github.catchaos8.levelup.event;

import github.catchaos8.levelup.LevelUP;
import github.catchaos8.levelup.commands.get.*;
import github.catchaos8.levelup.commands.set.*;
import github.catchaos8.levelup.networking.ModNetwork;
import github.catchaos8.levelup.networking.packet.IncreaseStatC2SPacket;
import github.catchaos8.levelup.networking.packet.StatDataSyncS2CPacket;
import github.catchaos8.levelup.stats.PlayerStats;
import github.catchaos8.levelup.stats.PlayerStatsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerXpEvent;


@Mod.EventBusSubscriber(modid = LevelUP.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new GetStatsCommand(event.getDispatcher());
        new GetConCommand(event.getDispatcher());
        new GetDexCommand(event.getDispatcher());
        new GetStrCommand(event.getDispatcher());
        new GetIntCommand(event.getDispatcher());
        new GetEndCommand(event.getDispatcher());

        new GetAllStatsCommand(event.getDispatcher());

        new GetFreePointsCommand(event.getDispatcher());
        new GetClassXPCommand(event.getDispatcher());
        new GetClassLevelCommand(event.getDispatcher());


        new SetConCommand(event.getDispatcher());
        new SetDexCommand(event.getDispatcher());
        new SetStrCommand(event.getDispatcher());
        new SetIntCommand(event.getDispatcher());
        new SetEndCommand(event.getDispatcher());

        new SetAllStatsCommand(event.getDispatcher());

        new SetFreePointsCommand(event.getDispatcher());
        new SetClassXPCommand(event.getDispatcher());
        new SetClassLevelCommand(event.getDispatcher());
    }


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerStatsProvider.PLAYER_STATS).isPresent()) {
                event.addCapability(new ResourceLocation(LevelUP.MOD_ID, "properties"), new PlayerStatsProvider());
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });



        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilites(RegisterCapabilitiesEvent event) {
        event.register(PlayerStats.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(stats -> {
                    ModNetwork.sendToPlayer(new StatDataSyncS2CPacket(stats.getStatArr()), player);
                });
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerPickXP(PlayerXpEvent event) {
        if (event instanceof PlayerXpEvent.PickupXp pickupXpEvent) {
            Player player = pickupXpEvent.getEntity();
            ExperienceOrb orb = pickupXpEvent.getOrb();
            int xpAmount = orb.getValue();
            // server-side execution
            if (!player.level().isClientSide) {
                ModNetwork.sendToServer(new IncreaseStatC2SPacket(6, xpAmount));
            }
        }
    }

}