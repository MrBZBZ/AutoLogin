package me.LeavesHack;

import java.io.File;
import java.io.IOException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = me.MOD_ID,name = me.MOD_NAME,version = me.MOD_VER)
public class me {
    public static final String MOD_ID = "leaves";
    public static final String MOD_NAME = "AutoLogin";
    public static final String MOD_VER = "0.0.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AutoLogin());
        File directory = new File(getpath());
        if (!directory.exists()) {
            if (directory.mkdir()) {
            } else {
            }
        }
        File file = new File(getpath() + "\\password.txt");
        try {
            if (file.createNewFile()) {
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String getpath(){
        String Path1 = System.getProperty("user.dir");
        String Path2 = Path1.replace("mods", "\\AutoLgoin");
        return Path2;
    }
}