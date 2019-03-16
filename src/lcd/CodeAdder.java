/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lcd;

import java.awt.Component;
import java.util.Formatter;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author pc
 */
public class CodeAdder {
    static StringBuilder code;
    static HashMap<Character, String> lcdChars = new HashMap<>();
    static int counter = 0;
    static boolean begun = false, done = false;
    static void initChars(){
        lcdChars.put(' ', "00100000");
        lcdChars.put('!', "00100001");
        lcdChars.put('"', "00100010");
        lcdChars.put('#', "00100011");
        lcdChars.put('$', "00100100");
        lcdChars.put('%', "00100101");
        lcdChars.put('&', "00100110");
        lcdChars.put('\'', "00100111");
        lcdChars.put('(', "00101000");
        lcdChars.put(')', "00101001");
        lcdChars.put('*', "00101010");
        lcdChars.put('+', "00101011");
        lcdChars.put(',', "00101100");
        lcdChars.put('-', "00101101");
        lcdChars.put('.', "00101110");
        lcdChars.put('/', "00101111");
        lcdChars.put('0', "00110000");
        lcdChars.put('1', "00110001");
        lcdChars.put('2', "00110010");
        lcdChars.put('3', "00110011");
        lcdChars.put('4', "00110100");
        lcdChars.put('5', "00110101");
        lcdChars.put('6', "00110110");
        lcdChars.put('7', "00110111");
        lcdChars.put('8', "00111000");
        lcdChars.put('9', "00111001");
        lcdChars.put(':', "00111010");
        lcdChars.put(';', "00111011");
        lcdChars.put('<', "00111100");
        lcdChars.put('=', "00111101");
        lcdChars.put('>', "00111110");
        lcdChars.put('?', "00111111");
        lcdChars.put('@', "01000000");
        lcdChars.put('A', "01000001");
        lcdChars.put('B', "01000010");
        lcdChars.put('C', "01000011");
        lcdChars.put('D', "01000100");
        lcdChars.put('E', "01000101");
        lcdChars.put('F', "01000110");
        lcdChars.put('G', "01000111");
        lcdChars.put('H', "01001000");
        lcdChars.put('I', "01001001");
        lcdChars.put('J', "01001010");
        lcdChars.put('K', "01001011");
        lcdChars.put('L', "01001100");
        lcdChars.put('M', "01001101");
        lcdChars.put('N', "01001110");
        lcdChars.put('O', "01001111");
        lcdChars.put('P', "01010000");
        lcdChars.put('Q', "01010001");
        lcdChars.put('R', "01010010");
        lcdChars.put('S', "01010011");
        lcdChars.put('T', "01010100");
        lcdChars.put('U', "01010101");
        lcdChars.put('V', "01010110");
        lcdChars.put('W', "01010111");
        lcdChars.put('X', "01011000");
        lcdChars.put('Y', "01011001");
        lcdChars.put('Z', "01011010");
        lcdChars.put('[', "01011011");
        lcdChars.put(']', "01011101");
        lcdChars.put('^', "01011110");
        lcdChars.put('_', "01011111");
        lcdChars.put('`', "01100000");
        lcdChars.put('a', "01100001");
        lcdChars.put('b', "01100010");
        lcdChars.put('c', "01100011");
        lcdChars.put('d', "01100100");
        lcdChars.put('e', "01100101");
        lcdChars.put('f', "01100110");
        lcdChars.put('g', "01100111");
        lcdChars.put('h', "01101000");
        lcdChars.put('i', "01101001");
        lcdChars.put('j', "01101010");
        lcdChars.put('k', "01101011");
        lcdChars.put('l', "01101100");
        lcdChars.put('m', "01101101");
        lcdChars.put('n', "01101110");
        lcdChars.put('o', "01101111");
        lcdChars.put('p', "01110000");
        lcdChars.put('q', "01110001");
        lcdChars.put('r', "01110010");
        lcdChars.put('s', "01110011");
        lcdChars.put('t', "01110100");
        lcdChars.put('u', "01110101");
        lcdChars.put('v', "01110110");
        lcdChars.put('w', "01110111");
        lcdChars.put('x', "01111000");
        lcdChars.put('y', "01111001");
        lcdChars.put('z', "01111010");
        lcdChars.put('{', "01111011");
        lcdChars.put('|', "01111100");
        lcdChars.put('}', "01111101");
    }
    static void addBegin(){
        if(!begun){
            code = new StringBuilder("");
            counter = 0;
            done = false;
            begun = true;
            code.append("begin\n");
            code.append("\tif(CLK' event and CLK = '1') then\n");
        }
    }
    static void addEnd(){
        if(!done){
            done = true;
            begun = false;
            code.append("end process;");
        }
    }
    static void addCount(int num){
        if(num < 0)
            return;
        code.append("\t\t");
        if(counter > 0)
            code.append("els");
        counter+=num;
        code.append("if( count < ");
        code.append(counter);
        code.append(" ) then ");
        code.append("-- wait ");
        code.append(num);
        code.append(" cycles\n");
    }
    static void addEndIf(){
        code.append("\t\telse\n");
        code.append("\t\t\tLCD_E <= '0';\n");
        code.append("\t\tend if;\n");
        code.append("\t\tcount := count + 1;\n");
        code.append("\tend if;\n");
    }
    static String getUpperNibble(String s){
        return s.substring(0, 4);
    }
    static String getLowerNibble(String s){
        return s.substring(4, 8);
    }
    static void addSetData(String s){
        code.append("\t\t\tSF_D <= \"");
        code.append(s);
        code.append("\";\n");
    }
    static void addSetEnable(boolean enable){
        String en = enable?"1":"0";
        code.append("\t\t\tLCD_E <= '");
        code.append(en);
        code.append("';\n");
    }
    static void addSetRS(boolean rs){
        String en = rs?"1":"0";
        code.append("\t\t\tLCD_RS <= '");
        code.append(en);
        code.append("';\n");
    }
    static void addSetRW(boolean rw){
        String en = rw?"1":"0";
        code.append("\t\t\tLCD_RW <= '");
        code.append(en);
        code.append("';\n");
    }
    static void addComment(String comment){
        code.append("\t\t--");
        code.append(comment);
        code.append("\n");
    }
    static void addInitialize(){
        addNewLine();
        addComment("Initializing the display");
        addCount(750000);
        addSetData("0000");
        addSetEnable(false);
        addSetRW(false);
        addSetRS(false);
        addCount(12);
        addSetData("0011");
        addSetEnable(true);
        addCount(205000);
        addSetEnable(false);
        addCount(12);
        addSetData("0011");
        addSetEnable(true);
        addCount(5000);
        addSetEnable(false);
        addCount(12);
        addSetData("0011");
        addSetEnable(true);
        addCount(2000);
        addSetEnable(false);
        addCount(12);
        addSetData("0010");
        addSetEnable(true);
        addCount(2000);
        addSetEnable(false);
    }
    static void writeFile(Component parent){
        JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(parent);
        try{
            Formatter f = new Formatter(fc.getSelectedFile());
            f.format(code.toString());
            f.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(parent, "An error occured!", "Ooops!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    static void addNewLine(){
        code.append("\n");
    }
    static void addCommand(boolean rs, boolean rw, String s, String comment){
        addNewLine();
        addComment(comment);
        addCount(2);
        addSetEnable(false);
        addSetRS(rs);
        addSetRW(rw);
        addSetData(getUpperNibble(s));
        addCount(12);
        addSetEnable(true);
        addCount(1);
        addSetEnable(false);
        addCount(50);
        addSetRW(true);
        addCount(2);
        addSetEnable(false);
        addSetRS(rs);
        addSetRW(rw);
        addSetData(getLowerNibble(s));
        addCount(12);
        addSetEnable(true);
        addCount(1);
        addSetEnable(false);
        if(!rs && !rw && s.equals("00000001") || s.substring(0, 7).equals("0000001"))
            addCount(82000);
        else
            addCount(2000);
        addSetRW(true);
    }
    static void addConfigure(){
        addNewLine();
        addComment("Configuring the display");
        addCommand(false, false, "00101000", "Function Set");
        addCommand(false, false, "00000100", "Entry Mode Set");
        addCommand(false, false, "00001100", "Display On/ Off");
        addCommand(false, false, "00000001", "Clear Display");
        addComment("End Configuring the display");
    }
    static void addDelay(int cycles){
        if(cycles <= 0)
            return;
        addNewLine();
        addComment("Delay for "+cycles+" cycles");
        addCount(cycles);
        code.append("\t\t\tnull;\n");
    }
    static String decodeCharacter(char c){
        if(lcdChars.containsKey(c)){
            return lcdChars.get(c);
        }
        else
            return "11111111";
    }

}
