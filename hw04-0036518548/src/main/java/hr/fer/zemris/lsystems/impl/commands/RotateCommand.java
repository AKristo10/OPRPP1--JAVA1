package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Klasa koja predstavlja komandu za rotaciju kornjacice.
 * @author Andrea
 *
 */
public class RotateCommand implements Command {

	private double angle;
	/**
	 * Konstruktor koji prima vrijednost kuta.
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle * ( Math.PI / (double)180);
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.getDirection().rotate(angle);
	}

}
