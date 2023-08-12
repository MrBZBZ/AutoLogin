package me.LeavesHack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import static net.minecraft.client.Minecraft.*;

public class AutoLogin {
    @SubscribeEvent
    public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
            if (!event.isLocal()) {
                getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                        new TextComponentString(ChatFormatting.GREEN + "[AutoLogin]:\u5982\u679c\u662f\u7b2c\u4e00\u6b21\u4f7f\u7528\uff0c\u8bf7\u5728minecraft\u6e38\u620f\u76ee\u5f55\u4e0b\u7684\u201cpassword.txt\u201d\u5185\u8bbe\u7f6e\u4f60\u7684\u5bc6\u7801")
                );
                new Thread(
                        () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendLoginCommand();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    selectInventoryAndRightClick();
                }
                ).start();
            }
    }


    //发送登录指令
    private void sendLoginCommand() {
        String password = read();
        String command = "/login " + password;
        getMinecraft().player.sendChatMessage(command);
    }


    //右键指南针
    private void selectInventoryAndRightClick() {
        Minecraft mc = getMinecraft();

        mc.player.inventory.currentItem = getCompassSlotInHotbar();
        mc.playerController.processRightClick(mc.player, mc.player.world, EnumHand.MAIN_HAND);
    }


    //读取密码
    private String read(){
        String password = "";
        try {
            Path path = Paths.get(getpath()+"\\password.txt"); // 文件路径可以根据实际情况调整
            byte[] encoded = Files.readAllBytes(path);
            password = new String(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password.trim();
    }

    //获取文件路径
    public static String getpath(){
        String Path1 = System.getProperty("user.dir");
        String Path2 = Path1.replace("mods", "\\AutoLogin");
        return Path2;
    }

    //获取指南针所在格子
    public int getCompassSlotInHotbar() {
        for (int i = 0; i < 9; i++) {  // 快捷栏是物品栏的前9格
            ItemStack itemStack = getMinecraft().player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Items.COMPASS) {
                return i;
            }
        }
        return -1;
    }
}