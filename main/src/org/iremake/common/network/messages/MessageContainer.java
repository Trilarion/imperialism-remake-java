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
package org.iremake.common.network.messages;

/**
 *
 */
public class MessageContainer<T> {

    private T attachment;
    private Message type;

    private MessageContainer() {
    }

    public MessageContainer(Message type) {
        if (type == null) {
            throw new RuntimeException("Argument type cannot be null.");
        }
        this.type = type;
    }

    public MessageContainer(T attachment, Message type) {
        if (type == null) {
            throw new RuntimeException("Argument type cannot be null.");
        }
        if (!type.checkClass(attachment)) {
            throw new RuntimeException("Runtime class of argument content disagrees with information from MessageType type.");
        }
        this.attachment = attachment;
        this.type = type;
    }

    public T getAttachment() {
        return attachment;
    }

    public Message getType() {
        return type;
    }
}
