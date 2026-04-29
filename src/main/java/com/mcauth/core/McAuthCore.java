package com.mcauth.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"com.mcauth.core"})
public class McAuthCore implements IFMLLoadingPlugin {
    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.mcauth.core.transformer.AuthTransformer" };
    }

    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override
    public void injectData(Map<String, Object> map) {
    }

}
