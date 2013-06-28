package me.kustomkraft.kustomwarn.utils;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.Bukkit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class PluginUpdater {

    private KustomWarn plugin;
    private URL fileUrl;
    private Logger logger = Bukkit.getLogger();

    private String version;
    private String versionLink;

    public PluginUpdater(KustomWarn plugin, String url) {
        this.plugin = plugin;

        try {
            this.fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    public boolean updateRequired(){
        try {
            InputStream input = this.fileUrl.openConnection().getInputStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

            Node latestVersion = document.getElementsByTagName("item").item(0);
            NodeList subNodes = latestVersion.getChildNodes();

            this.version = subNodes.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
            this.versionLink = subNodes.item(3).getTextContent();
            logger.info(this.version + " " + versionLink);
            if (!plugin.getDescription().getVersion().equals(this.version)){
                return true;
            }

        } catch (Exception e) {
            logger.severe("Error: " + e.getMessage());
        }

        return false;
    }

    public String getVersion(){
        return this.version;
    }

    public String getVersionLink(){
        return this.versionLink;
    }

    public void getFile (String url) {
        try{
            String localfile = plugin.getDataFolder().getAbsolutePath();
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            String fileName = plugin.getDataFolder().getAbsolutePath() + File.separatorChar;
            FileOutputStream fileOutputStream = new FileOutputStream(fileName + "Kustom-Warn.jar");
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream,1024);
            byte data[] = new byte[1024];
            logger.severe(fileName);
            while(in.read(data,0,1024)>=0)
            {
                outputStream.write(data);
            }
            outputStream.close();
            in.close();
            //Files.copy(new URL("http://host/site/filename").openStream(), Paths.get(localfile));
        }catch (Exception e){
            logger.severe("Error: " + e.getMessage());
        }
    }
}
