package sample;

public class Subject {
    private String COURSEID;
    private String NAME;
    private String PREREQ;
    private String PASS;
    private int YEAR;
    private int SEM;
    private String COLOR;

    public Subject(String COURSEID, String NAME, String PREREQ, String PASS, int YEAR, int SEM, String COLOR) {
        this.COURSEID = COURSEID;
        this.NAME = NAME;
        this.PREREQ = PREREQ;
        this.PASS = PASS;
        this.YEAR = YEAR;
        this.SEM = SEM;
        this.COLOR = COLOR;
    }

    public String getCOURSEID() {
        return COURSEID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPREREQ() {
        return PREREQ;
    }

    public String getPASS() {
        return PASS;
    }

    public int getYEAR() {
        return YEAR;
    }

    public int getSEM() {
        return SEM;
    }

    public String getCOLOR() {
        return COLOR;
    }
}
