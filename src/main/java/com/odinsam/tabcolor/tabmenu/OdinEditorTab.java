package com.odinsam.tabcolor.tabmenu;

import com.github.markusbernhardt.proxy.selector.pac.JavaxPacScriptParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.odinsam.tabcolor.colors.TabColors;
import org.apache.tools.ant.taskdefs.Java;

import javax.swing.*;

public class OdinEditorTab extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        System.out.println("OdinEditorTab");
    }
}
