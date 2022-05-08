package tuman.learn.java;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
        learnGenerics.superAndExtends();
    }

    public void superAndExtends() {
        Holder<AA, ? super AA, ? extends AA> holder = new Holder<>();

//        holder.concreteHolder = new A(); // OK, Illegal
        holder.concreteHolder = new AA();  // OK
        holder.concreteHolder = new AAA(); // OK

//        holder.superHolder = new A(); // TODO: Why illegal
        holder.superHolder = new AA();  // OK
        holder.superHolder = new AAA(); // TODO: Why illegal

//        holder.extendsHolder = new A();   // TODO: Why illegal
//        holder.extendsHolder = new AA();  // TODO: Why illegal
//        holder.extendsHolder = new AAA(); // TODO: Why illegal
    }

}


class Holder <C, S, E> {

    C concreteHolder;
    S superHolder;
    E extendsHolder;

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

class AAB extends AA {
    public void aab() {
        System.out.println("A-A-B");
    }
}

class AB extends A {
    public void aa() {
        System.out.println("A-B");
    }
}

class B {
    public void aa() {
        System.out.println("B");
    }
}
