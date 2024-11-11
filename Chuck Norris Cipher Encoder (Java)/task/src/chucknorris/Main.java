import java.util.Arrays;
import java.util.Scanner;
//package chucknorris;

public class Main {
    public static void main(String[] args) {
        prompt();
    }
    public static void prompt(){
        while(true) {
            System.out.println("Please input operation (encode/decode/exit):");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("encode")) {
                System.out.println("Input string:");
                input = scanner.nextLine();
                String result = binaryConvert(input);
                System.out.println("Encoded string:");
                String chuckResult = chuckEncoder(result);
                System.out.println(chuckResult);
                System.out.println();
                continue;
            } else if (input.equals("decode")) {
                System.out.println("Input encoded string:");
                input = scanner.nextLine();
                if(validateDecode(input)) {
                    String decoded = chuckDecodoer(input);
                    System.out.println("Decoded string:");
                    System.out.println(asciiConvert(decoded));
                    System.out.println();
                }else{
                    System.out.println("not valid");
                    System.out.println();
                }
                continue;
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                System.out.println("There is no '"+input+"' operation");
                System.out.println();
            }
        }
    }

    public static boolean validateDecode(String input){
        boolean validDigit = containsZeroOne(input);
        boolean validHeader =  containsHeader(input);
        boolean validBlocksNum = input.split(" ").length%2 ==0;
        boolean validDecodeNum = chuckDecodoer(input).length()%7 == 0;
        //System.out.println(validDecodeNum);
        if (!validDigit){
            return false;
        }else if (!validHeader){
            return false;
        }else if(!validBlocksNum){
            return false;
        }else if(!validDecodeNum) {
            return false;
        }else {
            return true;
        }
    }
    public static boolean containsHeader(String input){
        String[] arr = input.split(" ");
        for(int i = 0; i<arr.length-1;i+=2) {
            if(arr[i].length()>2 || arr[i].length()<=0){
                return false;
            }

        }
        return true;
    }
    public static boolean containsZeroOne(String input){
        String[] newArray = input.split(" ");
        int sum = 0;
        try {
            for (int i = 0; i < newArray.length; i++) {
                sum += Integer.parseInt(newArray[i]);
            }
        }catch (Exception e) {
            return false;
        }finally {
            if (sum > 0) {
                return false;
            } else {
                return true;
            }
        }
    }
    public static String binaryConvert(String input){
        String[] binaryX;
        char[] inputSplit = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        StringBuilder asciiX = builder.append("");

        for (int i=0; i<inputSplit.length;i++) {
            int charX = inputSplit[i];
            String strX = Integer.toBinaryString(charX);
            asciiX.append("0000000".substring(0, (7 - strX.length())) + strX);
        }

        return asciiX.toString();
    }
    public static String chuckEncoder(String input){
        String[] inputSplit = input.split("");
        StringBuilder builder = new StringBuilder();
        StringBuilder chuckX = builder.append("");
        boolean isOne = inputSplit[0].equals("1");
        chuckX.append((isOne?"0":"00"));
        chuckX.append(" ");
        chuckX.append("0");
        for(int i = 1; i<inputSplit.length;i++){
            if(inputSplit[i].equals(inputSplit[i-1])){
                chuckX.append("0");
            }
            else{
                chuckX.append(" ");
                isOne = !isOne;
                chuckX.append(isOne?"0":"00");
                chuckX.append(" ");
                chuckX.append("0");
            }
        }
        return chuckX.toString();
    }
    public static String chuckDecodoer(String input) {
        String[] asciiDecoder = input.split(" ");
        //System.out.println(Arrays.toString(asciiDecoder));
        StringBuilder builder = new StringBuilder();
        StringBuilder decoded = builder;
        for(int i =0; i<=asciiDecoder.length-2;i+=2){
            String test = (asciiDecoder[i].equals("0")?"1":"0");
            int repeat = asciiDecoder[i+1].length();
            decoded.append(test.repeat(repeat));
        }
        return decoded.toString();
    }
    public static String asciiConvert(String input){
        String convertedASCII = "";
        for (int i=0;i<=input.length()-7;i+=7) {
            char ascii = (char) Integer.parseInt(input.substring(i,i+7), 2);
            convertedASCII += ascii;
        }
        return convertedASCII;
    }
}
