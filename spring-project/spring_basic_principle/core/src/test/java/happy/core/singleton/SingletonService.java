package happy.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService(); //자기 자신을 내부의 private으로 하나 갖고있음

    public static SingletonService getInstance(){
        return instance;
    }
    private SingletonService(){}








    public  void logic(){
        System.out.println("This is Singleton Constructor");

    }



}
