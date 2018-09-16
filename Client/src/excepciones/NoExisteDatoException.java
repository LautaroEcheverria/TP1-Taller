package excepciones;

public class NoExisteDatoException extends Exception
{
    public NoExisteDatoException()
    {
        super("No existe el dato a eliminar.");
    }
}
