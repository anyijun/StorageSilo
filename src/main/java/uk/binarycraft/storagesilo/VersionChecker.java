package uk.binarycraft.storagesilo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionChecker implements Runnable
{
    private static String latestVersion;
    private static boolean isLatestVersion;

    @Override
    public void run()
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new URL("http://www.binarycraft.uk/storagesilo/1.9.4.txt").openStream();
        }
        catch (MalformedURLException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
        }

        try
        {
            latestVersion = IOUtils.readLines(inputStream).get(0);
        }
        catch (IOException ex)
        {
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static boolean isLatestVersion()
    {
        return isLatestVersion;
    }
}
