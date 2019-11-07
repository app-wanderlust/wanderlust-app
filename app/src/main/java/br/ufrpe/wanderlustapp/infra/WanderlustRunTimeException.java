package br.ufrpe.wanderlustapp.infra;

public class WanderlustRunTimeException extends RuntimeException{
    public WanderlustRunTimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
