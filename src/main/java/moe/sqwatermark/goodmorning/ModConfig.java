package moe.sqwatermark.goodmorning;

import net.minecraftforge.common.config.Config;

@Config(modid = GoodMorning.MOD_ID)
public final class ModConfig {

    @Config.Comment("Enable the mod")
    @Config.LangKey("config.goodmorning.show")
    public static boolean show = true;

    @Config.Comment({"Whether to add one day to the shown date.", "Default Minecraft starts at day 0"})
    @Config.LangKey("config.goodmorning.addOneDay")
    public static boolean addOneDay = false;

    @Config.Comment({
            "If true, a day will start at 00:00 but not 06:00, (useful when the mod somnia loaded)"
    })
    @Config.LangKey("config.goodmorning.dayStartAtMidnight")
    public static boolean dayStartAtMidnight = true;

    @Config.Comment({
            "The first line of the shown information,",
            "Useful placeholders: @PLAYER@, @DATE@, @HOUR@, @MINUTE@",
            "\\u will be replaced with §.",
            "You can add more than one random titles"
    })
    @Config.LangKey("config.goodmorning.title")
    public static String[] title = {"早上好@PLAYER@", "今天真是个好天气", "睡了个好觉"};

    @Config.Comment({
            "The second line of the shown information,",
            "Useful placeholders: @PLAYER@, @DATE@, @HOUR@, @MINUTE@",
            "\\u will be replaced with §.",
            "You can add more than one random subtitles"
    })
    @Config.LangKey("config.goodmorning.subtitle")
    public static String[] subtitle = {"现在是第@DATE@天"};

}
