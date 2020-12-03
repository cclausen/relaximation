package de.horroreyes.relaximation;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.JBIntSpinner;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RelaximationSettingsComponent {
    private final JPanel myMainPanel;
    private final JBTextField searchString = new JBTextField("relax animal");
    String[] comboBoxListe = {"Alle Keywords kombinieren", "Alle Keywords zufällig kombinieren",
            "Alle Keywords einzeln nacheinander"};
    ComboBox<String> combination = new ComboBox<>(comboBoxListe);
    private final JBIntSpinner duration = new JBIntSpinner(30, 3, 600);
    private final JBIntSpinner loopSize = new JBIntSpinner(30, 3, 600);

    public RelaximationSettingsComponent() {

        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter keywords to search: "), searchString, 1, false)
                .addComponent(combination)
                .addLabeledComponent(new JBLabel("Wie lange soll ein gif laufen"), duration)
                .addLabeledComponent(new JBLabel("Wie viele Gifs von einer Suche sollen angezeigt werden bis die " +
                        "nächste Wuche angeschmissen wird?"), loopSize)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return searchString;
    }

    public String getSearchString() {
        return searchString.getText();
    }

    public void setUserNameText(@NotNull String search) {
        searchString.setText(search);
    }

    public Integer getDuration() {
        return duration.getNumber();
    }

    public void setDuration(int duration) {
        this.duration.setNumber(duration);
    }

    public Integer getLoopSize() {
        return duration.getNumber();
    }

    public void setLoopSize(int loopSize) {
        this.loopSize.setNumber(loopSize);
    }

    public String getCombination() {
        return combination.getItem();
    }

    public void setCombination(String combination) {
        this.combination.setItem(combination);
    }

}
