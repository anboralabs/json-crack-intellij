package co.anbora.labs.jsoncrack.ide.fileType;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FileTypePanel {
    private JTextField myPatternField;
    private JPanel myMainPanel;

    @NotNull
    public JTextField getPatternField() {
        return myPatternField;
    }

    @NotNull
    public JPanel getMainPanel() {
        return myMainPanel;
    }
}
