package br.com.dio.pagamentos.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** SINGLETON (via enum): uma unica instancia de log de auditoria, segura contra concorrencia. */
public enum AuditLog {
    INSTANCE;

    private final List<String> entries = Collections.synchronizedList(new ArrayList<>());

    public void record(String entry) {
        entries.add(entry);
    }

    public List<String> entries() {
        synchronized (entries) {
            return List.copyOf(entries);
        }
    }

    public void clear() {
        entries.clear();
    }
}
