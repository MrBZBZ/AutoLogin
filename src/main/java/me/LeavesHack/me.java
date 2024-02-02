package me.LeavesHack;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = me.MOD_ID,name = me.MOD_NAME,version = me.MOD_VER)
public class me {
    public static final String MOD_ID = "leaves";
    public static final String MOD_NAME = "AutoLogin";
    public static final String MOD_VER = "0.0.2";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AutoLogin());
        MinecraftForge.EVENT_BUS.register(new AutoReconnect());

        File();
    }

    public static void File() {
        File directory = new File("");//设定为当前文件夹
        try {
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String currentPath = directory.getAbsolutePath();
        String newFolderName = "AutoLogin";
        String newTxtFileName = "Settings.txt";
        String newFolderPath = currentPath + File.separator + newFolderName;

        try {
            File newFolder = new File(newFolderPath);
            if (!newFolder.exists()) {
                newFolder.mkdirs();
            }

            System.setProperty("user.dir", newFolderPath);

            File newTxtFile = new File(newFolderPath, newTxtFileName);
            if (newTxtFile.createNewFile()) {
                FileWriter writer = new FileWriter(newTxtFile);
                writer.write(
                        "\"AutoLogin\":\"true\"\n" +
                        "\"AutoReconnect\":\"true\"\n" +
                        "\"AutoSwitch\":\"true\"\n" +
                        "\"Password\":\"123456\"\n" +
                        "\"Command\":\"/Login\"\n");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}