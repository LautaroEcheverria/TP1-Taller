package excepciones;

public class YaExisteDatoException extends Exception
{
    public YaExisteDatoException()
    {
        super("Ya esta ingresado el dato a insertar.");
    }
}
