import java.util.*;
public class SetTest{
	public static void main(String... args){
		HashSet<Integer> unique = new HashSet<Integer>();
		int i=8;
		while(i >= 0){
			unique.add(i--);
		}
		System.out.println(unique);
	}
}