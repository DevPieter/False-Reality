package nl.devpieter.falsereality.Utils;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.awt.*;

public class TextUtils {

    //TODO Translate

    public static Text getEnabledText(boolean enabled) {
        return enabled ? new TranslatableText("enabled") : new TranslatableText("disabled");
    }

    public static Color getEnabledColor(boolean enabled) {
        return enabled ? new Color(84, 218, 129) : new Color(218, 84, 109);
    }

    public static Text appendText(Text base, Text toAppend) {
        LiteralText text = new LiteralText("");
        text.append(base);
        text.append(toAppend);
        return text;
    }
}
