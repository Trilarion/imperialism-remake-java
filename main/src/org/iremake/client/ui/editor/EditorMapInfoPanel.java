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
package org.iremake.client.ui.editor;

import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.iremake.client.ui.CommonElements;
import org.iremake.common.Settings;
import org.iremake.common.model.ServerScenario;
import org.iremake.common.model.map.MapPosition;
import org.iremake.common.model.map.Tile;

/**
 * The map info panel, located at the left lower part of the map tab. Infos about the current tile and ways to modify it.
 */
public class EditorMapInfoPanel extends JPanel {
    
    private static final Logger LOG = Logger.getLogger(EditorMapInfoPanel.class.getName());    

    private static final long serialVersionUID = 1L;
    private JLabel tile = new JLabel();
    private JLabel terrain = new JLabel();
    private JLabel resource = new JLabel();
    private JLabel nation = new JLabel();
    private JLabel province = new JLabel();

    /**
     * Setup of the panel and layout.
     */
    public EditorMapInfoPanel() {
        setBorder(CommonElements.createBorder("Info"));

        setLayout(new MigLayout("wrap 1"));
        add(tile);
        add(terrain);
        add(resource);
        add(nation);
        add(province);

        reset();
    }

    /**
     * When no tile is in focus, we need to reset the display.
     */
    private void reset() {
        tile.setText("Tile: None");
        terrain.setText("Terrain: None");
        resource.setText("Resource: None");
        nation.setText("Nation: None");
        province.setText("Province: None");
    }

    /**
     * Focus changed of main map. A new tile (of none) is in focus now. Need to update.
     *
     * @param p the map position
     * @param scenario the scenario
     */
    public void update(MapPosition p, ServerScenario scenario) {
        if (p.isOff()) {
            reset();
        } else {
            Tile t = scenario.getTileAt(p);
            tile.setText("Tile: " + Integer.toString(p.row) + ", " + Integer.toString(p.column));
            terrain.setText("Terrain: " + Settings.getTerrainName(t.terrainID));
            resource.setText("Resource: " + Settings.getResourceName(t.resourceID));
            nation.setText("Nation: " + scenario.getNationAt(p));
            province.setText("Province: " + scenario.getProvinceAt(p));
        }
    }
}
