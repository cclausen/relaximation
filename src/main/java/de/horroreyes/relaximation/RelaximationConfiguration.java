package de.horroreyes.relaximation;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RelaximationConfiguration implements SearchableConfigurable {

    private RelaximationSettingsComponent relaximationSettingsComponent;

    @Override
    public @NotNull String getId() {
        return "de.horroreyes.Relaximation";
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Relaximation";
    }

    @Override
    public @Nullable JComponent createComponent() {
        relaximationSettingsComponent = new RelaximationSettingsComponent();
        return relaximationSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        RelaximationSettingsState settings = RelaximationSettingsState.getInstance();
        boolean modified = !relaximationSettingsComponent.getUserNameText().equals(settings.userId);
        modified |= relaximationSettingsComponent.getIdeaUserStatus() != settings.ideaStatus;
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        RelaximationSettingsState settings = RelaximationSettingsState.getInstance();
        settings.userId = relaximationSettingsComponent.getUserNameText();
        settings.ideaStatus = relaximationSettingsComponent.getIdeaUserStatus();
    }
    @Override
    public void reset() {
        RelaximationSettingsState settings = RelaximationSettingsState.getInstance();
        relaximationSettingsComponent.setUserNameText(settings.userId);
        relaximationSettingsComponent.setIdeaUserStatus(settings.ideaStatus);
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return relaximationSettingsComponent.getPreferredFocusedComponent();
    }

    @Override
    public void disposeUIResources() {
        relaximationSettingsComponent = null;
    }
}
