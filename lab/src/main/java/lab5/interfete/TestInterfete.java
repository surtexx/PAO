package main.java.lab5.interfete;

public class TestInterfete {
}

class C1 implements I1{
    @Override
    public void m1() {
    }
}

interface I1 extends I2, I3{

}

interface I2 extends I4{

}

interface I3 extends I4{

}

interface I4{
    public static final Integer i1 = 0;

    public static final String s1 = "abc";

    static final Number n1 = 3.3;

    final StringBuilder sb1 = new StringBuilder();

    Long l1 = 123L;

    void m1();

    default void m2(){
        m4();
        System.out.println("in I4 m2()");
    }

    static void m3(){
        m4();
        System.out.println("static method m3() in I4");
    }
    private static void m4(){
        System.out.println("private method m4() in I4");
        System.out.println("private method m4() in I4");
        System.out.println("private method m4() in I4");
        System.out.println("private method m4() in I4");
    }
}
