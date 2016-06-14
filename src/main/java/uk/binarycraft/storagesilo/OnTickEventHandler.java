package uk.binarycraft.storagesilo;



import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OnTickEventHandler
{
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(TickEvent.PlayerTickEvent event)
    {
        if (!StorageSilo.haveNotifiedVersionOutOfDate && event.player.worldObj.isRemote && !VersionChecker.isLatestVersion())
        {
            ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "http://www.binarycraft.uk/storagesilo");
            event.player.addChatMessage(new TextComponentTranslation("message.version.update.available"));
            StorageSilo.haveNotifiedVersionOutOfDate = true;
        }
    }
}
