/*
 * Copyright (C) 2013 Trilarion
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.iremake.server.network;

import org.iremake.common.network.messages.MessageContainer;
import org.iremake.server.client.ServerClient;

/**
 *
 */
public interface ServerContext {
    public void sendLobbyOverview(ServerClient recipient);

    public void broadcastArrivingLobbyClient(ServerClient arriving);

    public void broadcastNewChatMessage(String text, ServerClient sender);

    public String getIP(Integer id);

    public void disconnect(Integer id);

    public void sendMessage(Integer id, MessageContainer message);

    public boolean isRunning();

    public void stop();

    public String getStatus();

    public boolean start();

    public void process(Integer id, MessageContainer message);
}
