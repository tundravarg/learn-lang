package tuman.learn.java;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
        learnGenerics.superAndExtends();
    }

    public void superAndExtends() {
        Holder<Object> holderOfObject = new Holder<>();
        Holder<A1> holderOfA1 = new Holder<>();
        Holder<A2> holderOfA2 = new Holder<>();
        Holder<A3> holderOfA3 = new Holder<>();
        Holder<A4> holderOfA4 = new Holder<>();
        Holder<A5> holderOfA5 = new Holder<>();
        Holder<? super A3> holderOfA3Super = new Holder<>();
        Holder<? extends A3> holderOfA3Extent = new Holder<>();

        Object o = new Object();
        A1 a1 = new A1();
        A2 a2 = new A2();
        A3 a3 = new A3();
        A4 a4 = new A4();
        A5 a5 = new A5();

        // Check

        a1.a1();
        a2.a2();
        a3.a3();
        a4.a4();
        a5.a5();

        // Field

//        holderOfA3.object = o;  // Illegal
//        holderOfA3.object = a1; // Illegal
//        holderOfA3.object = a2; // Illegal
        holderOfA3.object = a3;
        holderOfA3.object = a4;
        holderOfA3.object = a5;

//        holderOfA3Super.object = o;  // Illegal
//        holderOfA3Super.object = a1; // Illegal
//        holderOfA3Super.object = a2; // Illegal
        holderOfA3Super.object = a3;
        holderOfA3Super.object = a4;
        holderOfA3Super.object = a5;

//        holderOfA3Extent.object = o;  // Illegal
//        holderOfA3Extent.object = a1; // Illegal
//        holderOfA3Extent.object = a2; // Illegal
//        holderOfA3Extent.object = a3; // Illegal
//        holderOfA3Extent.object = a4; // Illegal
//        holderOfA3Extent.object = a5; // Illegal

        // Set exact type

//        holderOfA3.set(o);  // Illegal
//        holderOfA3.set(a1); // Illegal
//        holderOfA3.set(a2); // Illegal
        holderOfA3.set(a3);
        holderOfA3.set(a4);
        holderOfA3.set(a5);

//        holderOfA3Super.set(o);  // Illegal
//        holderOfA3Super.set(a1); // Illegal
//        holderOfA3Super.set(a2); // Illegal
        holderOfA3Super.set(a3);
        holderOfA3Super.set(a4);
        holderOfA3Super.set(a5);

//        holderOfA3Extent.set(o);  // Illegal
//        holderOfA3Extent.set(a1); // Illegal
//        holderOfA3Extent.set(a2); // Illegal
//        holderOfA3Extent.set(a3); // Illegal
//        holderOfA3Extent.set(a4); // Illegal
//        holderOfA3Extent.set(a5); // Illegal

        // Set extent

//        holderOfA3.setExtent(o);  // Illegal
//        holderOfA3.setExtent(a1); // Illegal
//        holderOfA3.setExtent(a2); // Illegal
        holderOfA3.setExtent(a3);
        holderOfA3.setExtent(a4);
        holderOfA3.setExtent(a5);

//        holderOfA3Super.setExtent(o);  // Illegal
//        holderOfA3Super.setExtent(a1); // Illegal
//        holderOfA3Super.setExtent(a2); // Illegal
        holderOfA3Super.setExtent(a3);
        holderOfA3Super.setExtent(a4);
        holderOfA3Super.setExtent(a5);

//        holderOfA3Extent.setExtent(o);  // Illegal
//        holderOfA3Extent.setExtent(a1); // Illegal
//        holderOfA3Extent.setExtent(a2); // Illegal
//        holderOfA3Extent.setExtent(a3); // Illegal
//        holderOfA3Extent.setExtent(a4); // Illegal
//        holderOfA3Extent.setExtent(a5); // Illegal

        // Set exact holder

//        holderOfA3.set(holderOfObject); // Illegal
//        holderOfA3.set(holderOfA1); // Illegal
//        holderOfA3.set(holderOfA2); // Illegal
        holderOfA3.set(holderOfA3);
//        holderOfA3.set(holderOfA4); // Illegal
//        holderOfA3.set(holderOfA5); // Illegal

//        holderOfA3Super.set(holderOfObject); // Illegal
//        holderOfA3Super.set(holderOfA1); // Illegal
//        holderOfA3Super.set(holderOfA2); // Illegal
//        holderOfA3Super.set(holderOfA3); // Illegal
//        holderOfA3Super.set(holderOfA4); // Illegal
//        holderOfA3Super.set(holderOfA5); // Illegal

//        holderOfA3Extent.set(holderOfObject); // Illegal
//        holderOfA3Extent.set(holderOfA1); // Illegal
//        holderOfA3Extent.set(holderOfA2); // Illegal
//        holderOfA3Extent.set(holderOfA3); // Illegal
//        holderOfA3Extent.set(holderOfA4); // Illegal
//        holderOfA3Extent.set(holderOfA5); // Illegal

        // Set super holder

        holderOfA3.setSuper(holderOfObject);
        holderOfA3.setSuper(holderOfA1);
        holderOfA3.setSuper(holderOfA2);
        holderOfA3.setSuper(holderOfA3);
//        holderOfA3.setSuper(holderOfA4); // Illegal
//        holderOfA3.setSuper(holderOfA5); // Illegal

        holderOfA3Super.setSuper(holderOfObject);
//        holderOfA3Super.setSuper(holderOfA1); // Illegal
//        holderOfA3Super.setSuper(holderOfA2); // Illegal
//        holderOfA3Super.setSuper(holderOfA3); // Illegal
//        holderOfA3Super.setSuper(holderOfA4); // Illegal
//        holderOfA3Super.setSuper(holderOfA5); // Illegal

        holderOfA3Extent.setSuper(holderOfObject);
        holderOfA3Extent.setSuper(holderOfA1);
        holderOfA3Extent.setSuper(holderOfA2);
        holderOfA3Extent.setSuper(holderOfA3);
//        holderOfA3Extent.setSuper(holderOfA4); // Illegal
//        holderOfA3Extent.setSuper(holderOfA5); // Illegal

        // Set extent holder

//        holderOfA3.setExtent(holderOfObject); // Illegal
//        holderOfA3.setExtent(holderOfA1); // Illegal
//        holderOfA3.setExtent(holderOfA2); // Illegal
        holderOfA3.setExtent(holderOfA3);
        holderOfA3.setExtent(holderOfA4);
        holderOfA3.setExtent(holderOfA5);

//        holderOfA3Super.setExtent(holderOfObject); // Illegal
//        holderOfA3Super.setExtent(holderOfA1); // Illegal
//        holderOfA3Super.setExtent(holderOfA2); // Illegal
        holderOfA3Super.setExtent(holderOfA3);
        holderOfA3Super.setExtent(holderOfA4);
        holderOfA3Super.setExtent(holderOfA5);

//        holderOfA3Extent.setExtent(holderOfObject); // Illegal
//        holderOfA3Extent.setExtent(holderOfA1); // Illegal
//        holderOfA3Extent.setExtent(holderOfA2); // Illegal
//        holderOfA3Extent.setExtent(holderOfA3); // Illegal
//        holderOfA3Extent.setExtent(holderOfA4); // Illegal
//        holderOfA3Extent.setExtent(holderOfA5); // Illegal



        // Invariance, Covariance and Contravariance

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

    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }

    public <E extends T> void setExtent(E object) {
        this.object = object;
    }

    public void set(Holder<T> holder) {
        this.object = holder.get();
    }

    public void setExtent(Holder<? extends T> holder) {
        this.object = holder.get();
    }

    public void setSuper(Holder<? super T> holder) {
//        this.object = holder.get(); // Illegal
    }

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
