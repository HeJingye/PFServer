package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.*;

public interface INetHandlerPlayServer extends INetHandler
{
    void handleAnimation(CPacketAnimation packetIn);

    void processChatMessage(CPacketChatMessage packetIn);

    void processTabComplete(CPacketTabComplete packetIn);

    void processClientStatus(CPacketClientStatus packetIn);

    void processClientSettings(CPacketClientSettings packetIn);

    void processConfirmTransaction(CPacketConfirmTransaction packetIn);

    void processEnchantItem(CPacketEnchantItem packetIn);

    void processClickWindow(CPacketClickWindow packetIn);

    void func_194308_a(CPacketPlaceRecipe p_194308_1_);

    void processCloseWindow(CPacketCloseWindow packetIn);

    void processCustomPayload(CPacketCustomPayload packetIn);

    void processUseEntity(CPacketUseEntity packetIn);

    void processKeepAlive(CPacketKeepAlive packetIn);

    void processPlayer(CPacketPlayer packetIn);

    void processPlayerAbilities(CPacketPlayerAbilities packetIn);

    void processPlayerDigging(CPacketPlayerDigging packetIn);

    void processEntityAction(CPacketEntityAction packetIn);

    void processInput(CPacketInput packetIn);

    void processHeldItemChange(CPacketHeldItemChange packetIn);

    void processCreativeInventoryAction(CPacketCreativeInventoryAction packetIn);

    void processUpdateSign(CPacketUpdateSign packetIn);

    void processTryUseItemOnBlock(CPacketPlayerTryUseItemOnBlock packetIn);

    void processTryUseItem(CPacketPlayerTryUseItem packetIn);

    void handleSpectate(CPacketSpectate packetIn);

    void handleResourcePackStatus(CPacketResourcePackStatus packetIn);

    void processSteerBoat(CPacketSteerBoat packetIn);

    void processVehicleMove(CPacketVehicleMove packetIn);

    void processConfirmTeleport(CPacketConfirmTeleport packetIn);

    void handleRecipeBookUpdate(CPacketRecipeInfo p_191984_1_);

    void handleSeenAdvancements(CPacketSeenAdvancements p_194027_1_);
}