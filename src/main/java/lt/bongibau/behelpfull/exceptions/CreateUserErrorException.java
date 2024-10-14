package lt.bongibau.behelpfull.exceptions;

public class CreateUserErrorException extends Throwable {
    public CreateUserErrorException(String createAskerError) {
        super(createAskerError);
    }
}
