package models.course;

public class Module {
    private int id;
    private String name;
    private String type;

    public Module(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static Module fromSql(int id, String name, String type) {
        return new Module(id, name, type);
    }

    @Override
    public String toString() {
        return name;
    }

}
