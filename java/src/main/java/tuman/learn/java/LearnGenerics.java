package tuman.learn.java;


public class LearnGenerics {

    public static void main(String[] args) {
        System.out.println("==== LearnGenerics ====");
        LearnGenerics learnGenerics = new LearnGenerics();
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
