package com.odinsam.tabcolor.listeners;


import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.FileColorManager;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.tabs.TabInfo;
import com.odinsam.tabcolor.tabmenu.TabsPainter;
import kotlin.TypeCastException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public  class  TabChangedListener implements  FileEditorManagerListener{
    private volatile int selectedTabIdx = -1;
    private final TabsPainter tabsPainter;
    
    public TabChangedListener(){
        tabsPainter = new TabsPainter();
    }
    
    @Override
    public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
//        FileEditorManagerListener.super.fileOpened(source, file);
        final Project project = fileEditorManager.getProject();
        final FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);
        final FileColorManager fileColorManager = FileColorManager.getInstance(project);
        for (EditorWindow editorWindow : manager.getWindows()) {
            final EditorWithProviderComposite fileComposite = editorWindow.findFileComposite(virtualFile);
            final int editorIndex = getEditorIndex(editorWindow, fileComposite);

            setSelectedTabIdx(editorIndex);
            tabsPainter.colorTabs(fileColorManager, fileColorManager.getFileColor(virtualFile), editorWindow, editorIndex);
            addTabChangedListeners(fileColorManager, editorWindow, editorIndex);
        }
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        var fileEditor = event.getManager().getSelectedEditor();
        var panel = getPanel(fileEditor);
        System.out.println(panel.getBackground());
    }

    public int getSelectedTabIdx() {
        return selectedTabIdx;
    }
    public TabsPainter getTabsPainter() {
        return tabsPainter;
    }
    
    
    private void addTabChangedListeners(FileColorManager colorManager, EditorWindow editorWindow, int editorIndex) {
        TabInfo tab = editorWindow.getTabbedPane().getTabs().getTabs().get(editorIndex);
        if (tab != null) {
            var fileEditor = (FileEditor)editorWindow.getSelectedEditor();
            var panel = getPanel(fileEditor);
            final TabColorChangeListener listener = new TabColorChangeListener(colorManager, editorWindow, this);
            tab.getChangeSupport().addPropertyChangeListener(TabInfo.TAB_COLOR, listener);


        }
    }
    private void setSelectedTabIdx(int index) {
        if (index >= 0) {
            selectedTabIdx = index;
        }
    }
    private int getEditorIndex(@NotNull EditorWindow editorWindow, EditorWithProviderComposite fileComposite) {
        int index = -1;
        for (EditorWithProviderComposite editorWithProviderComposite : editorWindow.getEditors()) {
            index++;
            if (editorWithProviderComposite.equals(fileComposite)) {
                break;
            }
        }

        return index;
    }
    private final JPanel getPanel(FileEditor editor) {
        if (!(editor instanceof TextEditor)) {
            // this.logger.debug("I01: Injection failed, only text editors are supported currently.");
            return null;
        } else {
            try {
                JComponent component = editor.getComponent();
                if (component == null) {
                    throw new TypeCastException("null cannot be cast to non-null type javax.swing.JPanel");
                } else {
                    JPanel outerPanel = (JPanel)component;
                    LayoutManager layoutManager = outerPanel.getLayout();
                    if (layoutManager == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.awt.BorderLayout");
                    } else {
                        BorderLayout outerLayout = (BorderLayout)layoutManager;
                        Component layoutComponent = outerLayout.getLayoutComponent("Center");
                        if (layoutComponent instanceof JBSplitter) {
                            component = ((JBSplitter)layoutComponent).getFirstComponent();
                            if (component == null) {
                                throw new TypeCastException("null cannot be cast to non-null type javax.swing.JPanel");
                            }

                            JPanel editorComp = (JPanel)component;
                            layoutManager = editorComp.getLayout();
                            if (layoutManager == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.awt.BorderLayout");
                            }

                            layoutComponent = ((BorderLayout)layoutManager).getLayoutComponent("Center");
                        }

                        if (layoutComponent == null) {
                            throw new TypeCastException("null cannot be cast to non-null type javax.swing.JLayeredPane");
                        } else {
                            JLayeredPane pane = (JLayeredPane)layoutComponent;
                            Component component1;
                            JPanel jpanel;
                            if (pane.getComponentCount() > 1) {
                                component1 = pane.getComponent(1);
                                if (component1 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type javax.swing.JPanel");
                                }

                                jpanel = (JPanel)component1;
                            } else {
                                component1 = pane.getComponent(0);
                                if (component1 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type javax.swing.JPanel");
                                }

                                jpanel = (JPanel)component1;
                            }

                            JPanel panel = jpanel;
                            layoutManager = panel.getLayout();
                            if (layoutManager == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.awt.BorderLayout");
                            } else {
                                return panel;
                            }
                        }
                    }
                }
            } catch (ClassCastException var7) {
                Throwable throwable = var7;
                if (throwable == null) {
                    return null;
                    // throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
                } else {
                    throwable.printStackTrace();
                    return null;
                }
            }
        }
    }
}