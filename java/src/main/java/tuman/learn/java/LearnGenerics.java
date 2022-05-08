package tuman.learn.java;


import tuman.learn.java.utils.ObjectHolder;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
        learnGenerics.superAndExtends();
    }

    public void superAndExtends() {
        Holder<AA> holderOfAa = new Holder<>();
        Holder<? super AA> holderOfAaSuper = new Holder<>();
        Holder<? extends AA> holderOfAaExtent = new Holder<>();

        A   a   = new A();
        AA  aa  = new AA();
        AAA aaa = new AAA();

        // Field

//        holderOfAa.object = a; // Illegal
        holderOfAa.object = aa;
        holderOfAa.object = aaa;

//        holderOfAaSuper.object = a; // Illegal
        holderOfAaSuper.object = aa;
        holderOfAaSuper.object = aaa;

//        holderOfAaExtent.object = a;   // Illegal
//        holderOfAaExtent.object = aa;  // Illegal
//        holderOfAaExtent.object = aaa; // Illegal

        // Set exact type

//        holderOfAa.set(a); // Illegal
        holderOfAa.set(aa);
        holderOfAa.set(aaa);

//        holderOfAaSuper.set(a); // Illegal
        holderOfAaSuper.set(aa);
        holderOfAaSuper.set(aaa);

//        holderOfAaExtent.set(a);   // Illegal
//        holderOfAaExtent.set(aa);  // Illegal
//        holderOfAaExtent.set(aaa); // Illegal

        // Set extent

//        holderOfAa.setExtent(a); // Illegal
        holderOfAa.setExtent(aa);
        holderOfAa.setExtent(aaa);

//        holderOfAaSuper.setExtent(a); // Illegal
        holderOfAaSuper.setExtent(aa);
        holderOfAaSuper.setExtent(aaa);

//        holderOfAaExtent.setExtent(a); // Illegal
//        holderOfAaExtent.setExtent(aa); // Illegal
//        holderOfAaExtent.setExtent(aaa); // Illegal
    }

}


class Holder <T> {

    public T object;

    public void set(T object) {
        this.object = object;
    }

    public <E extends T> void setExtent(E object) {
        this.object = object;
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
