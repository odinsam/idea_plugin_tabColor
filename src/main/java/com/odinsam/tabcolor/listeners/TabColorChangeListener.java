package com.odinsam.tabcolor.listeners;

import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.ui.FileColorManager;
import com.intellij.ui.tabs.TabInfo;
import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class TabColorChangeListener extends ChangeListenerBase {
    private JPanel _panel;
    public TabColorChangeListener(
            FileColorManager colorManager, 
            EditorWindow editorWindow, 
            TabChangedListener tabChangedListener,
            EditorDocumentListener editorDocumentListener) {
        super(colorManager,editorWindow,tabChangedListener,editorDocumentListener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final TabInfo selectedTab = getSelectedTab();
        if (evt.getSource() != null && selectedTab != null) {
            System.out.println("propertyChange");
        }
    }
}
