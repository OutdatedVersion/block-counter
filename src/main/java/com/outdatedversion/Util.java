package com.outdatedversion;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * OutdatedVersion
 * Sep/19/2016 (6:14 PM)
 */

public class Util
{

    /**
     * The paragraph character is effort
     * to type most of the time. So, this
     * is a solution.
     *
     * @param raw The String using '&' as
     *            the color code
     * @return The freshly colored string
     */
    static String colorize(String raw)
    {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    /**
     * Fancy up a message with colors, and
     * then replaces a certain parameter with
     * a value.
     *
     * @param raw The message you got
     * @param pairs What you will be replacing
     * @return What you got after everything
     *         is said and done
     */
    static String formatMessage(String raw, String... pairs)
    {
        for (String pair : pairs)
        {
            final String[] _split = pair.split(":");

            raw = raw.replaceAll("%" + _split[0] + "%", _split[1]);
        }

        return colorize(raw);
    }

    /**
     * Turns an enum into a much
     * more player safe name
     *
     * @param e The enum
     * @return Safe thing
     */
    static String friendlyEnum(Enum e)
    {
        return WordUtils.capitalizeFully(e.name().replaceAll("_", " "));
    }


    /**
     * Verifies that a user's input actually
     * matches some enum that we're searching
     * for
     *
     * @param input What the user entered
     * @param clazz The type we're looking for
     * @param message If they did not enter a
     *                valid enum we'll print
     *                these messages out to the
     *                server's console
     * @return Either the matched enum or {@code null}
     */
    static <B> B matchEnum(String input, Class<B> clazz, String... message)
    {
        Validate.isTrue(clazz.isEnum(), "You did not provide an enum!");

        // Check the user's input
        for (B constant : clazz.getEnumConstants())
            if (constant.toString().equalsIgnoreCase(input))
                return constant;

        // Print the message
        for (String line : message)
            Bukkit.getLogger().severe(line);

        return null;
    }

}
