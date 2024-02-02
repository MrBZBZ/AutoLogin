package me.LeavesHack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoReconnect {
    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            try {
                Thread.sleep(1000); // 等待1秒
                if (read("AutoReconnect").equals("true")){
                    reconnectToServer();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void reconnectToServer() {
        Minecraft mc = Minecraft.getMinecraft();
        ServerData serverData = mc.getCurrentServerData();
        if (serverData != null) {
            mc.addScheduledTask(() -> {
                mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, serverData));
            });
        }
    }
    private String read(String targetKey) {
        String SettingValue = "";
        try {
            Path path = Paths.get(getpath() + "\\Settings.txt"); // 文件路径可以根据实际情况调整
            byte[] encoded = Files.readAllBytes(path);
            SettingValue = new String(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 定义正则表达式
        String regex = "\"(\\w+)\":\"([^\"]+)\"";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(SettingValue);

        // 查找匹配
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);

            // 检查是否是目标键
            if (targetKey.equals(key)) {
                //返回对应value
                return value;
            }
        }
        //凑数用的没有这个return会报错
        return "";
    }
    public static String getpath(){
        String Path1 = System.getProperty("user.dir");
        String Path2 = Path1.replace("mods", "\\AutoLogin");
        return Path2;
    }
}
