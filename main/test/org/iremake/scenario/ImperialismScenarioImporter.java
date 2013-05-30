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
package org.iremake.scenario;

import icons.TestIOManager;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;
import nu.xom.ParsingException;
import org.iremake.common.model.Nation;
import org.iremake.common.model.Province;
import org.iremake.common.model.Scenario;
import org.iremake.common.model.map.MapPosition;
import org.iremake.common.model.map.Tile;
import org.tools.io.FileResource;
import org.tools.io.Resource;
import org.tools.io.ResourceUtils;
import org.tools.ui.utils.LookAndFeel;
import org.tools.xml.Node;
import org.tools.xml.XList;
import org.tools.xml.XMLHelper;

/**
 * Reads the output from the python map import script and inserts a scenario
 * file accordingly.
 */
public class ImperialismScenarioImporter extends JFrame {

    private final Map<Integer, Integer> riverIDs = new HashMap<>();

    private static final FileFilter ImportFileFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() || (f.getName().endsWith(".map") || f.getName().endsWith(".xml"));
        }

        @Override
        public String getDescription() {
            return "Map or Scenario Files";
        }
    };
    private static final long serialVersionUID = 1L;
    private JFileChooser fileChooser;

    /**
     * Creates new form ImperialismScenarioImporter
     */
    public ImperialismScenarioImporter() {
        // form initialization
        initComponents();

        // icon
        setIconImage(TestIOManager.getAsImage("app.icon.png"));

        // init file chooser
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(ImportFileFilter);

        // river overlay IDs
        riverIDs.put(0, Scenario.RIVERID_NONE);

        // connections
        riverIDs.put(11,1);
        riverIDs.put(12,2);
        riverIDs.put(13,4);
        riverIDs.put(14,3);
        riverIDs.put(15,6);
        riverIDs.put(16,10);
        riverIDs.put(17,8);
        riverIDs.put(18,12);
        riverIDs.put(19,7);
        riverIDs.put(20,11);
        riverIDs.put(21,9);
        riverIDs.put(22,13);
        riverIDs.put(23,15);
        riverIDs.put(24,14);
        riverIDs.put(25,16);
        riverIDs.put(26,18);

        // heads
        riverIDs.put(43,20);
        riverIDs.put(44,21);
        riverIDs.put(45,22);
        riverIDs.put(46,23);
        riverIDs.put(47,24);
        riverIDs.put(48,26);
        riverIDs.put(49,25);
        riverIDs.put(50,27);

        // mouths
        riverIDs.put(51,28);
        riverIDs.put(52,29);
        riverIDs.put(53,30);
        riverIDs.put(54,31);
        riverIDs.put(55,32);
        riverIDs.put(56,34);
        riverIDs.put(57,33);
        riverIDs.put(58,35);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        importButton = new JButton();
        progressBar = new JProgressBar();
        statusScrollPane = new JScrollPane();
        statusTextArea = new JTextArea();
        optionPanel = new JPanel();
        importmapTextField = new JTextField();
        scenarioTextField = new JTextField();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        titleTextField = new JTextField();
        jLabel4 = new JLabel();
        nationNamesTextField = new JTextField();
        jLabel5 = new JLabel();
        nationColorsTextField = new JTextField();
        jLabel6 = new JLabel();
        provinceReplacementsTextField = new JTextField();
        jLabel7 = new JLabel();
        baseTextField = new JTextField();
        loadButton = new JButton();
        saveButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Imperialism Map Import");
        setLocationByPlatform(true);
        setResizable(false);

        importButton.setText("Import and Convert Scenario");
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });

        statusTextArea.setColumns(20);
        statusTextArea.setFont(new Font("Tahoma", 0, 11)); // NOI18N
        statusTextArea.setRows(5);
        statusTextArea.setText("status");
        statusScrollPane.setViewportView(statusTextArea);

        optionPanel.setBorder(CommonElements.createBorder("Options"));

        importmapTextField.setName("import-file"); // NOI18N

        scenarioTextField.setToolTipText("Will be modified in the process!");
        scenarioTextField.setName("export-file"); // NOI18N

        jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel1.setText("import file name");

        jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel2.setText("export file name");

        jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel3.setText("scenario title");

        titleTextField.setName("title"); // NOI18N

        jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel4.setText("nation names");

        nationNamesTextField.setName("nation-names"); // NOI18N

        jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel5.setText("nation colors");

        nationColorsTextField.setName("nation-colors"); // NOI18N

        jLabel6.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel6.setText("province replace");

        provinceReplacementsTextField.setName("province-replacements"); // NOI18N

        jLabel7.setHorizontalAlignment(SwingConstants.TRAILING);
        jLabel7.setText("base path");

        baseTextField.setText("C:\\Users\\jkeller1\\Dropbox\\remake\\scenario import\\europe 1814\\");

            GroupLayout optionPanelLayout = new GroupLayout(optionPanel);
            optionPanel.setLayout(optionPanelLayout);
            optionPanelLayout.setHorizontalGroup(
                optionPanelLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(optionPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scenarioTextField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                        .addComponent(titleTextField)
                        .addComponent(importmapTextField)
                        .addComponent(nationNamesTextField, GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addComponent(nationColorsTextField)
                        .addComponent(provinceReplacementsTextField, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                        .addComponent(baseTextField))
                    .addContainerGap())
            );
            optionPanelLayout.setVerticalGroup(
                optionPanelLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(optionPanelLayout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(baseTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(importmapTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(scenarioTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(nationNamesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(nationColorsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(optionPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(provinceReplacementsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(145, Short.MAX_VALUE))
            );

            loadButton.setText("Load Setting");
            loadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    loadButtonActionPerformed(evt);
                }
            });

            saveButton.setText("Save Setting");
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    saveButtonActionPerformed(evt);
                }
            });

            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(statusScrollPane)
                        .addComponent(optionPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(loadButton)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(saveButton)
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(importButton)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(optionPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(statusScrollPane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(importButton)
                        .addComponent(loadButton)
                        .addComponent(saveButton))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void importButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        progressBar.setValue(0);

        // preparations
        String text = provinceReplacementsTextField.getText();
        Map<String, String> provinceReplacements = new HashMap<>();
        for (String replacement : text.split("\\|")) { // because split uses regex
            String[] replace = replacement.split(":");
            if (replace.length != 2) {
                updateStatus("error in replacement string");
                return;
            }
            provinceReplacements.put(replace[0], replace[1]);
        }


        // create files and test import file on existence
        String in = baseTextField.getText() + importmapTextField.getText();
        File importFile = new File(in);
        if (!importFile.exists() || !importFile.isFile()) {
            updateStatus("import file " + in + " not found, will stop");
            return;
        }
        String out = baseTextField.getText() + scenarioTextField.getText();
        File exportFile = new File(out);

        // read import file
        ByteBuffer bb;
        try {
            FileInputStream is = new FileInputStream(importFile);
            FileChannel ic = is.getChannel();
            bb = ByteBuffer.allocate((int) ic.size());
            ic.read(bb);
        } catch (IOException ex) {
            updateStatus("could not read import file, will stop");
            return;
        }
        bb.rewind();
        bb.order(ByteOrder.LITTLE_ENDIAN);
        IntBuffer ib = bb.asIntBuffer();
        progressBar.setValue(10);

        // read number of columns and rows
        int columns = ib.get();
        int rows = ib.get();
        if (columns < 0 || columns > 200 || rows < 0 || rows > 200) {
            updateStatus("values for columns/rows out of bounds, will stop");
            return;
        }
        updateStatus(String.format("map size %dx%d", rows, columns));

        // compare remaining size with estimated size
        final int Np = 384;
        final int chunk = columns * rows;
        int size = 8 * chunk + Np * 10;
        if (size != ib.remaining()) {
            updateStatus("size of input data not correct, will stop");
            return;
        }

        // read all chunks into different arrays
        int[] terrain_underlay = new int[chunk];
        ib.get(terrain_underlay);

        int[] terrain_overlay = new int[chunk];
        ib.get(terrain_overlay);

        int[] countries = new int[chunk];
        ib.get(countries);

        int[] resources = new int[chunk];
        ib.get(resources);

        int[] rivers = new int[chunk];
        ib.get(rivers);

        int[] provinces = new int[chunk];
        ib.get(provinces);

        int[] cities = new int[chunk];
        ib.get(cities);

        int[] railroad = new int[chunk];
        ib.get(railroad);

        int[] names = new int[Np * 10];
        ib.get(names);

        // this should never happen
        if (ib.hasRemaining()) {
            updateStatus("there is some data left, which shouldn't be");
            return;
        }

        progressBar.setValue(20);
        updateStatus("data imported successfully");

        // transform province names into string array
        String[] pnames = new String[Np];
        for (int i = 0; i < Np; i++) {
            StringBuilder builder = new StringBuilder(10);
            for (int j = 0; j < 10; j++) {
                int value = names[i * 10 + j];
                if (value == 0) {
                    break;
                } else {
                    builder.append((char) value);
                }
            }
            text = builder.toString();
            // look up replacement
            if (provinceReplacements.containsKey(text)) {
                text = provinceReplacements.get(text);
            }
            // store
            pnames[i] = text;
        }

        // create new scenario
        Scenario scenario = new Scenario();
        scenario.createEmptyMap(rows, columns);
        scenario.setTitle(titleTextField.getText());

        // check that if terrain_underlay is ocean also terrain_overlay is ocean
        for (int i = 0; i < chunk; i++) {
            if ((terrain_underlay[i] == 5) != (terrain_overlay[i] == 0)) {
                updateStatus("terrain underlay and overlay differ in ocean description, will stop");
                return;
            }
        }

        // detect countries
        Set<Integer> uc = new HashSet<>(20);
        for (int i = 0; i < chunk; i++) {
            if (terrain_underlay[i] != 5) {
                uc.add(countries[i]);
            }
        }
        updateStatus(String.format("contains %d nations", uc.size()));

        // put countries into list and get names
        XList<Nation> nations = scenario.getNations();
        Map<Integer, Nation> nmap = new HashMap<>(30);
        String[] nationNames = nationNamesTextField.getText().split(", ");
        String[] nationColors = nationColorsTextField.getText().split(", ");
        int id = 1;
        for (Integer i : uc) {
            String name = String.format("Nation %2d", id);
            if (id <= nationNames.length) {
                name = nationNames[id-1];
            }
            Nation nation = new Nation();
            nation.setProperty(Nation.KEY_NAME, name);
            if (id <= nationColors.length) {
                nation.setProperty(Nation.KEY_COLOR, nationColors[id-1]);
            }
            nmap.put(i, nation);
            nations.addElement(nation);
            id++;
        }

        // detect provinces
        Set<Integer> up = new HashSet<>(1_000);
        for (int i = 0; i < chunk; i++) {
            if (terrain_underlay[i] != 5) {
                up.add(provinces[i]);
            }
        }
        updateStatus(String.format("contains %d provinces", up.size()));

        // generate province names
        Map<Integer, String> pmap = new HashMap<>(Np);
        id = 1;
        for (Integer i : up) {
            // String name = String.format("Province %d", id);
            String name = pnames[i];
            pmap.put(i, name);
            id++;
        }

        // add provinces to scenario
        Map<Integer, Province> ppmap = new HashMap<>(1_000);
        Set<Integer> processed = new HashSet<>(1_000);
        for (int i = 0; i < chunk; i++) {
            if (terrain_underlay[i] != 5) {
                if (!processed.contains(provinces[i])) {
                    Nation nation = nmap.get(countries[i]);
                    String name = pmap.get(provinces[i]);
                    Province province = scenario.createProvince(name);
                    nation.addProvince(province);
                    ppmap.put(provinces[i], province);
                    processed.add(provinces[i]);
                }
            }
        }

        // random number generator for scrubforest
        Random rnd = new Random(42);

        // set terrain
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                MapPosition pos = new MapPosition(row, column);
                Tile tile = scenario.getTileAt(pos);
                int i = column + row * columns;

                // set terrains
                // sea
                if (terrain_underlay[i] == 5) {
                    tile.terrainID = 1;
                }
                // plains
                if (terrain_underlay[i] == 0 || terrain_underlay[i] == 1 || terrain_underlay[i] == 7) {
                    tile.terrainID = 2;
                }
                // hills
                if (terrain_underlay[i] == 2) {
                    tile.terrainID = 3;
                }
                // mountains
                if (terrain_underlay[i] == 3) {
                    tile.terrainID = 4;
                }
                // tundra
                if (terrain_underlay[i] == 6 && terrain_overlay[i] == 12) {
                    tile.terrainID = 5;
                }
                // swamp
                if (terrain_underlay[i] == 4) {
                    tile.terrainID = 6;
                }
                // desert
                if (terrain_underlay[i] == 6 && terrain_overlay[i] == 11) {
                    tile.terrainID = 7;
                }

                // set resources
                // grain
                tile.resourceVisible = false;
                if (resources[i] == 17) {
                    tile.resourceID = 1;
                    tile.resourceVisible = true;
                }
                // orchard
                if (resources[i] == 18) {
                    tile.resourceID = 2;
                    tile.resourceVisible = true;
                }
                // buffalo
                if (resources[i] == 20) {
                    tile.resourceID = 3;
                    tile.resourceVisible = true;
                }
                // cotton
                if (resources[i] == 0) {
                    tile.resourceID = 4;
                    tile.resourceVisible = true;
                }
                // sheep
                if (resources[i] == 1) {
                    tile.resourceID = 5;
                    tile.resourceVisible = true;
                }
                // forest
                if (resources[i] == 2 && terrain_overlay[i] == 13) {
                    tile.resourceID = 6;
                    tile.resourceVisible = true;
                    // 10% of forest goes randomly to scrubforest
                    if (rnd.nextFloat() < 0.1f) {
                        tile.resourceID = 7;
                    }
                }

                // scrubforest
                if (resources[i] == 2 && terrain_overlay[i] == 15) {
                    tile.resourceID = 7;
                    tile.resourceVisible = true;
                }
                // oil
                if (resources[i] == 6) {
                    tile.resourceID = 8;
                }
                // coal
                if (resources[i] == 3) {
                    tile.resourceID = 9;
                }
                // ore
                if (resources[i] == 4) {
                    tile.resourceID = 10;
                }

                // set provinces
                if (terrain_underlay[i] != 5) {
                    tile.provinceID = ppmap.get(provinces[i]).getID();
                }

                // set railroad (E, SE, SW)
                if ((railroad[i] & (1 << 2)) != 0) {
                    tile.railroadConfig &= Tile.RailroadEast;
                }
                if ((railroad[i] & (1 << 3)) != 0) {
                    tile.railroadConfig &= Tile.RailroadSouthEast;
                }
                if ((railroad[i] & (1 << 4)) != 0) {
                    tile.railroadConfig &= Tile.RailroadSouthWest;
                }

                // river
                tile.riverID = riverIDs.get(rivers[i]);

                // if city at this position, tell province about
                if (cities[i] != 0) {
                    ppmap.get(provinces[i]).setTownPosition(pos);
                }
                // if capital, tell nation about
                if (cities[i] == 35) {
                    nmap.get(countries[i]).setProperty(Nation.KEY_CAPITAL, String.valueOf(ppmap.get(provinces[i]).getID()));
                }
            }
        }

        // one engineer unit in all nation's capitals

        try {
            Resource resource = new FileResource(exportFile);
            XMLHelper.write(resource, scenario);
        } catch (IOException ex) {
            updateStatus("could not write to scenario file");
            return;
        }

        updateStatus("written to " + out);
        updateStatus("conversion successful");
        progressBar.setValue(100);
    }//GEN-LAST:event_importButtonActionPerformed

    private void saveButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        String out = baseTextField.getText() + "import.settings.xml";

        Resource resource;
        try {
            resource = ResourceUtils.asResource(out);
        } catch (IOException ex) {
            updateStatus("Cannot open save location.");
            return;
        }

        Node parent = new Node("Scenario-Importer");
        JTextComponent[] components = {importmapTextField, scenarioTextField, titleTextField, nationNamesTextField, nationColorsTextField, provinceReplacementsTextField};

        for (JTextComponent component : components) {
            Node child = new Node("Component-" + component.getName());
            // child.appendChild(component.getText());
            child.addAttribute("content", component.getText());
            parent.appendChild(child);
        }
        try {
            XMLHelper.write(resource, parent);
        } catch (IOException ex) {
            updateStatus("Cannot save to file.");
            return;
        }

        updateStatus("All content written.");
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed

        String in = baseTextField.getText() + "import.settings.xml";

        Resource resource;
        try {
            resource = ResourceUtils.asResource(in);
        } catch (IOException ex) {
            updateStatus("Cannot open load location.");
            return;
        }

        Node parent;
        try {
            parent = XMLHelper.read(resource);
        } catch (IOException | ParsingException ex) {
            updateStatus("Cannot load content.");
            return;
        }

        if (parent.getChildCount() != 6) {
            updateStatus("Wrong number of elements in xml.");
            return;
        }

        JTextComponent[] components = {importmapTextField, scenarioTextField, titleTextField, nationNamesTextField, nationColorsTextField, provinceReplacementsTextField};

        for (JTextComponent component : components) {
            component.setText(parent.getFirstChild("Component-" + component.getName()).getAttributeValue("content"));
        }

        updateStatus("All content read.");
    }//GEN-LAST:event_loadButtonActionPerformed

    private void updateStatus(String message) {
        statusTextArea.setText(statusTextArea.getText() + "\r\n" + message);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        LookAndFeel.setSystemLookAndFeel();

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImperialismScenarioImporter().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTextField baseTextField;
    private JButton importButton;
    private JTextField importmapTextField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JButton loadButton;
    private JTextField nationColorsTextField;
    private JTextField nationNamesTextField;
    private JPanel optionPanel;
    private JProgressBar progressBar;
    private JTextField provinceReplacementsTextField;
    private JButton saveButton;
    private JTextField scenarioTextField;
    private JScrollPane statusScrollPane;
    private JTextArea statusTextArea;
    private JTextField titleTextField;
    // End of variables declaration//GEN-END:variables
    private static final Logger LOG = Logger.getLogger(ImperialismScenarioImporter.class.getName());
}
