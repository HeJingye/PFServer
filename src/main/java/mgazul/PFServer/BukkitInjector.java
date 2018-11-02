package mgazul.PFServer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.Map;

public class BukkitInjector {
    public static boolean initializedBukkit = false;

    public static void injectItemBukkitMaterials()
    {
        for (Map.Entry<ResourceLocation, Item> entry : ForgeRegistries.ITEMS.getEntries()) {
            ResourceLocation key = entry.getKey();
            Item item = entry.getValue();
            if(!key.getResourceDomain().equals("minecraft")) {
                String materialName = key.toString().toUpperCase().replaceAll("(:|\\s)", "_").replaceAll("\\W", "");
                Material material = Material.addMaterial(EnumHelper.addEnum(Material.class, materialName, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(Item.getIdFromItem(item))}));
                if (material != null) {
                   PFServer.LOGGER.debug("Injected new Forge item material %s with ID %d.", material.name(), material.getId());
                } else {
                    PFServer.LOGGER.debug("Inject item failure %s with ID %d.", materialName, Item.getIdFromItem(item));
                }
            }
        }
    }

    public static void injectBlockBukkitMaterials()
    {
        for (Material material : Material.values()) {
            if (material.getId() < 256)
                Material.addBlockMaterial(material);
        }
        for (Map.Entry<ResourceLocation, Block> entry : ForgeRegistries.BLOCKS.getEntries()) {
            ResourceLocation key = entry.getKey();
            Block block = entry.getValue();
            if(!key.getResourceDomain().equals("minecraft")) {
                String materialName = key.toString().toUpperCase().replaceAll("(:|\\s)", "_").replaceAll("\\W", "");
                Material material = Material.addBlockMaterial(EnumHelper.addEnum(Material.class, materialName, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(Block.getIdFromBlock(block))}));
                if (material != null) {
                    PFServer.LOGGER.debug("Injected new Forge block material %s with ID %d.", material.name(), material.getId());
                } else {
                    PFServer.LOGGER.debug("Inject block failure %s with ID %d.", materialName, Block.getIdFromBlock(block));
                }
            }
        }
    }

    public static void injectBiomes() {
        for1:
        for (Map.Entry<ResourceLocation, net.minecraft.world.biome.Biome> entry : ForgeRegistries.BIOMES.getEntries()) {
            String biomeName = entry.getKey().getResourcePath().toUpperCase(java.util.Locale.ENGLISH);
            for (Biome biome : Biome.values()) {
                if (biome.toString().equals(biomeName)) continue for1;
            }
            EnumHelper.addEnum(Biome.class, biomeName, new Class[]{}, new Object[]{});
        }
    }
}
