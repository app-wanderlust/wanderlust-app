package br.ufrpe.wanderlustapp.infra.persistencia;

import java.io.Closeable;
import java.io.IOException;
import br.ufrpe.wanderlustapp.infra.WanderlustRunTimeException;

public class AbstractDAO {
    protected void close(Closeable... args) throws WanderlustRunTimeException {
        for (Closeable closable : args) {
            try {
                closable.close();
            } catch (IOException e) {
                throw new WanderlustRunTimeException("Erro ao fechar as conexões",e);
            }
        }
    }
}
