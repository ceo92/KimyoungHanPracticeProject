package hello.springtx;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Serim2 {
    //삭제 , 추가가 많이 일어날 때는 배열 선택하지 말자 ㅅㅂ
    private static int SECOND = 0;

    public static void main(String[] args) throws IOException {
        int sum=0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        //지혜 메모리 크기
        int N = Integer.parseInt(st.nextToken());
        //지혜 메모리
        LinkedList<String> jihyeList = new LinkedList<>();

        //단어 수
        int W = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        //단어 리스트
        LinkedList<String> wordList = new LinkedList<>();
        while (st.hasMoreTokens()) {
            wordList.add(st.nextToken());
        }
        System.out.println("jihyeList1 = " + jihyeList);



        for (int i=0 ; i<W ; i++){
            if (jihyeList.size()<N) {
                jihyeList.add(wordList.get(i));
                SECOND+=3;
                continue;
            }
            System.out.println("jihyeList2 = " + jihyeList);


            String currentWord = wordList.get(i);
            //같은 거 위치 삭제 후 맨 앞에 삽입해!
            //String findWord = jihyeList.stream().filter(word -> word.equals(currentWord)).findFirst().orElseThrow();
            if (jihyeList.removeIf(word -> word.equals(currentWord))) {
                jihyeList.addLast(currentWord);
                SECOND++;
                continue;
            }
            //평균
            secondFilter(jihyeList, currentWord);
            System.out.println("jihyeList3 = " + jihyeList);

        }
        sb.append(SECOND);
        bw.write(sb.toString());
        bw.flush();
        bw.close();

    }

    private static void secondFilter(LinkedList<String> jihyeList, String currentWord) {
        int avg = jihyeList.stream().mapToInt(String::length).sum() / jihyeList.size();
        //int avg = jihyeList.stream().map(String::length).reduce(0, Integer::sum) / jihyeList.size();
        //평균길이보다 삭제하려는 단어 길이가 같거나 크면 해당 단어로 변경 , 아마 여기서 예외 예상

        String findWord = jihyeList.stream().filter(word -> word.length() <= avg).findFirst().orElse(null); // 하나만 반환
        int findIndex;
        if (findWord==null){
            findIndex = jihyeList.size()-1;
        }
        else {
            findIndex = jihyeList.indexOf(findWord);
        }
        jihyeList.set(findIndex, currentWord);
        SECOND+=3;
    }

}
