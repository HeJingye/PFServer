/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.common;

import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.minecraftforge.common.ForgeVersion.Status.PENDING;

public class ForgeVersion
{
    // This is Forge's Mod Id, used for the ForgeModContainer and resource locations
    public static final String MOD_ID = "forge";
    //This number is incremented every time we remove deprecated code/major API changes, never reset
    public static final int majorVersion    = 14;
    //This number is incremented every minecraft release, never reset
    public static final int minorVersion    = 23;
    //This number is incremented every time a interface changes or new major feature is added, and reset every Minecraft version
    public static final int revisionVersion = 5;
    //This number is incremented every time Jenkins builds Forge, and never reset. Should always be 0 in the repo code.
    public static final int buildVersion    = 2796;
    // This is the minecraft version we're building for - used in various places in Forge/FML code
    public static final String mcVersion = "1.12.2";
    // This is the MCP data version we're using
    public static final String mcpVersion = "9.42";
    @SuppressWarnings("unused")
    private static Status status = PENDING;
    @SuppressWarnings("unused")
    private static String target = null;

    private static final int MAX_HTTP_REDIRECTS = Integer.getInteger("http.maxRedirects", 20);

    public static int getMajorVersion()
    {
        return majorVersion;
    }

    public static int getMinorVersion()
    {
        return minorVersion;
    }

    public static int getRevisionVersion()
    {
        return revisionVersion;
    }

    public static int getBuildVersion()
    {
        return buildVersion;
    }

    public static Status getStatus()
    {
        return getResult(ForgeModContainer.getInstance()).status;
    }

    @Nullable
    public static String getTarget()
    {
        CheckResult res = getResult(ForgeModContainer.getInstance());
        return res.target != null ? res.target.toString() : null;
    }

    public static String getVersion()
    {
        return String.format("%d.%d.%d.%d", majorVersion, minorVersion, revisionVersion, buildVersion);
    }

    public static enum Status
    {
        PENDING(),
        FAILED(),
        UP_TO_DATE(),
        OUTDATED(3, true),
        AHEAD(),
        BETA(),
        BETA_OUTDATED(6, true);

        final int sheetOffset;
        final boolean draw, animated;

        Status()
        {
            this(0, false, false);
        }

        Status(int sheetOffset)
        {
            this(sheetOffset, true, false);
        }

        Status(int sheetOffset, boolean animated)
        {
            this(sheetOffset, true, animated);
        }

        Status(int sheetOffset, boolean draw, boolean animated)
        {
            this.sheetOffset = sheetOffset;
            this.draw = draw;
            this.animated = animated;
        }

        public int getSheetOffset()
        {
            return sheetOffset;
        }

        public boolean shouldDraw()
        {
            return draw;
        }

        public boolean isAnimated()
        {
            return animated;
        }

    }

    public static class CheckResult
    {
        public final Status status;
        @Nullable
        public final ComparableVersion target;
        public final Map<ComparableVersion, String> changes;
        @Nullable
        public final String url;

        private CheckResult(Status status, @Nullable ComparableVersion target, @Nullable Map<ComparableVersion, String> changes, @Nullable String url)
        {
            this.status = status;
            this.target = target;
            this.changes = changes == null ? Collections.<ComparableVersion, String>emptyMap() : Collections.unmodifiableMap(changes);
            this.url = url;
        }
    }

    // Gather a list of mods that have opted in to this update system by providing a URL.
    public static Map<ModContainer, URL> gatherMods()
    {
        Map<ModContainer, URL> ret = new HashMap<ModContainer, URL>();
        for (ModContainer mod : Loader.instance().getActiveModList())
        {
            URL url = mod.getUpdateUrl();
            if (url != null)
                ret.put(mod, url);
        }
        return ret;
    }

    private static Map<ModContainer, CheckResult> results = new ConcurrentHashMap<ModContainer, CheckResult>();
    private static final CheckResult PENDING_CHECK = new CheckResult(PENDING, null, null, null);

    public static CheckResult getResult(ModContainer mod)
    {
        if (mod == null) return PENDING_CHECK;
        if (mod instanceof InjectedModContainer)
            mod = ((InjectedModContainer)mod).wrappedContainer;
        CheckResult ret = results.get(mod);
        return ret == null ? PENDING_CHECK : ret;
    }
}

