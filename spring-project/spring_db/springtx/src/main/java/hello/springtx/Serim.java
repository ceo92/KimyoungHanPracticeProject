/*
package hello.springtx;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Serim {

    private static final int SECOND = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int jihyeMemoryLength = Integer.parseInt(st.nextToken());
        String[] jihyeList = new String[jihyeMemoryLength];
        int avg , sum=0;

        int W = Integer.parseInt(st.nextToken());
        String[] wordList = new String[W]; //부른 단어들 저장됨
        StringTokenizer words = new StringTokenizer(br.readLine());

        for (int i=0 ; i<wordList.length ; i++){
            wordList[i] = words.nextToken();
        }




        while(true) {
            for (int i = 0; i < jihyeMemoryLength; i++) {
                //1~3
                jihyeList[i] = wordList[i];
            }
            sum=0;
            for (int i=0 ; i< jihyeMemoryLength ; i++){
                sum += jihyeList[i].length();
            }
            avg = sum/ jihyeMemoryLength;

            for (int i = jihyeMemoryLength; i < wordList.length; i++) {
                //4~10
                for (int j=0 ; j<jihyeMemoryLength ; j++) {
                    if (avg <= jihyeList[j].length()) {
                        for (int k=0 ; k<jihyeMemoryLength ; k++) {
                            if (jihyeList[k] == wordList[i]) {
                                j
                            }
                        }
                        jihyeList[j] = wordList[i];

                    }
                }
            }
        }









    }

}
*/
