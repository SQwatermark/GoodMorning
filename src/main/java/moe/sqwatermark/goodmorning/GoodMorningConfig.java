package moe.sqwatermark.goodmorning;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class GoodMorningConfig {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue SHOW;
    public static ForgeConfigSpec.BooleanValue ADD_ONE_DAY;

    public static ForgeConfigSpec.BooleanValue DAY_START_AT_MIDNIGHT;
    public static ForgeConfigSpec.ConfigValue<List<String>> TITLE;
    public static ForgeConfigSpec.ConfigValue<List<String>> SUBTITLE;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER
                .comment("General settings")
                .push("general");

        SHOW = COMMON_BUILDER
                .comment("Enable the mod")
                .translation("config.goodmorning.show")
                .define("show", true);

        ADD_ONE_DAY = COMMON_BUILDER
                .comment("Whether to add one day to the shown date.", "Default Minecraft starts at day 0")
                .translation("config.goodmorning.addOneDay")
                .define("addOneDay", false);

        DAY_START_AT_MIDNIGHT = COMMON_BUILDER
                .comment("If true, a day will start at 00:00 but not 06:00, (useful when the mod somnia loaded)")
                .translation("config.goodmorning.dayStartAtMidnight")
                .define("dayStartAtMidnight", false);

        ArrayList<String> defaultTitles = new ArrayList<>();
        defaultTitles.add("早上好@PLAYER@");
        defaultTitles.add("今天真是个好天气");
        defaultTitles.add("睡了个好觉");

        TITLE = COMMON_BUILDER
                .comment("The first line of the shown information,",
                        "Useful placeholders: @PLAYER@, @DATE@, @HOUR@, @MINUTE@",
                        "\\u will be replaced with §.",
                        "You can add more than one random titles")
                .translation("config.goodmorning.title")
                .define("title", defaultTitles);

        ArrayList<String> defaultSubtitles = new ArrayList<>();
        defaultSubtitles.add("现在是第@DATE@天");

        SUBTITLE = COMMON_BUILDER
                .comment("The second line of the shown information,",
                        "Useful placeholders: @PLAYER@, @DATE@, @HOUR@, @MINUTE@",
                        "\\u will be replaced with §.",
                        "You can add more than one random subtitles")
                .translation("config.goodmorning.subtitle")
                .define("subtitle", defaultSubtitles);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}