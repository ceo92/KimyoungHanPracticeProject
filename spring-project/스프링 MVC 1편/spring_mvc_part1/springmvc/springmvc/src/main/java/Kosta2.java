public class Kosta2 {

    public static void main(String[] args) {
        int sum=0;
        /*for (int i=1;i<=100;i++){
            if ((i%3)!=0)sum+=i;
        }*/


       /* for(int i=1 ; i<=100 ;i++){
            sum+=i;
            if (sum>=100){
                System.out.println(i);
                return;
            }
        }*/

        for (int i=2; i<10;i++){
            for (int j=1;j<10;j++){
                System.out.println(String.format("%d * %d = %d",i,j,i*j));
            }
            System.out.println();
            System.out.println("-------------------");
            System.out.println();
        }



    }

}
