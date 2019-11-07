package br.ufrpe.wanderlustapp.infra;

import java.util.HashMap;
import java.util.Map;

import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class Sessao {
    public static final Sessao instance = new Sessao();
    private final Map<String,Object> values = new HashMap<>();

    public void setUsuario(Usuario usuario) {
        setValue("sessao.Usuario", usuario);
    }

    public Usuario getUsuario() {

        return (Usuario)values.get("sessao.Usuario");
    }

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}
