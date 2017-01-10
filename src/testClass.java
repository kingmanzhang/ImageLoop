import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Integer;
import java.util.Iterator;

public class testClass {
   public static void main(String[] args) {
   	
   	//test LinkedLoop class
   	LinkedLoop<String> stringlist = new LinkedLoop<>();
   	/*
   	System.out.println("list size " + stringlist.size());
   	System.out.println("list empty? " + stringlist.isEmpty());
   	stringlist.add("aa");
   	System.out.println("list size " + stringlist.size());
   	stringlist.add("bb");
   	System.out.println("list size " + stringlist.size());
   	stringlist.add("cc");
   	System.out.println("list size " + stringlist.size());
   	System.out.println("list empty? " + stringlist.isEmpty());
   	
   	//test getCurrent(), next(), prev();
   	try {
   		System.out.println(stringlist.getCurrent());
   		stringlist.next();
			System.out.println(stringlist.getCurrent());
			stringlist.next();
			System.out.println(stringlist.getCurrent());
			stringlist.next();
			System.out.println(stringlist.getCurrent());
			stringlist.next();
			System.out.println(stringlist.getCurrent());
			stringlist.previous();
			System.out.println(stringlist.getCurrent());
			stringlist.previous();
			System.out.println(stringlist.getCurrent());
			stringlist.previous();
			System.out.println(stringlist.getCurrent());
			stringlist.previous();
			System.out.println(stringlist.getCurrent());
			stringlist.next();
			System.out.println("list size " + stringlist.size());
			System.out.println("current is " + stringlist.getCurrent());
			System.out.println("remove current " + stringlist.removeCurrent());
			System.out.println("current becomes " + stringlist.getCurrent());
			System.out.println("list size " + stringlist.size());
			
			stringlist.next();
			System.out.println(stringlist.getCurrent());
			System.out.println("list size " + stringlist.size());
			System.out.println("current is " + stringlist.getCurrent());
			System.out.println("remove current " + stringlist.removeCurrent());
			System.out.println("current becomes " + stringlist.getCurrent());
			System.out.println("list size " + stringlist.size());
			
			System.out.println("remove current " + stringlist.removeCurrent());
			System.out.println("current becomes " + stringlist.getCurrent());
			System.out.println("list size " + stringlist.size());
			
			System.out.println("remove current " + stringlist.removeCurrent());
			System.out.println("list size " + stringlist.size());
			
			
			System.out.println("list empty? " + stringlist.isEmpty());
	   	stringlist.add("aa");
	   	System.out.println("list size " + stringlist.size());
	   	stringlist.add("bb");
	   	System.out.println("list size " + stringlist.size());
	   	stringlist.add("cc");
	   	System.out.println("list size " + stringlist.size());
	   	System.out.println("list empty? " + stringlist.isEmpty());
			
	   	stringlist.next();
	   	stringlist.next();
			System.out.println(stringlist.getCurrent());
			stringlist.next();
			System.out.println("current is " + stringlist.getCurrent());
			stringlist.next();
			System.out.println("current is " + stringlist.getCurrent());
			stringlist.next();
			System.out.println("current is " + stringlist.getCurrent());
			stringlist.next();
			System.out.println("current is " + stringlist.getCurrent());
			
		} catch (EmptyLoopException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	*/
   	System.out.println("list empty? " + stringlist.isEmpty());
   	stringlist.add("aa");
   	
   	System.out.println("list size " + stringlist.size());
   	stringlist.add("bb");
   	stringlist.next();
   	System.out.println("list size " + stringlist.size());
   	stringlist.add("cc");
   	System.out.println("list size " + stringlist.size());
   	stringlist.next();
   	stringlist.next();
   	try {
			System.out.println("current is " + stringlist.getCurrent());
		} catch (EmptyLoopException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
   	System.out.println("test iterator");
   	Iterator<String> itr = stringlist.iterator();
   	while(itr.hasNext()) {
   		System.out.println("current is " + itr.next());
   	}
   	
   	FileOutputStream output;
		try {
			output = new FileOutputStream("testtext.txt");
			PrintWriter writer = new PrintWriter(output);
			for (int i = 0; i < 100; i++) {
				writer.println(i + "abc      abc");
			}
			writer.close();
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

   	
   }

   
}
