public class Kosta {

    public static void main(String[] args) {
        /*int kor = 87;
        int eng= 85;
        int math=83;

        int sum = getSum(kor, eng, math); // 합
        double avg = getAvg(kor, eng, math); // 평균
        char grade = getGrade(avg); // 학점
        printAll(sum , avg , grade);*/



    }

    private static void printAll(int sum, double avg, char grade) {
        System.out.println(String.format("TotalScore : %d , Average : %f , Grade : %c" , sum , avg , grade));
    }


    private static int getSum(int kor, int eng, int math) {
         return kor + eng + math;
    }

    private static double getAvg(int kor, int eng, int math) {
        double avg =(double)(kor + eng + math)/3; //평균 (연산자중 가장 큰 타입  따름)
        return avg;
    }
    public static char getGrade(double avg){
        switch ((int)avg/10){
            case 10:
                return 'A';
            case 9:
                return 'A';
            case 8:
                return 'B';
            case 7:
                return 'C';
            case 6:
                return 'D';
            default:
                return 'F';
        }
    }



}
