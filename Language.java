package exam;

import java.util.*;

public class Language{ 
		
	public String name;
	public List<Integer> words;

	public Language(String name) {
		this.name = name;
		this.words = new ArrayList<Integer>();
	}

	
	public String getName(){
		return this.name;
	}
}
