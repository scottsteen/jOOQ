package org.jooq.impl;

import org.jooq.DiagnosticsListener;
import org.jooq.DiagnosticsListenerProvider;

import java.util.ServiceLoader;

public class ServiceLoaderDiagnosticsListenerProvider implements DiagnosticsListenerProvider {

    @Override
    public DiagnosticsListener provide() {
        var loader = ServiceLoader.load(DiagnosticsListener.class);
        var providers = loader.stream()
                .map(ServiceLoader.Provider::get)
                .map(DefaultDiagnosticsListenerProvider::new)
                .toArray(DiagnosticsListenerProvider[]::new);
        return new DiagnosticsListeners(providers);
    }
}
