package Model.db;

public interface Writable {

    /**
     *
     * @return the content of the class implementing this that shall be written to a text.
     */
    String toFile();
}
