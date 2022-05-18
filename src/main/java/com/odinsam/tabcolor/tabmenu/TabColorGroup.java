package com.odinsam.tabcolor.tabmenu;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.tabs.TabInfo;
import org.apache.velocity.runtime.directive.Foreach;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;

import java.awt.*;

public class TabColorGroup extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        return new AnAction[]{
                new OdinTabColor("FS Tab"),
                new OdinTabColor("WS Tab"),
                new OdinTabColor("DZ Tab"),
                new OdinTabColor("QS Tab"),
                new OdinTabColor("SS Tab"),
                new OdinTabColor("XD Tab"),
                new OdinTabColor("DH Tab"),
                new OdinTabColor("LR Tab"),
                new OdinTabColor("ZS Tab"),
                new OdinTabColor("SM Tab"),
                new OdinTabColor("MS Tab"),
                new OdinTabClose("Close All haven't Tag Tabs")
        };
    }

    /**
     * @param e AnActionEvent
     * @return 获取所有editorWindow
     */
    public EditorWindow[] GetAllEditorWindow(@NotNull AnActionEvent e)
    {
        var project = e.getProject();
        final FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);
        if (manager.getWindows() != null) {
            return manager.getWindows();
        }
        return null;
    }

    /**
     * @param ew editorWindows
     * @return 当前操作的tab
     */
    public TabInfo GetTargetActionTab(EditorWindow @NotNull [] ew){
        for (var item : ew) {
            var targetEW = item.getTabbedPane().getTabs().getTargetInfo();
            if(targetEW!=null) {
                return targetEW;
            }
        }
        return null;
    }

    /**
     * @param tab 当前操作的tab
     * @param tabColorName 当前需要赋予的颜色
     */
    public void SetTabColor(@NotNull TabInfo tab, @NotNull String tabColorName)
    {
        var setColor = TabColors.OdinTabColors.get(tabColorName);
        if(tab.getTabColor()==null) {
            tab.setTabColor(setColor);
        }
        else {
            var currentColor = tab.getTabColor();
            var currentColorName = TabColors.GetColorNameByColor(currentColor);
            if(tabColorName.equals(currentColorName))
                tab.setTabColor(null);
            else 
                tab.setTabColor(setColor);
        }
    }
    class OdinTabSplit extends AnAction {
        OdinTabSplit(String title)
        {
            super(title);
        }
        public void actionPerformed(AnActionEvent e) {

        }
    }
    class OdinTabClose extends AnAction {
        OdinTabClose(String title)
        {
            super(title);
        }
        public void actionPerformed(AnActionEvent e) {
            // 获取所有editorWindow
            var editorWindows = GetAllEditorWindow(e);
            if(editorWindows!=null && editorWindows.length>0) {
                for (var ew : editorWindows) {
                    var tabs = ew.getTabbedPane().getTabs();
                    if(tabs!=null) {
                        for (var t :tabs.getTabs()) {
                            if(t.getTabColor()==null) {
                                ew.closeFile(ew.getFiles()[tabs.getIndexOf(t)]);
                            }
                        }
                    }
                }
            }
        }
    }
    class OdinTabColor extends AnAction {
        private String tabColorName = null;
        OdinTabColor(String title)
        {
            super(title);
            tabColorName = title.split(" ")[0];
        }

        public void actionPerformed(AnActionEvent e) {
            // 获取所有editorWindow
            var editorWindows = GetAllEditorWindow(e);
            if(editorWindows!=null && editorWindows.length>0) {
                var tab = GetTargetActionTab(editorWindows);
                if(tab!=null){
                    SetTabColor(tab,tabColorName);
                }
            }
        }
    }
    
}
