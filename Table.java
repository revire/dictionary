package exam;

import java.util.*;

public class Table{ 
	
	public Integer id;
	public List<Word> words;

	public Table (Integer id, Word word1, Word word2){
		this.id = id;
		this.words = new ArrayList<Word>();
		words.add(word1);
		words.add(word2);
		//this.words = new <Word> asList<Word>(word1, word2);
	}
	
	public void printTable(Table table){
		System.out.println("составлена таблица: ");
		System.out.print(id + " ");
		for (Word word : words) {
			System.out.print(word.meaning + " ");
			
		}
		System.out.println(" ");
	}
}
