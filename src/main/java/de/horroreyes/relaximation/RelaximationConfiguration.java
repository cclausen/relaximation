package de.horroreyes.relaximation;

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
        boolean modified = !relaximationSettingsComponent.getSearchString().equals(settings.searchString);
        modified = modified || !relaximationSettingsComponent.getDuration().equals(settings.duration);
        modified = modified || !relaximationSettingsComponent.getLoopSize().equals(settings.loopSize);
        modified =
                modified || !relaximationSettingsComponent.getCombination().equals(settings.combination);
        return modified;
    }

    @Override
    public void apply() {
        RelaximationSettingsState settings = RelaximationSettingsState.getInstance();
        settings.searchString = relaximationSettingsComponent.getSearchString();
        settings.duration = relaximationSettingsComponent.getDuration();
        settings.loopSize = relaximationSettingsComponent.getLoopSize();
        settings.combination = relaximationSettingsComponent.getCombination();
    }

    @Override
    public void reset() {
        RelaximationSettingsState settings = RelaximationSettingsState.getInstance();
        relaximationSettingsComponent.setUserNameText(settings.searchString);
        relaximationSettingsComponent.setDuration(settings.duration);
        relaximationSettingsComponent.setLoopSize(settings.loopSize);
        relaximationSettingsComponent.setCombination(settings.combination);
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
