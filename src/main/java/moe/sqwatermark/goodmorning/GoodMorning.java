package moe.sqwatermark.goodmorning;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
@Mod(GoodMorning.MOD_ID)
public class GoodMorning {

    public GoodMorning() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GoodMorningConfig.COMMON_CONFIG);
    }

    public static final String MOD_ID = "goodmorning";

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        if (GoodMorningConfig.SHOW.get()) {
            if (event.getEntity() instanceof ServerPlayer serverPlayerEntity) {
                TimeParser parser = new TimeParser();
                if (GoodMorningConfig.DAY_START_AT_MIDNIGHT.get()) parser.setDayStartAtMidnight();
                MCCalender calender = parser.parse(serverPlayerEntity.getCommandSenderWorld().getDayTime());
                if (ModList.get().isLoaded("somnia") || (calender.getHour() == 6 && calender.getMinute() == 0)) {
                    int date = calender.getDate();
                    if (GoodMorningConfig.ADD_ONE_DAY.get()) date += 1;
                    // 确定标题
                    String title = "";
                    if (GoodMorningConfig.TITLE.get().size() > 0) {
                        title = GoodMorningConfig.TITLE.get().get((int)(Math.random()*GoodMorningConfig.TITLE.get().size()))
                                .replace("@PLAYER@", serverPlayerEntity.getName().getString())
                                .replace("@DATE@", String.valueOf(date))
                                .replace("@HOUR@",  (calender.getHour() > 9 ? "" : "0") + calender.getHour())
                                .replace("@MINUTE@", (calender.getMinute() > 9 ? "" : "0") + calender.getMinute())
                                .replace("\\u", "§");
                    }
                    // 确定副标题
                    String subtitle = "";
                    if (GoodMorningConfig.SUBTITLE.get().size() > 0) {
                        subtitle = GoodMorningConfig.SUBTITLE.get().get((int)(Math.random()*GoodMorningConfig.SUBTITLE.get().size()))
                                .replace("@PLAYER@", serverPlayerEntity.getName().getString())
                                .replace("@DATE@", String.valueOf(date))
                                .replace("@HOUR@",  (calender.getHour() > 9 ? "" : "0") + calender.getHour())
                                .replace("@MINUTE@", (calender.getMinute() > 9 ? "" : "0") + calender.getMinute())
                                .replace("\\u", "§");
                    }
                    // 发送标题和副标题
                    ClientboundSetTitleTextPacket spackettitle = new ClientboundSetTitleTextPacket(Component.literal(title));
                    ClientboundSetSubtitleTextPacket spacketsubtitle = new ClientboundSetSubtitleTextPacket(Component.literal(subtitle));
                    serverPlayerEntity.connection.send(spackettitle);
                    serverPlayerEntity.connection.send(spacketsubtitle);
                }
            }
        }
    }
}