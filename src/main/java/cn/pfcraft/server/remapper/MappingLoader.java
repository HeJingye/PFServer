package cn.pfcraft.server.remapper;

import cn.pfcraft.server.PFServer;
import net.md_5.specialsource.JarMapping;
import net.md_5.specialsource.transformer.MappingTransformer;
import net.md_5.specialsource.transformer.MavenShade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MappingLoader {
    private static final String org_bukkit_craftbukkit = new String(new char[]{'o', 'r', 'g', '/', 'b', 'u', 'k', 'k', 'i', 't', '/', 'c', 'r', 'a', 'f', 't', 'b', 'u', 'k', 'k', 'i', 't'});

    private static void loadNmsMappings(JarMapping jarMapping, String obfVersion) throws IOException {
        Map relocations = new HashMap();
        relocations.put("net.minecraft.server", "net.minecraft.server." + obfVersion);
        jarMapping.loadMappings(new BufferedReader(new InputStreamReader(MappingLoader.class.getClassLoader().getResourceAsStream("mappings/" + obfVersion + "/cb2srg.srg"))), new MavenShade(relocations), (MappingTransformer)null, false);
    }

    public static JarMapping loadMapping() {
        JarMapping jarMapping = new JarMapping();

        try {
            jarMapping.packages.put(org_bukkit_craftbukkit + "/libs/com/google/gson", "com/google/gson");
            jarMapping.packages.put(org_bukkit_craftbukkit + "/libs/it/unimi/dsi/fastutil", "it/unimi/dsi/fastutil");
            jarMapping.packages.put(org_bukkit_craftbukkit + "/libs/jline", "jline");
            jarMapping.packages.put(org_bukkit_craftbukkit + "/libs/joptsimple", "joptsimple");
            loadNmsMappings(jarMapping, PFServer.getNativeVersion());
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return jarMapping;
    }
}