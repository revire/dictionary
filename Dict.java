package exam;

import java.util.*;
public class Dict {

	private final Scanner sc;
	private static int numSource = 10000;
	private int numTarget = 10000;
	int id = 0;
	boolean backStaff = true;

	
	private static ArrayList<Language> languages;
	private static ArrayList<Table> tables;
	

	public Dict() {
		sc = new Scanner(System.in);
		languages = new ArrayList<>();
		tables = new ArrayList<>();
	}

	public void run() {
		System.out.println("Привет! /help для выведения списка команд");


		while (true) {
			//пусть он всегда показывает направление перевода
			if(numSource != 10000 & numTarget != 10000){
				System.out.println();
				showLangDir();
			}
			System.out.print("> ");
			String command = sc.nextLine();
			String[] commandArray = command.split(" ");
			command = commandArray[0];
				switch (command) {
	
					case "/help":
						help();
						break;
					case "/showlangdir":
						showLangDir();
						break;
					case "/showlangs":
						showLangs();
						break;
					case "/setlangfrom":
						setLangFrom();
						break;
					case "/setlangto":
						setLangTo();
						break;
					case "/addLang":
						addLang();
						break;
					case "/addTranslation":
						addTranslation();
						break;
					case "/q":
						quite();
						break;
					case "/showwords":
						showWords();
						break;
					case "/back":	
						back();
						break;
					case "/set":
						String source = commandArray[1];
						String target = commandArray[2];
						set(source, target);
					
					default:
						if(sc.nextLine().equals("")){
							System.out.println("неправильно набранная команда");
						} else {
							char checkChar = command.charAt(0);
							if(checkChar == '/'){
							System.out.println("неправильно набранная команда");
							} else{			
							search(command);
						}
						
				
				}
			}
		}	
	}
	

	//команды словаря
	
	
	//выход из команды
	public void back(){
	}
	
	//выход из программы
	public static void quite(){
		System.exit(0);
	}
	
	
	//напечатать слова, которые есть
	public static void showWords(){
		System.out.println("слова в словаре: " + languages.get(numSource).getName());
		for (Integer id : languages.get(numSource).words) {
			System.out.print(id + " ");
			for (Word word : tables.get(id).words) {
				System.out.println(word.meaning);				
			}						
		}
	}
	
	//вывод команд
	public static void help() {
		System.out.println("/showlangdir - показать направление перевода, например ru->eng");
		System.out.println("/showlangs - показать список поддерживаемых языков, например ru, eng, ita, fra");
		System.out.println("/setlangfrom - установить язык-источник");
		System.out.println("/setlangto   - установить язык-цель");
		System.out.println("/addTranslation - добавить перевод");
		System.out.println("<word> - перевести слово <word> с языка-источника на язык-цель");
		System.out.println("/addLang - добавить язык");
		System.out.println("set <word-source> <word-target> - запомнить пару в словаре word-source - слово на языке источнике, word-target - на языке-цели");
		System.out.println("/back - выйти из команды"); 
		System.out.println("/q - выйти из программы");
	}
	
	
	
	//добавление языка
	public void addLang() {
		System.out.print("введите имя нового языка: ");
		String nameOfLang = this.sc.nextLine();
		if(nameOfLang.matches("/back")){
			back();			
		}else{
			languages.add(new Language(nameOfLang));
			System.out.println("язык " + nameOfLang + " добавлен");
		}
	}
	
	//печать имеющихся языков
	public void showLangs() {
		System.out.println("список кораблей: ");
		for (Language language : languages) {
			System.out.println(language.getName());
		}
	
	}
	
	//установка направления перевода
	public void setLangFrom() {
		System.out.print("наберите язык, с которого переводить: ");
		String forSetLang = this.sc.nextLine();
		if(forSetLang.matches("/back")){
			back();			
		}else{
			boolean isThereLang = false;			
			for(int i = 0; i < languages.size(); i++){
				Language language = languages.get(i);
				if(forSetLang.equals(language.name)){
					numSource = i;
					System.out.println("язык " + forSetLang + " установлен");
					isThereLang = true;
				}						
			}
			if (isThereLang == false){
				System.out.println("язык не найден");
			}				
			isThereLang = false;
		}
	}
	
	//установка направления перевода
	public void setLangTo() {
		System.out.print("наберите язык, в который переводить: ");
		String forSetLang = this.sc.nextLine();
		if(forSetLang.matches("/back")){
			back();			
		}else{
			boolean isThereLang = false;	
			for(int i = 0; i < languages.size(); i++){
				Language language = languages.get(i);
				if(forSetLang.equals(language.name)){
					numTarget = i;
					System.out.println("язык " + forSetLang + " установлен");
					isThereLang = true;
				}						
			}
			if (isThereLang == false){
				System.out.println("язык не найден");
			}
			isThereLang = false;
		}
	}
	
			
	
	public void showLangDir() {
		
		while(numSource == 10000){
			setLangFrom();			
		} 	
		while(numTarget == 10000){
			setLangTo();
		}
		System.out.print("направление перевода: ");
		System.out.println(languages.get(numSource).name + " ---> " + languages.get(numTarget).name);
	}

	public void addTranslation() {
		
		//установлены ли языки перевода		
		checkLang();
		if (backStaff == false){
			return;
		}
			
		String langSource;
		langSource = languages.get(numSource).getName();
		String langTarget;	
		langTarget = languages.get(numTarget).getName();
		
		System.out.println("");
		showLangDir();
		
		//ввод слов для перевода
		System.out.print("введите слово для перевод: ");
		String source = this.sc.nextLine();		
		System.out.print("введите перевод слова " + source + ": ");
		String target = this.sc.nextLine();

		
		//проверка на наличие повторяющихся слов
		
		int forSource = 10000;
		int forTarget = 9999;
		int idOfSource = 10000;
		int idOfTarget = 10000;
		
		//проходим по коллекции слов у одного из языков и ищем id в таблице
		//System.out.println("есть ли уже у языка " + languages.get(numSource).name + " слово " + source);
		sourceLoop:	for(int i = 0; i < languages.get(numSource).words.size(); i++){
			Integer word = languages.get(numSource).words.get(i);
			idOfSource = word;
			Table table = tables.get(word);
			
			for(int j = 0; j < table.words.size(); j++){
				//System.out.print(table.id + " ");
				//System.out.println(table.words.get(j).meaning);
				if(source.equals(table.words.get(j).meaning)){
					forSource = table.id;
					//System.out.println(source + " уже добавлен в словарь");
					break sourceLoop;
				}
			}
		}
		
		
		//System.out.println("есть ли уже у языка " + languages.get(numTarget).name + " слово " + target);		
		targetLoop:	for(int i = 0; i < languages.get(numTarget).words.size(); i++){
				Integer word = languages.get(numTarget).words.get(i);
				idOfTarget = word;
				Table table = tables.get(word);
				
				for(int j = 0; j < table.words.size(); j++){
					//System.out.print(table.id + " ");
					//System.out.println(table.words.get(j).meaning);
					if(target.equals(table.words.get(j).meaning)){
						forTarget = table.id;
						System.out.println(target + " уже добавлен в словарь");
						break targetLoop;
					}
					
				}
				
			}
		
		
		if(forSource == forTarget){//если слова под одним id
			//проверка переменных
			//System.out.println(idOfSource + " " + idOfTarget + " " + forSource + " " + forTarget);
			System.out.println("эти слова уже занесены в базу");
		
		} else if(forSource < 10000 & forTarget == 9999){ //если source уже есть
			//проверка переменных
			//System.out.println(idOfSource + " " + idOfTarget + " " + forSource + " " + forTarget);
			//System.out.println("добавляю только target");
			System.out.println(source + " " + target + " добавлены");//white lie
			Word word = new Word(target, langTarget, forSource);
			languages.get(numTarget).words.add(idOfSource);
			tables.get(idOfSource).words.add(word);
			tables.get(idOfSource).printTable(tables.get(idOfSource));
			idOfSource = 10000;
			forSource = 10000;
		
		} else if(forTarget < 9999 & forSource == 10000){ //если  target уже есть
			//проверка переменных
			//System.out.println(idOfSource + " " + idOfTarget + " " + forSource + " " + forTarget);
			System.out.println("добавляю только source");
			Word word = new Word(source, langSource, forTarget);
			languages.get(numSource).words.add(idOfTarget);
			tables.get(idOfTarget).words.add(word);
			tables.get(idOfTarget).printTable(tables.get(idOfTarget));
			idOfTarget = 10000;
			forTarget = 9999;
		} else{		
			//если проверка прошла успешно, создаются новые слова
					
			Word sourceWord = new Word(source, langSource, id);
			Word targetWord = new Word(target, langTarget, id);
					
			Table table = new Table(id, sourceWord, targetWord);
			//table.printTable(table);
			tables.add(table);						
			languages.get(numSource).words.add(id);
			languages.get(numTarget).words.add(id);
			System.out.println(source + " и " + target + " добавлены в словарь");
			
			//даем переменным значения для будущего использования
			id ++;
			idOfSource = 10000;
			forSource = 10000;
			idOfSource = 10000;
			forSource = 10000;
		}		
	}
	
	public void set(String source, String target) {
		
		//установлены ли языки перевода				
		checkLang();
		if (backStaff == false){
			return;
		}
		
		String langSource;
		langSource = languages.get(numSource).getName();
		String langTarget;	
		langTarget = languages.get(numTarget).getName();
		
		
		System.out.println("");
		showLangDir();
		//System.out.println(source + " " + target);
		
		//разные переменные для проверки слов
		int forSource = 10000;
		int forTarget = 9999;
		int idOfSource = 10000;
		int idOfTarget = 10000;
		
		//проход по коллекции слов у одного из языков и ищем id
		//System.out.println("есть ли уже у языка " + languages.get(numSource).name + " слово " + source);
		sourceLoop:	for(int i = 0; i < languages.get(numSource).words.size(); i++){
			Integer word = languages.get(numSource).words.get(i);
			idOfSource = word;
			Table table = tables.get(word);
			
			for(int j = 0; j < table.words.size(); j++){
				//System.out.print(table.id + " ");
				//System.out.println(table.words.get(j).meaning);
				if(source.equals(table.words.get(j).meaning)){
					forSource = table.id;
					//System.out.println(source + " уже есть");
					break sourceLoop;
				}
			}
		}
		
		
		//System.out.println("есть ли уже у языка " + languages.get(numTarget).name + " слово " + target);		
		targetLoop:	for(int i = 0; i < languages.get(numTarget).words.size(); i++){
				Integer word = languages.get(numTarget).words.get(i);
				idOfTarget = word;
				Table table = tables.get(word);
				
				for(int j = 0; j < table.words.size(); j++){
					//System.out.print(table.id + " ");
					//System.out.println(table.words.get(j).meaning);
					if(target.equals(table.words.get(j).meaning)){
						forTarget = table.id;
						//System.out.println("target уже есть");
						break targetLoop;
					}
					
				}
				
			}
		
		
		if(forSource == forTarget){//если слова под одним id
			System.out.println(idOfSource + " " + idOfTarget + " " + forSource + " " + forTarget);
			System.out.println("эти слова уже занесены в базу");
		} else if(forSource < 10000 & forTarget == 9999){ //если source уже есть
			System.out.println(idOfSource + " " + idOfTarget + " " + forSource + " " + forTarget);
			//System.out.println("добавляю только target");
			System.out.println(source + " и " + target + "добавлены"); //everybody lies
			Word word = new Word(target, langTarget, forSource);
			languages.get(numTarget).words.add(idOfSource);
			tables.get(idOfSource).words.add(word);
			tables.get(idOfSource).printTable(tables.get(idOfSource));
			idOfSource = 10000;
			forSource = 10000;
		
		} else if(forTarget < 9999 & forSource == 10000){ //случай, если  target уже есть
			//System.out.println(idOfSource + " " + idOfTarget + " " + forSource + " " + forTarget);
			//System.out.println("добавляю только source");
			System.out.println(source + " и " + target + " добавлены"); //everybody lies
			Word word = new Word(source, langSource, forTarget);
			languages.get(numSource).words.add(idOfTarget);
			tables.get(idOfTarget).words.add(word);
			tables.get(idOfTarget).printTable(tables.get(idOfTarget));
			idOfTarget = 10000;
			forTarget = 9999;
		} else{		
			//если проверка прошла успешно, создаются новые слова
				
			Word sourceWord = new Word(source, langSource, id);
			Word targetWord = new Word(target, langTarget, id);
					
			Table table = new Table(id, sourceWord, targetWord);
			//table.printTable(table);
			tables.add(table);
					
			languages.get(numSource).words.add(id);
			languages.get(numTarget).words.add(id);			
			System.out.println(source + " и " + target + " добавлены в словарь");
			
			//даем переменным значения для будущего использования
			id ++;		
			idOfSource = 10000;
			forSource = 10000;
			idOfSource = 10000;
			forSource = 10000;
		}	
	}
		
	
	public void search(String source) {

		checkLang();
		
		if (backStaff == false){
			return;
		}
		
		//showLangDir();
		
		//настройка язык ");
		String langTarget = languages.get(numTarget).getName();
		//System.out.println(langTarget + " <-- вот он язык а теперь переводим: " + source + " --> ");
		
		//цикл идет по массиву слов в языке, с которого переводят		
		//System.out.println("количество слов в языке " + languages.get(numTarget).words.size());
		searchLoop:	for(int i = 0; i < languages.get(numSource).words.size(); i++){
			//System.out.println("зашли в массив языка "+ languages.get(numTarget).name + " и вытаскиваем таблицу " + i);
			//создаем таблицу, в которой есть source word
			Integer word = languages.get(numSource).words.get(i);
			Table table = tables.get(word);
			//System.out.println("вот эта таблица: ");
			//table.printTable(table);
			//идем по этой таблице и ищем данное слово (source)
			
			//System.out.println("длина таблицы" + table.words.size());
			//System.out.println("номера слов и сами слова в " + table.id);
			
			for(int j = 0; j < table.words.size(); j++){
				//System.out.println(table.words.get(j).meaning + " ");
				//System.out.println("одинаковые ли они: " + source + " " + table.words.get(j).meaning);
				if(source.equals(table.words.get(j).meaning)){ //если translateIt совпадает с каким-то словом в таблице
					for(int q = 0; q < table.words.size(); q++){//опять идем по этой таблице
						//System.out.println("проход по таблице, в поисках языка с нужным полем lang");
						boolean oneMoreVar = false;
						if(langTarget.equals(table.words.get(q).lang)){//достаем слово, у которого такой же ланг, как нужный нам
							oneMoreVar = true;
							System.out.println("   " + table.words.get(q).meaning);
						//	System.out.println("вот такие дела");
						
						} 
						if(oneMoreVar == false){
							System.out.println("слово не найдено или неверно напечатанная команда");
						}
						
					}
					//System.out.println("слово не найдено или неверно напечатанная команда");
					break searchLoop;
							
				}
			}
		}				
	}	
	
	public void checkLang(){
		while(numSource == 10000){
			System.out.print("наберите язык, с которого переводить: ");
			String forSetLang = this.sc.nextLine();
			if(forSetLang.matches("/back")){
				backStaff = false;
				back();
				return;
			} else{
				boolean isThereLang = false;			
				for(int i = 0; i < languages.size(); i++){
					Language language = languages.get(i);
					if(forSetLang.equals(language.name)){
						numSource = i;
						System.out.println("язык " + forSetLang + " установлен");
						isThereLang = true;
					}						
				}
				if (isThereLang == false){
					System.out.println("язык не найден");
				}				
				isThereLang = false;
			}
		}
			
		
		
		while(numTarget == 10000){
			System.out.print("наберите язык, в который переводить: ");
			String forSetLang = this.sc.nextLine();
			if(forSetLang.matches("/back")){
				backStaff = false;
				back();
				return;			
			} else{
				boolean isThereLang = false;	
				for(int i = 0; i < languages.size(); i++){
					Language language = languages.get(i);
					if(forSetLang.equals(language.name)){
						numTarget = i;
						System.out.println("язык " + forSetLang + " установлен");
						isThereLang = true;
					}						
				}
				if (isThereLang == false){
					System.out.println("язык не найден");
				}
					isThereLang = false;
			}
		}
		
	}
} 
