package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    public static ArrayList<Contact> readData() throws IOException {
        ArrayList<Contact> arrayList = new ArrayList<>(); // creating a Contact-type array list
        File file = new File("src/data.txt");
        if(file.exists()){}
        else{
            file.createNewFile();
        } // O(1)
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String[] list = scanner.nextLine().split("_");
            String name = list[0];
            String phone = list[1];
            String email = list[2];
            String note = list[3];
            Contact contact = new Contact(name,phone, email, note);
            arrayList.add(contact);
        } //O(n)
        scanner.close();
        return arrayList;
    }
    public static void writeData(ArrayList<Contact> arrayList) throws IOException {

        File file = new File("src/data.txt");
        if(file.exists()){}
        else{
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (Contact x: arrayList) {
            String data_string = String.format("%s_%s_%s_%s\n",x.getName(),x.getPhone_number(),x.getEmail(),x.getAbout());
            fileWriter.write(data_string);
        }
        fileWriter.close();
    }
}
