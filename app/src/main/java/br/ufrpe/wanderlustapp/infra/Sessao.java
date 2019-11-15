package br.ufrpe.wanderlustapp.infra;

import java.util.HashMap;
import java.util.Map;

import br.ufrpe.wanderlustapp.pratoImagem.dominio.PratoImagem;
import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
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

    public void setPratoTipico(PratoTipico prato){setValue("sessao.PratoTipico", prato);}

    public PratoTipico getPratoTipico(){return (PratoTipico)values.get("sessao.PratoTipico");}

    public void setPratoImagem(PratoImagem pratoImagem){setValue("sessao.PratoImagem", pratoImagem);}

    public PratoImagem getPratoImagem(){return (PratoImagem)values.get("sessao.PratoImgaem");}

    public void resetPrato(){setPratoTipico(null);}

    public void resetImagem(){setPratoImagem(null);}

    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }


    public void reset() {
        this.values.clear();
    }
}
