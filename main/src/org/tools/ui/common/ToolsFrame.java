/*
 * Copyright (C) 2012 Trilarion
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
package org.tools.ui.common;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.tools.common.Loader;

/**
 * Just sets the basic properties of probably all Frames we are going to use.
 * 
 */
public class ToolsFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public ToolsFrame(String title) {
        super(title);
        initComponents();
    }

    /**
     * Initializes components.
     */
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setIconImage(Loader.getAsImage("/icons/tools/app.icon.png"));
    }
}