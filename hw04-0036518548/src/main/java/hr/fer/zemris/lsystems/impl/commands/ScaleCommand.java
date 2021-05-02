package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Klasa koja predstavlja komandu skaliranja.
 * @author Andrea
 *
 */
public class ScaleCommand implements Command {
	double factor;
	/**
	 * Konstrukor koji prima vrijednost varijable faktor skaliranja.
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setShift(state.getShift() * factor);

	}

}
