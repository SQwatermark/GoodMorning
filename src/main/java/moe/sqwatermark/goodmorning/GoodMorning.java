package moe.sqwatermark.goodmorning;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

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
            if (event.getPlayer() instanceof ServerPlayer) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) event.getPlayer();
                // 在玩家进入服务器的一瞬间似乎connection是null的
                if (serverPlayerEntity.connection != null) {
                    long time = serverPlayerEntity.world.getDayTime();
                    if (time % 24000L == 0) {
                        // 确定日期
                        int date = (int)(time / 24000L);
                        if (GoodMorningConfig.ADD_ONE_DAY.get()) {
                            date += 1;
                        }
                        // 确定标题
                        String title = "";
                        List<String> titles = GoodMorningConfig.TITLE.get();
                        if (titles.size() > 0) {
                            title = titles.get((int)(Math.random()* titles.size()))
                                    .replace("@DATE@", String.valueOf(date))
                                    .replace("@PLAYER@", serverPlayerEntity.getName().getString())
                                    .replace("\\u", "§");
                        }
                        // 确定副标题
                        String subtitle = "";
                        List<String> subtitles = GoodMorningConfig.SUBTITLE.get();
                        if (subtitles.size() > 0) {
                            subtitle = subtitles.get((int)(Math.random()* subtitles.size()))
                                    .replace("@DATE@", String.valueOf(date))
                                    .replace("@PLAYER@", serverPlayerEntity.getName().getString())
                                    .replace("\\u", "§");
                        }
                        // 发送标题和副标题
                        STitlePacket spackettitle = new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent(title));
                        STitlePacket spacketsubtitle = new STitlePacket(STitlePacket.Type.SUBTITLE, new StringTextComponent(subtitle));
                        serverPlayerEntity.connection.sendPacket(spackettitle);
                        serverPlayerEntity.connection.sendPacket(spacketsubtitle);
                    }
                }
            }
        }
    }
}