//
// MIT License
//
// Copyright (c) 2024 Incendo
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package org.incendo.cloud.discord.discord4j.example;

import cloud.commandframework.execution.ExecutionCoordinator;
import discord4j.core.DiscordClient;
import java.io.File;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.discord.discord4j.Discord4JCommandManager;
import org.incendo.cloud.discord.discord4j.Discord4JInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example Discord bot using cloud-discord4j
 */
public final class ExampleBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleBot.class);

    /**
     * Launches the bot.
     *
     * @param args ignored args
     */
    public static void main(final @NonNull String@NonNull[] args) {
        new ExampleBot();
    }

    private final Discord4JCommandManager<Discord4JInteraction> commandManager;
    private final BotConfiguration botConfiguration;
    private final DiscordClient discordClient;

    private ExampleBot() {
        LOGGER.info("Loading configuration...");
        this.botConfiguration = new PropertiesBotConfiguration(new File("bot.properties"));

        LOGGER.info("Creating command manager...");
        this.commandManager = new Discord4JCommandManager<>(
                ExecutionCoordinator.simpleCoordinator(),
                Discord4JInteraction.InteractionMapper.identity()
        );

        new Examples(this.commandManager).registerExamples();

        LOGGER.info("Starting Discord4J...");
        this.discordClient = DiscordClient.create(this.botConfiguration.token());
        this.discordClient.withGateway(this.commandManager::installEventListener).block();
    }
}
