package de.horroreyes.relaximation;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Supports storing the application settings in a persistent way.
 * The {@link State} and {@link Storage} annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
        name = "de.horroreyes.relaximation.RelaximationSettingsState",
        storages = {@Storage("SdkSettingsPlugin.xml")}
)
public class RelaximationSettingsState implements PersistentStateComponent<RelaximationSettingsState> {

    public String searchString = "animal relax";
    public boolean ideaStatus = false;

    public static RelaximationSettingsState getInstance() {
        return ServiceManager.getService(RelaximationSettingsState.class);
    }

    @Nullable
    @Override
    public RelaximationSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull RelaximationSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
