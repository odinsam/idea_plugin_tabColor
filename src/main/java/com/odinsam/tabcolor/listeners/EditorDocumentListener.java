package com.odinsam.tabcolor.listeners;

import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import org.apache.tools.ant.Project;
import org.jetbrains.annotations.NotNull;

public class EditorDocumentListener implements DocumentListener {
    
    @Override
    public void beforeDocumentChange(@NotNull DocumentEvent event) {
//        DocumentListener.super.beforeDocumentChange(event);
        System.out.println("beforeDocumentChange");
    }

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
//        DocumentListener.super.documentChanged(event);
        System.out.println("documentChanged");
    }
}
