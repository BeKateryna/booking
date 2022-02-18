package app.flight;

public class NoFlightsExistExeption extends Throwable {
    private final int id;

    public NoFlightsExistExeption(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "There are no flights with this id = : ";
    }
}
