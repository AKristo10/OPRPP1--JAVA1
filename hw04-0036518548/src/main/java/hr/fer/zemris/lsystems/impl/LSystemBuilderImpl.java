package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
/**
 * Klasa koja predstavlja glavnu aplikaciju.
 * @author Andrea
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	Dictionary<Character, String> productions = new Dictionary<Character, String>();
	Dictionary<Character, Command> actions = new Dictionary<Character, Command>();

	double unitLength = 0.1;
	double unitLengthDegreeScaler = 1;
	Vector2D origin = new Vector2D(0, 0);
	double angle = 0;
	String axiom = "";

	@Override
	public LSystem build() {
		return new Builder();
	}

	class Builder implements LSystem {

		@Override
		public String generate(int arg0) {
			String result = "";
			for (int i = 0; i <= arg0; i++) {
				if (result.equals("")) {
					result = axiom;
				} else {
					String novi = "";
					for (int j = 0; j < result.length(); j++) {
						if (productions.get(result.toCharArray()[j]) == null) {
							novi += String.valueOf(result.toCharArray()[j]);
						} else {
							novi += productions.get(result.toCharArray()[j]);
						}
					}
					result = novi;
				}
			}
			return result;
		}

		@Override
		public void draw(int arg0, Painter arg1) {

			Context context = new Context();
			TurtleState turtle = new TurtleState();
			turtle.setColor(Color.BLACK);
			turtle.setDirection(new Vector2D(1, 0));
			turtle.getDirection().rotate(angle * (Math.PI / (double) 180));
			turtle.setPosition(origin);
			turtle.setShift(unitLength * Math.pow(unitLengthDegreeScaler, arg0));
			context.pushState(turtle);

			String generateString = generate(arg0);

			for (int i = 0; i < generateString.toCharArray().length; i++) {
				if (actions.get(generateString.toCharArray()[i]) != null)
					actions.get(generateString.toCharArray()[i]).execute(context, arg1);

			}
		}
	}

	/**
	 * Metoda koja parsira dobiveni string i poziva odgovarajucu metodu nad njim
	 * 
	 * @param s
	 */
	public void parse(String s) {
		String tab = "	";
		s = s.replace(tab, " ");
		String[] arrayWithoutBlanks = s.split("\\s+");
		if (arrayWithoutBlanks[0].equals("origin")) {
			setOrigin(Double.valueOf(arrayWithoutBlanks[1]), Double.valueOf(arrayWithoutBlanks[2]));
		} else if (arrayWithoutBlanks[0].equals("angle")) {
			setAngle(Double.parseDouble(arrayWithoutBlanks[1]));
		} else if (arrayWithoutBlanks[0].equals("unitLength")) {
			setUnitLength(Double.valueOf(arrayWithoutBlanks[1]));
		} else if (arrayWithoutBlanks[0].equals("unitLengthDegreeScaler")) {
			if (arrayWithoutBlanks.length == 2) {
				String[] tmp = arrayWithoutBlanks[1].split("/");
				if (tmp.length == 2) {
					setUnitLengthDegreeScaler(Double.valueOf(tmp[0]) / Double.valueOf(tmp[1]));
				} else
					setUnitLengthDegreeScaler(Double.valueOf(tmp[0]));
			} else if (arrayWithoutBlanks.length == 3) {
				if (arrayWithoutBlanks[1].contains("/")) {
					String first = arrayWithoutBlanks[1].split("/")[0];
					setUnitLengthDegreeScaler(Double.valueOf(first) / Double.valueOf(arrayWithoutBlanks[2]));
				} else if (arrayWithoutBlanks[2].contains("/")) {
					String second = arrayWithoutBlanks[2].split("/")[1];
					setUnitLengthDegreeScaler(Double.valueOf(arrayWithoutBlanks[1]) / Double.valueOf(second));
				}
			} else if (arrayWithoutBlanks.length == 4)
				setUnitLengthDegreeScaler(
						Double.valueOf(Double.valueOf(arrayWithoutBlanks[1]) / Double.valueOf(arrayWithoutBlanks[3])));

		} else if (arrayWithoutBlanks[0].equals("command")) {
			if (arrayWithoutBlanks.length == 4)
				registerCommand(arrayWithoutBlanks[1].toCharArray()[0],
						arrayWithoutBlanks[2] + " " + arrayWithoutBlanks[3]);
			else
				registerCommand(arrayWithoutBlanks[1].toCharArray()[0], arrayWithoutBlanks[2]);
		} else if (arrayWithoutBlanks[0].equals("axiom")) {
			setAxiom(arrayWithoutBlanks[1]);
		} else if (arrayWithoutBlanks[0].equals("production")) {
			registerProduction(arrayWithoutBlanks[1].toCharArray()[0], arrayWithoutBlanks[2]);

		}
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String s : arg0) {
			parse(s);
		}
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] commandAndparameter = arg1.split("\\s+");
		String command = commandAndparameter[0];
		String parameter = new String();
		if (commandAndparameter.length > 1) {
			parameter = commandAndparameter[1];
		}
		if (command.equals("draw")) {
			DrawCommand dc = new DrawCommand(Double.valueOf(parameter));
			actions.put(arg0, dc);
		} else if (command.equals("push")) {
			PushCommand pc = new PushCommand();
			actions.put(arg0, pc);
		} else if (command.equals("pop")) {
			PopCommand pc = new PopCommand();
			actions.put(arg0, pc);
		} else if (command.equals("rotate")) {
			RotateCommand rc = new RotateCommand(Double.valueOf(parameter));
			actions.put(arg0, rc);
		} else if (command.equals("skip")) {
			SkipCommand sc = new SkipCommand(Double.valueOf(parameter));
			actions.put(arg0, sc);
		} else if (command.equals("scale")) {
			ScaleCommand sc = new ScaleCommand(Double.valueOf(parameter));
			actions.put(arg0, sc);
		} else if (command.equals("color")) {
			ColorCommand cc = new ColorCommand(new Color(Integer.parseInt(parameter, 16)));
			actions.put(arg0, cc);
		}

		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}

}
