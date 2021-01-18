package moe.sqwatermark.goodmorning;

import moe.sqwatermark.mccalender.MCCalender;
import moe.sqwatermark.mccalender.TimeParser;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(
        modid = GoodMorning.MOD_ID,
        name = GoodMorning.MOD_NAME,
        version = GoodMorning.VERSION,
        acceptableRemoteVersions = "*",
        guiFactory = "moe.sqwatermark.goodmorning.GuiFactory")
public class GoodMorning {

    public static final String MOD_ID = "goodmorning";
    public static final String MOD_NAME = "Good Morning";
    public static final String VERSION = "1.12.2-1.2";

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        if (ModConfig.show) {
            if (event.getEntityPlayer() instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) event.getEntityPlayer();
                // 在玩家进入服务器的一瞬间似乎connection是null的
                if (entityplayermp.connection != null) {
                    TimeParser parser = new TimeParser();
                    if (ModConfig.dayStartAtMidnight) parser.setDayStartAtMidnight();
                    MCCalender calender = parser.parse(entityplayermp.world.getWorldTime());
                    if (Loader.isModLoaded("somnia") || (calender.getHour() == 6 && calender.getMinute() == 0)) {
                        // 确定日期
                        int date = calender.getDate();
                        if (ModConfig.addOneDay) date += 1;
                        // 确定标题
                        String title = "";
                        if (ModConfig.title.length > 0) {
                            title = ModConfig.title[(int)(Math.random()*ModConfig.title.length)]
                                    .replace("@PLAYER@", entityplayermp.getName())
                                    .replace("@DATE@", String.valueOf(date))
                                    .replace("@HOUR@",  (calender.getHour() > 9 ? "" : "0") + calender.getHour())
                                    .replace("@MINUTE@", (calender.getMinute() > 9 ? "" : "0") + calender.getMinute())
                                    .replace("\\u", "§");
                        }
                        // 确定副标题
                        String subtitle = "";
                        if (ModConfig.subtitle.length > 0) {
                            subtitle = ModConfig.subtitle[(int)(Math.random()*ModConfig.subtitle.length)]
                                    .replace("@PLAYER@", entityplayermp.getName())
                                    .replace("@DATE@", String.valueOf(date))
                                    .replace("@HOUR@",  (calender.getHour() > 9 ? "" : "0") + calender.getHour())
                                    .replace("@MINUTE@", (calender.getMinute() > 9 ? "" : "0") + calender.getMinute())
                                    .replace("\\u", "§");
                        }
                        // 发送标题和副标题
                        SPacketTitle spackettitle = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString(title));
                        SPacketTitle spacketsubtitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, new TextComponentString(subtitle));
                        entityplayermp.connection.sendPacket(spackettitle);
                        entityplayermp.connection.sendPacket(spacketsubtitle);
                    }
                }
            }
        }
    }

    /**
     * 同步配置文件
     */
    @Mod.EventBusSubscriber(modid = GoodMorning.MOD_ID)
    public static class ConfigSyncHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(GoodMorning.MOD_ID)) {
                ConfigManager.sync(GoodMorning.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}