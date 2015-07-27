package exceptions;

/**
 * Created by jls on 7/22/2015.
 */
public class RepositoryNotFoundException extends Throwable {
    public RepositoryNotFoundException(String s) {
        super(s);
    }
}
