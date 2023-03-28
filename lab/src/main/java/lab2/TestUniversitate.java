package main.java.lab2;

public class TestUniversitate {
    public static void main(String[] args) {
        Profesor p1 = new Profesor("Ion", "mate");
        Profesor p2 = new Profesor("Andrei", "info");
        Profesor[] profesori = new Profesor[]{p1, p2};

        Universitate mateinfo = new Universitate("mateinfo", profesori);

        Departament d1 = new Departament("mate");
        Departament d2 = new Departament("info");
        Departament[] departamente = new Departament[]{d1, d2};

        mateinfo.setDepartamente(departamente);
        System.out.println(mateinfo);

        Universitate u2 = new Universitate("u2", profesori, departamente);
        System.out.println(u2);

        departamente = new Departament[]{d1};
        u2.getDepartamente()[0] = null;
        System.out.println(u2);
    }
}
