package tuman.learn.java;


import tuman.learn.java.utils.ObjectHolder;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
        learnGenerics.superAndExtends();
    }

    public void superAndExtends() {
        Holder<Object> holderOfObject = new Holder<>();
        Holder<A> holderOfA = new Holder<>();
        Holder<AA> holderOfAa = new Holder<>();
        Holder<AAA> holderOfAaa = new Holder<>();
        Holder<? super AA> holderOfAaSuper = new Holder<>();
        Holder<? extends AA> holderOfAaExtent = new Holder<>();

        Object o   = new Object();
        A   a   = new A();
        AA  aa  = new AA();
        AAA aaa = new AAA();

        // Field

//        holderOfAa.object = o; // Illegal
//        holderOfAa.object = a; // Illegal
        holderOfAa.object = aa;
        holderOfAa.object = aaa;

//        holderOfAaSuper.object = o; // Illegal
//        holderOfAaSuper.object = a; // Illegal
        holderOfAaSuper.object = aa;
        holderOfAaSuper.object = aaa;

//        holderOfAaExtent.object = o;   // Illegal
//        holderOfAaExtent.object = a;   // Illegal
//        holderOfAaExtent.object = aa;  // Illegal
//        holderOfAaExtent.object = aaa; // Illegal

        // Set exact type

//        holderOfAa.set(o); // Illegal
//        holderOfAa.set(a); // Illegal
        holderOfAa.set(aa);
        holderOfAa.set(aaa);

//        holderOfAaSuper.set(o); // Illegal
//        holderOfAaSuper.set(a); // Illegal
        holderOfAaSuper.set(aa);
        holderOfAaSuper.set(aaa);

//        holderOfAaExtent.set(o);   // Illegal
//        holderOfAaExtent.set(a);   // Illegal
//        holderOfAaExtent.set(aa);  // Illegal
//        holderOfAaExtent.set(aaa); // Illegal

        // Set extent

//        holderOfAa.setExtent(o); // Illegal
//        holderOfAa.setExtent(a); // Illegal
        holderOfAa.setExtent(aa);
        holderOfAa.setExtent(aaa);

//        holderOfAaSuper.setExtent(o); // Illegal
//        holderOfAaSuper.setExtent(a); // Illegal
        holderOfAaSuper.setExtent(aa);
        holderOfAaSuper.setExtent(aaa);

//        holderOfAaExtent.setExtent(o); // Illegal
//        holderOfAaExtent.setExtent(a); // Illegal
//        holderOfAaExtent.setExtent(aa); // Illegal
//        holderOfAaExtent.setExtent(aaa); // Illegal

        // Set exact holder

//        holderOfAa.set(holderOfObject);
//        holderOfAa.set(holderOfA);
        holderOfAa.set(holderOfAa);
//        holderOfAa.set(holderOfAaa);

//        holderOfAaSuper.set(holderOfObject);
//        holderOfAaSuper.set(holderOfA);
//        holderOfAaSuper.set(holderOfAa);
//        holderOfAaSuper.set(holderOfAaa);

//        holderOfAaExtent.set(holderOfObject);
//        holderOfAaExtent.set(holderOfA);
//        holderOfAaExtent.set(holderOfAa);
//        holderOfAaExtent.set(holderOfAaa);

        // Set super holder

        holderOfAa.setSuper(holderOfObject);
        holderOfAa.setSuper(holderOfA);
        holderOfAa.setSuper(holderOfAa);
        holderOfAa.setSuper(holderOfAaa);

        holderOfAaSuper.setSuper(holderOfObject);
        holderOfAaSuper.setSuper(holderOfA);
        holderOfAaSuper.setSuper(holderOfAa);
        holderOfAaSuper.setSuper(holderOfAaa);

        holderOfAaExtent.setSuper(holderOfObject);
        holderOfAaExtent.setSuper(holderOfA);
        holderOfAaExtent.setSuper(holderOfAa);
        holderOfAaExtent.setSuper(holderOfAaa);

        // Set extent holder

        holderOfAa.setExtent(holderOfObject);
        holderOfAa.setExtent(holderOfA);
        holderOfAa.setExtent(holderOfAa);
        holderOfAa.setExtent(holderOfAaa);

        holderOfAaSuper.setExtent(holderOfObject);
        holderOfAaSuper.setExtent(holderOfA);
        holderOfAaSuper.setExtent(holderOfAa);
        holderOfAaSuper.setExtent(holderOfAaa);

        holderOfAaExtent.setExtent(holderOfObject);
        holderOfAaExtent.setExtent(holderOfA);
        holderOfAaExtent.setExtent(holderOfAa);
        holderOfAaExtent.setExtent(holderOfAaa);
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


class A {
    public void a() {
        System.out.println("A");
    }
}

class AA extends A {
    public void aa() {
        System.out.println("A-A");
    }
}

class AAA extends AA {
    public void aaa() {
        System.out.println("A-A-A");
    }
}
