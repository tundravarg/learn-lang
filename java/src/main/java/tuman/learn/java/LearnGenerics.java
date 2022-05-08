package tuman.learn.java;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
        learnGenerics.superAndExtends();
    }

    public void superAndExtends() {
        A1 a1 = new A1();
        A2 a2 = new A2();
        A3 a3 = new A3();
        A4 a4 = new A4();
        A5 a5 = new A5();

        Holder<A2> holderA2 = new Holder<>();
        Holder<A3> holderA3 = new Holder<>();
        Holder<A4> holderA4 = new Holder<>();

        Holder<? extends A2> extentHolder2 = new Holder<>();
        Holder<? extends A3> extentHolder3 = new Holder<>();
        Holder<? extends A4> extentHolder4 = new Holder<>();

        Holder<? super A2> superHolder2 = new Holder<>();
        Holder<? super A3> superHolder3 = new Holder<>();
        Holder<? super A4> superHolder4 = new Holder<>();


        // Set Concrete Holder

//        holderA3 = holderA2; // Illegal
        holderA3 = holderA3;
//        holderA3 = holderA4; // Illegal

//        holderA3 = extentHolder2; // Illegal
//        holderA3 = extentHolder3; // Illegal
//        holderA3 = extentHolder4; // Illegal

//        holderA3 = superHolder2; // Illegal
//        holderA3 = superHolder3; // Illegal
//        holderA3 = superHolder4; // Illegal


        // Set Extent Holder

//        extentHolder3 = holderA2; // Illegal
        extentHolder3 = holderA3;
        extentHolder3 = holderA4;

//        extentHolder3 = extentHolder2; // Illegal
        extentHolder3 = extentHolder3;
        extentHolder3 = extentHolder4;

//        extentHolder3 = superHolder2; // Illegal
//        extentHolder3 = superHolder3; // Illegal
//        extentHolder3 = superHolder4; // Illegal


        // Set Super Holder

        superHolder3 = holderA2;
        superHolder3 = holderA3;
//        superHolder3 = holderA4; // Illegal

//        superHolder3 = extentHolder2; // Illegal
//        superHolder3 = extentHolder3; // Illegal
//        superHolder3 = extentHolder4; // Illegal

        superHolder3 = superHolder2;
        superHolder3 = superHolder3;
//        superHolder3 = superHolder4; // Illegal


        // Set/Get value of Concrete Holder

        // `holderA3` can reference only to H<A3>, which can contain A3 and subclasses.
        // We can assign them here.
//        holderA3.object = o;  // Illegal
//        holderA3.object = a1; // Illegal
//        holderA3.object = a2; // Illegal
        holderA3.object = a3;
        holderA3.object = a4;
        holderA3.object = a5;

        // `holderA3` can reference only to H<A3>, which can contain A3 and subclasses, but not A2 and superclasses.
        // We can can access to members of A3 but not A4 ans subclasses, because we don't know, what is here.
        // We only can be sure, that here are at least A3.
        holderA3.object.hashCode();
        holderA3.object.a1();
        holderA3.object.a2();
        holderA3.object.a3();
//        holderA3.object.a4(); // Illegal
//        holderA3.object.a5(); // Illegal


        // Set/Get value of Extent Holder

        // `extentHolder3` can reference to H<A3+>, which can contain A3 and subclasses and can't contain A2 and superclasses.
        // So we can't assign A2 and superclasses,
        // In other side, `extentHolder3` can contain H<A4>, H<A5>, etc, which can't contain H3, H4, etc.
        // So we can't assign anything here.
//        extentHolder3.object = o;  // Illegal
//        extentHolder3.object = a1; // Illegal
//        extentHolder3.object = a2; // Illegal
//        extentHolder3.object = a3; // Illegal
//        extentHolder3.object = a4; // Illegal
//        extentHolder3.object = a5; // Illegal

        // `extentHolder3` can reference to H<A3+>, which can contain A3 and subclasses.
        // It can reference To H<A4> or H<A5>, but we don't know this, we only know that here can be at least A3.
        // So we can call all methods from A3 ans superclasses.
        extentHolder3.object.hashCode();
        extentHolder3.object.a1();
        extentHolder3.object.a2();
        extentHolder3.object.a3();
//        extentHolder3.object.a4(); // Illegal
//        extentHolder3.object.a5(); // Illegal


        // Set/Get value of Super Holder

        // `superHolder3` can reference to H<A3->, which can contain only A3 and subclasses and can't contain A2 and superclasses.
        // So, we can set only A3 ans subclasses instances here.
//        superHolder3.object = o;  // Illegal
//        superHolder3.object = a1; // Illegal
//        superHolder3.object = a2; // Illegal
        superHolder3.object = a3;
        superHolder3.object = a4;
        superHolder3.object = a5;

        // `superHolder3` can reference to H<A3->, which can contain A3+.
        // But we don't know, which A3 subclass is hold, so we can't call methods of A3 subclasses.
        // In other side, `superHolder3` can reference to H<A2>, so we can't call A3 methods.
        // As a result we can call only Object methods.
        superHolder3.object.hashCode();
//        superHolder3.object.a1(); // Illegal
//        superHolder3.object.a2(); // Illegal
//        superHolder3.object.a3(); // Illegal
//        superHolder3.object.a4(); // Illegal
//        superHolder3.object.a5(); // Illegal

    }

}


class Holder <T> {
    public T object;
}


class A1 {
    public void a1() {
        System.out.println("A1");
    }
}

class A2 extends A1 {
    public void a2() {
        System.out.println("A2");
    }
}

class A3 extends A2 {
    public void a3() {
        System.out.println("A3");
    }
}

class A4 extends A3 {
    public void a4() {
        System.out.println("A4");
    }
}

class A5 extends A4 {
    public void a5() {
        System.out.println("A5");
    }
}
