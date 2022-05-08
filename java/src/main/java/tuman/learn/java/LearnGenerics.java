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

        // Field

//        holderOfAa.object = new A(); // Illegal
        holderOfAa.object = new AA();
        holderOfAa.object = new AAA();

//        holderOfAaSuper.object = new A(); // Illegal
        holderOfAaSuper.object = new AA();
        holderOfAaSuper.object = new AAA();

//        holderOfAaExtent.object = new A();   // Illegal
//        holderOfAaExtent.object = new AA();  // Illegal
//        holderOfAaExtent.object = new AAA(); // Illegal

        // Setter

//        holderOfAa.set(new A()); // Illegal
        holderOfAa.set(new AA());
        holderOfAa.set(new AAA());

//        holderOfAaSuper.set(new A()); // Illegal
        holderOfAaSuper.set(new AA());
        holderOfAaSuper.set(new AAA());

//        holderOfAaExtent.set(new A());   // Illegal
//        holderOfAaExtent.set(new AA());  // Illegal
//        holderOfAaExtent.set(new AAA()); // Illegal
    }

}


class Holder <T> {

    public T object;

    public void set(T object) {
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
