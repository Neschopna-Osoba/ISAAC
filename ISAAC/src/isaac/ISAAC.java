/*
 * ISAAC
 * Petr Salavec, 4.E
 */
package isaac;

import java.util.ArrayList;
import java.util.Scanner;

public class ISAAC {

    public static Integer getNumber(ArrayList<Character> arr, int j) {
        int number = 0;
        int dec = 1;

        for (int i = j; i < arr.size(); i++) {
            if (isNumber(arr.get(i))) {
                number = number * dec + Character.getNumericValue(arr.get(i));
                dec = dec * 10;
            } else {
                break;
            }
        }
        return number;
    }

    public static ArrayList<Character> cleanup(ArrayList<Character> arr, int x, int y) {

        System.out.println(arr);
        for (int i = y; i > x; i--) { //JE potřeba jet odzadu
            System.out.println(arr);
            arr.remove(i);
        }
        return arr;
    }

    public static Boolean isSign(Character ch) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=') {
            return true;
        }
        return false;
    }

    public static Boolean isNumber(Character ch) {
        if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = "2x+9*2=12-3x*3";
        int numValue = 0; // Hodnota x
        int xValue = 0;  // Hodnota absolutního členu
        int znamenko = 1; //Znamenko, které se otočí na druhé straně rovnice

        ArrayList<Character> arr = new ArrayList();
        for (int i = 0; i < s.length(); i++) {
            arr.add(s.charAt(i));
        }
        System.out.println(arr);

//**************NÁSOBENÍ****************
        
        for (int i = 0; i < arr.size(); i++) {        // Procházení celým výrazem nejdříve pro násobení
            if (arr.get(i) == '*') {

                boolean isXLeft = false; //Pozice proměné v činitelech
                boolean isXRight = false;
                int low = 0; //Nejnižší činitel
                int high = 0;  //Nejvyšší činitel
                
                
                int tempXL = 0; //Levý x činitel
                int tempNumL = 0;  //Levý čistě numerický činitel
                System.out.println("test1");

                for (int j = i - 1; j >= 0; j--) { //Zjištění činitele na levo od znamínka
                    if (arr.get(j) == 'x') {
                        isXLeft = true;
                    } else if (isSign(arr.get(j)) || j - 1 == -1) {
                        if (isXLeft) {
                            tempXL = tempXL + getNumber(arr, j + 1) * znamenko;
                            low = j - 1;
                            System.out.println(tempXL);
                            break;
                        } else {
                            tempNumL = tempNumL + getNumber(arr, j + 1) * znamenko;
                            low = j - 1;
                            System.out.println(tempNumL);
                            break;
                        }

                    }
                }
                int tempXR = 0; //Pravý x činitel
                int tempNumR = 0; //Pravý čistě numerický činitel

                for (int j = i + 1; j < arr.size(); j++) { //Zjištění činitele napravo od znamínka
                    if (arr.get(j) == 'x') {
                        isXRight = true;
                    } else if (isSign(arr.get(j)) || j + 1 == arr.size()) {
                        if (isXRight) {
                            tempXR = tempXR + getNumber(arr, i + 1) * znamenko;
                            high = j - 1;
                            if (j + 1 == arr.size()) { //Líná záplata na bug s cleanupem
                                high = high + 1;
                            }
                            System.out.println(tempXR);
                            break;
                        } else {
                            tempNumR = tempNumR + getNumber(arr, i + 1) * znamenko;
                            high = j - 1;
                            if (j + 1 == arr.size()) { //Líná záplata na bug s cleanupem
                                high = high + 1;
                            }
                            System.out.println(tempNumR);
                            break;
                        }

                    }
                }

                if (isXLeft) { //Pokud je x nalevo
                    if (arr.get(low) == '+') {
                        xValue = xValue + (tempXL * tempNumR * znamenko);
                    } else {
                        xValue = xValue - (tempXL * tempNumR * znamenko);
                    }
                    System.out.println(znamenko);

                } else if (isXRight) { //POkud je x napravo
                    if (arr.get(low) == '+') {
                        xValue = xValue + (tempXR * tempNumL * znamenko);
                    } else {
                        xValue = xValue - (tempXR * tempNumL * znamenko);
                    }

                } else { //Pokud se v násobení x nevyskytuje (zatím nejsou implementovány mocniny, proto zatím jenom else)
                    if (arr.get(low) == '+') {
                        numValue = numValue + (tempNumL * tempNumR * znamenko);
                    } else {
                        numValue = numValue + (tempNumL * tempNumR * znamenko);
                    }
                }

                arr = cleanup(arr, low, high); //Vyjmutí celého násobení z ArrayListu
                System.out.println(arr);
                System.out.println(numValue);
                System.out.println(xValue);
                i = 0; //Jelikož se ArrayList měnil, musí se vrátit se cyklus vrátit na začátek
            } else if (arr.get(i) == '=') {    // Když se narazí na = tak se otočí znamenko a zkontroluje se další člen
                znamenko = -1;
            }

        }

        znamenko = 1;

        
//**************SČÍTÁNÍ A ODČÍTÁNÍ****************        
               
        for (int i = 0; i < arr.size(); i++) {          // Procházení celým výrazem
            if (arr.get(i) == '+') {
                if (arr.get(i + 1) == 'x') {
                    xValue = xValue + znamenko;
                } else {
                    for (int j = i + 1; j < arr.size(); j++) {  //Procházení následujícím členem 
                        if (arr.get(j) == 'x') {               //Pokud se narazí na x je to člen ve formě 11x a 11 se musí přičíst k xValue 
                            xValue = xValue + getNumber(arr, i + 1) * znamenko;
                            break;
                        } else if (isSign(arr.get(j)) || j + 1 == arr.size()) {    //Pokud se nenerazí na x, tak je to normální číslo
                            numValue = numValue + getNumber(arr, i + 1) * znamenko;
                            break;
                        }
                    }
                }

            } else if (arr.get(i) == '-') {    //Identické jako v případě +, jenom s obráceným znaámenkem
                if (arr.get(i + 1) == 'x') {
                    xValue = xValue - znamenko;
                } else {
                    for (int j = i + 1; j < arr.size(); j++) {
                        if (arr.get(j) == 'x') {
                            xValue = xValue - getNumber(arr, i + 1) * znamenko;
                            break;
                        } else if (isSign(arr.get(j)) || j + 1 == arr.size()) {
                            numValue = numValue - getNumber(arr, i + 1) * znamenko;
                            break;
                        }
                    }
                }

            } else if (arr.get(i) == '=') {    // Když se narazí na = tak se otočí znamenko a zkontroluje se další člen
                znamenko = -1;

                if (arr.get(i + 1) == 'x') {   //Kontrola dalšího členu (pouze pro kladné - pro záporné by tam bylo - a chytila by to předchzí podmínka)
                    xValue = xValue + znamenko;
                } else if (!isSign(arr.get(i + 1))) {
                    for (int j = i + 1; j < arr.size(); j++) {
                        if (arr.get(j) == 'x') {
                            xValue = xValue + getNumber(arr, i + 1) * znamenko;
                            break;
                        } else if (isSign(arr.get(j)) || j + 1 == arr.size()) {
                            numValue = numValue + getNumber(arr, i + 1) * znamenko;
                            break;
                        }
                    }

                }
            } else if (i == 0) { //Kontrola začátku výrazu (opět pouze pro kladné - pro záporné by to chytila předchozí podmínka)
                if (arr.get(i) == 'x') {
                    xValue = xValue + znamenko;
                } else if (!isSign(arr.get(i))) {
                    for (int j = i; j < arr.size(); j++) {
                        if (arr.get(j) == 'x') {
                            xValue = xValue + getNumber(arr, i) * znamenko;
                            break;
                        } else if (isSign(arr.get(j)) || j + 1 == arr.size()) {
                            numValue = numValue + getNumber(arr, i) * znamenko;
                            break;
                        }
                    }

                }
            }
        }

        Double result = 0.0;
        numValue = numValue * -1; // Dostanu absolutní člen na "druhou stranu výrazu"

        System.out.println(xValue + "  " + numValue);
        if (xValue < 0) {
            xValue = xValue * -1; //Otočení znaménka x
            numValue = numValue * -1;

            result = Double.valueOf(numValue) / Double.valueOf(xValue); // Vydělení absolutního členu velikostí x
            xValue = 1; // X je po vydělení 1

            System.out.println("x = " + result);

        } else if (xValue >= 0) {

            result = Double.valueOf(numValue) / Double.valueOf(xValue);  // Vydělení absolutního členu velikostí x
            xValue = 1; // X je po vydělení 1

            System.out.println("x = " + result);

        }

    }
}
