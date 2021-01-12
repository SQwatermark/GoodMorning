package moe.sqwatermark.goodmorning;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class GuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        List<IConfigElement> childElements = ConfigElement.from(ModConfig.class).getChildElements();
        List<IConfigElement> sortedElements = new ArrayList<>();
        String[] sort = {"show", "addOneDay", "title", "subtitle"};
        for (String s : sort) {
            for (IConfigElement childElement : childElements) {
                if (childElement.getName().equals(s)) {
                    sortedElements.add(childElement);
                }
            }
        }
        return new GuiConfig(parentScreen, sortedElements, GoodMorning.MOD_ID, false, false, GoodMorning.MOD_NAME, "Config");
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return Collections.emptySet();
    }
}
