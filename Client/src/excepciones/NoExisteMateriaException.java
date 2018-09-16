package excepciones;

public class NoExisteMateriaException extends Exception
{
    public NoExisteMateriaException()
    {
        super("No existe la materia solicitada.");
    }
}
