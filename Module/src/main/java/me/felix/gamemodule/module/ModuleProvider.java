package me.felix.gamemodule.module;

import lombok.Getter;
import lombok.Setter;

public class ModuleProvider {

    @Getter
    @Setter
    private Module module;

    public ModuleProvider() {
        module = null;
    }

}
