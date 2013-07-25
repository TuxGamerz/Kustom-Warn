package me.kustomkraft.kustomwarn.utils;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.Bukkit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
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

            version = subNodes.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
            versionLink = subNodes.item(3).getTextContent();
            double pluginVersion = Double.valueOf(plugin.getDescription().getVersion());
            double webVersion = Double.valueOf(version);
            if (webVersion > pluginVersion){
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

}
