/*
 * Copyright (C) 2011 leclaire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openmole.ide.core;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openmole.ide.core.control.TaskSettingsManager;
import org.openmole.ide.core.palette.PaletteSupport;
import org.openmole.ide.core.control.MoleScenesManager;
import org.openmole.ide.core.action.AddMoleSceneAction;
import org.openmole.ide.core.action.EnableTaskDetailedViewAction;
import org.openmole.ide.core.action.RemoveAllMoleSceneAction;
import org.netbeans.spi.palette.PaletteController;
import org.openide.nodes.AbstractNode;
import org.openmole.ide.core.palette.CategoryBuilder;
import org.openmole.ide.core.display.MenuToggleButton2;
import org.openmole.ide.core.action.RemoveMoleSceneAction;
import scala.swing.Menu;
import scala.swing.MenuItem;


/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.openmole.ide.core//MoleScene//EN",
autostore = false)
@TopComponent.Description(preferredID = "MoleSceneTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
public final class MoleSceneTopComponent extends TopComponent {

    private static MoleSceneTopComponent instance;
    private PaletteController palette;
    private final InstanceContent ic = new InstanceContent();
    /** path to the icon used by the component and its open action */
    private static final String PREFERRED_ID = "MoleSceneTopComponent";

    public MoleSceneTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(MoleSceneTopComponent.class, "CTL_MoleSceneTopComponent"));
        setToolTipText(NbBundle.getMessage(MoleSceneTopComponent.class, "HINT_MoleSceneTopComponent"));

        MoleScenesManager.setTabbedPane(tabbedPane);
        TaskSettingsManager.setTabbedPane(tabbedPane);
        MoleScenesManager.display(MoleScenesManager.addMoleScene());

        createPalette();
        associateLookup(new AbstractLookup(ic));
        ic.add(palette);

        //  palette.addPropertyChangeListener(this);

        JToggleButton detailedViewButton = new JToggleButton("Detailed view");
        detailedViewButton.addActionListener(new EnableTaskDetailedViewAction());

        
        MenuToggleButton2 mt = new MenuToggleButton2("Mole");
        mt.addItem(new MenuItem(new AddMoleSceneAction("Add")));
        mt.addItem(new MenuItem(new RemoveMoleSceneAction("Remove")));
        mt.addItem(new MenuItem(new RemoveAllMoleSceneAction("Remove All")));

        toolBar.add(detailedViewButton);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(mt.peer());
        //  add(toolBar);
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

    }

    public void refreshPalette() {
        ic.remove(palette);
        createPalette();
        ic.add(palette);
        repaint();
    }

    public void createPalette() {
        AbstractNode paletteRoot = new AbstractNode(new CategoryBuilder());
        paletteRoot.setName("Entities");
        palette = PaletteSupport.createPalette(paletteRoot);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabbedPane = new javax.swing.JTabbedPane();
        toolBar = new javax.swing.JToolBar();

        jScrollPane1.setViewportView(tabbedPane);

        toolBar.setRollover(true);
        toolBar.setMaximumSize(new java.awt.Dimension(30, 15));
        toolBar.setMinimumSize(new java.awt.Dimension(30, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, toolBar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized MoleSceneTopComponent getDefault() {
        if (instance == null) {
            instance = new MoleSceneTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the MoleSceneTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized MoleSceneTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(MoleSceneTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof MoleSceneTopComponent) {
            return (MoleSceneTopComponent) win;
        }
        Logger.getLogger(MoleSceneTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
}