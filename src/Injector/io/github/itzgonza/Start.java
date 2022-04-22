package io.github.itzgonza;

import io.github.itzgonza.impl.DiscordInjector;

public class Start {

	public static void main(String[] argument) throws Exception {
		if (DiscordInjector.instance == null)
			DiscordInjector.instance = new DiscordInjector();
		DiscordInjector.instance.initialize();
	}
	
}