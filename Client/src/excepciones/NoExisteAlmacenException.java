package excepciones;

public class NoExisteAlmacenException extends Exception
{
    public NoExisteAlmacenException()
    {
        super("No existe el almacen solicitado.");
    }
}
