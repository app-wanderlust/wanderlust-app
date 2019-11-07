package br.ufrpe.wanderlustapp.usuario.negocio;

import android.content.Context;

import br.ufrpe.wanderlustapp.infra.Sessao;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;
import br.ufrpe.wanderlustapp.usuario.persistencia.UsuarioDAO;

public class UsuarioServices {
    private UsuarioDAO usuarioDAO;

    public UsuarioServices(Context context){
        usuarioDAO = new UsuarioDAO(context);
    }

    public void login(String email, String senha) throws Exception{
        Usuario usuario = usuarioDAO.getUsuario(email, senha);
        if (usuario == null){
            throw new Exception();
        }else {
            Sessao.instance.setUsuario(usuario);
        }
    }

    public void cadastrar(Usuario usuario) throws Exception{
        if (usuarioDAO.getUsuario(usuario.getEmail()) != null){
            throw new Exception();
        }
        long idUsuario = usuarioDAO.cadastrar(usuario);
        usuario.setId(idUsuario);
    }
}
