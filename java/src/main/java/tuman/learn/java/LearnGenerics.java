package tuman.learn.java;


import tuman.learn.java.utils.ObjectHolder;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
        learnGenerics.superAndExtends();
    }

    public void superAndExtends() {
        ObjectHolder<AA> holderOfAa = new ObjectHolder<>();
        ObjectHolder<? super AA> holderOfAaSuper = new ObjectHolder<>();
        ObjectHolder<? extends AA> holderOfAaExtent = new ObjectHolder<>();

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
