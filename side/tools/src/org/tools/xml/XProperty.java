/*
 * Copyright (C) 2010-12 Trilarion
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
package org.tools.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Property implementation with a HashMap and XML import/export capability.
 *
 * If you want to use it as a Table, just convert to XTable and back
 */
// TODO null to XML and back? how is it handled
// TODO add statistics (how to make them transient)
public class XProperty implements FullXMLable {

    public final static String XML_NAME = "Properties";
    private static final Logger LOG = Logger.getLogger(XProperty.class.getName());
    private Map<String, String> content;

    public XProperty(int capacity) {
        content = new HashMap<>(capacity);
    }

    public XProperty(Map<String, String> content) {
        this.content = content;
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return content.containsKey(key);
    }

    /**
     * Number of properties, i.e. entries in the map.
     *
     * @return The size.
     */
    public int size() {
        return content.size();
    }

    /**
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return content.get(key);
    }

    /**
     *
     * @return
     */
    public Set<String> keySet() {
        return content.keySet();
    }

    /**
     * If the key is not existing the return is false without any warning.
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        // if key is not existing we have parseBoolean(null) == false
        return Boolean.parseBoolean(get(key));
    }

    /**
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    /**
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        content.put(key, value);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        content.put(key, Boolean.toString(value));
    }

    /**
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        content.put(key, Integer.toString(value));
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean removeKey(String key) {
        return content.remove(key) != null;
    }

    /**
     * Removes all entries.
     */
    public void clear() {
        content.clear();
    }

    /**
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    public boolean renameKey(String oldKey, String newKey) {
        if (content.containsKey(oldKey) && !content.containsKey(newKey)) {
            String value = get(oldKey);
            removeKey(oldKey);
            put(newKey, value);
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @return
     */
    @Override
    public Node toXML() {
        return new Node(content, XML_NAME);
    }

    /**
     * Create from XML node.
     *
     * @param parent
     */
    @Override
    public void fromXML(Node parent) {
        parent.checkNode(XML_NAME);
        content = parent.toStringMap();
    }
}