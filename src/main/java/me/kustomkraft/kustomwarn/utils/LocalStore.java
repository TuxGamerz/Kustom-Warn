package me.kustomkraft.kustomwarn.utils;

import me.kustomkraft.kustomwarn.commands.KWarn;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

public class LocalStore {

    private KWarn kwarn;
    private ArrayList<String> output;
    private Logger logger = Bukkit.getLogger();
    private File storageFile;

    public LocalStore (File file) {
        storageFile = file;
        output = new ArrayList();

        if (!storageFile.exists()){
            try {
                storageFile.createNewFile();
            } catch (IOException e){
                logger.info(e.getLocalizedMessage());
            }
        }
    }

    public void load () {
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(storageFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) == null) {
                if (kwarn.warningReason != null) {
                    add(line, line, line, line);
                } else {
                    addReason(line, line, line, line, line);
                }
            }
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            logger.info(e.getLocalizedMessage());
        }
    }

    public void save () {
        try {
            FileWriter fileWriter = new FileWriter(storageFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String value: output) {
                bufferedWriter.write(value + " ");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            logger.info(e.getLocalizedMessage());
        }
    }

    public int getWarnings (String player) {
        int warnings = 0;
        try {
            Scanner scanner = new Scanner(storageFile);
            int count = 0;
            while (scanner.hasNextLine()) {
                String playerName = null;
                if (playerName.equalsIgnoreCase(player)) {
                    count++;
                    warnings += count;
                }
            }
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }
        return warnings;
    }

    public String signWarnings (String player) {
        String warningText = null;
        try{
            Scanner scanner = new Scanner(storageFile);
            while (scanner.next() != null) {
                String playerName = null;
                if (playerName.equalsIgnoreCase(player)) {
                    if (scanner.next().equalsIgnoreCase(playerName)) {

                    }
                }
            }
        } catch (IOException e) {
            logger.info(e.getLocalizedMessage());
        }
        return warningText;
    }

    public String displayWarning (String player) {
        String warningInfo = null;
        return warningInfo;
    }

    public String displaySpecific (String indexNumber, String player) {
        String warningInfo = null;
        return warningInfo;
    }

    public void add (String warningNumber,String offender, String admin, String date) {
        output.add(warningNumber + " | " + offender + " was warned by  " + admin + " | " + date);
    }

    public void addReason (String warningNumber, String offender, String admin, String reason, String date) {
        output.add(warningNumber + " | " + offender + " was warned by " + admin +  " for " + reason + " | " + date);
    }

    public void remove (int index, String playerName) {
        storageFile.delete();
    }

}
