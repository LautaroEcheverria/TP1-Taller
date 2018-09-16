package excepciones;

public class NoExisteOperandoException extends Exception
{
    public NoExisteOperandoException(String string, Throwable throwable, boolean b, boolean b1)
    {
        super(string, throwable, b, b1);
    }

    public NoExisteOperandoException(Throwable throwable)
    {
        super(throwable);
    }

    public NoExisteOperandoException(String string, Throwable throwable)
    {
        super(string, throwable);
    }

    public NoExisteOperandoException(String string)
    {
        super(string);
    }

    public NoExisteOperandoException()
    {
        super();
    }
}
