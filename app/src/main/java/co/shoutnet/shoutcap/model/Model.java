package co.shoutnet.shoutcap.model;

/**
 * Created by mikqi on 11/24/15.
 */
public class Model {
    private int id;
    private String name;

    public Model() {
    }

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
