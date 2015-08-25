package exam;

import java.util.ArrayList;
import java.util.List;

public class Word{ 
	
	public String meaning;
	public List<Integer> translations;
	public String lang;

	public Word(String meaning, String langSource, int translation) {
		this.meaning = meaning;
		this.lang = langSource;
		this.translations = new ArrayList<Integer>();
		translations.add(translation);		
	}
	
	public String getName(){
		return this.meaning;
	}	
}
