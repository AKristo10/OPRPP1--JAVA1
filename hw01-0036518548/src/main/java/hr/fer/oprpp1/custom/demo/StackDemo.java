package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		ObjectStack s= new ObjectStack();
		for(int i = 0; i<args[0].split(" ").length ; i++) {

			try {
				int number = Integer.parseInt(args[0].split(" ")[i]);
				s.push(number);
				//System.out.println("Stavio sam broj");
				//System.out.println(s.isEmpty());
			}
			catch(Exception e){
				Integer value1=(Integer) s.pop();
				Integer value2=(Integer) s.pop();
				String znak = args[0].split(" ")[i];
				switch (znak) {
				case "/": {
					s.push(value2 / value1);
					break;
				}
				case "*":{
					s.push(value2 * value1);
					break;
				}
				case "+":{
					s.push(value2 + value1);
					break;
				}
				case "-":{
					s.push(value2 - value1);
					break;
				}
				case "%":{
					s.push(value2 % value1);
					break;
				}
				}

			}
		}
		System.out.println(s.peek());
	}
}

