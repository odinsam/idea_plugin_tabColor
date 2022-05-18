package com.odinsam.tabcolor.tabmenu;

import java.awt.*;
import java.util.*;

public class TabColors {
    public static HashMap<String,Color> OdinTabColors = new HashMap<String, Color>() {{
        put("FS",new Color(104,204,239));
        put("WS",new Color(0,255,150));
        put("DZ",new Color(255,244,104));
        put("QS",new Color(244,140,186));
        put("SS",new Color(147,130,201));
        put("XD",new Color(255,124,10));
        put("DH",new Color(163,48,201));
        put("LR",new Color(170,211,114));
        put("ZS",new Color(198,155,109));
        put("SM",new Color(35,89,255));
        put("MS",new Color(255,255,255));
    }};
    
    public static String GetColorNameByColor(Color color)
    {
        for (var colorName:OdinTabColors.keySet()) {
            if(OdinTabColors.get(colorName) == color)
                return colorName;
        }
        return null;
    }
}
