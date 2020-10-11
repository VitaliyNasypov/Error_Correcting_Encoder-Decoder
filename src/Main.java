import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write a mode: ");
        switch (scanner.nextLine().toLowerCase()) {
            case "encode" -> {
                System.out.println("Please specify the absolute path to the file: ");
                String fileName = scanner.nextLine();
                new EncodingAndDecoding().messageEncoding(fileName);
                System.out.println("The result is saved in encoded.txt");
            }
            case "send" -> {
                new Interference().imitationInterference();
                System.out.println("The result is saved in received.txt");
            }
            case "decode" -> {
                new EncodingAndDecoding().messageDecoding();
                System.out.println("The result is saved in decoded.txt");
            }
            default -> System.out.println("Incorrect mode. App will be closed.");
        }
    }
}

class InputOutputData {
    String inputData(String fileNameInput) {
        try (FileInputStream fileInputStream = new FileInputStream(fileNameInput)) {
            StringBuilder readFromFile = new StringBuilder();
            while (fileInputStream.available() > 0) {
                int c = fileInputStream.read();
                readFromFile.append((char) c);
            }
            return String.valueOf(readFromFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    void outputData(String fileNameOutput, String text) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileNameOutput)) {
            for (int i = 0; i < text.length(); i++) {
                int d = text.charAt(i);
                fileOutputStream.write(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Interference {
    void imitationInterference() {
        String textEncoded = new InputOutputData().inputData("encoded.txt");
        StringBuilder textToBinary = ConvertText.convertCharToBinary(textEncoded);
        StringBuilder textInterference = new StringBuilder();
        char[] afterInterference;
        while (textToBinary.length() > 0) {
            afterInterference = textToBinary.substring(0, 8).toCharArray();
            int changeChars = indexChange(afterInterference.length - 1);
            if (afterInterference[changeChars] == '1') {
                afterInterference[changeChars] = '0';
            } else {
                afterInterference[changeChars] = '1';
            }
            textInterference.append(afterInterference);
            textToBinary.delete(0, 8);
        }
        String outputInterferenceText = ConvertText.convertBinaryToChar(textInterference);
        new InputOutputData().outputData("received.txt", outputInterferenceText);
    }

    private int indexChange(int upper) {
        Random random = new Random();
        return random.nextInt(upper - 1 + 1) + 1;
    }
}

class EncodingAndDecoding {
    void messageEncoding(String fileNameInput) {
        String textSend = new InputOutputData().inputData(fileNameInput);
        StringBuilder textToBinary = ConvertText.convertCharToBinary(textSend);
        StringBuilder textEncoding = new StringBuilder();
        char[] characterBinary;
        while (textToBinary.length() > 0) {
            characterBinary = new char[8];
            for (int i = 0, y = 0; i < characterBinary.length; i++) {
                if (i == 0) {
                    int controlBit = textToBinary.charAt(0) + textToBinary.charAt(1) + textToBinary.charAt(3);
                    characterBinary[i] = controlBit % 2 == 1 ? '1' : '0';
                } else if (i == 1) {
                    int controlBit = textToBinary.charAt(0) + textToBinary.charAt(2) + textToBinary.charAt(3);
                    characterBinary[i] = controlBit % 2 == 1 ? '1' : '0';
                } else if (i == 3) {
                    int controlBit = textToBinary.charAt(1) + textToBinary.charAt(2) + textToBinary.charAt(3);
                    characterBinary[i] = controlBit % 2 == 1 ? '1' : '0';
                } else if (i == 7) {
                    characterBinary[i] = '0';
                } else {
                    characterBinary[i] = textToBinary.charAt(y);
                    y++;
                }
            }
            textEncoding.append(characterBinary);
            textToBinary.delete(0, 4);
        }
        String outputEncodeText = ConvertText.convertBinaryToChar(textEncoding);
        new InputOutputData().outputData("encoded.txt", outputEncodeText);
    }

    void messageDecoding() {
        String textReceived = new InputOutputData().inputData("received.txt");
        StringBuilder textToBinary = ConvertText.convertCharToBinary(textReceived);
        StringBuilder textDecoding = new StringBuilder();
        while (textToBinary.length() > 0) {
            int searchIndexChange = 0;
            if (0 != (textToBinary.charAt(0) + textToBinary.charAt(2) + textToBinary.charAt(4) + textToBinary.charAt(6)) % 2) {
                searchIndexChange += 1;
            }
            if (0 != (textToBinary.charAt(1) + textToBinary.charAt(2) + textToBinary.charAt(5) + textToBinary.charAt(6)) % 2) {
                searchIndexChange += 2;
            }
            if (0 != (textToBinary.charAt(3) + textToBinary.charAt(4) + textToBinary.charAt(5) + textToBinary.charAt(6)) % 2) {
                searchIndexChange += 4;
            }
            if (searchIndexChange > 0) {
                if (textToBinary.charAt(searchIndexChange - 1) == '1') {
                    textToBinary.setCharAt(searchIndexChange - 1, '0');
                } else {
                    textToBinary.setCharAt(searchIndexChange - 1, '1');
                }
            }
            textDecoding.append(textToBinary.charAt(2)).append(textToBinary.charAt(4)).append(textToBinary.charAt(5)).append(textToBinary.charAt(6));
            textToBinary.delete(0, 8);
        }
        String outputDecodingText = ConvertText.convertBinaryToChar(textDecoding);
        new InputOutputData().outputData("decoded.txt", outputDecodingText);
    }
}

class ConvertText {
    static StringBuilder convertCharToBinary(String text) {
        StringBuilder textBinary = new StringBuilder(text.length() * 9);
        for (char character : text.toCharArray()) {
            String charToBinary = Integer.toBinaryString(character);
            if (charToBinary.length() < 8) {
                int x = 8 - charToBinary.length();
                textBinary.append("0".repeat(x));
            }
            textBinary.append(charToBinary);
        }
        return textBinary;
    }

    static String convertBinaryToChar(StringBuilder textBinary) {
        StringBuilder textBinaryToChar = new StringBuilder();
        while (textBinary.length() > 0) {
            textBinaryToChar.append((char) Integer.parseInt(textBinary.substring(0, 8), 2));
            textBinary.delete(0, 8);
        }
        return String.valueOf(textBinaryToChar);
    }
}