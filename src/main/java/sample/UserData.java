package sample;

public class UserData {
    private String id;
    private String name;
    private int sem;
    private int year;

    public UserData(String id, String name, int sem) {
        this.id = id;
        this.name = name;
        this.sem = sem;
        this.year=61-Integer.parseInt(id.substring(0,1)+1);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSem() {
        return sem;
    }

    public int getYear() {
        return year;
    }
}
